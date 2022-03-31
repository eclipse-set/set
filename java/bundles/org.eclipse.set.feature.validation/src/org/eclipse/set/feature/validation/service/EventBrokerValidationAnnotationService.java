/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.validation.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.validation.AnnotationResult;
import org.eclipse.set.core.services.validation.ValidationAnnotationService;
import org.eclipse.set.feature.validation.report.ReportToAnnotationModelTransformation;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.utils.SetImages;
import org.eclipse.set.utils.events.NewValidationAnnotationEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.graphics.Image;
import org.osgi.service.event.EventHandler;

/**
 * Implementation for {@link ValidationAnnotationService} using the event broker
 * to publish a new annotation model.
 * 
 * @author Schaefer
 */
public class EventBrokerValidationAnnotationService
		implements ValidationAnnotationService {

	@Inject
	private IEventBroker broker;

	private final Image image;

	private final LocalResourceManager localResourceManager;

	private volatile NewValidationAnnotationEvent lastEvent;
	private final EventHandler modelChangedHandler = event -> {
		this.invalidate();
	};

	private static final String VALIDATION_PROBLEM = "Validation Problem"; //$NON-NLS-1$

	/**
	 * Create the service implementation.
	 */
	public EventBrokerValidationAnnotationService() {
		localResourceManager = new LocalResourceManager(
				JFaceResources.getResources());
		image = localResourceManager
				.createImage(SetImages.WARNING_RED_TRANSPARENT);
	}

	@PostConstruct
	private void postConstruct() {
		broker.subscribe(Events.MODEL_CHANGED, modelChangedHandler);
	}

	@PreDestroy
	private void preDestroy() {
		broker.unsubscribe(modelChangedHandler);
	}

	private void invalidate() {
		lastEvent = null;
	}

	@Override
	public void createModel(final Path path,
			final ValidationReport validationReport) {
		CompletableFuture //
				.supplyAsync(() -> supplyEvent(path, validationReport, image))
				.thenApply(this::publishEvent);
	}

	private static NewValidationAnnotationEvent supplyEvent(final Path path,
			final ValidationReport validationReport, final Image image) {
		final IDocument document = createDocument(path);
		final ReportToAnnotationModelTransformation annotationModelTransformation = new ReportToAnnotationModelTransformation(
				document, VALIDATION_PROBLEM, image);
		final IAnnotationModel annotationModel = annotationModelTransformation
				.transform(validationReport);
		return new NewValidationAnnotationEvent(annotationModel, document);
	}

	private static IDocument createDocument(final Path path) {
		try {
			final Document document = new Document();
			document.set(Files.readString(path));
			return document;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Void publishEvent(final NewValidationAnnotationEvent event) {
		this.lastEvent = event;
		ToolboxEvents.send(broker, event);
		return null;
	}

	@Override
	public Optional<AnnotationResult> getResult() {
		return Optional.ofNullable(lastEvent);
	}
}
