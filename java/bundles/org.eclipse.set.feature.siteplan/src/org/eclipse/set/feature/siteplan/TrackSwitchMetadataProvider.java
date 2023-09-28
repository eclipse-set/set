/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrArt;

/**
 * Reads metadata from the Weichenbauformen.csv
 * 
 * @author Stuecker
 *
 */
public class TrackSwitchMetadataProvider {
	private static final String CSV_PATH = "data/Weichenbauformen.csv"; //$NON-NLS-1$

	private final Map<ENUMWKrArt, List<TrackSwitchMetadata>> metadata = new EnumMap<>(
			ENUMWKrArt.class);

	/**
	 * Initialize the metadata provider by reading the CSV file
	 * 
	 * @throws IOException
	 *             if the file could not be read
	 */
	public void initialize() throws IOException {
		try (final Stream<String> stream = Files.lines(Paths.get(CSV_PATH))) {
			stream.skip(8).forEach(line -> {
				final TrackSwitchMetadata entry = TrackSwitchMetadata
						.fromCSVLine(line);
				if (entry != null) {
					metadata.putIfAbsent(entry.type, new ArrayList<>());
					metadata.get(entry.type).add(entry);
				}
			});

		}
	}

	/**
	 * Returns the metadata for a track switch
	 * 
	 * @param type
	 *            the type of the track switch found as W_Kr_Art
	 * @param design
	 *            the design of the track switch found as W_Kr_Grundform
	 * @return the track switch metadata or null
	 */
	public TrackSwitchMetadata getTrackSwitchMetadata(final ENUMWKrArt type,
			final String design) {
		final List<TrackSwitchMetadata> md = metadata.get(type);
		if (md == null) {
			return null;
		}

		// Try an exact match first
		for (final TrackSwitchMetadata entry : md) {
			if (design.equals(entry.getDesignString())) {
				return entry;
			}
		}

		// In some planning data, there are additional spaces
		// around the W_Kr_Grundform entries. Attempt to match
		// without them
		final String designNoWhitespace = design.replace(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
		for (final TrackSwitchMetadata entry : md) {
			if (designNoWhitespace
					.equals(entry.getDesignString().replace(" ", ""))) { //$NON-NLS-1$ //$NON-NLS-2$
				return entry;
			}
		}

		// In some planning data, the type prefix is missing
		// for the W_Kr_Grundform entries. Add it and attempt to match again
		final String designWithPrefix = type.toString() + designNoWhitespace;
		for (final TrackSwitchMetadata entry : md) {
			if (designWithPrefix
					.equals(entry.getDesignString().replace(" ", ""))) { //$NON-NLS-1$ //$NON-NLS-2$
				return entry;
			}
		}

		return null;
	}

}
