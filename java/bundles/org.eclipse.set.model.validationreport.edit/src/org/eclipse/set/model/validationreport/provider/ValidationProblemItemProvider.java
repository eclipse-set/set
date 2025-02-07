/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationreportPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.set.model.validationreport.ValidationProblem} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ValidationProblemItemProvider extends ItemProviderAdapter
		implements IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ValidationProblemItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addIdPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
			addSeverityPropertyDescriptor(object);
			addSeverityTextPropertyDescriptor(object);
			addLineNumberPropertyDescriptor(object);
			addMessagePropertyDescriptor(object);
			addObjectArtPropertyDescriptor(object);
			addAttributeNamePropertyDescriptor(object);
			addObjectScopePropertyDescriptor(object);
			addObjectStatePropertyDescriptor(object);
			addGeneralMsgPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Id feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_id_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_id_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__ID, true,
				false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null,
				null));
	}

	/**
	 * This adds a property descriptor for the Type feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_type_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_type_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__TYPE, true,
				false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
				null));
	}

	/**
	 * This adds a property descriptor for the Severity feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSeverityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_severity_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_severity_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__SEVERITY,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Severity Text feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSeverityTextPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_severityText_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_severityText_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__SEVERITY_TEXT,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Line Number feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLineNumberPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_lineNumber_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_lineNumber_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__LINE_NUMBER,
				true, false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Message feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMessagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_message_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_message_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__MESSAGE,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Object Art feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addObjectArtPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_objectArt_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_objectArt_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__OBJECT_ART,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Attribute Name feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addAttributeNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_attributeName_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_attributeName_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__ATTRIBUTE_NAME,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Object Scope feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addObjectScopePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_objectScope_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_objectScope_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__OBJECT_SCOPE,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Object State feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addObjectStatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_objectState_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_objectState_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__OBJECT_STATE,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the General Msg feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addGeneralMsgPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationProblem_generalMsg_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ValidationProblem_generalMsg_feature",
						"_UI_ValidationProblem_type"),
				ValidationreportPackage.Literals.VALIDATION_PROBLEM__GENERAL_MSG,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This returns ValidationProblem.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
				getResourceLocator().getImage("full/obj16/ValidationProblem"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		ValidationProblem validationProblem = (ValidationProblem) object;
		return getString("_UI_ValidationProblem_type") + " "
				+ validationProblem.getId();
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ValidationProblem.class)) {
			case ValidationreportPackage.VALIDATION_PROBLEM__ID:
			case ValidationreportPackage.VALIDATION_PROBLEM__TYPE:
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY:
			case ValidationreportPackage.VALIDATION_PROBLEM__SEVERITY_TEXT:
			case ValidationreportPackage.VALIDATION_PROBLEM__LINE_NUMBER:
			case ValidationreportPackage.VALIDATION_PROBLEM__MESSAGE:
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_ART:
			case ValidationreportPackage.VALIDATION_PROBLEM__ATTRIBUTE_NAME:
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_SCOPE:
			case ValidationreportPackage.VALIDATION_PROBLEM__OBJECT_STATE:
			case ValidationreportPackage.VALIDATION_PROBLEM__GENERAL_MSG:
				fireNotifyChanged(new ViewerNotification(notification,
						notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ValidationreportEditPlugin.INSTANCE;
	}

}
