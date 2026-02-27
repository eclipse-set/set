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
package org.eclipse.set.feature.table.pt1.sxxx;

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich;
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.EnumeratorExtensions;
import org.eclipse.set.utils.table.RowFactory;
import org.eclipse.set.utils.table.TMFactory;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Streams;

/**
 * Table transformation for a Bearbeitungsvermerke tabelle
 * 
 * @author truong
 */
public class SxxxTransformator extends AbstractPlanPro2TableModelTransformator {
	/**
	 * @param cols
	 *            the columns descriptor
	 * @param enumTranslationService
	 *            the {@link EnumTranslationService}
	 * @param eventAdmin
	 *            the {@link EventAdmin}
	 */
	public SxxxTransformator(final Set<ColumnDescriptor> cols,
			final EnumTranslationService enumTranslationService,
			final EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Table transformTableContent(
			final MultiContainer_AttributeGroup container,
			final TMFactory factory) {
		final List<ID_Bearbeitungsvermerk_TypeClass> idReferences = Streams
				.stream(container.getAllContents())
				.parallel()
				.filter(ID_Bearbeitungsvermerk_TypeClass.class::isInstance)
				.map(ID_Bearbeitungsvermerk_TypeClass.class::cast)
				.toList();
		for (final Bearbeitungsvermerk bv : container
				.getBearbeitungsvermerk()) {
			final RowFactory rowGroup = factory.newRowGroup(bv);
			final List<EObject> referencedByList = idReferences.stream()
					.parallel()
					.filter(ref -> ref.getValue().equals(bv))
					.map(EObject::eContainer)
					.toList();

			final List<EObject> sonstigeEnumReferee = referencedByList.stream()
					.filter(obj -> obj instanceof final BasisAttribut_AttributeGroup basisAttribut
							&& EnumeratorExtensions
									.isSonstigeEnumWert(basisAttribut))
					.toList();

			if (sonstigeEnumReferee.size() > 0
					&& referencedByList.size() == sonstigeEnumReferee.size()) {
				// bearbeitungsvermerke that are only used at sonstige enum
				// values shall not be displayed at all
				continue;
			}

			if (referencedByList.isEmpty()) {
				final TableRow row = rowGroup.newTableRow();
				// A: Bearbeitungsvermerke inhalt
				fill(row, getColumn(cols, SxxxColumns.Text_Content), bv,
						note -> EObjectExtensions
								.getNullableObject(note,
										e -> e.getBearbeitungsvermerkAllg()
												.getKommentar()
												.getWert())
								.orElse("")); //$NON-NLS-1$
				continue;
			}
			for (final EObject referencedBy : referencedByList) {
				if (Thread.currentThread().isInterrupted()) {
					return null;
				}
				if (sonstigeEnumReferee.contains(referencedBy)) {
					// ignore those referees that are connected to a sonstige
					// enum value
					continue;
				}
				final TableRow row = rowGroup.newTableRow();
				row.setRowObject(referencedBy);

				// A: Bearbeitungsvermerke inhalt
				fill(row, getColumn(cols, SxxxColumns.Text_Content), bv,
						note -> EObjectExtensions
								.getNullableObject(note,
										e -> e.getBearbeitungsvermerkAllg()
												.getKommentar()
												.getWert())
								.orElse("")); //$NON-NLS-1$

				// B: Referenziert von Objects
				fill(row, getColumn(cols, SxxxColumns.Reference_Object), bv,
						note -> getReferenceObjDesignation(referencedBy));

				// C: Ausgabe in Plan
				// Will fill in TableService
			}
		}

		return factory.getTable();
	}

	@SuppressWarnings("nls")
	private static String getReferenceObjDesignation(final EObject refObj) {
		final String typeName = UrObjectExtensions.getTypeName(refObj)
				.replace("_TypeClass", "");
		final String objDesignation = switch (refObj) {
			case final Aussenelementansteuerung aussenelement -> AussenelementansteuerungExtensions
					.getElementBezeichnung(aussenelement);
			case final Bedien_Einrichtung_Oertlich beo -> getNullableObject(beo,
					e -> e.getBezeichnung()
							.getBedienEinrichtOertlBez()
							.getWert()).orElse("");
			case final ESTW_Zentraleinheit estwZentral -> AussenelementansteuerungExtensions
					.getElementBezeichnung(estwZentral);
			case final FMA_Anlage fmaAnlage -> getNullableObject(fmaAnlage,
					fma -> fma.getFMAAnlageKaskade()
							.getFMAKaskadeBezeichnung()
							.getWert()).orElse("");
			case final FMA_Komponente fmaKomponent -> getNullableObject(
					fmaKomponent,
					fma -> fma.getBezeichnung()
							.getBezeichnungTabelle()
							.getWert()).orElse("");
			case final Signal signal -> getNullableObject(signal,
					s -> s.getBezeichnung().getBezeichnungTabelle().getWert())
							.orElse("");
			case final Zugeinwirkung ein -> getNullableObject(ein,
					e -> e.getBezeichnung().getBezeichnungTabelle().getWert())
							.orElse("");
			default -> "";
		};
		if (objDesignation != null && !objDesignation.isEmpty()) {
			return typeName + " " + objDesignation;
		}
		return typeName;
	}
}
