/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

/**
 * PlanPro reconcile strategy.
 * 
 * @author Schaefer
 */
public class PlanProReconcileStrategy
		implements IReconcilingStrategy, IReconcilingStrategyExtension {

	private IDocument document;
	private final PlanProFoldingStructureProvider foldingProvider;

	/**
	 * @param viewer
	 *            the projection viewer
	 * @param sync
	 *            UI synchronize
	 */
	public PlanProReconcileStrategy(final ProjectionViewer viewer,
			final UISynchronize sync) {
		foldingProvider = new PlanProFoldingStructureProvider(viewer, sync);
	}

	/**
	 * @return the folding provider
	 */
	public synchronized PlanProFoldingStructureProvider getFoldingProvider() {
		return foldingProvider;
	}

	@Override
	public void initialReconcile() {
		reconcile(new Region(0, document.getLength()));
	}

	@Override
	public void reconcile(final DirtyRegion dirtyRegion,
			final IRegion subRegion) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reconcile(final IRegion partition) {
		foldingProvider.updateFoldingRegions(partition);
	}

	@Override
	public void setDocument(final IDocument document) {
		this.document = document;
		foldingProvider.setDocument(document);
	}

	@Override
	public void setProgressMonitor(final IProgressMonitor monitor) {
		// not used
	}
}
