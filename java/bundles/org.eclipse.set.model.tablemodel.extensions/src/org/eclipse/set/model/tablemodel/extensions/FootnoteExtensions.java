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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.model.planpro.BasisTypen.BasisTypenFactory;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.ENUMObjektzustandBesonders;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass;
import org.eclipse.set.model.planpro.Signale.ENUMRahmenArt;
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung;
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen;
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.Footnote;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Extension for table footnote
 * 
 * @author truong
 */
public class FootnoteExtensions {
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
	public static Set<Footnote> getNotesInTable(final Table table) {
		return IterableExtensions.toSet(IterableExtensions
				.flatMap(TableExtensions.getTableRows(table), row -> {
					final List<Footnote> footnotes = FootnoteContainerExtensions
							.getFootnotes(row.getFootnotes());
					return footnotes;
				}));
	}

	/**
	 * Special handle for column C of Sxxx table.
	 *
	 * @param sxxxTable
	 *            the Sxxx table
	 * @param footnotesPerTable
	 *            all the tables with their {@link Footnote}
	 * @param allTablesGenerated
	 *            information if all tables have generated its footnotes yet
	 */
	public static void fillSxxxTableColumnC(final Table sxxxTable,
			final Map<String, Set<Footnote>> footnotesPerTable,
			final boolean allTablesGenerated) {
		final Map<Object, Set<String>> tablesPerOwnerObject = new HashMap<>();
		footnotesPerTable.forEach((table, footnotes) -> {
			footnotes.forEach(footnote -> tablesPerOwnerObject
					.compute(footnote.getOwnerObject(), (key, value) -> {
						Set<String> set = value;
						if (set == null) {
							set = new HashSet<>();
						}
						set.add(table);
						return set;
					}));
		});
		final List<TableRow> tableRows = TableExtensions
				.getTableRows(sxxxTable);
		tableRows.forEach(tableRow -> {
			final Set<String> tables = tablesPerOwnerObject
					.get(tableRow.getRowObject());
			if (tables != null && tables.size() > 0) {
				tables.forEach(table -> fillValue(tableRow, table));
			} else {
				fillValue(tableRow, allTablesGenerated ? "--" : "??");
			}
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

	/**
	 * Generates a prefix for every footnote that is related to the provided
	 * basis objekt.
	 * 
	 * @param obj
	 *            the basis objekt for which the prefix shall be generated
	 * @return null as basis objekte don't have a prefix by default
	 */
	public static String getPrefix(final Basis_Objekt obj) {
		return null;
	}

	/**
	 * Generates a prefix for every footnote that is related to the provided
	 * signal befestigung.
	 * 
	 * @param signalBefestigung
	 *            the signal befestigung for which the prefix shall be generated
	 * @return the prefix or null if not possible to create prefix
	 */
	public static String getPrefix(final Signal_Befestigung signalBefestigung) {
		if (signalBefestigung == null) {
			return null;
		}
		final EnumTranslationService enumTranslationService = Services
				.getEnumTranslationService();
		return enumTranslationService
				.translate(signalBefestigung.getSignalBefestigungAllg()
						.getBefestigungArt()
						.getWert())
				.getPresentation();
	}

	/**
	 * Generates a prefix for every footnote that is related to the provided
	 * signal rahmen.
	 * 
	 * @param signalRahmen
	 *            the signal rahmen for which the prefix shall be generated
	 * @return the prefix or null if not possible to create prefix
	 */
	public static String getPrefix(final Signal_Rahmen signalRahmen) {
		if (signalRahmen == null || signalRahmen.getRahmenArt() == null
				|| signalRahmen.getRahmenArt().getWert() == null) {
			return null;
		}
		final ENUMRahmenArt rahmenArt = signalRahmen.getRahmenArt().getWert();
		final EnumTranslationService enumTranslationService = Services
				.getEnumTranslationService();
		final String prefix = enumTranslationService.translate(rahmenArt)
				.getPresentation();
		if (rahmenArt.equals(ENUMRahmenArt.ENUM_RAHMEN_ART_BLECHTAFEL)
				|| rahmenArt
						.equals(ENUMRahmenArt.ENUM_RAHMEN_ART_ZUSATZANZEIGER)) {
			final List<String> signalBegriffe = SignalRahmenExtensions
					.getSignalbegriffe(signalRahmen)
					.stream()
					.map((signalBegriff) -> getPrefix(signalBegriff, false))
					.filter(begriff -> begriff != null)
					.collect(Collectors.toSet())
					.stream()
					.sorted()
					.toList();
			if (signalBegriffe.size() > 0) {
				return prefix + " " + String.join(", ", signalBegriffe); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return prefix;
	}

	/**
	 * Generates a prefix for every footnote that is related to the provided
	 * signal begriff.
	 * 
	 * @param signalBegriff
	 *            the signal begriff for which the prefix shall be generated
	 * @return the prefix or null if not possible to create prefix
	 */
	public static String getPrefix(final Signal_Signalbegriff signalBegriff) {
		return getPrefix(signalBegriff, true);
	}

	private static String getPrefix(final Signal_Signalbegriff signalBegriff,
			final boolean withSymbol) {
		if (signalBegriff == null
				|| signalBegriff.getSignalbegriffID() == null) {
			return null;
		}
		final Signalbegriff_ID_TypeClass signalBegriffId = signalBegriff
				.getSignalbegriffID();
		// TODO: Properly determine name of Signalbegriff_ID
		final String prefix = signalBegriffId.getClass()
				.getSimpleName()
				.replace("Impl", ""); //$NON-NLS-1$ //$NON-NLS-2$
		if (withSymbol && signalBegriffId.getSymbol() != null) {
			return prefix + " " + signalBegriffId.getSymbol(); //$NON-NLS-1$
		}
		return prefix;
	}

	/**
	 * Adds the given prefix to all the footnotes. Bearbeitungsvermerke of
	 * footnotes are cloned so that the original bearbeitungsvermerke stay
	 * untouched.
	 * 
	 * @param footnotes
	 *            the footnotes to prefix
	 * @param prefix
	 *            the prefix to for the kommentar of bearbeitungsvermerk or null
	 *            if no prefix should be added
	 * @return the extended footnotes
	 */
	public static List<Footnote> withPrefix(final List<Footnote> footnotes,
			final String prefix) {
		if (prefix == null) {
			return footnotes;
		}
		return footnotes.stream().map(footnote -> {
			final Bearbeitungsvermerk bearbeitungsvermerk = createBearbeitungsvermerkWithoutGuid(
					prefix + ": " //$NON-NLS-1$
							+ footnote.getBearbeitungsvermerk()
									.getBearbeitungsvermerkAllg()
									.getKommentar()
									.getWert());
			bearbeitungsvermerk.setIdentitaet(
					BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass());
			bearbeitungsvermerk.getIdentitaet()
					.setWert(footnote.getBearbeitungsvermerk()
							.getIdentitaet()
							.getWert());
			footnote.setBearbeitungsvermerk(bearbeitungsvermerk);
			return footnote;
		}).toList();
	}

	/**
	 * A util class to manage a map of what footnote was coming from what column
	 * for a row.
	 */
	public static class FootnoteColumnReferences {
		private final Map<EObject, String> columnReferences = new HashMap<>();

		/**
		 * 
		 * @param object
		 *            the owner object of the Bearbeitungsvermerk reference
		 * @param column
		 *            the column where the object was used for
		 */
		public void addReference(final EObject object, final String column) {
			this.columnReferences.put(object, column);
		}

		/**
		 * Adds footnote references for the ID_Strecke and Strecke_Km objects.
		 * 
		 * @param po
		 *            the Punkt_Objekt whose strecke and km footnotes shall be
		 *            referenced
		 * @param streckeColumn
		 *            the column for ID_Strecke object
		 * @param kmColumn
		 *            the column for the Strecke_Km object
		 */
		public void addStreckeKm(final Punkt_Objekt po,
				final String streckeColumn, final String kmColumn) {
			po.getPunktObjektStrecke().stream().forEach(pos -> {
				this.addReference(pos.getIDStrecke(), streckeColumn);
				this.addReference(pos.getStreckeKm(), kmColumn);
			});
		}

		/**
		 * Applys column prefix to a footnote if necessary
		 * 
		 * @param footnote
		 *            the footnote to add a prefix
		 */
		public void applyColumnPrefix(final Footnote footnote) {
			if (!columnReferences.containsKey(footnote.getOwnerObject())) {
				return;
			}
			withPrefix(List.of(footnote),
					columnReferences.get(footnote.getOwnerObject()));
		}
	}
}
