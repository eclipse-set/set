/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.emfforms;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

import org.eclipse.set.utils.VDomainModelReferenceExtensions;

/**
 * Utilities for renderer.
 * 
 * @author Schaefer
 */
public class Renderers {

	/**
	 * Determine the domain model feature type of an {@link VElement} with a
	 * {@link VFeaturePathDomainModelReference}.
	 * 
	 * @param vElement
	 *            the vElement
	 * @param noLists
	 *            if the upper bound for the element reference is not 1 return
	 *            an empty optional
	 * 
	 * @return the domain type
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>vElement</code> is no {@link VControl} or has no
	 *             {@link VFeaturePathDomainModelReference}
	 */
	public static Optional<EClassifier> getFeaturePathDomainType(
			final VElement vElement, final boolean noLists) {
		if (!VControl.class.isInstance(vElement)) {
			throw new IllegalArgumentException(
					String.format("%s is no VControl", vElement.toString())); //$NON-NLS-1$
		}
		final VControl control = (VControl) vElement;
		final VDomainModelReference domainModelReference = control
				.getDomainModelReference();
		if (!(domainModelReference instanceof VFeaturePathDomainModelReference)) {
			throw new IllegalArgumentException(
					String.format("%s has no VFeaturePathDomainModelReference", //$NON-NLS-1$
							vElement.toString()));
		}
		final VFeaturePathDomainModelReference featurePathDomainModelReference = (VFeaturePathDomainModelReference) domainModelReference;
		final EStructuralFeature domainModelEFeature = featurePathDomainModelReference
				.getDomainModelEFeature();
		final ETypedElement domainModelEReference = domainModelEFeature;
		if (domainModelEFeature == null) {
			return Optional.empty();
		}
		if (noLists && domainModelEReference.getUpperBound() != 1) {
			return Optional.empty();
		}
		return Optional.of(domainModelEFeature.getEType());
	}

	/**
	 * Determine the {@link TypedSetting} for a non-list element.
	 * 
	 * @param <S>
	 *            the domain type to check
	 * @param domainType
	 *            the domain type to check
	 * @param vElement
	 *            The {@link VElement} to check
	 * @param viewModelContext
	 *            The {@link ViewModelContext} to use for the renderer
	 * 
	 * @return the wanted domain element or <code>null</code>
	 */
	public static <S> TypedSetting<S> getTypedSetting(final Class<S> domainType,
			final VElement vElement, final ViewModelContext viewModelContext) {
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
		if (domainModelEFeature == null) {
			return null;
		}
		if (!domainType.isAssignableFrom(
				domainModelEFeature.getEType().getInstanceClass())) {
			return null;
		}
		final EObject domainModel = viewModelContext.getDomainModel();
		final EObject featureValueContainingTheDomainElement = VDomainModelReferenceExtensions
				.resolve(featurePathDomainModelReference, domainModel);
		final S domainElement = domainType
				.cast(featureValueContainingTheDomainElement
						.eGet(domainModelEFeature));
		return new TypedSetting<>(featureValueContainingTheDomainElement,
				domainModelEFeature, Optional.ofNullable(domainElement));
	}

	/**
	 * @param vElementName
	 *            the view element name
	 * @param viewModelContext
	 *            the view model context
	 * 
	 * @return the view element
	 * 
	 * @throws IllegalArgumentException
	 *             if there is no unique element with the given name within the
	 *             view model of the given context
	 */
	public static VElement getVElement(final String vElementName,
			final ViewModelContext viewModelContext) {
		final VElement viewModel = viewModelContext.getViewModel();
		final List<EObject> eAllContents = new LinkedList<>();
		viewModel.eAllContents().forEachRemaining(e -> {
			eAllContents.add(e);
		});
		final List<EObject> matchingElements = eAllContents.stream()
				.filter(e -> isVElementWithName(e, vElementName))
				.collect(Collectors.toList());
		if (matchingElements.size() == 1) {
			return (VElement) matchingElements.get(0);
		}
		throw new IllegalArgumentException(vElementName);
	}

	/**
	 * @param renderer
	 *            the renderer
	 * 
	 * @return whether the renderer is disposed
	 */
	public static boolean isDisposed(final ToolboxRenderer renderer) {
		try {
			renderer.checkToolboxRenderer();
		} catch (final IllegalStateException e) {
			return true;
		}
		return false;
	}

	private static boolean isVElementWithName(final EObject e,
			final String vElementName) {
		if (!(e instanceof VElement)) {
			return false;
		}
		final VElement vElement = (VElement) e;
		return vElementName.equals(vElement.getName());
	}
}
