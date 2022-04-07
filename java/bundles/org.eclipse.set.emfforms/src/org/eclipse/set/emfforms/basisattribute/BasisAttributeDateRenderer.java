/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import java.util.Calendar;
import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.XMLDateControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.utils.CalendarTextConversion;
import org.eclipse.set.utils.emfforms.Annotations;
import org.eclipse.set.utils.emfforms.Renderers;
import org.eclipse.set.utils.emfforms.ToolboxRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.set.toolboxmodel.BasisTypen.BasisAttribut_AttributeGroup;

/**
 * Render a {@link BasisAttribut_AttributeGroup} date element.
 * 
 * <ul>
 * <li>A {@link Text} {@link Consumer} can be registered with the key
 * <b>textConsumer</b> and provided by
 * {@link ToolboxViewModelService#get(String)}.</li>
 * </ul>
 * <p>
 * 
 * @author Schaefer
 */
public class BasisAttributeDateRenderer extends XMLDateControlSWTRenderer
		implements ToolboxRenderer,
		BasisAttributeRenderer<XMLGregorianCalendar> {

	private final class DateButtonSelectionListener extends SelectionAdapter {

		private final Button button;

		public DateButtonSelectionListener(final Button button) {
			this.button = button;
		}

		@Override
		public void widgetSelected(final SelectionEvent e) {
			if (dialog != null && !dialog.isDisposed()) {
				dialog.dispose();
				return;
			}
			dialog = new Shell(button.getShell(), SWT.NONE);
			dialog.setLayout(new GridLayout(1, false));

			dateTime = Optional
					.of(new DateTime(dialog, SWT.CALENDAR | SWT.BORDER));
			final XMLGregorianCalendar gregorianCalendar = getModelWert()
					.orElse(null);
			final Calendar cal = Calendar
					.getInstance(localeProvider.getLocale());
			if (gregorianCalendar != null) {
				cal.setTime(gregorianCalendar.toGregorianCalendar().getTime());
			}
			dateTime.get().setDate(cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			final Button okButton = new Button(dialog, SWT.PUSH);
			okButton.setText(
					JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY));
			GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER)
					.grab(false, false).applyTo(okButton);
			okButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(final SelectionEvent e1) {
					updateModelFromDateTime();
					dialog.close();
				}
			});

			dialog.pack();
			dialog.layout();
			final Point dialogSize = dialog.getSize();
			final Rectangle displayBounds = dialog.getDisplay().getBounds();
			final Point buttonLocation = button.toDisplay(button.getSize().x,
					button.getSize().y);

			int dialogX = buttonLocation.x - dialogSize.x;
			int dialogY = buttonLocation.y;
			if (dialogY + dialogSize.y > displayBounds.height) {
				dialogY = dialogY - button.getSize().y - dialogSize.y;
			}
			if (dialogX + dialogSize.x > displayBounds.width) {
				dialogX = dialogX - dialogSize.x;
			} else if (dialogX - dialogSize.x < displayBounds.x) {
				dialogX = buttonLocation.x - button.getSize().x;
			}
			dialog.setLocation(dialogX, dialogY);

			dialog.open();
		}
	}

	private static final String EMPTY_TEXT = ""; //$NON-NLS-1$
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BasisAttributeDateRenderer.class);

	/**
	 * @param type
	 *            the type to test
	 * 
	 * @return whether this renderer can render an element with the given domain
	 *         type
	 */
	public static boolean isApplicable(final EClassifier type) {
		return BasisAttributeSetting.isWertFeatureTypeAssignableTo(type,
				XMLGregorianCalendar.class);
	}

	private final BasisAttributeSetting<XMLGregorianCalendar> basisAttributeSetting;
	private final BasisAttributeBinding<XMLGregorianCalendar> binding;
	private Button dateButton;
	private Text dateText;
	private FocusAdapter focusListener;
	private Optional<Consumer<Text>> optionalTextConsumer;
	private final EObject parent;
	Optional<DateTime> dateTime;
	Shell dialog;
	final EMFFormsLocaleProvider localeProvider;

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
	 * @param localizationService
	 *            The {@link EMFFormsLocalizationService}
	 * @param localeProvider
	 *            The {@link EMFFormsLocaleProvider}
	 * @param imageRegistryService
	 *            The {@link ImageRegistryService}
	 */
	@Inject
	public BasisAttributeDateRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport,
			final EMFFormsLocalizationService localizationService,
			final EMFFormsLocaleProvider localeProvider,
			final ImageRegistryService imageRegistryService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				emfFormsEditSupport, localizationService, localeProvider,
				imageRegistryService);
		LOGGER.debug("Render {}", vElement); //$NON-NLS-1$
		this.localeProvider = localeProvider;
		basisAttributeSetting = new BasisAttributeSetting<>(
				XMLGregorianCalendar.class, vElement, viewContext);
		parent = basisAttributeSetting.getParent();
		binding = new BasisAttributeBinding<>(this);

		// add text consumer
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
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
	}

	@Override
	public void checkToolboxRenderer() {
		checkRenderer();
	}

	@Override
	public BasisAttributeSetting<XMLGregorianCalendar> getBasisAttributeSetting() {
		return basisAttributeSetting;
	}

	@Override
	public Control getControl() {
		return dateText;
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
		final Optional<XMLGregorianCalendar> modelWert = getModelWert();

		// we do not update empty model values (yet) to allow the user to edit
		// invalid values
		if (!modelWert.isPresent() && dateText.isFocusControl()) {
			return;
		}

		if (modelWert.isPresent()) {
			final String modelWertText = CalendarTextConversion
					.getText(modelWert.get());
			final String controlWertText = dateText.getText();

			if (!modelWertText.equals(controlWertText)) {
				// save caret
				final Point selection = dateText.getSelection();

				dateText.setText(modelWertText);

				// restore caret
				dateText.setSelection(selection);

				LOGGER.debug("Updated control text to \"{}\".", modelWertText); //$NON-NLS-1$
			}
		} else {
			clearText();
		}
	}

	@Override
	public void updateModel() {
		final Optional<XMLGregorianCalendar> modelWert = getModelWert();
		final Optional<XMLGregorianCalendar> controlWert = getControlWert();

		if (!modelWert.equals(controlWert)) {
			if (isRemoveable(controlWert)) {
				basisAttributeSetting.removeBasisAttribute();
				LOGGER.debug("Basis attribute removed."); //$NON-NLS-1$
			} else {
				updateBasisAttributeWert(controlWert);
				LOGGER.debug("Updated wert to \"{}\".", //$NON-NLS-1$
						controlWert.orElse(null));
			}
		}
	}

	@Override
	public Optional<XMLGregorianCalendar> valueOf(final String representation) {
		return CalendarTextConversion.getCalendar(representation);
	}

	private Optional<XMLGregorianCalendar> getControlWert() {
		return CalendarTextConversion.getCalendar(dateText.getText());
	}

	private boolean isRemoveable(
			final Optional<XMLGregorianCalendar> controlWert) {
		// check for contents to protect Bearbeitungshinweise from being removed
		return !controlWert.isPresent() && !basisAttributeSetting.hasContents();
	}

	private void updateBasisAttributeWert(
			final Optional<XMLGregorianCalendar> controlWert) {
		if (basisAttributeSetting.updateValue(controlWert.orElse(null))) {
			binding.unbind();
			binding.bind();
		}
	}

	@Override
	protected Binding[] createBindings(final Control control)
			throws DatabindingFailedException {
		// bind date text control
		dateText = (Text) ((Composite) ((Composite) control).getChildren()[0])
				.getChildren()[0];
		binding.bind();

		// update possible invalid (empty) date when leaving the text field
		focusListener = new FocusAdapter() {

			@Override
			public void focusLost(final FocusEvent e) {
				if (!getModelWert().isPresent()) {
					clearText();
				} else {
					updateControl();
				}
			}
		};
		dateText.addFocusListener(focusListener);

		// bind date button control
		dateButton = (Button) ((Composite) control).getChildren()[1];

		// replace listener of XMLDateControlSWTRenderer implementation
		final Listener[] listeners = dateButton.getListeners(SWT.Selection);
		Assert.isTrue(listeners.length == 1);
		final TypedListener listener = (TypedListener) listeners[0];
		final SelectionListener selectionListener = (SelectionListener) listener
				.getEventListener();
		dateButton.removeSelectionListener(selectionListener);
		dateButton.addSelectionListener(
				new DateButtonSelectionListener(dateButton));

		// text consumer
		if (optionalTextConsumer.isPresent()) {
			final Consumer<Text> textConsumer = optionalTextConsumer.get();
			textConsumer.accept(dateText);
		}

		return new Binding[] {};
	}

	@Override
	protected void dispose() {
		super.dispose();
		dateText.removeFocusListener(focusListener);
	}

	void clearText() {
		dateText.setText(EMPTY_TEXT);
		LOGGER.debug("Removed control text."); //$NON-NLS-1$
	}

	Optional<XMLGregorianCalendar> getModelWert() {
		return basisAttributeSetting.getWertValue();
	}

	void updateModelFromDateTime() {
		final Optional<XMLGregorianCalendar> calendar = CalendarTextConversion
				.getCalendar(dateTime.get());
		final Optional<XMLGregorianCalendar> modelWert = getModelWert();

		if (!modelWert.equals(calendar)) {
			if (isRemoveable(calendar)) {
				basisAttributeSetting.removeBasisAttribute();
				LOGGER.debug("Basis attribute removed."); //$NON-NLS-1$
			} else {
				updateBasisAttributeWert(calendar);
				LOGGER.debug("Updated wert to \"{}\".", //$NON-NLS-1$
						calendar.orElse(null));
			}
		}
	}
}
