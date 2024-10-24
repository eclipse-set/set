/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;

/**
 * A segment on a GEO_Kante with a list of relevant Bereich_Objekte for the
 * GEO_Kante
 * 
 * @author Stuecker
 *
 */
public class GEOKanteSegment {
	protected final Set<Bereich_Objekt> bereichObjekte;
	protected BigDecimal length;
	protected BigDecimal start;

	/**
	 * @param start
	 *            the start of the segment relevant to the top level TOP_Kante
	 * @param length
	 *            the length of the segment
	 */
	public GEOKanteSegment(final BigDecimal start, final BigDecimal length) {
		this.start = start;
		this.length = length;
		this.bereichObjekte = new HashSet<>();
	}

	/**
	 * Copy constructor
	 * 
	 * @param other
	 *            the object to copy from
	 */
	public GEOKanteSegment(final GEOKanteSegment other) {
		this.length = other.length;
		this.start = other.start;
		this.bereichObjekte = new HashSet<>(other.bereichObjekte);
	}

	/**
	 * @return all Bereich_Objekte covering this segment
	 */
	public Set<Bereich_Objekt> getBereichObjekte() {
		return bereichObjekte;
	}

	/**
	 * @return the length of the segment
	 */
	public BigDecimal getLength() {
		return length;
	}

	/**
	 * @return the start of the segment as defined by the distance from the
	 *         start of the original TOP_Kante that was used to determine this
	 *         segment object
	 */
	public BigDecimal getStart() {
		return start;
	}

	/**
	 * @return the end of the segment as defined per {@link #getStart()} +
	 *         {@link #getLength()}
	 */
	public BigDecimal getEnd() {
		return start.add(length);
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(final BigDecimal length) {
		this.length = length;
	}

	/**
	 * @param start
	 *            the start distance to set
	 */
	public void setStart(final BigDecimal start) {
		this.start = start;
	}
}
