/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 */
package org.eclipse.set.utils.xml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.model.validationreport.ObjectState;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.xml.sax.SAXException;

/**
 * Resolves an EObject to its line number within a XML document
 *
 * @author Peters
 */
@SuppressWarnings("static-method")
public class EObjectXMLFinder {

    PlanProXMLNode document = null;

    /**
     * LineNotFoundException
     */
    public static class LineNotFoundException extends Exception {

        /**
         *
         */
        public LineNotFoundException() {
            super("No line for given EObject found"); //$NON-NLS-1$
        }
    }

    /**
     * XmlParseException
     */
    public static class XmlParseException extends Exception {

        /**
         * @param m the messages
         * @param e the exception
         */
        public XmlParseException(final String m, final Throwable e) {
            super(m, e);
        }
    }

    /**
     * @param toolboxFile the {@link ToolboxFile}
     * @param docPath path to the XML document
     * @throws XmlParseException the {@link XmlParseException}
     */
    public EObjectXMLFinder(final ToolboxFile toolboxFile, final Path docPath)
            throws XmlParseException {
        try {
            this.document = ObjectMetadataXMLReader.read(toolboxFile, docPath);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new XmlParseException(
                    "The given XML document could not be parsed", e); //$NON-NLS-1$
        }
    }

    /**
     * Find Node in XML of EObject
     *
     * @param object the EObject
     * @return node in XMNL
     */
    public PlanProXMLNode find(final EObject object) {
        if (object == null) {
            return null;
        }
        final EObject parent = object.eContainer();
        final EStructuralFeature feature = object.eContainingFeature();
        final String name = ExtendedMetaData.INSTANCE.getName(feature);
        PlanProXMLNode parentNode = null;
        if (parent == null || parent.eContainer() == null) {
            parentNode = document;
        } else {
            parentNode = find(parent);
        }

        final List<PlanProXMLNode> children = node.getChildren();
        final Stream<PlanProXMLNode> childrenStream = IntStream
                .range(0, children.size())
                .mapToObj(children::get);
        return childrenStream
                .filter(child -> getSanetizedName(child) != null
                && getSanetizedName(child).equals(name))
                .skip(n)
                .findFirst()
                .orElse(null);
    }

    private static PlanProXMLNode getFirstChildByName(final PlanProXMLNode node,
            final String name) {
        return getNthChildByName(node, name, 0);
    }

    private static PlanProXMLNode getNthChildByName(final PlanProXMLNode node,
            final String name, final int n) {
        if (node == null) {
            return null;
        }

        final List<PlanProXMLNode> children = node.getChildrens();
        final Stream<PlanProXMLNode> childrenStream = IntStream
                .range(0, children.size())
                .mapToObj(children::get);
        return childrenStream
                .filter(child -> getSanetizedName(child) != null
                && getSanetizedName(child).equals(name))
                .skip(n)
                .findFirst()
                .orElse(null);
    }

    private static String getSanetizedName(final PlanProXMLNode node) {
        // XML node names can contain a prefix while the EMF model names don't
        return IterableExtensions
                .lastOrNull(Arrays.asList(node.getNodeName().split(":"))); //$NON-NLS-1$
    }

    /**
     * Resolves an Node to its line number
     *
     * @param node the Node
     * @return the line number in which the Node starts occurring or zero if it
     * cannot be located
     * @throws LineNotFoundException the {@link LineNotFoundException}
     */
    public int getLineNumber(final PlanProXMLNode node)
            throws LineNotFoundException {
        if (node == null) {
            throw new LineNotFoundException();
        }
        final String lineNum = String.valueOf(node.getStartLineNumber());
        if (lineNum == null) {
            throw new LineNotFoundException();
        }
        return Integer.parseInt(lineNum);
    }

    /**
     * Returns the type of a object represented by a XML node
     *
     * @param node the node
     * @return the type of the object
     */
    public String getObjectType(final PlanProXMLNode node) {
        return ObjectMetadataXMLReader.getObjectType(node);
    }

    /**
     * Returns the LST state of a object represented by a XML node
     *
     * @param node the node
     * @return the LST state of the object or null
     */
    public ObjectState getObjectState(final PlanProXMLNode node) {
        return ObjectMetadataXMLReader.getObjectState(node);
    }

    /**
     * Returns the attribute name of a object represented by a XML node
     *
     * @param node the node
     * @return the type of the object
     */
    public String getAttributeName(final PlanProXMLNode node) {
        return ObjectMetadataXMLReader.getAttributeName(node);
    }

    /**
     * Returns the scope of a object represented by a XML node
     *
     * @param node the node
     * @return the scope of the object
     */
    public ObjectScope getObjectScope(final PlanProXMLNode node) {
        return ObjectMetadataXMLReader.getObjectScope(node);
    }

    /**
     * Check if the object is nil value
     *
     * @param object the object
     * @return true if it the nil value
     */
    public boolean isNilValue(final EObject object) {
        final Node node = find(object);
        if (node == null) {
            return false;
        }
        if (isNilValue(node)) {
            return true;
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            final Node childNode = node.getChildNodes().item(i);
            // Only check the directly Wert node
            if (childNode.getNodeName().equals(XMLNodeFinder.NODE_WERT)) {
                return isNilValue(childNode);
            }
        }

        return false;
    }

    private boolean isNilValue(final Node node) {
        if (node == null) {
            return false;
        }
        final Optional<Node> nilAttirbute = Optional
                .ofNullable(node.getAttributes().getNamedItem(NIL));
        return nilAttirbute.isPresent();
    }
}
