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
import java.util.Comparator;
import java.util.LinkedList;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.events.NewTableTypeEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
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

	private static boolean isPlanning(final IModelSession session) {
		return PlanProSchnittstelleExtensions
				.isPlanning(session.getPlanProSchnittstelle());
	}

	@Inject
	private MApplication application;

	@Inject
	private IEventBroker broker;

	private Combo combo;

	@Inject
	private EnumTranslationService enumTranslationService;

	@Inject
	@Translation
	private Messages messages;

	private IModelSession oldSession;

	@Inject
	ToolboxPartService partService;

	private String getMaxLengthItem() {
		final LinkedList<String> items = new LinkedList<>();
		items.add(messages.TableTypeSelectionControl_noSession);
		items.add(enumTranslationService.translate(TableType.INITIAL)
				.getPresentation());
		items.add(enumTranslationService.translate(TableType.FINAL)
				.getPresentation());
		items.add(enumTranslationService.translate(TableType.SINGLE)
				.getPresentation());

		Collections.sort(items, new Comparator<String>() {
			@Override
			public int compare(final String o1, final String o2) {
				if (o1.length() > o2.length()) {
					return -1;
				}

				if (o2.length() > o1.length()) {
					return 1;
				}

				return 0;
			}
		});

		return items.getFirst();
	}

	private IModelSession getSession() {
		return application.getContext().get(IModelSession.class);
	}

	private TableType getTableType() {
		if (isStart()) {
			return TableType.INITIAL;
		}
		if (isZiel()) {
			return TableType.FINAL;
		}
		if (isZustand()) {
			return TableType.SINGLE;
		}
		if (isStartZielVergleich()) {
			return TableType.DIFF;
		}
		throw new IllegalStateException();
	}

	// correct size for combo
	private void initCombo() {
		combo.removeAll();
		combo.add(messages.TableTypeSelectionControl_noSession);
		combo.add(getMaxLengthItem());
		combo.select(0);
		combo.setEnabled(false);
	}

	private boolean isStart() {
		// IMPROVE Apart from the problem we had with the string comparison here
		// in PLANPRO-4338, the technique is a bit unpleasant and depends on
		// different translations for the combo items.
		// Perhaps its possible to store the table type as meta data when using
		// JFace ComboViewer.
		final String text = combo.getText();
		return enumTranslationService.translate(TableType.INITIAL)
				.getPresentation().equals(text);
	}

	private boolean isStartZielVergleich() {
		final String text = combo.getText();
		return enumTranslationService.translate(TableType.DIFF)
				.getPresentation().equals(text);
	}

	private boolean isZiel() {
		final String text = combo.getText();
		return enumTranslationService.translate(TableType.FINAL)
				.getPresentation().equals(text);
	}

	private boolean isZustand() {
		final String text = combo.getText();
		return enumTranslationService.translate(TableType.SINGLE)
				.getPresentation().equals(text);
	}

	@PostConstruct
	private void postConstruct(final Composite parent) {
		combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		initCombo();
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				selectTableType();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				selectTableType();
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

	void selectTableType() {
		ToolboxEvents.send(broker, new NewTableTypeEvent(getTableType()));
	}

	void setCombo(final IModelSession session) {
		if (session == oldSession) {
			return;
		}
		oldSession = session;
		if (session == null) {
			initCombo();
		} else {
			if (isPlanning(session)) {
				combo.removeAll();
				combo.add(enumTranslationService.translate(TableType.DIFF)
						.getPresentation());
				combo.add(enumTranslationService.translate(TableType.INITIAL)
						.getPresentation());
				combo.add(enumTranslationService.translate(TableType.FINAL)
						.getPresentation());
				combo.select(0);
				combo.setEnabled(true);
			} else {
				combo.removeAll();
				combo.add(enumTranslationService.translate(TableType.SINGLE)
						.getPresentation());
				combo.select(0);
				combo.setEnabled(false);
			}
		}
	}
}
