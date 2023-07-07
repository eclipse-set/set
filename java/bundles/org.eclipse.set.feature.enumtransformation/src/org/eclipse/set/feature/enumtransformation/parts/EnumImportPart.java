/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.enumtransformation.parts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilterBuilder;
import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.eclipse.set.feature.enumtransformation.Messages;
import org.eclipse.set.feature.enumtransformation.impl.EnumTransformation;
import org.eclipse.set.feature.enumtransformation.impl.TranslationCode;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.widgets.FileField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Part for import/export enumeration translations.
 * 
 * @author Schaefer
 */
public class EnumImportPart extends BasePart {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EnumImportPart.class);

	private static final String XLS = "xls"; //$NON-NLS-1$

	private static Path getMessagesFile(final TranslationCode code,
			final Path path) {
		return Paths.get(path.getParent().toString(), code.getMessageFile());
	}

	private static Path getPropertiesFile(final TranslationCode code,
			final Path path) {
		return Paths.get(path.getParent().toString(), code.getPropertiesFile());
	}

	private static void writeToFile(final String content, final Path path,
			final Charset charset) throws IOException {
		try (final FileOutputStream fileOutputStream = new FileOutputStream(
				path.toFile());
				final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						fileOutputStream, charset)) {
			outputStreamWriter.write(content);
		}
	}

	private FileField fileField;

	@Inject
	@Translation
	private Messages messages;

	private Text textField;

	/**
	 * Create the part.
	 */
	public EnumImportPart() {
		super();
	}

	private void addLine(final String text) {
		textField.setText(textField.getText() + text + String.format("%n")); //$NON-NLS-1$
	}

	private List<ToolboxFileFilter> createFileFilter() {
		final ToolboxFileFilter xls = ToolboxFileFilterBuilder
				.forName(messages.EnumImportPart_FileFilterName).add(XLS)
				.filterNameWithFilterList(true).create();
		return Lists.newArrayList(xls);
	}

	private void pathSelected(final Path path) {
		addLine(String.format(messages.EnumImportPart_StartImportPattern,
				path.toString()));
		Map<String, EnumTranslation> translations;
		try {
			translations = EnumTransformation.transform(path);
			final TranslationCode code = EnumTransformation
					.transform(translations);
			writeToFile(code.getMessages(), getMessagesFile(code, path),
					StandardCharsets.UTF_8);
			writeToFile(code.getProperties(), getPropertiesFile(code, path),
					StandardCharsets.UTF_8);
			addLine(messages.EnumImportPart_FinishImport);
		} catch (final Exception e) {
			addLine(e.getMessage());
			final StringWriter stringWriter = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			addLine(stringWriter.toString());
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	protected void createView(final Composite parent) {
		// parent layout
		GridLayoutFactory.fillDefaults().applyTo(parent);

		// file field
		fileField = new FileField(parent, createFileFilter(),
				getDialogService());
		fileField.getButton().setText(messages.EnumImportPart_ImportButtonText);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(fileField.getComposite());
		fileField.addPathListener(this::pathSelected);

		// text field
		textField = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY
				| SWT.V_SCROLL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(textField);

		// initial advice
		addLine(messages.EnumImportPart_Advice);
	}
}
