/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.set.model.tablemodel.CompareTableCellContent} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class CompareTableCellContentItemProvider
		extends CellContentItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CompareTableCellContentItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(
			Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(
					TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT);
			childrenFeatures.add(
					TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns CompareTableCellContent.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator()
				.getImage("full/obj16/CompareTableCellContent"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((CompareTableCellContent) object).getSeparator();
		return label == null || label.length() == 0
				? getString("_UI_CompareTableCellContent_type")
				: getString("_UI_CompareTableCellContent_type") + " " + label;
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

		switch (notification.getFeatureID(CompareTableCellContent.class)) {
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT:
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT:
				fireNotifyChanged(new ViewerNotification(notification,
						notification.getNotifier(), true, false));
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

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createStringCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createCompareStateCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createMultiColorCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createCompareTableCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createStringCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createCompareStateCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createMultiColorCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
				TablemodelFactory.eINSTANCE.createCompareTableCellContent()));
	}

	/**
	 * This returns the label text for
	 * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child,
			Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT
				|| childFeature == TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT;

		if (qualify) {
			return getString("_UI_CreateChild_text2",
					new Object[] { getTypeText(childObject),
							getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
