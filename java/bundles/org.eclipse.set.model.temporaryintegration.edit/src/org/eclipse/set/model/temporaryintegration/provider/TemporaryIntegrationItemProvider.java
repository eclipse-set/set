/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

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

import org.eclipse.set.model.simplemerge.SimplemergeFactory;

import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TemporaryIntegrationItemProvider 
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemporaryIntegrationItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addPrimaryPlanningFilenamePropertyDescriptor(object);
			addPrimaryPlanningWasValidPropertyDescriptor(object);
			addSecondaryPlanningFilenamePropertyDescriptor(object);
			addSecondaryPlanningWasValidPropertyDescriptor(object);
			addIntegrationDirectoryPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Primary Planning Filename feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPrimaryPlanningFilenamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TemporaryIntegration_primaryPlanningFilename_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TemporaryIntegration_primaryPlanningFilename_feature", "_UI_TemporaryIntegration_type"),
				 TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Primary Planning Was Valid feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPrimaryPlanningWasValidPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TemporaryIntegration_primaryPlanningWasValid_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TemporaryIntegration_primaryPlanningWasValid_feature", "_UI_TemporaryIntegration_type"),
				 TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Secondary Planning Filename feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondaryPlanningFilenamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TemporaryIntegration_secondaryPlanningFilename_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TemporaryIntegration_secondaryPlanningFilename_feature", "_UI_TemporaryIntegration_type"),
				 TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Secondary Planning Was Valid feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondaryPlanningWasValidPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TemporaryIntegration_secondaryPlanningWasValid_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TemporaryIntegration_secondaryPlanningWasValid_feature", "_UI_TemporaryIntegration_type"),
				 TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Integration Directory feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIntegrationDirectoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TemporaryIntegration_integrationDirectory_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TemporaryIntegration_integrationDirectory_feature", "_UI_TemporaryIntegration_type"),
				 TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__PRIMARY_PLANNING);
			childrenFeatures.add(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__SECONDARY_PLANNING);
			childrenFeatures.add(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPOSITE_PLANNING);
			childrenFeatures.add(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE);
			childrenFeatures.add(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns TemporaryIntegration.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TemporaryIntegration"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((TemporaryIntegration)object).getPrimaryPlanningFilename();
		return label == null || label.length() == 0 ?
			getString("_UI_TemporaryIntegration_type") :
			getString("_UI_TemporaryIntegration_type") + " " + label;
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(TemporaryIntegration.class)) {
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__PRIMARY_PLANNING:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__SECONDARY_PLANNING:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__COMPOSITE_PLANNING:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE:
			case TemporaryintegrationPackage.TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__PRIMARY_PLANNING,
				 org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory.eINSTANCE.createPlanPro_Schnittstelle()));

		newChildDescriptors.add
			(createChildParameter
				(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__SECONDARY_PLANNING,
				 org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory.eINSTANCE.createPlanPro_Schnittstelle()));

		newChildDescriptors.add
			(createChildParameter
				(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPOSITE_PLANNING,
				 org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory.eINSTANCE.createPlanPro_Schnittstelle()));

		newChildDescriptors.add
			(createChildParameter
				(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE,
				 SimplemergeFactory.eINSTANCE.createSComparison()));

		newChildDescriptors.add
			(createChildParameter
				(TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE,
				 SimplemergeFactory.eINSTANCE.createSComparison()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__PRIMARY_PLANNING ||
			childFeature == TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__SECONDARY_PLANNING ||
			childFeature == TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPOSITE_PLANNING ||
			childFeature == TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE ||
			childFeature == TemporaryintegrationPackage.Literals.TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return TemporaryIntegrationEditPlugin.INSTANCE;
	}

}
