/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.emfforms.text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.version.PlanProVersionService.PlanProVersionFormat;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import jakarta.inject.Inject;

/**
 * Custom control for get planpro/signalbegriff versions IMPROVE: convert EMF
 * Forms view model to java code with SWTWidget
 * 
 * @author truong
 */
public class VersionInfoRenderer extends TextControlSWTRenderer {

	/**
	 * Default constructor.
	 *
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
	 * @param emfFormsEditSupport
	 *            The {@link EMFFormsEditSupport}
	 */
	@Inject
	public VersionInfoRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				emfFormsEditSupport);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Binding bindValue(final Control text,
			final IObservableValue modelValue,
			final DataBindingContext dataBindingContext,
			final UpdateValueStrategy targetToModel,
			final UpdateValueStrategy modelToTarget) {
		final Control controlToObserve = Composite.class.cast(text)
				.getChildren()[0];
		final boolean useOnModifyDatabinding = useOnModifyDatabinding();
		final IObservableValue value;
		if (useOnModifyDatabinding) {
			value = WidgetProperties.text(SWT.Modify)
					.observeDelayed(250, controlToObserve);
		} else {
			value = WidgetProperties.text(SWT.FocusOut)
					.observe(controlToObserve);
		}

		return dataBindingContext.bindValue(value, modelValue, targetToModel,
				new CustomModelToTargetStrategy());
	}

	protected class CustomModelToTargetStrategy extends EMFUpdateValueStrategy {
		@Override
		protected IConverter<?, ?> createConverter(final Object fromType,
				final Object toType) {
			if (fromType == String.class
					&& toType instanceof final EAttribute eAttribute) {
				return stringToEAttribute(fromType, eAttribute);
			}

			if (toType == String.class
					&& fromType instanceof final EAttribute eAttribute) {
				return eAttributeToString(eAttribute, toType);
			}

			return super.createConverter(fromType, toType);
		}

		protected IConverter<?, ?> stringToEAttribute(final Object from,
				final EAttribute to) {
			final EDataType eDataType = to.getEAttributeType();
			final EFactory eFactory = eDataType.getEPackage()
					.getEFactoryInstance();
			return new Converter<>(from, to) {
				@Override
				public Object convert(final Object fromObject) {
					final String value = fromObject == null ? null
							: fromObject.toString();
					if (to.isMany()) {
						final List<Object> result = new ArrayList<>();
						if (value != null) {
							for (final String element : value.split(" ")) { //$NON-NLS-1$
								result.add(eFactory.createFromString(eDataType,
										element));
							}
						}
						return result;
					}
					return eFactory.createFromString(eDataType, value);
				}
			};
		}

		protected IConverter<?, ?> eAttributeToString(final EAttribute from,
				final Object to) {
			final EDataType eDataType = from.getEAttributeType();
			final EFactory eFactory = eDataType.getEPackage()
					.getEFactoryInstance();
			return new Converter<>(from, to) {
				@Override
				public Object convert(final Object fromObject) {
					if (from.isMany()) {
						return ((List<?>) fromObject).stream()
								.map(obj -> eFactory.convertToString(eDataType,
										obj))
								.sorted(PlanProVersionFormat.compareVersion())
								.collect(Collectors.joining(
										ToolboxConstants.VERSION_SEPARATOR));
					}
					return eFactory.convertToString(eDataType, fromObject);
				}
			};
		}
	}

}
