/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.collect.Lists
import java.util.List
import org.eclipse.set.ppmodel.extensions.utils.LstObjectAttribute
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.basis.constants.ToolboxConstants
import java.math.RoundingMode

import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckeExtensions.*

/**
 * Diese Klasse erweitert {@link Basis_Objekt}.
 */
class BasisObjektExtensions extends UrObjectExtensions {
	static val Logger logger = LoggerFactory.getLogger(BasisObjektExtensions);

	/**
	 * @param object this {@link Basis_Objekt}
	 * 
	 * @return list of all LST object/attributes
	 */
	def static List<LstObjectAttribute> getObjectAttributes(
		Basis_Objekt object
	) {
		val List<LstObjectAttribute> result = Lists.newLinkedList

		// first we list this object without an attribute
		result.add(new LstObjectAttribute(object))

		// then we add a combination for each contained Basis_Attribut
		object.eAllContents.filter(BasisAttribut_AttributeGroup).forEach [
			result.add(new LstObjectAttribute(object, it))
		]

		return result
	}

	def static Iterable<Bearbeitungsvermerk> getBearbeitungsvermerk(
		Basis_Objekt object) {
		return object.IDBearbeitungsvermerk?.map[value]?.filterNull
	}

	static BigDecimal GEO_LENGTH_DEVIATION_TOLERANCE = BigDecimal.valueOf(0.001)
	static BigDecimal GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE = BigDecimal.
		valueOf(0.0001)

	/**
	 * In some planning data there is a minor deviation between the length
	 * of a
	 * TOP_Kante/Strecke and the total length of all GEO_Kanten on the TOP_Kante
	 * As objects are positioned on a TOP_Kante but a GEO_Kante is used
	 * to determine the geographical position, calculate a scaling factor
	 * 
	 * @param geoArt TOP_Kante or Strecke
	 */
	def static BigDecimal getGeoArtScalingFactor(
		Basis_Objekt geoArt) throws IllegalArgumentException {

		val geoKantenOnGeoArt = getGeoKanten(geoArt)
		var geoLength = BigDecimal.ZERO
		for (GEO_Kante geoKante : geoKantenOnGeoArt) {
			try {
				geoLength = geoLength.add(geoKante.GEOKanteAllg.GEOLaenge.wert)
			} catch (NullPointerException e) {
				logger.
					error('''Geo_Kante «geoKante.identitaet.wert» missing Geo_Laenge''')
			}
		}

		val geoArtLength = geoArt.geoArtLength
		val diffrence = geoLength.subtract(geoArtLength).abs
		val tolerance = GEO_LENGTH_DEVIATION_TOLERANCE.max(geoArtLength *
			GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE)
		if (diffrence > tolerance) {
			logger.debug('''lengthGeoArt= «geoArtLength»''')
			logger.debug('''lengthGeoKanten= «geoLength»''')
			logger.debug('''geoKantenOnGeoArt= «geoKantenOnGeoArt.size»''')
			logger.
				warn('''Difference of GEO_Kanten length and GeoArt length for GeoArt «geoArt.identitaet.wert» greater than tolerance «tolerance» («diffrence»)''')
		}
		if (geoLength <= BigDecimal.ZERO || geoArtLength <= BigDecimal.ZERO) {
			return BigDecimal.ONE
		}
		
		val scale = geoLength.divide(geoArtLength,
			ToolboxConstants.ROUNDING_TO_PLACE, RoundingMode.DOWN)
		return scale > BigDecimal.ZERO ? scale : BigDecimal.ONE

	}

	private def static List<GEO_Kante> getGeoKanten(Basis_Objekt geoArt) {
		return switch (geoArt) {
			TOP_Kante:
				getGeoKanten(geoArt)
			Strecke:
				getGeoKanten(geoArt)
			default:
				throw new IllegalArgumentException("Unexpected value: " +
					geoArt)
		}
	}

	private def static BigDecimal geoArtLength(Basis_Objekt geoArt) {
		return switch (geoArt) {
			TOP_Kante:
				geoArt.TOPKanteAllg.TOPLaenge.wert
			Strecke:
				getStreckeLength(geoArt)
			default:
				throw new IllegalArgumentException("Unexpected value: " +
					geoArt)
		}
	}
}
