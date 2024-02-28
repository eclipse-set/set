/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.radiocontrol;

import jakarta.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.SelectObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.set.basis.exceptions.NoEnumTranslationFound;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A radio renderer can render an Enum type as radio button group
 * 
 * @author Truong
 * 
 *
 */
public class RadioControlRenderer extends SimpleControlSWTControlSWTRenderer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RadioControlRenderer.class);
	private final EnumTranslationService translationService;

	SelectObservableValue<Enumerator> selectedRadioButtonObservable;

	/**
	 * @param vElement
	 *            the view model element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param reportService
	 *            The {@link ReportService}
	 * @param emfFormsDatabinding
	 *            The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider
	 *            The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider
	 *            The {@link VTViewTemplateProvider}
	 */
	@Inject
	public RadioControlRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider);
		translationService = Services.getToolboxViewModelService()
				.getTranslationService();
	}

	@Override
	protected Binding[] createBindings(final Control control)
			throws DatabindingFailedException {
		// IMPROVE analyze suppressed warnings
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Binding binding = getDataBindingContext().bindValue(
				selectedRadioButtonObservable, getModelValue(),
				withPreSetValidation(new UpdateValueStrategy()), null);
		return new Binding[] { binding };
	}

	@Override
	protected Control createSWTControl(final Composite parent)
			throws DatabindingFailedException {
		final Composite radioGroup = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(radioGroup);
		final IValueProperty<?, ?> valueProperty = getEMFFormsDatabinding()
				.getValueProperty(getVElement().getDomainModelReference(),
						getViewModelContext().getDomainModel());
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty
				.getValueType();
		final EEnum eEnum = EEnum.class.cast(structuralFeature.getEType());
		selectedRadioButtonObservable = new SelectObservableValue<>();
		for (final EEnumLiteral literal : eEnum.getELiterals()) {
			final Button button = new Button(radioGroup, SWT.RADIO);
			try {
				button.setText(translationService.translate(literal)
						.getPresentation());
			} catch (final NoEnumTranslationFound e) {
				LOGGER.error(e.getMessage(), e);
				button.setText(literal.getName());
			}

			final IObservableValue<Boolean> observe = WidgetProperties
					.buttonSelection().observe(button);
			selectedRadioButtonObservable.addOption(literal.getInstance(),
					observe);

		}
		return radioGroup;
	}

	@Override
	protected String getUnsetText() {
		return null;
	}

}
