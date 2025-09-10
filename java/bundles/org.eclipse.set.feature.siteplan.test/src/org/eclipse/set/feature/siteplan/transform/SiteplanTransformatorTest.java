/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;

import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.module.EMFModule.Feature;
import org.eclipse.set.feature.siteplan.json.SiteplanEObjectSerializer;
import org.eclipse.set.feature.siteplan.transform.utils.SiteplanTest;
import org.eclipse.set.model.siteplan.Siteplan;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

/**
 * @author Stuecker
 *
 */
public class SiteplanTransformatorTest extends SiteplanTest {
	SiteplanTransformatorImpl testee;
	Siteplan siteplan;

	ObjectMapper mapper;
	private JsonNode referenceJson;
	private JsonNode siteplanJson;

	@ParameterizedTest
	@MethodSource("getSiteplanReferenceFiles")
	void testSiteplanTransformSuccessful(final String file) throws Exception {
		givenPlanProFile(file);
		givenGeoKanteGeometryService();
		givenSiteplanTransformator();
		// then no exception occurs
		assertDoesNotThrow(this::whenTransformingToSiteplanModel);
		givenObjectMapper();
		givenSiteplanJsonReference();
		assertNotNull(referenceJson);
		givenSiteplanJson();
		assertNotNull(siteplanJson);
		assertEquals(siteplanJson, referenceJson);
	}

	private void whenTransformingToSiteplanModel() {
		siteplan = testee.transform(planProSchnittstelle);
	}

	private void givenSiteplanTransformator() throws Exception {
		testee = new SiteplanTransformatorImpl();
		setupTransformators(testee);
	}

	private void givenObjectMapper() {
		mapper = new ObjectMapper();
		final EMFModule emfModule = new EMFModule();
		emfModule.configure(Feature.OPTION_SERIALIZE_TYPE, false);
		emfModule.configure(Feature.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		mapper.registerModule(emfModule);
		mapper.registerModule(SiteplanEObjectSerializer.getModule(emfModule));
	}

	private void givenSiteplanJson() throws Exception {
		siteplanJson = mapper.convertValue(siteplan, JsonNode.class);
	}

	private void givenSiteplanJsonReference() throws Exception {
		final URL resource = SiteplanTransformatorTest.class.getClassLoader()
				.getResource("siteplan.json");
		final File file = new File(resource.toURI());
		try (final Reader reader = Files.newBufferedReader(file.toPath())) {
			final ObjectReader readerFor = mapper
					.readerFor(new TypeReference<JsonNode>() {
						// do nothing
					});
			referenceJson = (JsonNode) readerFor.readValue(file);
		}
	}

}
