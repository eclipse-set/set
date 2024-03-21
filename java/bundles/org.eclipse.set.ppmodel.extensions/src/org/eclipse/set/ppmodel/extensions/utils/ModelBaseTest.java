/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.set.ppmodel.extensions.DwegExtensions;
import org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg;
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage;
import org.eclipse.set.model.planpro.PlanPro.DocumentRoot;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.util.PlanProResourceFactoryImpl;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package;

/**
 * Provides common functions for model 1.8.0.0.
 * 
 * @author Schaefer
 */
public class ModelBaseTest {
	private static final String EXTENSION = "xml"; //$NON-NLS-1$

	protected static MultiContainer_AttributeGroup container;

	protected static FMA_Anlage getFMAAnlage(final String name) {
		final Iterable<FMA_Anlage> fmaAnlagen = container.getFMAAnlage();
		FMA_Anlage fmaAnlage = null;
		for (final FMA_Anlage a : fmaAnlagen) {
			if (FmaAnlageExtensions.getTableName(a).contains(name)) {
				fmaAnlage = a;
			}
		}
		return fmaAnlage;
	}

	protected static Fstr_DWeg getFstrDweg(final String name) {
		final Iterable<Fstr_DWeg> fstrDWege = container.getFstrDWeg();
		Fstr_DWeg dweg = null;
		for (final Fstr_DWeg d : fstrDWege) {
			if (DwegExtensions.getFullName(d).equals(name)) {
				dweg = d;
			}
		}
		return dweg;
	}

	protected static void loadPlanProContainer(final String filename) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
				.put(EXTENSION, new PlanProResourceFactoryImpl());
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(PlanProPackage.eNS_URI,
				PlanProPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				Signalbegriffe_Ril_301Package.eNS_URI,
				Signalbegriffe_Ril_301Package.eINSTANCE);

		final Resource resource = resourceSet
				.getResource(URI.createFileURI(filename), true);
		final EObject root = resource.getContents().get(0);
		if (root instanceof DocumentRoot) {
			final DocumentRoot documentRoot = (DocumentRoot) root;
			final PlanPro_Schnittstelle planPro_Schnittstelle = documentRoot
					.getPlanProSchnittstelle();
			container = new MultiContainer_AttributeGroup(
					planPro_Schnittstelle.getLSTZustand().getContainer());
		} else {
			throw new IllegalArgumentException(
					"Ressource contains no PlanPro model with the requested version."); //$NON-NLS-1$
		}
	}
}
