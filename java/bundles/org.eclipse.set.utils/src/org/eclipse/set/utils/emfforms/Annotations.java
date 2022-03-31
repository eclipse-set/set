/**
 * Copyright (c) 2015-2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.emfforms;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.set.utils.StringExtensions;

import com.google.common.collect.Lists;

/**
 * Provides functions to access domain and view model annotations.
 * 
 * @author Schaefer
 */
public class Annotations {

	/**
	 * The key for the default value.
	 */
	public static final String KEY_DEFAULT_VALUE = "Default"; //$NON-NLS-1$

	/**
	 * @param element
	 *            the element
	 * @param source
	 *            name of the annotation source
	 * @param key
	 *            the key
	 * 
	 * @return the value for the given element, source and key
	 */
	public static String getDomainModelValue(final VControl element,
			final String source, final String key) {
		return getDomainModelValue(element.getDomainModelReference(), source,
				key);
	}

	/**
	 * @param domainModelReference
	 *            the domain model reference
	 * @param source
	 *            name of the annotation source
	 * @param key
	 *            the key
	 * 
	 * @return the value for the given element, source and key
	 */
	public static String getDomainModelValue(
			final VDomainModelReference domainModelReference,
			final String source, final String key) {
		if (domainModelReference instanceof VFeaturePathDomainModelReference) {
			final VFeaturePathDomainModelReference featureRef = (VFeaturePathDomainModelReference) domainModelReference;
			final EStructuralFeature feature = featureRef
					.getDomainModelEFeature();
			return getValue(feature, source, key);
		}
		return null;
	}

	/**
	 * @param feature
	 *            the feature
	 * @param source
	 *            name of the annotation source
	 * @param key
	 *            the key
	 * 
	 * @return the value for the given feature, source and key
	 */
	public static String getValue(final EStructuralFeature feature,
			final String source, final String key) {
		final EList<EAnnotation> annotations = feature.getEAnnotations();
		for (final EAnnotation annotation : annotations) {
			if (StringExtensions.nullSafeEquals(annotation.getSource(),
					source)) {
				return annotation.getDetails().get(key);
			}
		}
		return null;
	}

	/**
	 * @param element
	 *            the element
	 * @param key
	 *            the key
	 * 
	 * @return the value for the given element and key
	 */
	public static String getViewModelValue(final VElement element,
			final String key) {
		final EList<VAttachment> attachments = element.getAttachments();
		for (final VAttachment attachment : attachments) {
			if (attachment instanceof VAnnotation) {
				final VAnnotation annotation = (VAnnotation) attachment;
				if (annotation.getKey().equals(key)) {
					return annotation.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * @param element
	 *            the element
	 * @param key
	 *            the key
	 * 
	 * @return the values for the given element and key
	 */
	public static List<String> getViewModelValues(final VCustomControl element,
			final String key) {
		final List<String> result = Lists.newLinkedList();
		final EList<VAttachment> attachments = element.getAttachments();
		for (final VAttachment attachment : attachments) {
			if (attachment instanceof VAnnotation) {
				final VAnnotation annotation = (VAnnotation) attachment;
				if (annotation.getKey().equals(key)) {
					result.add(annotation.getValue());
				}
			}
		}
		return result;
	}

	/**
	 * @param feature
	 *            the feature
	 * @param source
	 *            name of the annotation source
	 * 
	 * @return whether the given feature has an annotation with the given source
	 */
	public static boolean hasDomainModelAnnotation(
			final EStructuralFeature feature, final String source) {
		final EList<EAnnotation> annotations = feature.getEAnnotations();
		for (final EAnnotation annotation : annotations) {
			if (annotation.getSource().equals(source)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param element
	 *            the element
	 * @param source
	 *            name of the annotation source
	 * 
	 * @return whether the given element has an annotation with the given source
	 */
	public static boolean hasDomainModelAnnotation(final VElement element,
			final String source) {
		if (!(element instanceof VControl)) {
			return false;
		}
		final VControl control = (VControl) element;
		final VDomainModelReference modelRef = control
				.getDomainModelReference();
		if (!(modelRef instanceof VFeaturePathDomainModelReference)) {
			return false;
		}
		final EStructuralFeature feature = ((VFeaturePathDomainModelReference) modelRef)
				.getDomainModelEFeature();
		if (feature == null) {
			return false;
		}
		return hasDomainModelAnnotation(feature, source);
	}

	/**
	 * @param element
	 *            the element
	 * @param name
	 *            the name
	 * 
	 * @return whether the element has an annotation with the given name
	 */
	public static boolean hasViewModelAnnotation(final VElement element,
			final String name) {
		final EList<VAttachment> attachments = element.getAttachments();
		for (final VAttachment attachment : attachments) {
			if (attachment instanceof VAnnotation) {
				final VAnnotation annotation = (VAnnotation) attachment;
				if (annotation.getKey().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
}
