/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.xml

import java.io.IOException
import javax.xml.parsers.ParserConfigurationException
import org.eclipse.emf.ecore.EObject
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.SAXException
import org.eclipse.emf.ecore.util.ExtendedMetaData
import java.util.List
import org.eclipse.set.utils.xml.LineNumberXMLReader
import org.eclipse.set.feature.plazmodel.xml.EObjectXMLFinder.XmlParseException
import org.eclipse.set.feature.plazmodel.xml.EObjectXMLFinder.LineNotFoundException
import org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader
import org.eclipse.set.basis.files.ToolboxFile
import java.util.stream.IntStream
import org.eclipse.set.model.validationreport.ObjectScope
import java.nio.file.Path
import org.eclipse.set.model.validationreport.ObjectState

/** 
 * Resolves an EObject to its line number within a XML document
 * @author Peters
 */
class EObjectXMLFinder {
	Document document = null

	static class LineNotFoundException extends Exception {
		new() {
			super("No line for given EObject found")
		}
	}

	static class XmlParseException extends Exception {
		new(String m, Throwable e) {
			super(m, e)
		}
	}

	/** 
	 * @param path path to the XML document
	 */
	new(ToolboxFile toolboxFile, Path docPath) throws XmlParseException {
		try {
			this.document = ObjectMetadataXMLReader.read(toolboxFile, docPath)
		} catch (IOException | SAXException | ParserConfigurationException e) {
			throw new XmlParseException(
				"The given XML document could not be parsed", e)
		}
	}

	/** 
	 * Find Node in XML of EObject
	 * @param EObject the EObject
	 * @return node in XMNL
	 */
	def Node find(EObject object) {
		if (object === null) {
			return null
		}
		var parent = object.eContainer
		val feature = object.eContainmentFeature
		val name = ExtendedMetaData.INSTANCE.getName(feature)
		val parentNode = parent?.eContainer === null ? document : find(parent)
		if (feature.isMany) {
			val list = object.eContainer().eGet(feature) as List<?>
			return parentNode.getNthChildByName(name, list.indexOf(object))
		} else {
			return parentNode.getFirstChildByName(name)
		}
	}

	def private Node getFirstChildByName(Node node, String name) {
		return getNthChildByName(node, name, 0)
	}

	def private Node getNthChildByName(Node node, String name, int n) {
		if (node === null) {
			return null
		}
		val children = node.childNodes
		val childrenStream = IntStream.range(0, children.length).mapToObj[i | children.item(i)]
		return childrenStream.filter[sanetizedName.equals(name)].skip(n).findFirst.orElse(null)
	}

	def private String getSanetizedName(Node node) {
		// XML node names can contain a prefix while the EMF model names don't
		return node.nodeName.split(":").last
	}

	/** 
	 * Resolves an Node to its line number
	 * @param node the Node
	 * @return the line number in which the Node starts occurring
	 * 		   or zero if it cannot be located
	 */
	def int getLineNumber(Node node) throws LineNotFoundException {
		val String lineNum = (node?.getUserData(
			LineNumberXMLReader.LINE_NUMBER_KEY) as String)
		if (lineNum === null) {
			throw new LineNotFoundException()
		}
		return Integer.parseInt(lineNum)
	}
	
	/**
	 * Returns the type of a object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the type of the object
	 */
	def String getObjectType(Node node) {
		return ObjectMetadataXMLReader.getObjectType(node)
	}
	
	/**
	 * Returns the LST state of a object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the LST state of the object or null
	 */
	def ObjectState getObjectState(Node node) {
		return ObjectMetadataXMLReader.getObjectState(node)
	}
	
	/**
	 * Returns the attribute name of a object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the type of the object
	 */
	def String getAttributeName(Node node) {
		return ObjectMetadataXMLReader.getAttributeName(node)
	}
	
	/**
	 * Returns the scope of a object represented by a XML node
	 * 
	 * @param node
	 *            the node
	 * @return the scope of the object
	 */
	def ObjectScope getObjectScope(Node node) {
		return ObjectMetadataXMLReader.getObjectScope(node)
	}
}
