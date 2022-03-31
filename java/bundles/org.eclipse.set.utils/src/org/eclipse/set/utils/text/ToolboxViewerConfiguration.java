/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.set.basis.constants.TextType;
import org.eclipse.set.utils.ToolboxConfiguration;

/**
 * Configuration for toolbox viewer.
 * 
 * @author Schaefer
 */
public class ToolboxViewerConfiguration extends TextSourceViewerConfiguration {

	private static final String ATTRIBUTE_DEFAULTS = "red,white,normal"; //$NON-NLS-1$
	private static final String DEFAULT_DEFAULTS = "black,white,bold"; //$NON-NLS-1$
	private static final String STRING_DEFAULTS = "dark_magenta,white,bold"; //$NON-NLS-1$
	private static final String TAG_DEFAULTS = "dark_blue,white,normal"; //$NON-NLS-1$

	private static void addDamagerRepairer(
			final PresentationReconciler reconciler,
			final RuleBasedScanner scanner, final String contentType) {
		final DefaultDamagerRepairer damagerRepairer = new DefaultDamagerRepairer(
				scanner);
		reconciler.setDamager(damagerRepairer, contentType);
		reconciler.setRepairer(damagerRepairer, contentType);
	}

	private final Composite parent;
	private PlanProReconcileStrategy strategy;
	private final DescriptionToResourceTransformation stringToTextAttributeTransformation;
	private final UISynchronize sync;

	/**
	 * @param parent
	 *            parent composite for freeing resources
	 * @param stringToTextAttributeTransformation
	 *            the string to text attribute transformation
	 * @param sync
	 *            UI synchronize
	 */
	public ToolboxViewerConfiguration(final Composite parent,
			final DescriptionToResourceTransformation stringToTextAttributeTransformation,
			final UISynchronize sync) {
		this.parent = parent;
		this.stringToTextAttributeTransformation = stringToTextAttributeTransformation;
		this.sync = sync;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			final ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();
		addDamagerRepairer(reconciler, createPlanProScanner(),
				IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

	@Override
	public IReconciler getReconciler(final ISourceViewer sourceViewer) {
		strategy = new PlanProReconcileStrategy((ProjectionViewer) sourceViewer,
				sync);
		return new MonoReconciler(getStrategy(), false);
	}

	private RuleBasedScanner createPlanProScanner() {
		final RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setDefaultReturnToken(getDefaultToken());
		final TagRule tagRule = new TagRule(getTagToken());
		final IRule[] rules = { //
				createStringRule1(), //
				createStringRule2(), //
				tagRule, //
				new AttributeRule(tagRule, getAttributeToken()) //
		};
		scanner.setRules(rules);
		return scanner;
	}

	private IRule createStringRule1() {
		final PatternRule rule = new PatternRule("\"", "\"", getStringToken(), //$NON-NLS-1$ //$NON-NLS-2$
				'\\', true);
		return rule;
	}

	private IRule createStringRule2() {
		final PatternRule rule = new PatternRule("'", "'", getStringToken(), //$NON-NLS-1$ //$NON-NLS-2$
				'\\', true);
		return rule;
	}

	private IToken getAttributeToken() {
		final String textAttributeString = ToolboxConfiguration
				.getTextAttribute(TextType.TOOLBOX_VIEWER_ATTRIBUTES,
						ATTRIBUTE_DEFAULTS, parent);
		final TextAttribute textAttribute = stringToTextAttributeTransformation
				.transformToTextAttribute(textAttributeString);
		return new Token(textAttribute);
	}

	private IToken getDefaultToken() {
		final String textAttributeString = ToolboxConfiguration
				.getTextAttribute(TextType.TOOLBOX_VIEWER_DEFAULTS,
						DEFAULT_DEFAULTS, parent);
		final TextAttribute textAttribute = stringToTextAttributeTransformation
				.transformToTextAttribute(textAttributeString);
		return new Token(textAttribute);
	}

	private synchronized PlanProReconcileStrategy getStrategy() {
		return strategy;
	}

	private IToken getStringToken() {
		final String textAttributeString = ToolboxConfiguration
				.getTextAttribute(TextType.TOOLBOX_VIEWER_STRINGS,
						STRING_DEFAULTS, parent);
		final TextAttribute textAttribute = stringToTextAttributeTransformation
				.transformToTextAttribute(textAttributeString);
		return new Token(textAttribute);
	}

	private IToken getTagToken() {
		final String textAttributeString = ToolboxConfiguration
				.getTextAttribute(TextType.TOOLBOX_VIEWER_TAGS, TAG_DEFAULTS,
						parent);
		final TextAttribute textAttribute = stringToTextAttributeTransformation
				.transformToTextAttribute(textAttributeString);
		return new Token(textAttribute);
	}
}
