/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import static org.eclipse.set.utils.VDomainModelReferenceExtensions.VALUE_FIELD_NAME;

import java.util.Optional;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.utils.emfforms.Renderers;
import org.eclipse.set.utils.emfforms.TypedSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes the {@link Setting} of a basis attribute.
 *
 * @param <T>
 *            the type of the Wert field
 * 
 * @author Schaefer
 */
public class BasisAttributeSetting<T> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BasisAttributeSetting.class);

	/**
	 * @param basisAttribute
	 *            the basis attribute
	 * 
	 * @return the Wert-Feature
	 */
	public static EStructuralFeature getWertFeature(
			final EObject basisAttribute) {
		return basisAttribute.eClass().getEStructuralFeature(VALUE_FIELD_NAME);
	}

	/**
	 * @param type
	 *            the type to test
	 * 
	 * @return whether the type has a Wert feature
	 */
	public static boolean hasWertFeature(final EClassifier type) {
		return getWertFeatureFromClassifier(type).isPresent();
	}

	/**
	 * @param <S>
	 *            the wanted type
	 * @param classifier
	 *            the classifier to test
	 * @param wantedType
	 *            the class of the wanted type
	 * 
	 * @return whether a Wert feature type exists and is assignable to the
	 *         given, wanted type
	 */
	public static <S> boolean isWertFeatureTypeAssignableTo(
			final EClassifier classifier, final Class<S> wantedType) {
		return getWertFeatureFromClassifier(classifier)
				.map(f -> Boolean.valueOf(wantedType
						.isAssignableFrom(f.getEType().getInstanceClass())))
				.orElse(Boolean.FALSE).booleanValue();
	}

	private static Optional<EStructuralFeature> getWertFeatureFromClassifier(
			final EClassifier type) {
		if (!(type instanceof EClass)) {
			return Optional.empty();
		}
		return Optional.ofNullable(
				((EClass) type).getEStructuralFeature(VALUE_FIELD_NAME));
	}

	private final EStructuralFeature basisAttributeFeature;
	private final EClassifier domainType;
	private final EditingDomain editingDomain;
	private final EObject parent;
	private final VControl vElement;
	private final ViewModelContext viewModelContext;
	private final EStructuralFeature wertFeature;
	private final Class<T> wertFieldType;

	/**
	 * @param type
	 *            the class of the Wert field type
	 * @param vElement
	 *            the view model element for the basis attribute
	 * @param viewModelContext
	 *            the view model context
	 */
	public BasisAttributeSetting(final Class<T> type, final VControl vElement,
			final ViewModelContext viewModelContext) {
		this.wertFieldType = type;
		this.vElement = vElement;
		this.viewModelContext = viewModelContext;
		domainType = Renderers.getFeaturePathDomainType(vElement, true).get();
		parent = getTypedSetting().getEObject();
		basisAttributeFeature = getTypedSetting().getEStructuralFeature();
		wertFeature = ((EClass) basisAttributeFeature.getEType())
				.getEStructuralFeature(VALUE_FIELD_NAME);
		editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(parent);
	}

	/**
	 * @return the basis attribute feature
	 */
	public EStructuralFeature getBasisAttributeFeature() {
		return basisAttributeFeature;
	}

	/**
	 * @return the editing domain (of the base attribute parent)
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * @return the parent of the basis attribute
	 */
	public EObject getParent() {
		return parent;
	}

	/**
	 * @return the wert feature
	 */
	public EStructuralFeature getWertFeature() {
		return wertFeature;
	}

	/**
	 * @return the optional value of the Wert field
	 */
	public Optional<T> getWertValue() {
		return getTypedSetting().getValue().map(EObject.class::cast)
				.map(o -> o.eGet(wertFeature)).map(wertFieldType::cast);
	}

	/**
	 * @return whether the basis attribute exists and has some contents
	 */
	public boolean hasContents() {
		return getTypedSetting().getValue().map(EObject.class::cast)
				.map(e -> Boolean.valueOf(!e.eContents().isEmpty()))
				.orElse(Boolean.FALSE).booleanValue();
	}

	/**
	 * @return whether the basis attribute is optional
	 */
	public boolean isOptional() {
		return basisAttributeFeature.getLowerBound() == 0;
	}

	/**
	 * Remove the basis attribute.
	 */
	public void removeBasisAttribute() {
		if (parent.eGet(basisAttributeFeature) != null) {
			final Command command = SetCommand.create(editingDomain, parent,
					basisAttributeFeature, null);
			editingDomain.getCommandStack().execute(command);
		}
	}

	/**
	 * Remove the Wert attribute (if present).
	 */
	public void removeWert() {
		final EObject basisAttribute = getBasisAttribute();
		if (basisAttribute != null) {
			if (basisAttribute.eGet(wertFeature) != null) {
				final Command command = SetCommand.create(editingDomain,
						basisAttribute, wertFeature, null);
				editingDomain.getCommandStack().execute(command);
			}
		}
	}

	/**
	 * @param value
	 *            the new value for the basis attribute Wert field
	 * 
	 * @return whether the basis attribute was created on demand
	 */
	public boolean updateValue(final T value) {
		if (getTypedSetting().getValue().isPresent()) {
			updateWithValue(value);
			return false;
		}
		createWithValue(value);
		return true;
	}

	private void createWithValue(final T value) {
		final EClass type = (EClass) basisAttributeFeature.getEType();
		final EObject newBasisAttributeElement = type.getEPackage()
				.getEFactoryInstance().create(type);
		newBasisAttributeElement.eSet(wertFeature, value);
		final Command command = SetCommand.create(editingDomain, parent,
				basisAttributeFeature, newBasisAttributeElement);
		editingDomain.getCommandStack().execute(command);
		Assert.isTrue(getTypedSetting().getValue().isPresent());
	}

	private EObject getBasisAttribute() {
		return getTypedSetting().getEObject();
	}

	private TypedSetting<?> getTypedSetting() {
		return Renderers.getTypedSetting(domainType.getInstanceClass(),
				vElement, viewModelContext);
	}

	private boolean isUpdateNeeded(final T oldValue, final T newValue) {
		if (oldValue == null) {
			return newValue != null;
		}
		return !oldValue.equals(newValue);
	}

	private void updateWithValue(final T value) {
		final Optional<T> wertValue = getWertValue();
		if (isUpdateNeeded(wertValue.orElse(null), value)) {
			LOGGER.debug("oldValue={}", wertValue); //$NON-NLS-1$
			final Command command = SetCommand.create(editingDomain,
					getTypedSetting().getValue().get(), wertFeature, value);
			editingDomain.getCommandStack().execute(command);
		}
	}
}
