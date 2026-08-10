/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { isGeometryIntersection, Line } from '@/collision/CollisionExtension'
import { FeatureLayerType, FeatureType, getFeatureData, getFeatureLayer, getFeatureMovePriority, getFeatureType } from '@/feature/FeatureInfo'
import { SheetCutFeatureData } from '@/feature/LayoutInfoFeature'
import { TrackSwitchFeatureData } from '@/feature/TrackSwitchFeature'
import { CantPoint } from '@/model/Cant'
import { Coordinate, Position } from '@/model/Position'
import { Signal } from '@/model/Signal'
import { SignalMount } from '@/model/SignalMount'
import TrackClose from '@/model/TrackClose'
import { store } from '@/store'
import { FeatureCollection, Feature as GeoJSONFeature, GeometryCollection as GeoJSONGeometryCollection } from 'geojson'
import { Feature } from 'ol'
import GeoJSON from 'ol/format/GeoJSON'
import Geometry from 'ol/geom/Geometry'
import GeometryCollection from 'ol/geom/GeometryCollection'
import LineString from 'ol/geom/LineString'
import { getSignalPrio } from './SignalExtension'

export function getFeatureGUIDs (feature: Feature<Geometry>): string[] {
  const name = getFeatureType(feature)
  switch (name) {
    case FeatureType.PZB:
    case FeatureType.PZBGU:
      const pzb = getFeatureData(feature)
      return pzb ? [pzb.guid] : []
    case FeatureType.FMA:
      const fma = getFeatureData(feature)
      return fma ? [fma.guid] : []
    case FeatureType.Track:
      const isTrackColorVisible = store.state.trackColorVisible
      const trackData = getFeatureData(feature)
      if (!trackData) {
        return []
      }

      return isTrackColorVisible ? [trackData.guid] : [trackData.section.guid]
    case FeatureType.TrackOutline:
      const trackOutline = getFeatureData(feature)
      return trackOutline ? [trackOutline.guid] : []
    case FeatureType.TrackSwitch:
      const trackSwitch = getFeatureData(feature) as TrackSwitchFeatureData
      return trackSwitch.component ? [trackSwitch.component.guid] : []
    case FeatureType.TrackLock:
      const trackLock = getFeatureData(feature)
      return trackLock ? [trackLock.guid] : []
    case FeatureType.Route:
      const routeSection = getFeatureData(feature)
      return routeSection ? [routeSection.guid] : []
    case FeatureType.Signal:
      const signalData = getFeatureData(feature) as SignalMount | Signal
      if ('attachedSignals' in signalData) {
        return signalData.attachedSignals.map(s => s.guid)
      }

      if ('system' in signalData) {
        return [signalData.guid]
      }

      return  []
    case FeatureType.ExternalElementControl:
      const eec = getFeatureData(feature)
      return eec ? [eec.guid] : []
    case FeatureType.LockKey:
      const lockKey = getFeatureData(feature)
      return lockKey ? [lockKey.guid] : []
    case FeatureType.Collision:
      const refFeature = getFeatureData(feature)?.refFeature
      return refFeature ? getFeatureGUIDs(refFeature) : []
    case FeatureType.SheetCut:
      const sheetCut = getFeatureData(feature) as SheetCutFeatureData
      return sheetCut ? [sheetCut.guid] : []
    case FeatureType.TrackClose:
      const trackClose = getFeatureData(feature) as TrackClose
      return trackClose ? [trackClose.guid] : []
    case FeatureType.Cant:
      const cant = getFeatureData(feature) as CantPoint
      return cant ? [cant.guid] : []
    case FeatureType.CantLine:
      const cantLine = getFeatureData(feature) as Cant
      return cantLine ? [cantLine.guid] : []
    case FeatureType.Unknown:
      const unkonwnData = getFeatureData(feature)
      if ('guid' in unkonwnData) {
        return [unkonwnData.guid]
      }
    default:
      return []
  }
}

export function getFeaturePosition (feature: Feature<Geometry>): Position[] | Coordinate[] {
  const type = getFeatureType(feature)
  switch (type) {
    case FeatureType.PZB:
    case FeatureType.PZBGU:
      const pzb = getFeatureData(feature)
      return pzb ? [pzb.position] : []
    case FeatureType.FMA:
      const fma = getFeatureData(feature)
      return fma ? [fma.position] : []
    case FeatureType.Track:
      const trackSection = getFeatureData(feature)
      return trackSection ? trackSection.segment.positions : []
    case FeatureType.TrackSwitch:
      const trackSwitch = getFeatureData(feature)
      return trackSwitch
        ? trackSwitch.component.mainLeg.coordinates
          .concat(trackSwitch.component.sideLeg.coordinates.reverse())
        : []
    case FeatureType.TrackLock:
      const trackLock = getFeatureData(feature)
      return trackLock ? [trackLock.position] : []
    case FeatureType.Route:
      const routeSection = getFeatureData(feature)
      return routeSection ? routeSection.positions : []
    case FeatureType.Signal:
      const signal = getFeatureData(feature)
      return signal ? [signal.position] : []
    case FeatureType.Collision:
      const refFeature = getFeatureData(feature)?.refFeature
      return refFeature ? getFeaturePosition(refFeature) : []
    default:
      return []
  }
}

/**
 * Check if two feature intersection.
 * Only work with polygon geometry feature
 * @param featureA the feature
 * @param featureB the feature
 * @returns true/false
 */
export function isFeaturesIntersect (featureA: Feature<Geometry>, featureB: Feature<Geometry>): boolean {
  const geometryA = featureA.getGeometry()
  const geometryB = featureB.getGeometry()
  if (!geometryA || !geometryB) {
    return false
  }

  if (geometryA instanceof GeometryCollection && geometryB instanceof GeometryCollection) {
    const geometriesA = (geometryA as GeometryCollection).getGeometriesArray()
    const geometriesB = (geometryB as GeometryCollection).getGeometriesArray()
    return geometriesA.some(geoA =>
      geometriesB.some(geoB =>
        isGeometryIntersection(geoA, geoB)))
  } else if (geometryA instanceof GeometryCollection) {
    const geometriesA = (geometryA as GeometryCollection).getGeometriesArray()
    return geometriesA.some(geoA => isGeometryIntersection(geoA, geometryB))
  } else if (geometryB instanceof GeometryCollection) {
    const geometriesB = (geometryB as GeometryCollection).getGeometriesArray()
    return geometriesB.some(geoB => isGeometryIntersection(geometryA, geoB))
  } else {
    return isGeometryIntersection(geometryA, geometryB)
  }
}

/**
 * Sort features list by priority
 * @param features list features
 * @returns sort list features
 */
export function sortFeaturesByPrio (features: Feature<Geometry>[]): Feature<Geometry>[] {
  return features.sort((a, b) => {
    const aFeature = getFeatureData(a)?.refFeature || a
    const bFeature = getFeatureData(b)?.refFeature || b

    const alayer = getFeatureLayer(aFeature)
    const blayer = getFeatureLayer(bFeature)

    // Sort Feature by signal priority
    if (alayer === FeatureLayerType.Signal && blayer === FeatureLayerType.Signal) {
      const prioA = getSignalPrio(getFeatureData(aFeature)?.attachedSignals[0])
      const prioB = getSignalPrio(getFeatureData(bFeature)?.attachedSignals[0])
      return prioA > prioB ? -1 : 1
    }

    // Sort by feature type priority
    const aPriority = getFeatureMovePriority(aFeature)
    const bPriority = getFeatureMovePriority(bFeature)
    if (aPriority !== bPriority) {
      return aPriority > bPriority ? -1 : 1
    }

    return 0
  })
}

/**
 * Transform Openlayer Features to GeoJSON Features
 * @param features openlayer features
 * @returns collection of GeoJSON Features
 */
export function getGeoPolygons (features: Feature<Geometry>[]): FeatureCollection {
  const geojson = new GeoJSON()
  let polygonFeatures = features.map(f => geojson.writeFeatureObject(f))
  polygonFeatures = polygonFeatures.flatMap(feature => {
    if (feature.geometry.type !== 'GeometryCollection') {
      return feature
    }

    const collection = feature.geometry as GeoJSONGeometryCollection
    return collection.geometries.map(geo => {
      return {
        properties: feature.properties,
        geometry: geo
      } as GeoJSONFeature
    })
  })

  return {
    type: 'FeatureCollection',
    features: polygonFeatures
  }
}

export function toLines (guid: string, s: LineString): Line[] {
  const points = s.getCoordinates()
  let start = points[0]
  let end = points[1]
  let index = 2
  const result = []
  while (true) {
    result.push({
      guid,
      points: [start, end]
    } as Line)
    start = end
    end = points[index++]
    if (!end) {
      break
    }
  }
  return result
}
