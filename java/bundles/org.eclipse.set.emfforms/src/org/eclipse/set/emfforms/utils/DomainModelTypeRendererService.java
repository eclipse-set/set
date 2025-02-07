/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.utils;

import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.set.basis.DomainElementList;
import org.eclipse.set.basis.MediaInfo;
import org.eclipse.set.utils.VDomainModelReferenceExtensions;
import org.eclipse.set.utils.emfforms.Renderers;

/**
 * This abstract renderer service tests for a special domain type to be
 * rendered.
 * 
 * @param <S>
 *            the domain type to check
 * @param <T>
 *            the type of the specified renderer
 * 
 * @author Schaefer
 */
public class DomainModelTypeRendererService<S, T extends AbstractSWTRenderer<VControl>>
		implements EMFFormsDIRendererService<VControl> {

	/**
	 * Describes for which multiplicity the renderer is applicable for.
	 */
	public static enum Multiplicity {
		/**
		 * The renderer is applicable for lists of elements (0..*)
		 */
		MANY,

		/**
		 * The renderer is applicable for individual elements (0..1)
		 */
		ZERO_OR_ONE
	}

	private static final Logger logger = LoggerFactory
			.getLogger(DomainModelTypeRendererService.class);

	/**
	 * @param <S>
	 *            the domain type to check
	 * @param <I>
	 *            the type of the element info
	 * 
	 * @param domainType
	 *            the domain type to check
	 * @param vElement
	 *            The {@link VElement} to check
	 * @param viewModelContext
	 *            The {@link ViewModelContext} to use for the renderer
	 * 
	 * @return the wanted domain element list or <code>null</code>
	 */
	public static <S, I extends MediaInfo<S>> DomainElementList<S, I> getDomainElementList(
			final Class<S> domainType, final VElement vElement,
			final ViewModelContext viewModelContext) {
		return getDomainElementList(domainType, vElement, viewModelContext,
				Optional.empty());
	}

	/**
	 * @param <S>
	 *            the domain type to check
	 * @param <I>
	 *            the type of the element info
	 * 
	 * @param domainType
	 *            the domain type to check
	 * @param vElement
	 *            The {@link VElement} to check
	 * @param viewModelContext
	 *            The {@link ViewModelContext} to use for the renderer
	 * @param additionalTypeTest
	 *            additional test for the domain type
	 * 
	 * @return the wanted domain element list or <code>null</code>
	 */
	public static <S, I extends MediaInfo<S>> DomainElementList<S, I> getDomainElementList(
			final Class<S> domainType, final VElement vElement,
			final ViewModelContext viewModelContext,
			final Optional<Predicate<EClassifier>> additionalTypeTest) {
		if (!VControl.class.isInstance(vElement)) {
			return null;
		}
		final VControl control = (VControl) vElement;
		final VDomainModelReference domainModelReference = control
				.getDomainModelReference();
		if (!(domainModelReference instanceof VFeaturePathDomainModelReference)) {
			return null;
		}
		final VFeaturePathDomainModelReference featurePathDomainModelReference = (VFeaturePathDomainModelReference) domainModelReference;
		final EStructuralFeature domainModelEFeature = featurePathDomainModelReference
				.getDomainModelEFeature();
		if (!(domainModelEFeature instanceof EReference)) {
			return null;
		}
		final EReference domainModelEReference = (EReference) domainModelEFeature;
		if (domainModelEReference.getUpperBound() != -1) {
			return null;
		}
		final EClassifier type = domainModelEReference.getEType();
		if (!additionalTypeTest.map(t -> Boolean.valueOf(t.test(type)))
				.orElse(Boolean.TRUE)
				.booleanValue()) {
			return null;
		}
		final String instanceTypeName = type.getInstanceTypeName();
		final String domainTypeName = domainType.getName();
		if (!instanceTypeName.equals(domainTypeName)) {
			return null;
		}
		final EObject domainModel = viewModelContext.getDomainModel();
		final EObject featureValueContainingTheDomainElement = VDomainModelReferenceExtensions
				.resolve(featurePathDomainModelReference, domainModel);
		return new DomainElementListImpl<>(
				featureValueContainingTheDomainElement,
				featurePathDomainModelReference.getDomainModelEFeature(),
				viewModelContext.getDomainModel().eContainingFeature());
	}

	private final Optional<Predicate<EClassifier>> additionalTypeTest;
	private final EPackage ePackage;
	private final Multiplicity multiplicity;
	private final double priority;
	private final Class<T> rendererType;
	private final Class<S> wantedDomainType;

	/**
	 * @param ePackage
	 *            the package of the wanted domain model
	 * @param wantedDomainType
	 *            the domain type to check
	 * @param rendererType
	 *            the type of the specified renderer
	 * @param priority
	 *            the priority of the specified renderer
	 * @param multiplicity
	 *            the multiplicity the renderer is applicable for
	 */
	public DomainModelTypeRendererService(final EPackage ePackage,
			final Class<S> wantedDomainType, final Class<T> rendererType,
			final double priority, final Multiplicity multiplicity) {
		this(ePackage, wantedDomainType, rendererType, priority, multiplicity,
				null);
	}

	/**
	 * @param ePackage
	 *            the package of the wanted domain model
	 * @param wantedDomainType
	 *            the domain type to check
	 * @param rendererType
	 *            the type of the specified renderer
	 * @param priority
	 *            the priority of the specified renderer
	 * @param multiplicity
	 *            the multiplicity the renderer is applicable for
	 * @param additionalTypeTest
	 *            additional test for the domain type
	 */
	public DomainModelTypeRendererService(final EPackage ePackage,
			final Class<S> wantedDomainType, final Class<T> rendererType,
			final double priority, final Multiplicity multiplicity,
			final Predicate<EClassifier> additionalTypeTest) {
		this.ePackage = ePackage;
		this.wantedDomainType = wantedDomainType;
		this.rendererType = rendererType;
		this.multiplicity = multiplicity;
		this.priority = priority;
		this.additionalTypeTest = Optional.ofNullable(additionalTypeTest);
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VControl>> getRendererClass() {
		return rendererType;
	}

	@Override
	public double isApplicable(final VElement vElement,
			final ViewModelContext viewModelContext) {
		if (multiplicity == Multiplicity.ZERO_OR_ONE
				&& isElementApplicableForType(vElement)) {
			logger.debug("{} is as element applicable for {}", //$NON-NLS-1$
					rendererType.getSimpleName(), vElement.getName());
			return priority;
		}
		if (multiplicity == Multiplicity.MANY
				&& getDomainElementList(wantedDomainType, vElement,
						viewModelContext, additionalTypeTest) != null) {
			logger.debug("{} is as list applicable for {}", //$NON-NLS-1$
					rendererType.getSimpleName(), vElement.getName());
			return priority;
		}
		return NOT_APPLICABLE;
	}

	private boolean isElementApplicableForType(final VElement vElement) {
		if (vElement instanceof VControl) {
			try {
				final Optional<EClassifier> type = Renderers
						.getFeaturePathDomainType(vElement, true);
				if (!type.isPresent()) {
					return false;
				}
				return modelTest(type.get())
						&& wantedDomainType
								.isAssignableFrom(type.get().getInstanceClass())
						&& additionalTypeTest
								.map(t -> Boolean.valueOf(t.test(type.get())))
								.orElse(Boolean.TRUE)
								.booleanValue();
			} catch (final IllegalArgumentException e) {
				return false;
			}
		}
		return false;
	}

	private boolean modelTest(final EClassifier eClassifier) {
		final boolean result = ePackage == eClassifier.getEPackage();
		if (!result) {
			logger.debug(
					"Renderer disqualified because {} does not belong to {} package.", //$NON-NLS-1$
					eClassifier.getName(), ePackage.getName());
		}
		return result;
	}
}
