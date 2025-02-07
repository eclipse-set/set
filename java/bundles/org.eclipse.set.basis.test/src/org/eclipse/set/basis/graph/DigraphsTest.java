/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.basis.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.set.basis.graph.testmodel.TestDigraph;
import org.eclipse.set.basis.graph.testmodel.TestEdge;
import org.eclipse.set.basis.graph.testmodel.TestPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.Sets;

/**
 * Tests for {@link Digraphs}.
 * 
 * @author Schaefer
 */
@SuppressWarnings({ "boxing", "hiding", "nls" })
class DigraphsTest {

	private static class TestGetPathsDigraphPP {
		TestDigraph digraph = new TestDigraph();
		Integer start;
		Integer end;
		Set<TestPath> expected = Sets.newHashSet();

		TestGetPathsDigraphPP edge(final String edge, final Integer... points) {
			digraph.addEdge(new TestEdge(edge, points));
			return this;
		}

		TestGetPathsDigraphPP start(final Integer start) {
			this.start = start;
			return this;
		}

		TestGetPathsDigraphPP end(final Integer end) {
			this.end = end;
			return this;
		}

		TestGetPathsDigraphPP expected(final String... edges) {
			final TestPath path = new TestPath();
			Arrays.stream(edges).map(this::expectedEdge).forEach(path::append);
			path.setStart(start);
			path.setEnd(end);
			expected.add(path);
			return this;
		}

		TestEdge expectedEdge(final String edge) {
			return digraph.getEdges()
					.stream()
					.map(TestEdge.class::cast)
					.filter(e -> e.isEdge(edge))
					.findFirst()
					.orElseThrow(() -> new NoSuchElementException(edge));
		}
	}

	private Digraph<String, Character, Integer> digraph;
	private Integer start;
	private Integer end;

	private Set<DirectedEdgePath<String, Character, Integer>> paths;

	@ParameterizedTest
	@MethodSource
	void testGetPathsDigraphPP(final TestGetPathsDigraphPP arguments) {
		givenDigraphAndStartAndEnd(arguments);
		whenGettingPaths();
		thenExpectPaths(arguments.expected);
	}

	private static Stream<TestGetPathsDigraphPP> testGetPathsDigraphPP() {
		return Stream.of( //
				new TestGetPathsDigraphPP() //
						.edge("AB", 1, 2, 3, 4) //
						.start(1)
						.end(3) //
						.expected("AB"), //
				new TestGetPathsDigraphPP() //
						.edge("AB", 1, 2, 3, 4) //
						.edge("BC", 5, 6, 7) //
						.start(2)
						.end(6) //
						.expected("AB", "BC"), //
				new TestGetPathsDigraphPP() //
						.edge("AB", 1, 2, 3, 4) //
						.edge("BC", 5, 6, 7) //
						.start(6)
						.end(2), //
				new TestGetPathsDigraphPP() //
						.edge("AB", 1, 2, 3, 4) //
						.edge("BC", 5, 6, 7) //
						.edge("CD", 8, 9, 10) //
						.edge("BX", 11, 12) //
						.edge("XC", 13, 14) //
						.start(2)
						.end(9) //
						.expected("AB", "BC", "CD") //
						.expected("AB", "BX", "XC", "CD"), //
				new TestGetPathsDigraphPP() //
						.edge("AB", 1, 2, 3, 4) //
						.edge("BC", 5, 6, 7) //
						.edge("CD", 8, 9, 10) //
						.edge("BX", 11, 12) //
						.start(2)
						.end(9) //
						.expected("AB", "BC", "CD") //
		);
	}

	private void givenDigraphAndStartAndEnd(
			final TestGetPathsDigraphPP arguments) {
		digraph = arguments.digraph;
		start = arguments.start;
		end = arguments.end;
	}

	private void whenGettingPaths() {
		paths = Digraphs.getPaths(digraph, start, end);
	}

	private void thenExpectPaths(final Set<TestPath> expected) {
		assertEquals(expected, paths);
	}
}
