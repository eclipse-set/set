/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plazmodel.check;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.core.services.font.FontService;
import org.eclipse.set.core.services.font.FontService.FopFont;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.common.collect.Streams;

/**
 * Valid the String-Value, whether contain illegal characters or not
 * 
 * @author truong
 */
@Component
public class UnicodeCharactersValid implements PlazCheck {

	@Reference
	FontService fontService;

	@Override
	public List<PlazError> run(final IModelSession modelSession) {
		try {
			final List<Font> fopFonts = new ArrayList<>();
			for (final FopFont font : fontService.getFopFonts()) {
				fopFonts.add(Font.createFont(Font.TRUETYPE_FONT,
						font.path().toFile()));
			}
			final List<Pair<EObject, String>> wertObjects = Streams
					.stream(modelSession.getPlanProSchnittstelle()
							.eAllContents())
					.map(UnicodeCharactersValid::getObjectWithValue)
					.filter(pair -> pair != null && pair.getSecond() != null
							&& !pair.getSecond().isEmpty())
					.toList();

			return wertObjects.stream()
					.filter(pair -> !isValidStringValue(pair.getSecond(),
							fopFonts))
					.map(pair -> createError(pair.getFirst(), pair.getSecond()))
					.toList();
		} catch (IOException | FontFormatException e) {
			return List.of(createFontError());
		}
	}

	private static Pair<EObject, String> getObjectWithValue(final EObject obj) {
		try {
			final Method getWertMethod = obj.getClass()
					.getDeclaredMethod("getWert"); //$NON-NLS-1$
			if (getWertMethod == null || !getWertMethod.getReturnType()
					.isAssignableFrom(String.class)) {
				return null;
			}

			return new Pair<>(obj, (String) getWertMethod.invoke(obj));
		} catch (final Exception e) {
			return null;
		}
	}

	private static boolean isValidStringValue(final String value,
			final List<Font> availableFonts) {
		return availableFonts.stream()
				.anyMatch(font -> value.chars()
						.allMatch(chInt -> font.canDisplay(chInt)));
	}

	private PlazError createError(final EObject obj, final String value) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setType(checkType());
		plazError.setSeverity(ValidationSeverity.ERROR);
		plazError.setObject(obj);
		plazError.setMessage(String
				.format("Der Wert: %s enthält ungültig sonderzeichen", value)); //$NON-NLS-1$
		return plazError;
	}

	@Override
	public String checkType() {
		return "Sonderzeichen"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Es gibt keine ungültig Sonderzeichen"; //$NON-NLS-1$
	}

	@Override
	public String getGeneralErrMsg() {
		return "Es gibt Objekte mit ungültige Sonderzeichen"; //$NON-NLS-1$
	}

	private PlazError createFontError() {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setType(checkType());
		plazError.setSeverity(ValidationSeverity.ERROR);
		plazError.setMessage("Can't load fop fonts"); //$NON-NLS-1$
		return plazError;
	}
}
