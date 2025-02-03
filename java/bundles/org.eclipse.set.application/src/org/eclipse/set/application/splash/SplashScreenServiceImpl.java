/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.splash;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.set.core.services.splash.SplashScreenService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import jakarta.inject.Inject;

/**
 * Implementation for {@link SplashScreenService}.
 * 
 * @author Schaefer
 */
public class SplashScreenServiceImpl implements SplashScreenService {

	private static void createSplash(final Shell shell,
			final ImageDescriptor descriptor) {
		final Label label = new Label(shell, SWT.BORDER);
		final LocalResourceManager localResourceManager = new LocalResourceManager(
				JFaceResources.getResources(), shell);
		final Image image = localResourceManager.create(descriptor);
		label.setImage(image);
		final ProgressBar progressBar = new ProgressBar(shell,
				SWT.INDETERMINATE);
		progressBar.setLayoutData(GridDataFactory.fillDefaults().create());
	}

	private static void setPosition(final Shell shell) {
		final Rectangle displaySize = shell.getMonitor().getBounds();
		final Point shellSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		final int x = (displaySize.width - shellSize.x) / 2;
		final int y = (displaySize.height - shellSize.y) / 2;
		final int width = shellSize.x;
		final int height = shellSize.y;
		shell.setBounds(x, y, width, height);
	}

	private final boolean stopSplash = true;

	@Inject
	IApplicationContext applicationContext;

	@Inject
	IEventBroker eventBroker;

	@Override
	public void show(final ImageDescriptor descriptor) {
		final Shell shell = new Shell(SWT.NO_TRIM);
		shell.setLayout(GridLayoutFactory.swtDefaults().margins(0, 0)
				.spacing(0, 0).create());
		if (stopSplash) {
			eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE,
					new EventHandler() {
						@Override
						public void handleEvent(final Event event) {
							shell.close();
							shell.dispose();
							eventBroker.unsubscribe(this);
						}
					});
		}
		applicationContext.applicationRunning();
		createSplash(shell, descriptor);
		setPosition(shell);
		shell.pack();
		shell.open();
	}
}
