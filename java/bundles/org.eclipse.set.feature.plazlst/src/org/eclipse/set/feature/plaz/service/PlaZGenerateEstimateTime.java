/**
 * 
 */
package org.eclipse.set.feature.plaz.service;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.xml.PlanProXMLNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Formell for calculate generate time of PlaZ-LST <br>
 * These fromell come from PlaZ-LST lieferant
 * 
 * @author truong
 */
public class PlaZGenerateEstimateTime {
	/**
	 * The steps for generate PlaZ-LST
	 */
	public enum GenerateStep {
		/**
		 * 2. Step:Generate XSL file form SVRL file
		 */
		FOP,
		/**
		 * 3. Step: Generate HTML site from SVRL file
		 */
		HTML,
		/**
		 * 2. Step: Generate final PDF from XSL file
		 */
		PDF,
		/**
		 * 1. Step: Generate SVRL file form PlanPro-Data
		 */
		SVRL
	}

	private static final Logger logger = LoggerFactory
			.getLogger(PlaZGenerateEstimateTime.class);

	private static Pair<Path, PlanProXMLNode> svrlNode = null;

	/**
	 * @param step
	 *            the generate step
	 * @param toolboxFile
	 *            the {@link ToolboxFile}
	 * @param svrlPath
	 *            the path to svrl file
	 * @return the estimate time for given step
	 */
	@SuppressWarnings("boxing")
	public static double getEstimateTime(final GenerateStep step,
			final ToolboxFile toolboxFile, final Path svrlPath) {
		try {
			return switch (step) {
				case SVRL: {
					if (toolboxFile == null) {
						throw new IllegalArgumentException(
								"Missing ToolboxFile");
					}
					final int totalLSTElement = getLSTElementCount(toolboxFile);
					logger.info("Total Final Element: {}", totalLSTElement);
					yield totalDuration(totalLSTElement);
				}
				default:
					yield getEstimateTime(step, svrlPath);
			};
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static double foDuration(final int svrlElementCount) {
		return 4.440e+00 * Math.pow(svrlElementCount, 0.618) + 2643.2;
	}

	private static double getEstimateTime(final GenerateStep step,
			final Path svrlPath) throws Exception {
		if (svrlNode == null || !svrlNode.getFirst()
				.toAbsolutePath()
				.toString()
				.equals(svrlPath.toAbsolutePath().toString())) {
			svrlNode = new Pair<>(svrlPath,
					PlanProXMLNode.read(svrlPath, Optional.empty()));
		}
		final int size = getNodeCount(svrlNode.getSecond());
		return switch (step) {
			case FOP -> foDuration(size);
			case PDF -> pdfDuration(size);
			case HTML -> htmlDuration(size);
			default -> throw new IllegalArgumentException();
		};
	}

	private static int getLSTElementCount(final ToolboxFile toolboxFile) {
		final PlanProXMLNode rootNode = toolboxFile.getXMLDocument(
				PathExtensions.getBaseFileName(toolboxFile.getModelPath()));
		return getNodeCount(rootNode);
	}

	private static int getNodeCount(final PlanProXMLNode rootNode) {
		final Deque<PlanProXMLNode> parents = new ArrayDeque<>();
		parents.add(rootNode);
		int count = 0;
		while (!parents.isEmpty()) {
			final var head = parents.poll();
			final List<PlanProXMLNode> children = head.getChildren();
			if (!children.isEmpty()) {
				parents.addAll(children);
				count++;
			}
		}
		return count;
	}

	private static double htmlDuration(final int svrlElementCount) {
		return 2.422e+00 * Math.pow(svrlElementCount, 0.654) + 2583.6;
	}

	private static double pdfDuration(final int svrlElementCount) {
		return 5.140e-02 * Math.pow(svrlElementCount, 1.118) + 2938.5;
	}

	private static double totalDuration(final int lstElementCount) {
		return 5.185e-01 * Math.pow(lstElementCount, 0.872) + 8795.1;
	}
}
