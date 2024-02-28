/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.parts;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.application.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * 
 * Part wird angezeigt wenn kein Dokument geladen ist.
 * 
 * @author bleidiessel
 *
 */
public class NoSessionPart {

	@Inject
	@Translation
	Messages messages;

	/**
	 * @param parent
	 *            Das Eltern Composite
	 */
	@PostConstruct
	public void postConstruct(final Composite parent) {

		parent.setLayout(new GridLayout(1, false));
		final Label labelPathDesc = new Label(parent, SWT.NONE);
		labelPathDesc.setText(messages.NoSessionPart_Info);
		labelPathDesc.setLayoutData(
				new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));

	}

}