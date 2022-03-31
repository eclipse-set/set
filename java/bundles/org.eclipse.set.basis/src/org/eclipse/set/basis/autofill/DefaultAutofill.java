/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.edit.domain.EditingDomain;

import com.google.common.collect.Lists;

/**
 * Default implementation of {@link Autofill}.
 * 
 * @author Schaefer
 */
public class DefaultAutofill implements Autofill {

	private EditingDomain editingDomain;
	private final Consumer<Exception> exceptionHandler;
	private final List<FillInstruction> instructions = Lists.newLinkedList();

	/**
	 * @param exceptionHandler
	 *            the exception handler used when executing instructions
	 */
	public DefaultAutofill(final Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public void addFillingInstruction(final FillInstruction instruction) {
		instructions.add(instruction);
		instruction.setAutofill(this);
	}

	@Override
	public void execute(final FillInstruction instruction) {
		try {
			final FillSetting source = instruction.getSourceSetting();
			final FillSetting target = instruction.getTargetSetting();
			target.setValue(editingDomain, source.getValue());
		} catch (final Exception e) {
			if (exceptionHandler != null) {
				exceptionHandler.accept(e);
			}
			throw e;
		}
	}

	@Override
	public void setEditingDomain(final EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}
}
