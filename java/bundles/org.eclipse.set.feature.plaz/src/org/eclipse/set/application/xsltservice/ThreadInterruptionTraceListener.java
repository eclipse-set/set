/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.xsltservice;

import java.util.Map;

import org.eclipse.set.basis.exceptions.XsltTransformationAbortedException;

import net.sf.saxon.Controller;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.TraceListener;
import net.sf.saxon.om.Item;
import net.sf.saxon.trace.Traceable;

/**
 * Helper class to allow interruption of a Saxon transformation
 * 
 * Saxon originally does not support interruptions, as a workaround this class
 * provides a trace listener which checks whether the current thread was
 * interrupted. If so, a XsltTransformationAbortedException is thrown.
 * 
 * @author Stuecker
 */
class ThreadInterruptionTraceListener implements TraceListener {

	private static void throwIfAborted() {
		if (Thread.currentThread().isInterrupted()) {
			throw new XsltTransformationAbortedException();
		}
	}

	@Override
	public void setOutputDestination(final net.sf.saxon.lib.Logger stream) {
		throwIfAborted();
	}

	@Override
	public void open(final Controller controller) {
		throwIfAborted();
	}

	@Override
	public void close() {
		throwIfAborted();
	}

	@Override
	public void enter(final Traceable instruction,
			final Map<String, Object> properties, final XPathContext context) {
		throwIfAborted();
	}

	@Override
	public void leave(final Traceable instruction) {
		throwIfAborted();
	}

	@Override
	public void startCurrentItem(final Item currentItem) {
		throwIfAborted();
	}

	@Override
	public void endCurrentItem(final Item currentItem) {
		throwIfAborted();
	}
}