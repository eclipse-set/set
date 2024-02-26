/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import Feature from 'ol/Feature'
import { Geometry } from 'ol/geom'
import FeatureMetadata, { ModelType } from './FeatureMetadata'

export enum FeatureType {
  Track
}

export enum FeatureLayerType
{
    Track,
    Flash
}

export function getFeatureLayerByType (type: FeatureType): FeatureLayerType {
  switch (type) {
    case FeatureType.Track:
      return FeatureLayerType.Track
    default: throw new Error('Missing layer for type: ' + type)
  }
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export function getFeatureLayerDefaultVisibility (type: FeatureLayerType) : boolean | undefined {
  return true
}

export function getFeatureLayerDisplayName (type: FeatureLayerType) : string {
  switch (type) {
    case FeatureLayerType.Track:
      return 'Gleise'
    case FeatureLayerType.Flash: return 'Hervorhebungen'
    default: throw new Error('Missing name for layer type: ' + type)
  }
}

export function getFeatureName (type: FeatureType): string {
  switch (type) {
    case FeatureType.Track:
      return 'Gleis'
    default:
      console.error('Missing name for type: ' + type)
      return 'Unbenanntes Objekt'
  }
}

export function getFeatureMovePriority (feature: Feature<Geometry>) {
  const type = getFeatureType(feature)
  switch (type) {
    case FeatureType.Track:
      return 0
    default:
      return -1
  }
}

export function getFeatureType (feature: Feature<Geometry>): FeatureType {
  return (feature.get('data') as FeatureMetadata).type
}

export function getFeatureLabel (feature: Feature<Geometry>): string {
  const label = (feature.get('data') as FeatureMetadata).label
  if (label === undefined || label.length === 0) {
    return 'Keine Bezeichnung'
  }

  return label
}

export function getFeatureLayer (feature: Feature<Geometry>): FeatureLayerType {
  return getFeatureLayerByType(getFeatureType(feature))
}

export function createFeature (
  type: FeatureType,
  model: ModelType,
  geometry: Geometry | undefined,
  label?: string
) : Feature<Geometry> {
  return new Feature<Geometry>(
    {
      name: getFeatureName(type),
      data: new FeatureMetadata(model, type, label),
      geometry
    }
  )
}

export function getFeatureData (feature: Feature<Geometry>): ModelType {
  return (feature.get('data') as FeatureMetadata).model
}

export function getFeatureGUID (feature: Feature<Geometry>): ModelType {
  return (feature.get('data') as FeatureMetadata).guid
}
