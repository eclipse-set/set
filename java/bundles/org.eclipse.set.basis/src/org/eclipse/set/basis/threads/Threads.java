/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.threads;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Utilities for Threads.
 * 
 * @author Schaefer
 */
public class Threads {

	protected static final long MONITOR_POLLING_TIME = 500;

	/**
	 * Stops the current thread, if the given monitor is canceled.
	 * 
	 * @param monitor
	 *            the monitor
	 */
	public static void stopCurrentOnCancel(final IProgressMonitor monitor) {
		final Thread thread = Thread.currentThread();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (thread.isAlive()) {
					if (monitor.isCanceled()) {
						thread.interrupt();
					}
					try {
						Thread.sleep(MONITOR_POLLING_TIME);
					} catch (final InterruptedException e) {
						return;
					}
				}

			}
		}).start();
	}
}
