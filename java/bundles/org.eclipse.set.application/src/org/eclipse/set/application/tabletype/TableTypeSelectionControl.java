/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.tabletype;

import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.set.application.Messages;
import org.eclipse.set.application.toolcontrol.ServiceProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.events.NewTableTypeEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selects the table type for table presentations.
 * 
 * @author Schaefer
 */
public class TableTypeSelectionControl {

	static final Logger LOGGER = LoggerFactory
			.getLogger(TableTypeSelectionControl.class);

	private static class TableTypeLabelProvider extends LabelProvider {

		EnumTranslationService translationService;

		public TableTypeLabelProvider(
				final EnumTranslationService translationService) {
			super();
			this.translationService = translationService;
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof final TableType type) {
				return translationService.translate(type).getPresentation();
			}
			return super.getText(element);
		}
	}

	private final MApplication application;

	private final IEventBroker broker;

	private final EnumTranslationService enumTranslationService;

	@Translation
	private final Messages messages;

	private IModelSession oldSession;

	ToolboxPartService partService;

	private ComboViewer comboViewer;
	private TableType oldSelectedValue;

	/**
	 * @param parent
	 *            the parent
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 */
	public TableTypeSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		application = serviceProvider.application;
		broker = serviceProvider.broker;
		enumTranslationService = serviceProvider.enumTranslationService;
		messages = serviceProvider.messages;
		partService = serviceProvider.partService;
		createTableCombo(parent);
		oldSelectedValue = TableType.DIFF;
	}

	private void createTableCombo(final Composite parent) {
		comboViewer = new ComboViewer(parent);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(
				new TableTypeLabelProvider(enumTranslationService));
		initCombo();
		comboViewer.addSelectionChangedListener(event -> {
			if (event
					.getSelection() instanceof final IStructuredSelection selection
					&& selection
							.getFirstElement() instanceof final TableType selectedType
					&& !oldSelectedValue.equals(selectedType)) {
				oldSelectedValue = selectedType;
				ToolboxEvents.send(broker, new NewTableTypeEvent(selectedType));
			}
		});

		setCombo(getSession());

		// register for session changes
		application.getContext().runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(final IEclipseContext context) {
				setCombo(context.get(IModelSession.class));
				return true;
			}
		});

	}

	private static boolean isPlanning(final IModelSession session) {
		return PlanProSchnittstelleExtensions
				.isPlanning(session.getPlanProSchnittstelle());
	}

	private String getMaxLengthItem() {
		final LinkedList<String> items = new LinkedList<>();
		items.add(messages.TableTypeSelectionControl_noSession);
		items.add(enumTranslationService.translate(TableType.INITIAL)
				.getPresentation());
		items.add(enumTranslationService.translate(TableType.FINAL)
				.getPresentation());
		items.add(enumTranslationService.translate(TableType.SINGLE)
				.getPresentation());

		Collections.sort(items, (o1, o2) -> {
			if (o1.length() > o2.length()) {
				return -1;
			}

			if (o2.length() > o1.length()) {
				return 1;
			}

			return 0;
		});

		return items.getFirst();
	}

	private IModelSession getSession() {
		return application.getContext().get(IModelSession.class);
	}

	// correct size for combo
	private void initCombo() {
		clearCombo();
		comboViewer.add(messages.TableTypeSelectionControl_noSession);
		comboViewer.add(getMaxLengthItem());
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(false);

	}

	private void setCombo(final IModelSession session) {
		if (session == oldSession) {
			return;
		}
		oldSession = session;
		if (session == null) {
			initCombo();
		} else {
			clearCombo();
			if (isPlanning(session)) {
				comboViewer.setInput(new Object[] { TableType.DIFF,
						TableType.INITIAL, TableType.FINAL });
				comboViewer.getCombo().select(0);
				comboViewer.getCombo().setEnabled(true);
			} else {
				comboViewer.add(TableType.SINGLE);
				comboViewer.getCombo().select(0);
				comboViewer.getCombo().setEnabled(false);
				oldSelectedValue = TableType.SINGLE;
			}

		}
	}

	private void clearCombo() {
		comboViewer.getCombo().removeAll();
		comboViewer.getCombo().clearSelection();
		oldSelectedValue = TableType.DIFF;
	}
}
