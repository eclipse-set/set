/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.guid;

import java.util.UUID;

/**
 * A GUID with a string representation according to PlanPro rules.
 * 
 * @author Schaefer
 */
public class Guid {

	/**
	 * @return a new PlanPro GUID
	 */
	public static Guid create() {
		return new Guid(UUID.randomUUID());
	}

	/**
	 * @param id
	 *            a string standard representation of a GUID
	 * 
	 * @return a new PlanPro GUID for the given representation
	 */
	public static Guid create(final String id) {
		return new Guid(UUID.fromString(id));
	}

	private final UUID uuid;

	private Guid(final UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the UUID
	 */
	public UUID getUuid() {
		return uuid;
	}

	@Override
	public String toString() {
		return uuid.toString().toUpperCase();
	}
}
