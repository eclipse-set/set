/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.model.tablemodel.extensions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.model.planpro.BasisTypen.BasisTypenFactory;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.ENUMObjektzustandBesonders;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;

/**
 * Extension for table footnote
 * 
 * @author truong
 */
public class FootnoteExtensions {
	/**
	 * @param ownerObj
	 *            the {@link Ur_Objekt}
	 * @param notes
	 *            the notes of the object
	 */
	public static record WorkNotesUsage(Ur_Objekt ownerObj,
			Set<Bearbeitungsvermerk> notes) {
	}

	/**
	 * Transformation {@link ENUMObjektzustandBesonders} to
	 * {@link Bearbeitungsvermerk} and referenced to
	 * {@link ID_Bearbeitungsvermerk_TypeClass} without GUID for used in table
	 * Foot note
	 * 
	 * @param owner
	 *            the owner object
	 * @return the {@link ID_Bearbeitungsvermerk_TypeClass} without reference
	 *         GUID, only value
	 */
	public static ID_Bearbeitungsvermerk_TypeClass transformObjectStateEnum(
			final Basis_Objekt owner) {
		if (owner == null || owner.getBasisObjektAllg() == null
				|| owner.getBasisObjektAllg()
						.getObjektzustandBesonders() == null) {
			return null;
		}
		return transformObjectStateEnum(owner,
				owner.getBasisObjektAllg()
						.getObjektzustandBesonders()
						.getWert());
	}

	/**
	 * Transformation {@link ENUMObjektzustandBesonders} to
	 * {@link Bearbeitungsvermerk} and referenced to
	 * {@link ID_Bearbeitungsvermerk_TypeClass} without GUID for used in table
	 * Foot note
	 * 
	 * @param owner
	 *            the owner object
	 * 
	 * @param objState
	 *            the {@link ENUMObjektzustandBesonders}
	 * @return the {@link ID_Bearbeitungsvermerk_TypeClass} without reference
	 *         GUID, only value
	 */
	public static ID_Bearbeitungsvermerk_TypeClass transformObjectStateEnum(
			final Basis_Objekt owner,
			final ENUMObjektzustandBesonders objState) {
		if (objState == null) {
			return null;
		}
		final EnumTranslationService enumTranslationService = Services
				.getEnumTranslationService();
		final Bearbeitungsvermerk bearbeitungsvermerk = createBearbeitungsvermerkWithoutGuid(
				enumTranslationService.translate(objState).getPresentation());
		bearbeitungsvermerk.setIdentitaet(
				BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass());
		// Add virtual guid for used later in Compare
		bearbeitungsvermerk.getIdentitaet()
				.setWert(objState.getClass().getName() + "-" //$NON-NLS-1$
						+ owner.getIdentitaet().getWert());
		final ID_Bearbeitungsvermerk_TypeClass idBv = BasisTypenFactory.eINSTANCE
				.createID_Bearbeitungsvermerk_TypeClass();
		idBv.setValue(bearbeitungsvermerk);
		return idBv;

	}

	/**
	 * @param infoText
	 *            the information text
	 * @return the {@link Bearbeitungsvermerk} without guid
	 */
	public static Bearbeitungsvermerk createBearbeitungsvermerkWithoutGuid(
			final String infoText) {
		final Bearbeitungsvermerk bv = BasisobjekteFactory.eINSTANCE
				.createBearbeitungsvermerk();
		final Bearbeitungsvermerk_Allg_AttributeGroup bvAttr = BasisobjekteFactory.eINSTANCE
				.createBearbeitungsvermerk_Allg_AttributeGroup();
		bvAttr.setKommentar(
				BasisobjekteFactory.eINSTANCE.createKommentar_TypeClass());
		bvAttr.getKommentar().setWert(infoText);

		bvAttr.setKurztext(
				BasisobjekteFactory.eINSTANCE.createKurztext_TypeClass());
		bvAttr.getKurztext().setWert(infoText);

		bv.setBearbeitungsvermerkAllg(bvAttr);
		return bv;
	}

	/**
	 * @param table
	 *            the {@link Table}
	 * @return the {@link Ur_Objekt} and the belong {@link Bearbeitungsvermerk}
	 */
	public static Set<WorkNotesUsage> getNotesInTable(final Table table) {
		return TableExtensions.getTableRows(table).stream().map(row -> {
			final Set<Bearbeitungsvermerk> footnotes = FootnoteContainerExtensions
					.getFootnotes(row.getFootnotes())
					.stream()
					.collect(Collectors.toSet());
			if (footnotes.isEmpty()) {
				return null;
			}
			return new WorkNotesUsage(TableRowExtensions.getLeadingObject(row),
					footnotes);
		}).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	/**
	 * Special handle for column C of Sxxx table.
	 *
	 * @param sxxxTable
	 *            the Sxxx table
	 * @param workNotesInAnotherTable
	 *            the {@link WorkNotesUsage} in another table
	 * @param tableName
	 *            the table name of table, which worknote belong to
	 */
	public static void fillSxxxTableColumnC(final Table sxxxTable,
			final Set<WorkNotesUsage> workNotesInAnotherTable,
			final String tableName) {
		final List<TableRow> tableRows = TableExtensions
				.getTableRows(sxxxTable);
		workNotesInAnotherTable.forEach(workNotes -> {
			final Optional<TableRow> rowOpt = tableRows.stream()
					.filter(r -> r.getRowObject() != null)
					.filter(r -> r.getRowObject().equals(workNotes.ownerObj))
					.findFirst();
			if (rowOpt.isEmpty()) {
				// For notes, which not direct in group leading object
				// attachment like Signal_Befestigung, Signal_Begriff,... then
				// fill table name whole rows in group
				TableExtensions.getTableRowGroups(sxxxTable)
						.stream()
						.filter(group -> workNotes.notes()
								.stream()
								.anyMatch(note -> group.getLeadingObject()
										.equals(note)))
						.forEach(group -> group.getRows()
								.forEach(r -> fillValue(r, tableName)));
				return;
			}
			fillValue(rowOpt.get(), tableName);
		});
	}

	private static void fillValue(final TableRow row, final String value) {
		final CellContent content = row.getCells().get(2).getContent();
		if (content == null) {
			final StringCellContent cellContent = TablemodelFactory.eINSTANCE
					.createStringCellContent();
			cellContent.getValue().add(value);
			row.getCells().get(2).setContent(cellContent);
		} else if (content instanceof final StringCellContent stringCellContent) {
			stringCellContent.getValue().add(value);
			stringCellContent.getValue().removeIf(String::isEmpty);
		}
	}
}
