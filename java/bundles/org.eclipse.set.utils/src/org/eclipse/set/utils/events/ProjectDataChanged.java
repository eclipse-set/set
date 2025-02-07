/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import org.eclipse.emf.common.notify.Notification;

import com.google.common.base.MoreObjects;

/**
 * The project data have changed.
 * 
 * @author Schaefer
 */
public class ProjectDataChanged extends DataEvent {

	private static final String SUBTOPIC = "/project/changed"; //$NON-NLS-1$

	private final Notification notification;

	/**
	 * @param notification
	 *            the notification triggering this event
	 */
	public ProjectDataChanged(final Notification notification) {
		this.notification = notification;
	}

	/**
	 * @return the notification triggering this event
	 */
	public Notification getNotification() {
		return notification;
	}

	@Override
	public String getTopic() {
		return super.getBaseTopic() + SUBTOPIC;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("notification", getNotification())
				.toString();
	}
}
