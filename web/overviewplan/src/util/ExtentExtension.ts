/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Feature } from 'ol'
import { Extent, createEmpty, extend} from 'ol/extent'
import Geometry from 'ol/geom/Geometry'

/**
 * Extension for {@link Extent} class for Svg Element
 * @author Truong
 */

export function getExtent (features: Feature<Geometry>[]): Extent {
  // Only fit if features exist
  if (features.length === 0) {
    return createEmpty()
  }

  // Determine an extent containing all features
  let extent = createEmpty()
  features.forEach(feature => {
    const featureExtent = feature.getGeometry()?.getExtent()
    if (featureExtent != null) {
      extent = extend(extent, featureExtent)
    }
  })
  return extent
}
