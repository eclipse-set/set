/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1

import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.common.util.Enumerator
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.events.TableDataChangeEvent
import org.eclipse.set.utils.table.AbstractTableModelTransformator
import org.eclipse.set.utils.table.Pt1TableChangeProperties
import org.eclipse.set.utils.table.TableError
import org.osgi.service.event.EventAdmin

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CacheUtils.*

abstract class AbstractPlanPro2TableModelTransformator extends AbstractTableModelTransformator<MultiContainer_AttributeGroup> {
	protected val FootnoteTransformation footnoteTransformation = new FootnoteTransformation()
	protected val EnumTranslationService enumTranslationService
	protected val Set<ColumnDescriptor> cols = newHashSet
	protected val EventAdmin eventAdmin
	protected static val String FILL_DELAY_CELL_THREAD = "fillDelayCell"
	protected val List<WaitFillingCell<Ur_Objekt>> delayFillingCells
	protected val Map<TableRow, Set<ColumnDescriptor>> topologicalCells;
	/**
	 * Compares mixed strings groupwise.
	 */
	protected static val Comparator<String> MIXED_STRING_COMPARATOR = ToolboxConstants.
		LST_OBJECT_NAME_COMPARATOR

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super()
		this.enumTranslationService = enumTranslationService
		this.cols.addAll(cols)
		delayFillingCells = Collections.synchronizedList(
			new ArrayList<WaitFillingCell<Ur_Objekt>>)
		this.topologicalCells = newHashMap
		this.eventAdmin = eventAdmin
	}

	protected def void fillFootnotes(TableRow row, Basis_Objekt object) {
		footnoteTransformation.transform(object, row)

		// Ensure a cell exists for the last column to fill footnotes into
		val column = this.cols.filter[columnPosition !== null].sortBy [
			columnPosition
		].last
		val cell = row.getCell(column)
		if (cell.content === null)
			cell.content = TablemodelFactory.eINSTANCE.createStringCellContent
	}

	/**
	 * Translates the value of the field "wert" of the provided basis attribute.
	 * 
	 * @param owner the basis attribute owner
	 * 
	 * @return the translation or <code>null</code>, if the owner is <code>null</code> 
	 * or the owner has no field "wert" or the value of "wert" is neither of type Boolean 
	 * nor of type Enumerator
	 */
	def String translate(BasisAttribut_AttributeGroup owner) {
		try {
			if (owner === null) {
				return null
			}
			val wertField = owner.class.declaredFields.findFirst [
				it.name === "wert"
			]
			if (wertField === null) {
				return null
			}
			wertField.accessible = true
			val wert = wertField.get(owner);
			if (wert instanceof Boolean) {
				return translate(wert);
			}
			if (wert instanceof Enumerator) {
				return enumTranslationService.translate(owner, wert).alternative
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e)
		}

	}

	/**
	 * Translates the boolean via the enum translation service.
	 * 
	 * @param value the value
	 * 
	 * @return the translation or <code>null</code>, if the value is <code>null</code>
	 */
	def String translate(Boolean value) {
		if (value === null) {
			return null
		}
		return enumTranslationService.translate(value).alternative
	}

	def ColumnDescriptor getColumn(Set<ColumnDescriptor> columns, String pos) {
		val column = columns.findFirst [
			columnPosition !== null && columnPosition.equals(pos)
		]
		if (column === null) {
			throw new RuntimeException("Missing column " + pos);
		}
		return column;
	}

	def <S, T extends Ur_Objekt> void fillSingleCellWhenAllowed(
		TableRow row,
		ColumnDescriptor column,
		T object,
		()=>Boolean fillCondition,
		String tableShortcut,
		String threadName,
		(T)=>String filling
	) {
		fillIterableSingleCellWhenAllowed(
			row,
			column,
			object,
			fillCondition,
			[#[filling.apply(it)]],
			null,
			ITERABLE_FILLING_SEPARATOR,
			tableShortcut
		)
	}

	def <S, T extends Ur_Objekt> void fillIterableSingleCellWhenAllowed(
		TableRow row,
		ColumnDescriptor column,
		T object,
		()=>Boolean fillCondition,
		(T)=>List<String> sequence,
		Comparator<String> comparator,
		String separator,
		String tableShortcut
	) {
		try {
			if (fillCondition.apply) {
				fillIterable(row, column, object, sequence, comparator, [
					it
				], separator)
				return
			}
			fill(row, column, object, [CellContentExtensions.HOURGLASS_ICON])
			new Thread([
				val changeProperty = newArrayList
				try {
					while (!fillCondition.apply()) {
						Thread.sleep(5000)
					}
					val result = sequence.apply(object)
					if (comparator === null) {
						result.sortWith(
							ToolboxConstants.LST_OBJECT_NAME_COMPARATOR)
					} else {
						result.sortWith(comparator)
					}
					changeProperty.add(
						new Pt1TableChangeProperties(object.container, row,
							column, result.toList,
							separator.
								nullOrEmpty ? ITERABLE_FILLING_SEPARATOR : separator))
				} catch (Exception e) {
					changeProperty.add(
						fillWaitingCellException(row, column, object.container,
							e))
				}
				val updateValueEvent = new TableDataChangeEvent(
					tableShortcut.toLowerCase, changeProperty)
				TableDataChangeEvent.sendEvent(eventAdmin, updateValueEvent)
			], '''«tableShortcut»/«FILL_DELAY_CELL_THREAD»/«column.label»/«object.cacheKey»''').
				start
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	def <S, T extends Ur_Objekt> void fillIterableMultiCellWhenAllowed(
		TableRow row,
		ColumnDescriptor column,
		T object,
		()=>Boolean fillCondition,
		(T)=>String filling
	) {

		fillIterableMultiCellWhenAllow(row, column, object, fillCondition, [
			#[filling.apply(it)]
		], null, ITERABLE_FILLING_SEPARATOR)
	}

	def <S, T extends Ur_Objekt> void fillIterableMultiCellWhenAllow(
		TableRow row,
		ColumnDescriptor column,
		T object,
		()=>Boolean fillCondition,
		(T)=>List<String> sequence,
		Comparator<String> comparator,
		String separator
	) {
		if (fillCondition.apply) {
			fillIterable(row, column, object, sequence, comparator, [it],
				separator)
			return
		}
		fill(row, column, object, [CellContentExtensions.HOURGLASS_ICON])
		delayFillingCells.add(
			new WaitFillingCell(column, row, object, sequence, fillCondition))
	}

	def void updateWaitingFillCell(String tableShortcut) {
		if (delayFillingCells.nullOrEmpty) {
			return
		}

		new Thread([
			val changeProperties = newArrayList
			while (!delayFillingCells.nullOrEmpty) {
				try {
					delayFillingCells.filter[shouldFill].forEach [
						val container = object.container
						try {
							changeProperties.add(
								new Pt1TableChangeProperties(container, row,
									column, fillValue,
									ITERABLE_FILLING_SEPARATOR))
						} catch (Exception e) {
							changeProperties.add(
								fillWaitingCellException(row, column, container,
									e)
							)
						}

					]
					delayFillingCells.removeIf [ cell |
						changeProperties.exists [ property |
							cell.column === property.changeDataColumn &&
								cell.row == property.row
						]
					]
					Thread.sleep(5000)
				} catch (Exception e) {
					Thread.currentThread.interrupt
					return
				}

			}
			val updateTableEvent = new TableDataChangeEvent(
				tableShortcut.toLowerCase, changeProperties)
			TableDataChangeEvent.sendEvent(eventAdmin, updateTableEvent)
		], '''«tableShortcut»/«FILL_DELAY_CELL_THREAD»''').start
	}

	private def Pt1TableChangeProperties fillWaitingCellException(TableRow row,
		ColumnDescriptor column, MultiContainer_AttributeGroup container,
		Exception e) {
		val errorMsg = createErrorMsg(e, row)
		val leadingObject = row.group.leadingObject
		val errorIdentiefer = getLeadingObjectIdentifier(row, leadingObject?.identitaet?.wert)
		val tableType = container.containerType.defaultTableType
		val error = new TableError(leadingObject, errorIdentiefer, "", errorMsg, row)
		error.tableType = tableType
		tableErrors.add(error)
		return new Pt1TableChangeProperties(container, row, column,
			#['''«ERROR_PREFIX»«errorMsg»'''], ITERABLE_FILLING_SEPARATOR)
	}

	def Map<TableRow, Set<ColumnDescriptor>> getTopologicalCells() {
		return topologicalCells
	}

	protected def void addTopologicalCell(TableRow row, ColumnDescriptor col) {
		if (topologicalCells.containsKey(row)) {
			topologicalCells.get(row).add(col)
		} else {
			topologicalCells.put(row, newHashSet(col))
		}
	}

}
