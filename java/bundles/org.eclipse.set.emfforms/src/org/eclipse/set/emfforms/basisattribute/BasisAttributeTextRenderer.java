/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.scheidtbachmann.planpro.model.model1902.BasisTypen.BasisAttribut_AttributeGroup;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.helpmessage.HelpMessageService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.emfforms.controls.ButtonControl;
import org.eclipse.set.emfforms.utils.RendererContextImpl;
import org.eclipse.set.utils.ButtonAction;
import org.eclipse.set.utils.emfforms.Annotations;
import org.eclipse.set.utils.emfforms.Renderers;
import org.eclipse.set.utils.emfforms.ToolboxRenderer;

/**
 * Render a {@link BasisAttribut_AttributeGroup} text element.
 * 
 * <ul>
 * <li>A {@link Text} {@link Consumer} can be registered with the key
 * <b>textConsumer</b> and provided by
 * {@link ToolboxViewModelService#get(String)}.</li>
 * <li>An optional Button can be rendered and registered with a
 * {@link ButtonAction} with the key <b>buttonActionKey</b> and provided by
 * {@link ToolboxViewModelService#get(String)}.</li>
 * </ul>
 * <p>
 * 
 * @author Schaefer
 */
public class BasisAttributeTextRenderer extends TextControlSWTRenderer
		implements BasisAttributeRenderer<String>, ToolboxRenderer {

	private static final String DEFAULT_TEXT = ""; //$NON-NLS-1$

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BasisAttributeTextRenderer.class);

	/**
	 * @param type
	 *            the type to test
	 * 
	 * @return whether this renderer can render an element with the given domain
	 *         type
	 */
	public static boolean isApplicable(final EClassifier type) {
		return BasisAttributeSetting.isWertFeatureTypeAssignableTo(type,
				String.class);
	}

	private static Text getTextWidget(final Control textBox) {
		return (Text) ((Composite) textBox).getChildren()[0];
	}

	private final BasisAttributeSetting<String> basisAttributeSetting;

	private final BasisAttributeBinding<String> binding;
	private final HelpMessageService helpMessageService;
	private Optional<Consumer<Text>> optionalTextConsumer;
	private final RendererContextImpl rendererContextImpl;

	final ButtonAction buttonAction;

	final EObject parent;

	Text textWidget;

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
	 * @param emfFormsEditSupport
	 *            The {@link EMFFormsEditSupport}
	 */
	@Inject
	public BasisAttributeTextRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				emfFormsEditSupport);
		LOGGER.debug("Render {}", vElement); //$NON-NLS-1$
		rendererContextImpl = new RendererContextImpl();
		rendererContextImpl.put(VControl.class, getVElement());
		basisAttributeSetting = new BasisAttributeSetting<>(String.class,
				vElement, viewContext);
		parent = basisAttributeSetting.getParent();
		rendererContextImpl.put(EObject.class, parent);
		rendererContextImpl.put(EditingDomain.class,
				basisAttributeSetting.getEditingDomain());
		final EStructuralFeature basisAttributeFeature = basisAttributeSetting
				.getBasisAttributeFeature();
		rendererContextImpl.put(EStructuralFeature.class,
				basisAttributeFeature);
		binding = new BasisAttributeBinding<>(this);

		// help message service
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
		helpMessageService = toolboxViewModelService
				.get(HelpMessageService.class);

		// add text consumer
		final String textConsumerName = Annotations.getViewModelValue(vElement,
				TEXT_CONSUMER);
		if (textConsumerName != null) {
			@SuppressWarnings("unchecked")
			final Consumer<Text> consumer = (Consumer<Text>) toolboxViewModelService
					.get(textConsumerName);
			optionalTextConsumer = Optional.of(consumer);
		} else {
			optionalTextConsumer = Optional.empty();
		}

		// add button
		final String buttonActionKey = Annotations.getViewModelValue(vElement,
				ButtonControl.BUTTON_ACTION_KEY);
		buttonAction = (ButtonAction) toolboxViewModelService
				.get(buttonActionKey);
	}

	@Override
	public void checkToolboxRenderer() {
		checkRenderer();
	}

	@Override
	public BasisAttributeSetting<String> getBasisAttributeSetting() {
		return basisAttributeSetting;
	}

	@Override
	public Control getControl() {
		return textWidget;
	}

	@Override
	public EObject getParent() {
		return parent;
	}

	@Override
	public boolean isDisposed() {
		return Renderers.isDisposed(this);
	}

	@Override
	public void updateControl() {
		final String text = getText();
		if (!text.equals(textWidget.getText())) {
			textWidget.setText(text);
		}
	}

	@Override
	public void updateModel() {
		final String text = textWidget.getText();
		if (isRemoveable(text)) {
			basisAttributeSetting.removeBasisAttribute();
		} else {
			updateBasisAttributeWert(text);
		}
	}

	@Override
	public Optional<String> valueOf(final String representation) {
		return Optional.ofNullable(representation);
	}

	private boolean isRemoveable(final String text) {
		// check for contents to protect Bearbeitungshinweise from being removed
		return text.isEmpty() && !basisAttributeSetting.hasContents();
	}

	private void updateBasisAttributeWert(final String text) {
		if (basisAttributeSetting.updateValue(text)) {
			binding.unbind();
			binding.bind();
			textWidget.setSelection(text.length());
		}
	}

	@Override
	protected Binding[] createBindings(final Control control)
			throws DatabindingFailedException {
		textWidget = getTextWidget(control);
		binding.bind();

		// text consumer
		if (optionalTextConsumer.isPresent()) {
			final Consumer<Text> textConsumer = optionalTextConsumer.get();
			textConsumer.accept(textWidget);
		}

		return new Binding[] {};
	}

	@Override
	protected Control createSWTControl(final Composite parentComposite) {
		if (buttonAction == null) {
			return super.createSWTControl(parentComposite);
		}

		// IMPROVE copy & paste from TextWithButtonControlSWTRenderer

		// the control for the text box and button
		final Composite textWithButtonComposite = new Composite(parentComposite,
				SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2)
				.applyTo(textWithButtonComposite);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.FILL, SWT.BEGINNING)
				.applyTo(textWithButtonComposite);

		// the text box
		final Control textBox = super.createSWTControl(textWithButtonComposite);
		rendererContextImpl.put(Text.class, getTextWidget(textBox));

		// the button parameters
		final String buttonText = buttonAction.getText();
		final int buttonWidth = buttonAction.getWidth();

		// the button
		final Button button = new Button(textWithButtonComposite, SWT.PUSH);
		rendererContextImpl.put(Button.class, button);
		button.setText(buttonText);
		GridDataFactory.swtDefaults().minSize(buttonWidth, 0)
				.hint(buttonWidth, SWT.DEFAULT).applyTo(button);
		buttonAction.register(rendererContextImpl);

		// the action
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				buttonAction.selected(e);
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				buttonAction.selected(e);
			}
		});

		return textBox;
	}

	@Override
	protected String getTextMessage() {
		// IMPROVE IItemPropertyDescriptor - Wir wollen den Weg über das edit
		// Plugin wählen, wenn die Information im Modell vorliegt.
		final String message = helpMessageService
				.getMessage(rendererContextImpl);
		if (message != null) {
			return message;
		}
		return super.getTextMessage();
	}

	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		super.rootDomainModelChanged();
		binding.unbind();
	}

	String getText() {
		return basisAttributeSetting.getWertValue().orElse(DEFAULT_TEXT);
	}
}
