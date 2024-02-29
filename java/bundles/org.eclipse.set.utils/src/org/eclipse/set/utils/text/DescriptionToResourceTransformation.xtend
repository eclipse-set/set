/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text

import org.eclipse.set.basis.Wrapper
import org.eclipse.jface.resource.FontDescriptor
import org.eclipse.jface.resource.ResourceManager
import org.eclipse.jface.text.TextAttribute
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.widgets.Display
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Transforms text descriptions to SWT resources and more formal, structural
 * resource description like {@link TextAttribute}s.
 * 
 * @author Schaefer
 */
class DescriptionToResourceTransformation {

	static final Logger LOGGER = LoggerFactory.getLogger(
		typeof(DescriptionToResourceTransformation));

	val ResourceManager resourceManager

	/**
	 * @param resourceManager resource manager for creating resources
	 */
	new(ResourceManager resourceManager) {
		this.resourceManager = resourceManager
	}

	/**
	 * @param descritpion the text description for the text attribute
	 * 
	 * @return the text attribute
	 */
	def TextAttribute transformToTextAttribute(String description) {
		try {
			val parts = description.transformToParts
			return parts.transformToTextAttribute
		} catch (NoSuchFieldException e) {
			return handleTextAttribute(e)
		} catch (IllegalAccessException e) {
			return handleTextAttribute(e)
		}
	}

	/**
	 * @param descritpion the text description for the font
	 * 
	 * @return the font
	 */
	def Font transformToFont(String description) {
		try {
			val parts = description.transformToParts
			return parts.transformToFont
		} catch (NoSuchFieldException e) {
			return handleFont(e)
		} catch (IllegalAccessException e) {
			return handleFont(e)
		}
	}

	private def String[] transformToParts(String description) {
		return description.split(",")
	}

	private def TextAttribute transformToTextAttribute(
		String[] parts) throws NoSuchFieldException, IllegalAccessException {
		val foreground = parts.transformToForeground
		val background = parts.transformToBackground
		val style = parts.transformToStyle
		return new TextAttribute(foreground, background, style)
	}

	private def Color transformToForeground(
		String[] parts
	) throws NoSuchFieldException , IllegalAccessException {
		return parts.get(0).transformToColor
	}

	private def Color transformToBackground(
		String[] parts
	) throws NoSuchFieldException, IllegalAccessException {
		return parts.get(1).transformToColor
	}

	private def int transformToStyle(
		String[] parts
	) throws NoSuchFieldException, IllegalAccessException {
		val styleAttribute = parts.get(2).toUpperCase
		return typeof(SWT).getField(styleAttribute).getInt(null)
	}

	private def Font transformToFont(
		String[] parts
	) throws NoSuchFieldException, IllegalAccessException {
		val height = Integer.parseInt(parts.get(1))
		val styleAttribute = parts.get(2).toUpperCase
		val style = typeof(SWT).getField(styleAttribute).getInt(null)
		return transformToFont(parts.get(0), height, style).value
	}

	private def Wrapper<Font> create new Wrapper transformToFont(
		String name,
		int height,
		int style
	) {
		val descriptor = FontDescriptor.createFrom(name, height, style)
		val font = resourceManager.create(descriptor)
		value = font
		return
	}

	private def Color transformToColor(
		String name
	) throws NoSuchFieldException, IllegalAccessException {
		val foregroundAttribute = '''COLOR_«name.toUpperCase»'''
		val foreground = typeof(SWT).getField(foregroundAttribute).getInt(null)
		return Display.current.getSystemColor(foreground)
	}

	static def TextAttribute handleTextAttribute(Exception e) {
		LOGGER.
			error('''«e.class.simpleName»: «e.message» - returning default text attribute''', e)
		return new TextAttribute(
			Display.current.getSystemColor(SWT.COLOR_BLACK),
			Display.current.getSystemColor(SWT.COLOR_WHITE),
			SWT.NORMAL
		)
	}

	static def Font handleFont(Exception e) {
		LOGGER.
			error('''«e.class.simpleName»: «e.message» - returning no text font''', e)
		return null
	}
}
