/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.toolcontrol;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * A icon control, which presentation the status loaded file when the file is
 * invalid incomplete
 * 
 * @author truong
 */
public class LoadedFileStatusControl {
	private final MApplication application;
	private final DialogService dialogService;
	private final Messages msg;
	private final Shell shell;
	private final Button iconButton;
	private Image invalidIconImage;
	private Image incompleteIconImage;
	private static final String INVALID_ICON_URI = "platform:/plugin/org.eclipse.set.utils/icons/warning_red_transparent.png"; //$NON-NLS-1$
	private static final String INCOMPLETE_ICON_URI = "platform:/plugin/org.eclipse.set.utils/icons/warning_yellow_transparent.png"; //$NON-NLS-1$

	/**
	 * @param parent
	 *            the parent
	 * @param provider
	 *            the service provider
	 * @param shell
	 *            the shell
	 */
	public LoadedFileStatusControl(final Composite parent,
			final ServiceProvider provider, final Shell shell) {
		application = provider.application;
		dialogService = provider.dialogService;
		msg = provider.messages;
		this.shell = shell;
		invalidIconImage = loadImage(INVALID_ICON_URI);
		incompleteIconImage = loadImage(INCOMPLETE_ICON_URI);
		iconButton = new Button(parent, SWT.PUSH);
		iconButton.setVisible(false);
		iconButton.addSelectionListener(onButtonClick());
		application.getContext().runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(final IEclipseContext context) {
				final IModelSession session = context.get(IModelSession.class);
				if (session != null && incompleteIconImage != null
						&& invalidIconImage != null) {
					switch (session.getFileValidateState()) {
					case INCOMPLETE: {
						iconButton.setImage(incompleteIconImage);
						iconButton.setVisible(true);
						break;
					}
					case INVALID: {
						iconButton.setImage(invalidIconImage);
						iconButton.setVisible(true);
						break;
					}
					default:
						iconButton.setImage(null);
						iconButton.setVisible(false);
						break;
					}
				} else {
					iconButton.setImage(null);
					iconButton.setVisible(false);
				}
				return true;
			}
		});
		provider.broker.subscribe(Events.CLOSE_SESSION, event -> {
			iconButton.setImage(null);
			iconButton.setVisible(false);
		});
	}

	private SelectionListener onButtonClick() {
		return new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);

			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				final IModelSession session = application.getContext()
						.get(IModelSession.class);
				if (session == null) {
					return;
				}
				switch (session.getFileValidateState()) {
				case INCOMPLETE: {
					dialogService.loadIncompleteModel(shell,
							msg.IncompleteFileDialog_Title,
							msg.IncompleteFileDialog_Message, JFaceResources
									.getString(IDialogLabelKeys.OK_LABEL_KEY));
					break;
				}
				case INVALID: {
					dialogService.loadInvalidModel(shell,
							msg.InvalidFileDialog_Title,
							msg.InvalidFileDialog_Message, JFaceResources
									.getString(IDialogLabelKeys.OK_LABEL_KEY));
					break;
				}
				default:
					break;
				}

			}

		};
	}

	private static Image loadImage(final String uri) {
		try {
			return ImageDescriptor.createFromURL(new URI(uri).toURL())
					.createImage();
		} catch (MalformedURLException | URISyntaxException e) {
			return null;
		}
	}
}
