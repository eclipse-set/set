/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer;
import org.eclipse.set.model.tablemodel.Footnote;
import org.eclipse.set.model.tablemodel.FootnoteContainer;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions.FootnoteInfo;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions.FootnoteType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link TableExtensions}.
 * 
 * @author Heine
 */
@SuppressWarnings("nls")
public class TableExtensionsTest {
	@Nested
	@DisplayName("Special cases")
	class SpecialCases {
		@Test
		@DisplayName("No footnote containers available")
		public void testGetAllFootnotesEmpty() {
			final Table table = setupTable(Collections.emptyList());

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(0)));
		}

		@Test
		@DisplayName("Footnote with no Bearbeitungsvermerk")
		public void testGetAllFootnotesWithOneEmptyCommonFootnote() {
			final Table table = setupTable(Arrays
					.asList(createSimpleFootnoteContainer(createFootnote())));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(1)));

			final FootnoteInfo info = footnotes.iterator().next();
			assertFootnoteInfo(info, null, 1, FootnoteType.COMMON_FOOTNOTE);
		}
	}

	@Nested
	@DisplayName("SimpleFootnoteContainer")
	class SimpleFootnoteContainerTests {
		@Test
		@DisplayName("One single footnote container")
		public void testGetAllFootnotesWithOneCommonFootnote() {
			final Bearbeitungsvermerk bv = createBearbeitungsvermerkt("bv-1",
					"bv 1");
			final Table table = setupTable(Arrays
					.asList(createSimpleFootnoteContainer(createFootnote(bv))));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(1)));

			final FootnoteInfo info = footnotes.iterator().next();
			assertFootnoteInfo(info, bv, 1, FootnoteType.COMMON_FOOTNOTE);
		}
	}

	@Nested
	@DisplayName("CompareFootnoteContainer")
	class CompareFootenoteContainerTests {
		@Test
		@DisplayName("One single footnote container with unchanged, new and old footnote")
		public void testGetAllFootnotesWithCompareFootnotes() {
			final Bearbeitungsvermerk bv1 = createBearbeitungsvermerkt("bv-1",
					"bv 1");
			final Bearbeitungsvermerk bv2 = createBearbeitungsvermerkt("bv-2",
					"bv 2");
			final Bearbeitungsvermerk bv3 = createBearbeitungsvermerkt("bv-3",
					"bv 3");
			final Table table = setupTable(
					Arrays.asList(createCompareFootnoteContainer(
							createSimpleFootnoteContainer(createFootnote(bv1)),
							createSimpleFootnoteContainer(createFootnote(bv2)),
							createSimpleFootnoteContainer(
									createFootnote(bv3)))));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(3)));

			final Iterator<FootnoteInfo> infoIt = footnotes.iterator();
			assertFootnoteInfo(infoIt.next(), bv1, 1,
					FootnoteType.COMMON_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv2, 2,
					FootnoteType.NEW_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv3, 3,
					FootnoteType.OLD_FOOTNOTE);
		}
	}

	@Nested
	@DisplayName("CompareTableFootnoteContainer")
	class CompareTableFootnoteContainerTests {
		@Test
		@DisplayName("simple compare planning with one removed and one new footnote")
		public void testGetAllFootnotesWithCompareTableFootnotes() {
			final Bearbeitungsvermerk bv1 = createBearbeitungsvermerkt("bv-1",
					"bv 1");

			final Bearbeitungsvermerk bv2 = createBearbeitungsvermerkt("bv-2",
					"bv 2");
			final Bearbeitungsvermerk bv3 = createBearbeitungsvermerkt("bv-3",
					"bv 3");
			final Table table = setupTable(
					Arrays.asList(createCompareTableFootnoteContainer(
							createSimpleFootnoteContainer(createFootnote(bv1),
									createFootnote(bv2)),
							createSimpleFootnoteContainer(createFootnote(bv1),
									createFootnote(bv3)))));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(2)));

			final Iterator<FootnoteInfo> infoIt = footnotes.iterator();
			assertFootnoteInfo(infoIt.next(), bv1, 1,
					FootnoteType.COMMON_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv2, 2,
					FootnoteType.COMMON_FOOTNOTE, true);
		}
	}

	@Nested
	@DisplayName("Mix of footnote container types")
	class MixedTests {
		@Test
		@DisplayName("combined simple and compare footnote containers")
		public void testGetAllFootnotesWithSimpleAndCompareFootnotes() {
			final Bearbeitungsvermerk bv1 = createBearbeitungsvermerkt("bv-1",
					"bv 1");

			final Bearbeitungsvermerk bv2 = createBearbeitungsvermerkt("bv-2",
					"bv 2");
			final Bearbeitungsvermerk bv3 = createBearbeitungsvermerkt("bv-3",
					"bv 3");
			final Bearbeitungsvermerk bv4 = createBearbeitungsvermerkt("bv-4",
					"bv 4");
			final Table table = setupTable(Arrays.asList(
					createSimpleFootnoteContainer(createFootnote(bv1)),
					createCompareFootnoteContainer(
							createSimpleFootnoteContainer(createFootnote(bv2)),
							createSimpleFootnoteContainer(createFootnote(bv3)),
							createSimpleFootnoteContainer(
									createFootnote(bv4)))));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(4)));

			final Iterator<FootnoteInfo> infoIt = footnotes.iterator();
			assertFootnoteInfo(infoIt.next(), bv1, 1,
					FootnoteType.COMMON_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv2, 2,
					FootnoteType.COMMON_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv3, 3,
					FootnoteType.NEW_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv4, 4,
					FootnoteType.OLD_FOOTNOTE);
		}

		@Test
		@DisplayName("footnote removed in one container of compare planning but still used in another")
		public void testGetAllFootnotesWithCompareTableFootnotesRemovedInOneRow() {
			final Bearbeitungsvermerk bv1 = createBearbeitungsvermerkt("bv-1",
					"bv 1");

			final Bearbeitungsvermerk bv2 = createBearbeitungsvermerkt("bv-2",
					"bv 2");
			final Bearbeitungsvermerk bv3 = createBearbeitungsvermerkt("bv-3",
					"bv 3");
			final Table table = setupTable(Arrays.asList(
					createSimpleFootnoteContainer(createFootnote(bv3)),
					createCompareTableFootnoteContainer(
							createSimpleFootnoteContainer(createFootnote(bv1),
									createFootnote(bv2)),
							createSimpleFootnoteContainer(createFootnote(bv1),
									createFootnote(bv3)))));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(3)));

			final Iterator<FootnoteInfo> infoIt = footnotes.iterator();
			assertFootnoteInfo(infoIt.next(), bv1, 1,
					FootnoteType.COMMON_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv2, 2,
					FootnoteType.COMMON_FOOTNOTE, true);
			assertFootnoteInfo(infoIt.next(), bv3, 3,
					FootnoteType.COMMON_FOOTNOTE);
		}

		@Test
		@DisplayName("footnote not changed in compare planning")
		public void testGetAllFootnotesWithCompareTableFootnotes() {
			final Bearbeitungsvermerk bv1 = createBearbeitungsvermerkt("bv-1",
					"bv 1");

			final Table table = setupTable(Arrays.asList(
					createSimpleFootnoteContainer(createFootnote(bv1)),
					createCompareTableFootnoteContainer(
							createSimpleFootnoteContainer(),
							createSimpleFootnoteContainer())));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(1)));

			final Iterator<FootnoteInfo> infoIt = footnotes.iterator();
			assertFootnoteInfo(infoIt.next(), bv1, 1,
					FootnoteType.COMMON_FOOTNOTE);
		}

		@Test
		@DisplayName("footnotes with overlaps between start-/zielzustand and change in compare planning")
		public void testGetAllFootnotesWithOverlapsBetweenCompareTableFootnotesAndCompareFootnotes() {
			final Bearbeitungsvermerk bv1 = createBearbeitungsvermerkt("bv-1",
					"bv 1");

			final Bearbeitungsvermerk bv2 = createBearbeitungsvermerkt("bv-2",
					"bv 2");
			final Table table = setupTable(Arrays.asList(
					createCompareFootnoteContainer(
							createSimpleFootnoteContainer(createFootnote(bv1)),
							createSimpleFootnoteContainer(),
							createSimpleFootnoteContainer()),
					createCompareTableFootnoteContainer(
							createSimpleFootnoteContainer(createFootnote(bv1),
									createFootnote(bv2)),
							createSimpleFootnoteContainer(
									createFootnote(bv1)))));

			final Iterable<FootnoteInfo> footnotes = TableExtensions
					.getAllFootnotes(table);

			assertThat(footnotes, is(iterableWithSize(2)));

			final Iterator<FootnoteInfo> infoIt = footnotes.iterator();
			assertFootnoteInfo(infoIt.next(), bv1, 1,
					FootnoteType.COMMON_FOOTNOTE);
			assertFootnoteInfo(infoIt.next(), bv2, 2,
					FootnoteType.COMMON_FOOTNOTE, true);
		}
	}

	// ############## utility functions ##############

	private static List<FootnoteContainer> getFootnoteContainer(
			final FootnoteContainer fnContainer) {
		return switch (fnContainer) {
			case final SimpleFootnoteContainer simple:
				yield getFootnoteContainer(simple);
			case final CompareFootnoteContainer compare:
				yield getFootnoteContainer(compare);
			case final CompareTableFootnoteContainer compareTable:
				yield getFootnoteContainer(compareTable);
			default:
				yield Collections.emptyList();
		};
	}

	private static List<FootnoteContainer> getFootnoteContainer(
			final SimpleFootnoteContainer fnContainer) {
		return Arrays.asList(fnContainer);
	}

	private static List<FootnoteContainer> getFootnoteContainer(
			final CompareFootnoteContainer fnContainer) {
		return Arrays.asList(fnContainer);
	}

	private static List<FootnoteContainer> getFootnoteContainer(
			final CompareTableFootnoteContainer fnContainer) {
		final List<FootnoteContainer> fnContainers = new ArrayList<>();
		fnContainers.add(fnContainer);
		if (fnContainer
				.getMainPlanFootnoteContainer() instanceof CompareFootnoteContainer) {
			fnContainers.addAll(getFootnoteContainer(
					fnContainer.getMainPlanFootnoteContainer()));
		}
		if (fnContainer
				.getComparePlanFootnoteContainer() instanceof CompareFootnoteContainer) {
			fnContainers.addAll(getFootnoteContainer(
					fnContainer.getComparePlanFootnoteContainer()));
		}
		return fnContainers;
	}

	private static TreeIterator<EObject> createTreeIterator(
			final List<FootnoteContainer> footnoteContainers) {
		final List<FootnoteContainer> allFootnoteContainers = footnoteContainers
				.stream()
				.flatMap(fC -> getFootnoteContainer(fC).stream())
				.collect(Collectors.toList());
		final Iterator<FootnoteContainer> iterator = allFootnoteContainers
				.iterator();
		return new TreeIterator<>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public EObject next() {
				return iterator.next();
			}

			@Override
			public void prune() {
				throw new UnsupportedOperationException("Not yet implemented");
			}
		};
	}

	private static Bearbeitungsvermerk createBearbeitungsvermerkt(
			final String identitaet, final String kommentar) {
		final Bearbeitungsvermerk bv = BasisobjekteFactory.eINSTANCE
				.createBearbeitungsvermerk();
		bv.setIdentitaet(
				BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass());
		bv.getIdentitaet().setWert(identitaet);
		bv.setBearbeitungsvermerkAllg(BasisobjekteFactory.eINSTANCE
				.createBearbeitungsvermerk_Allg_AttributeGroup());
		bv.getBearbeitungsvermerkAllg()
				.setKommentar(BasisobjekteFactory.eINSTANCE
						.createKommentar_TypeClass());
		bv.getBearbeitungsvermerkAllg().getKommentar().setWert(kommentar);
		return bv;
	}

	private static Footnote createFootnote() {
		return createFootnote(null, null);
	}

	private static Footnote createFootnote(final Bearbeitungsvermerk bv) {
		return createFootnote(bv, null);
	}

	private static Footnote createFootnote(final Bearbeitungsvermerk bv,
			final String referenceColumn) {
		final Footnote footnote = TablemodelFactory.eINSTANCE.createFootnote();
		footnote.setBearbeitungsvermerk(bv);
		footnote.setReferenceColumn(referenceColumn);
		return footnote;
	}

	private static SimpleFootnoteContainer createSimpleFootnoteContainer(
			final Footnote... footnotes) {
		final SimpleFootnoteContainer fnContainer = TablemodelFactory.eINSTANCE
				.createSimpleFootnoteContainer();
		fnContainer.getFootnotes().addAll(Arrays.asList(footnotes));
		return fnContainer;
	}

	private static CompareFootnoteContainer createCompareFootnoteContainer(
			final SimpleFootnoteContainer unchangedFootnotes,
			final SimpleFootnoteContainer newFootnotes,
			final SimpleFootnoteContainer oldFootnotes) {
		final CompareFootnoteContainer fnContainer = TablemodelFactory.eINSTANCE
				.createCompareFootnoteContainer();
		fnContainer.setUnchangedFootnotes(unchangedFootnotes);
		fnContainer.setNewFootnotes(newFootnotes);
		fnContainer.setOldFootnotes(oldFootnotes);
		return fnContainer;
	}

	private static CompareTableFootnoteContainer createCompareTableFootnoteContainer(
			final FootnoteContainer mainPlanFootnotes,
			final FootnoteContainer comparePlanFootnotes) {
		final CompareTableFootnoteContainer fnContainer = TablemodelFactory.eINSTANCE
				.createCompareTableFootnoteContainer();
		fnContainer.setMainPlanFootnoteContainer(mainPlanFootnotes);
		fnContainer.setComparePlanFootnoteContainer(comparePlanFootnotes);
		return fnContainer;
	}

	private static Table setupTable(final List<FootnoteContainer> contents) {
		final Table table = Mockito.mock(Table.class);
		// need to register return 3 times as it is called 3 times in
		// TableExtensions#getAllFootnotes
		when(table.eAllContents()).thenReturn(createTreeIterator(contents))
				.thenReturn(createTreeIterator(contents))
				.thenReturn(createTreeIterator(contents));
		return table;
	}

	private static void assertFootnoteInfo(final FootnoteInfo info,
			final Bearbeitungsvermerk bv, final int index,
			final FootnoteType type) {
		assertFootnoteInfo(info, bv, index, type, false);
	}

	private static void assertFootnoteInfo(final FootnoteInfo info,
			final Bearbeitungsvermerk bv, final int index,
			final FootnoteType type, final boolean changedInCompare) {
		final String kommentar = bv == null ? ""
				: bv.getBearbeitungsvermerkAllg().getKommentar().getWert();
		assertThat("Footnote has wrong Bearbeitungsvermerk",
				info.bearbeitungsvermerk, is(bv));
		assertThat("Footnote has wrong type", info.type, is(type));
		assertThat("Footnote has wrong flag changedInCompare",
				info.changedInCompare, is(changedInCompare));
		assertThat("Footnote has wrong shorthand", info.toShorthand(),
				is("*" + index));
		assertThat("Footnote has wrong text", info.toText(), is(kommentar));
		assertThat("Footnote has wrong reference text", info.toReferenceText(),
				is("*" + index + ": " + kommentar));
	}
}
