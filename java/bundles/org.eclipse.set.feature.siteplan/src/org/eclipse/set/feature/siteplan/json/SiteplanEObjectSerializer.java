/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.json;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.getParent;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfcloud.jackson.databind.property.EObjectProperty;
import org.eclipse.emfcloud.jackson.databind.property.EObjectPropertyMap;
import org.eclipse.emfcloud.jackson.databind.ser.EObjectSerializer;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.utils.EObjects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * Extended variant of {@link EObjectSerializer}, which serializes empty
 * collections as empty JSON arrays, rather than omitting them
 * 
 * @author Stuecker
 *
 */
public class SiteplanEObjectSerializer extends JsonSerializer<EObject> {
	/**
	 * @param module
	 *            an instance of EMFModule
	 * @return a module that can be used to register this class as a replacement
	 *         serializer for EObjectSerializer
	 */
	public static SimpleModule getModule(final EMFModule module) {
		return new SimpleModule()
				.setSerializerModifier(new BeanSerializerModifier() {
					@Override
					public JsonSerializer<?> modifySerializer(
							final SerializationConfig config,
							final BeanDescription desc,
							final JsonSerializer<?> serializer) {
						if (serializer instanceof EObjectSerializer) {
							return new SiteplanEObjectSerializer(module);
						}
						return serializer;
					}
				});
	}

	private final JsonSerializer<EObject> refSerializer;
	private final EObjectPropertyMap.Builder builder;

	private SiteplanEObjectSerializer(final EMFModule module) {
		this.builder = EObjectPropertyMap.Builder.from(module,
				module.getFeatures());
		this.refSerializer = module.getReferenceSerializer();
	}

	@Override
	public Class<EObject> handledType() {
		return EObject.class;
	}

	@Override
	public void serialize(final EObject object, final JsonGenerator jg,
			final SerializerProvider provider) throws IOException {
		final EObjectPropertyMap properties = builder.construct(provider,
				object.eClass());

		final EObject parent = getParent(provider);
		if (parent != null && (object.eIsProxy()
				|| EObjects.isContainmentProxy(provider, parent, object))) {
			// containment proxies are serialized as references
			refSerializer.serialize(object, jg, provider);
			return;
		}

		jg.writeStartObject();
		for (final EObjectProperty property : properties.getProperties()) {
			serializeProperty(object, jg, provider, property);
		}
		jg.writeEndObject();
	}

	private static void serializeProperty(final EObject object,
			final JsonGenerator jg, final SerializerProvider provider,
			final EObjectProperty property) throws IOException {
		final int beforeIndex = jg.getOutputContext().getCurrentIndex();
		property.serialize(object, jg, provider);
		final int afterIndex = jg.getOutputContext().getCurrentIndex();

		// Do not write anything if the property serializer has written to the
		// output
		if (beforeIndex != afterIndex) {
			return;
		}

		// Otherwise look up the feature
		final EStructuralFeature feature = object.eClass()
				.getEStructuralFeature(property.getFieldName());

		// If the feature is not set, but a collection, write an empty JSON
		// array
		if (feature != null && !object.eIsSet(feature)
				&& feature.getDefaultValue() == null && feature.isMany()) {
			jg.writeArrayFieldStart(property.getFieldName());
			jg.writeEndArray();
		}
	}

}
