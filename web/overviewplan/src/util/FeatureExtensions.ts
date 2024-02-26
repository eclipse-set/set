/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Coordinate, Position } from '@/model/Position'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import { FeatureType, getFeatureData, getFeatureType } from '@/feature/FeatureInfo'

export function getFeatureGUIDs (feature: Feature<Geometry>): string[] {
  const name = getFeatureType(feature)
  switch (name) {
    case FeatureType.Track:
      const trackSection = getFeatureData(feature)
      return trackSection ? [trackSection.section.guid] : []
    default:
      return []
  }
}

export function getFeaturePosition (feature: Feature<Geometry>): Position[] | Coordinate[] {
  const type = getFeatureType(feature)
  switch (type) {
    case FeatureType.Track:
      const trackSection = getFeatureData(feature)
      return trackSection ? trackSection.segment.positions : []
    default:
      return []
  }
}
