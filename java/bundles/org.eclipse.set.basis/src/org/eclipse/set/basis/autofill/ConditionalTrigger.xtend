/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill

import java.util.List
import java.util.function.BooleanSupplier
import com.google.common.collect.Lists

/** 
 * On calling activate, this trigger only executes its
 * {@link FillInstruction instructions}, if all added conditions are true.
 * 
 * @author Schaefer
 */
class ConditionalTrigger extends DefaultTrigger implements BooleanSupplier {
	
	List<BooleanSupplier> conditions = Lists.newLinkedList

	override void activate() {
		if (getAsBoolean()) {
			super.activate()
		}
	}
	
	def void addCondition(BooleanSupplier condition) {
		conditions.add(condition)
	}

	override boolean getAsBoolean() {
		return conditions.fold(true, [value, condition|value && condition.asBoolean])
	}
}
