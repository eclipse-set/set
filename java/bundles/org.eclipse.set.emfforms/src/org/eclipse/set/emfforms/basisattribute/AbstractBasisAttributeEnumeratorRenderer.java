/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.EnumComboViewerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.set.basis.exceptions.NoEnumTranslationFound;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.utils.emfforms.Renderers;
import org.eclipse.set.utils.emfforms.ToolboxRenderer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup;

/**
 * Render a {@link BasisAttribut_AttributeGroup} enum element.
 * 
 * @param <T>
 *            enumeration type
 * 
 * @author Schaefer
 */
public abstract class AbstractBasisAttributeEnumeratorRenderer<T>
		extends EnumComboViewerSWTRenderer
		implements BasisAttributeRenderer<T>, ToolboxRenderer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractBasisAttributeEnumeratorRenderer.class);

	protected static final String EMPTY_VALUE = "<empty value>"; //$NON-NLS-1$
	protected static final String EMPTY_VALUE_LABEL = ""; //$NON-NLS-1$

	private final BasisAttributeBinding<T> binding;
	private ComboViewer comboViewer;
	private final Class<T> enumerationClass;
	private final EObject parent;
	private final EnumTranslationService translationService;

	protected final BasisAttributeSetting<T> basisAttributeSetting;
	final EMFFormsEditSupport emfFormsEditSupport;

	/**
	 * @param enumerationClass
	 *            the enumeration class
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
	public AbstractBasisAttributeEnumeratorRenderer(
			final Class<T> enumerationClass, final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				emfFormsEditSupport);
		this.enumerationClass = enumerationClass;
		this.emfFormsEditSupport = emfFormsEditSupport;
		basisAttributeSetting = new BasisAttributeSetting<>(enumerationClass,
				vElement, viewContext);
		parent = basisAttributeSetting.getParent();
		binding = new BasisAttributeBinding<>(this);
		translationService = Services.getToolboxViewModelService()
				.getTranslationService();
	}

	@Override
	public void checkToolboxRenderer() {
		checkRenderer();
	}

	@Override
	public Control getControl() {
		return comboViewer.getControl();
	}

	@Override
	public EObject getParent() {
		return parent;
	}

	@Override
	public boolean isDisposed() {
		Renderers.isDisposed(this);
		return false;
	}

	@Override
	public void updateControl() {
		final Optional<T> modelWert = getModelWert();
		final Optional<T> controlWert = getControlWert();

		if (!modelWert.equals(controlWert)) {
			comboViewer.setSelection(toSelection(modelWert));
		}
	}

	@Override
	public void updateModel() {
		final Optional<T> controlWert = getControlWert();
		if (isRemoveable(controlWert)) {
			basisAttributeSetting.removeBasisAttribute();
		} else {
			updateBasisAttributeWert(controlWert);
		}
	}

	private String enumeratorToString(final Enumerator enumConstant) {
		try {
			return translationService.translate(enumConstant).getAlternative();
		} catch (final NoEnumTranslationFound e) {
			LOGGER.error(e.getMessage());
			return enumConstant.getLiteral();
		}
	}

	private Optional<T> getControlWert() {
		final ISelection selection = comboViewer.getSelection();
		if (selection.isEmpty()) {
			return Optional.empty();
		}
		if (!(selection instanceof StructuredSelection)) {
			return Optional.empty();
		}
		final StructuredSelection structuredSelection = (StructuredSelection) selection;
		return objectToEnumerator(structuredSelection.getFirstElement());
	}

	private Optional<T> getModelWert() {
		return basisAttributeSetting.getWertValue();
	}

	private boolean isRemoveable(final Optional<T> controlWert) {
		// check for contents to protect Bearbeitungshinweise from being removed
		return !controlWert.isPresent() && !basisAttributeSetting.hasContents();
	}

	private Optional<T> objectToEnumerator(final Object object) {
		if (enumerationClass.isAssignableFrom(object.getClass())) {
			return Optional.of(enumerationClass.cast(object));
		}
		return Optional.empty();
	}

	private ISelection toSelection(final Optional<T> modelWert) {
		final Object selectedObject = modelWert.map(e -> (Object) e)
				.orElse(EMPTY_VALUE);
		return new StructuredSelection(selectedObject);
	}

	private void updateBasisAttributeWert(final Optional<T> controlWert) {
		if (basisAttributeSetting.updateValue(controlWert.orElse(null))) {
			binding.unbind();
			binding.bind();
		}
	}

	@Override
	protected Binding[] createBindings(final Viewer viewer)
			throws DatabindingFailedException {
		this.comboViewer = (ComboViewer) viewer;
		binding.bind();
		return new Binding[] {};
	}

	@Override
	protected Viewer createJFaceViewer(final Composite parentComposite)
			throws DatabindingFailedException {
		final ComboViewer combo = new ComboViewer(parentComposite);
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return getLabel(element);
			}
		});
		combo.setInput(getInputValues());
		combo.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_enum"); //$NON-NLS-1$
		return combo;
	}

	@Override
	protected void dispose() {
		/*
		 * IMPROVE An dieser Stelle werden einige Element nicht richtig
		 * aufgeräumt. Eigentlich müsste hier folgender Code ausgeführt werden:
		 * super.dispose(); bzw. zur Vermeidung der NPE der (nicht zulässige)
		 * Code: super.super.dispose();
		 * 
		 * Die Klasse EnumComboViewerSWTRenderer hat in einer neueren
		 * ECP-Version das private Attribut pushValue dazu bekommen. Innerhalb
		 * der Methode EnumComboViewerSWTRenderer.dispose() wird auf pushValue
		 * die Methode dispose() aufgerufen. Durch das Überschreiben von
		 * createBindings() wird pushValue jedoch nicht mehr gesetzt und beim
		 * Aufruf von EnumComboViewerSWTRenderer.dispose() gibt es somit eine
		 * NPE.
		 * 
		 * Unter Umständen muss statt dem Ableiten der Klasse
		 * EnumComboViewerSWTRenderer der Code der Klasse in diese Klasse
		 * kopiert werden.
		 */
	}

	protected List<Object> getInputValues() {
		final List<Object> inputValues = new ArrayList<>();
		final EStructuralFeature wertFeature = basisAttributeSetting
				.getWertFeature();
		final EClassifier eType = wertFeature.getEType();
		final String objectName = eType.getName();
		final String enumName = objectName.replace("Object", ""); //$NON-NLS-1$ //$NON-NLS-2$
		final EEnum eEnum = (EEnum) eType.getEPackage()
				.getEClassifier(enumName);
		final Class<?> instanceClass = eEnum.getInstanceClass();
		final Object[] enumConstants = instanceClass.getEnumConstants();
		for (final Object enumConstant : enumConstants) {
			if (enumConstant instanceof Enumerator) {
				inputValues.add(enumConstant);
			} else {
				throw new IllegalArgumentException(enumConstant.toString());
			}
		}
		return inputValues;
	}

	protected String getLabel(final Object element) {
		if (element == EMPTY_VALUE) {
			return EMPTY_VALUE_LABEL;
		}
		return enumeratorToString((Enumerator) element);
	}

}
