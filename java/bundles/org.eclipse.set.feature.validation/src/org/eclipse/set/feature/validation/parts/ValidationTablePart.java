/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.parts;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.feature.validation.report.SessionToValidationReportTransformation;
import org.eclipse.set.feature.validation.table.ValidationTableView;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.utils.emfforms.AbstractEmfFormsPart;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.widgets.Composite;

/**
 * View with the validation table only.
 * 
 * @author Schaefer
 */
public class ValidationTablePart extends AbstractEmfFormsPart<IModelSession> {

	@Inject
	@Translation
	private Messages messages;

	@Inject
	private PlanProVersionService versionService;

	@Inject
	private TableMenuService tableMenuService;
	@Inject
	private EnumTranslationService enumTranslationService;

	/**
	 * Create the part.
	 */
	@Inject
	public ValidationTablePart() {
		super(IModelSession.class);
	}

	@Override
	protected void createFormsView(final Composite parent)
			throws ECPRendererException {
		// create validation report
		final SessionToValidationReportTransformation transformation = new SessionToValidationReportTransformation(
				messages, versionService);
		final ValidationReport validationReport = transformation
				.transform(getModelSession());
		final ValidationTableView tableView = new ValidationTableView(this,
				messages, tableMenuService, enumTranslationService);
		tableView.create(parent, validationReport);
	}
}
