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

import static org.eclipse.set.feature.table.pt1.sxxx.SxxxColumns.*;
import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.sskp.SskpTransformator;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement;
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt;
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.FootnoteExtensions;
import org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions;
import org.eclipse.set.ppmodel.extensions.DwegExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.FahrwegExtensions;
import org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions;
import org.eclipse.set.ppmodel.extensions.PZBElementExtensions;
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
			if (Thread.currentThread().isInterrupted()) {
				return null;
			}
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

			if (!sonstigeEnumReferee.isEmpty()
					&& referencedByList.size() == sonstigeEnumReferee.size()) {
				// bearbeitungsvermerke that are only used at sonstige enum
				// values shall not be displayed at all
				continue;
			}

			if (referencedByList.isEmpty()) {
				final TableRow row = rowGroup.newTableRow();
				fillBearbeitungsvermerkContent(row, bv);
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

				fillBearbeitungsvermerkContent(row, bv);
				final Pair<String, String> refObjInfo = getReferenceObjDesignation(
						referencedBy);
				// C: Referenziert von Objects Art
				fill(row, getColumn(cols, Reference_Object_Art), bv,
						note -> refObjInfo.getFirst());

				// D: Referenziert von Objects Bezeichnung
				fill(row, getColumn(cols, Reference_Object_Bezeichnung), bv,
						note -> refObjInfo.getSecond());

				// E: Ausgabe in Plan
				// Will fill later in TableService

			}
		}

		return factory.getTable();

	}

	private void fillBearbeitungsvermerkContent(final TableRow row,
			final Bearbeitungsvermerk bv) {
		// A: Bearbeitungsvermerke.Kurztext
		fill(row, getColumn(cols, Kurztext_Content), bv,
				note -> EObjectExtensions
						.getNullableObject(note,
								e -> e.getBearbeitungsvermerkAllg()
										.getKurztext()
										.getWert())
						.orElse("")); //$NON-NLS-1$

		// B: Bearbeitungsvermerke inhalt
		fill(row, getColumn(cols, Text_Content), bv,
				note -> EObjectExtensions
						.getNullableObject(note,
								e -> e.getBearbeitungsvermerkAllg()
										.getKommentar()
										.getWert())
						.orElse("")); //$NON-NLS-1$
	}

	@SuppressWarnings("nls")
	private Pair<String, String> getReferenceObjDesignation(
			final EObject refObj) {
		final String typeName = UrObjectExtensions.getTypeName(refObj)
				.replace("_TypeClass", "");
		final String objDesignation = switch (refObj) {
			case final Aussenelementansteuerung aussenelement -> AussenelementansteuerungExtensions
					.getElementBezeichnung(aussenelement);
			case final Bedien_Einrichtung_Oertlich beo -> getRefObjectBezeichnung(
					beo);
			case final ESTW_Zentraleinheit estwZentral -> AussenelementansteuerungExtensions
					.getElementBezeichnung(estwZentral);
			case final FMA_Anlage fmaAnlage -> getRefObjectBezeichnung(
					fmaAnlage);
			case final FMA_Komponente fmaKomponent -> getRefObjectBezeichnung(
					fmaKomponent);
			case final Fstr_DWeg fstrDweg -> getRefObjectBezeichnung(fstrDweg);
			case final Fstr_Zug_Rangier fstrZugR -> getRefObjectBezeichnung(
					fstrZugR);
			case final Markanter_Punkt markanterPunkt -> getRefObjectBezeichnung(
					markanterPunkt);
			case final Stellelement stellelement -> getRefObjectBezeichnung(
					stellelement);
			case final Signal signal -> getRefObjectBezeichnung(signal);
			case final Signal_Signalbegriff signalBegriff -> getRefObjectBezeichnung(
					signalBegriff);
			case final PZB_Element pzb -> getRefObjectBezeichnung(pzb);
			case final Zugeinwirkung ein -> getRefObjectBezeichnung(ein);
			default -> "";
		};
		if (objDesignation != null && !objDesignation.isEmpty()) {
			return new Pair<>(typeName, objDesignation);
		}
		return new Pair<>(typeName, "");
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(
			final Bedien_Einrichtung_Oertlich beo) {
		return getNullableObject(beo,
				e -> e.getBezeichnung().getBedienEinrichtOertlBez().getWert())
						.orElse(""); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(final FMA_Anlage fmaAnlage) {
		return getNullableObject(fmaAnlage,
				fma -> fma.getFMAAnlageKaskade()
						.getFMAKaskadeBezeichnung()
						.getWert()).orElse(""); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(final FMA_Komponente fmaKomponente) {
		return getNullableObject(fmaKomponente,
				fma -> fma.getBezeichnung().getBezeichnungTabelle().getWert())
						.orElse(""); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(final Signal signal) {
		return getNullableObject(signal,
				s -> s.getBezeichnung().getBezeichnungTabelle().getWert())
						.orElse(""); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(final Zugeinwirkung ein) {
		return getNullableObject(ein,
				e -> e.getBezeichnung().getBezeichnungTabelle().getWert())
						.orElse(""); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(
			final Signal_Signalbegriff signalBegriff) {
		return getNullableObject(signalBegriff,
				s -> FootnoteExtensions
						.getSignalBregiffIDName(s.getSignalbegriffID()))
								.orElse(""); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(final Stellelement stellelement) {
		return getNullableObject(stellelement,
				s -> AussenelementansteuerungExtensions
						.getElementBezeichnung(s.getIDInformation().getValue()))
								.orElse(""); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(final Fstr_Zug_Rangier fstrZugR) {
		if (FstrZugRangierExtensions.isR(fstrZugR)) {
			return getNullableObject(fstrZugR,
					fstr -> FstrZugRangierExtensions
							.getRangierFstrBezeichnung(fstr, f -> Boolean.TRUE))
									.orElse(""); //$NON-NLS-1$
		} else if (FstrZugRangierExtensions.isZ(fstrZugR)) {
			return getNullableObject(fstrZugR, fstr -> FstrZugRangierExtensions
					.getZugFstrBezeichnung(fstr, f -> Boolean.TRUE)).orElse(""); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	private String getRefObjectBezeichnung(final Fstr_DWeg fstrDweg) {
		final Fstr_Fahrweg fstrFahrweg = DwegExtensions
				.getFstrFahrweg(fstrDweg);
		final Signal start = FahrwegExtensions.getStart(fstrFahrweg);
		final String zielPunktBezeichung = getRefObjectBezeichnung(
				FahrwegExtensions.getZielPunkt(fstrFahrweg));
		return String.format("%s/%s", getRefObjectBezeichnung(start), //$NON-NLS-1$
				zielPunktBezeichung);

	}

	@SuppressWarnings("static-method")
	private String getRefObjectBezeichnung(
			final Markanter_Punkt markanterPunkt) {
		return getNullableObject(markanterPunkt,
				p -> p.getBezeichnung()
						.getBezeichnungMarkanterPunkt()
						.getWert()).orElse(""); //$NON-NLS-1$
	}

	private String getRefObjectBezeichnung(final PZB_Element pzb) {
		final String bezugpunkBeizechnung = PZBElementExtensions
				.getPZBElementBezugspunkt(pzb)
				.stream()
				.filter(Objects::nonNull)
				.map(SskpTransformator::fillBezugsElement)
				.collect(Collectors.joining(System.lineSeparator()));
		final String wirkfrequenz = getNullableObject(pzb,
				p -> translate(pzb.getPZBArt())).orElse(""); //$NON-NLS-1$
		return String.format("%s - %s", bezugpunkBeizechnung, wirkfrequenz); //$NON-NLS-1$
	}
}
