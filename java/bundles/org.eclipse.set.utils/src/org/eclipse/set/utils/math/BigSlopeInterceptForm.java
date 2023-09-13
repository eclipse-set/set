/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

/**
 * A slope intercept form describes a straight line on a cartesian plane.
 * 
 * @author Schaefer
 */
public class BigSlopeInterceptForm implements UnaryOperator<BigDecimal> {

	private final BigDecimal intercept;
	private final BigDecimal slope;

	/**
	 * @param slope
	 *            the slope
	 * @param intercept
	 *            the y intercept
	 */
	public BigSlopeInterceptForm(final BigDecimal slope,
			final BigDecimal intercept) {
		this.slope = slope;
		this.intercept = intercept;
	}

	@Override
	public BigDecimal apply(final BigDecimal x) {
		return slope.multiply(x).add(intercept);
	}
}
