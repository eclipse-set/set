/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Rectangle } from '@/collision/CollisionExtension'
import ModelError from '@/model/Error'
import Feature from 'ol/Feature'
import { Geometry, Polygon } from 'ol/geom'
import FeatureMetadata, { ModelType } from './FeatureMetadata'

export enum FeatureType {
  Signal,
  Error,
  FMA,
  Platform,
  PZB,
  PZBGU,
  Route,
  RouteMarker,
  Station,
  Track,
  TrackDesignationMarker,
  TrackLock,
  TrackSectionMarker,
  TrackSwitchEndMarker,
  TrackSwitch,
  SignalRouteMarker,
  Collision,
  TrackOutline,
  TrackClose,
  ExternalElementControl,
  LockKey,
  SheetCut,
  Cant,
  CantLine,
  Unknown,
  Flash,
  TrackDirectionArrow,
}

export enum FeatureLayerType
{
    Error,
    TrackSwitch,
    Track,
    Station,
    Route,
    PZB,
    TrackLock,
    FMA,
    ElementControl,
    Signal,
    Collision,
    SheetCut,
    Cant,
    Unknown,
    Flash,
    Measure,
    TrackDirection,
}

export function getFeatureLayerByType (type: FeatureType): FeatureLayerType {
  switch (type) {
    case FeatureType.Signal:
      return FeatureLayerType.Signal
    case FeatureType.Error:
      return FeatureLayerType.Error
    case FeatureType.FMA:
      return FeatureLayerType.FMA
    case FeatureType.Platform:
    case FeatureType.Station:
      return FeatureLayerType.Station
    case FeatureType.PZB:
    case FeatureType.PZBGU:
      return FeatureLayerType.PZB
    case FeatureType.SignalRouteMarker:
    case FeatureType.RouteMarker:
    case FeatureType.Route:
      return FeatureLayerType.Route
    case FeatureType.Track:
    case FeatureType.TrackDesignationMarker:
    case FeatureType.TrackSectionMarker:
    case FeatureType.TrackClose:
      return FeatureLayerType.Track
    case FeatureType.TrackLock:
    case FeatureType.LockKey:
      return FeatureLayerType.TrackLock
    case FeatureType.ExternalElementControl:
      return FeatureLayerType.ElementControl
    case FeatureType.TrackSwitchEndMarker:
    case FeatureType.TrackSwitch:
      return FeatureLayerType.TrackSwitch
    case FeatureType.Collision:
    case FeatureType.TrackOutline:
      return FeatureLayerType.Collision
    case FeatureType.SheetCut:
      return FeatureLayerType.SheetCut
    case FeatureType.Cant:
    case FeatureType.CantLine:
      return FeatureLayerType.Cant
    case FeatureType.Unknown:
      return FeatureLayerType.Unknown
    case FeatureType.TrackDirectionArrow:
      return FeatureLayerType.TrackDirection
    default: throw new Error('Missing layer for type: ' + type)
  }
}

export function getFeatureLayerDefaultVisibility (type: FeatureLayerType) : boolean | undefined {
  // Hide collision layer, cants and unknown objects by default
  return ![
    FeatureLayerType.Collision,
    FeatureLayerType.Cant,
    FeatureLayerType.Unknown,
    FeatureLayerType.TrackDirection
  ].includes(type)
}

export function getFeatureLayerDisplayName (type: FeatureLayerType) : string {
  switch (type) {
    case FeatureLayerType.Collision: return 'Kollisionen'
    case FeatureLayerType.Error: return 'Fehler'
    case FeatureLayerType.TrackSwitch: return 'Weichen'
    case FeatureLayerType.Track: return 'Gleise'
    case FeatureLayerType.Station: return 'Bahnsteige'
    case FeatureLayerType.Route: return 'Strecken(-elemente)'
    case FeatureLayerType.PZB: return 'Punktförmige Zugbeinflussungen (PZB)'
    case FeatureLayerType.TrackLock: return 'Gleissperren'
    case FeatureLayerType.FMA: return 'Achszähler'
    case FeatureLayerType.ElementControl: return 'Element Ansteuerungen'
    case FeatureLayerType.Signal: return 'Signale'
    case FeatureLayerType.Flash: return 'Hervorhebungen'
    case FeatureLayerType.SheetCut: return 'Blattschnitte'
    case FeatureLayerType.Cant: return 'Überhöhungen'
    case FeatureLayerType.Unknown: return 'Weitere Objekte'
    case FeatureLayerType.Measure: return 'Messen'
    case FeatureLayerType.TrackDirection: return 'Gleisausrichtung'
    default: throw new Error('Missing name for layer type: ' + type)
  }
}

export function getFeatureName (type: FeatureType): string {
  switch (type) {
    case FeatureType.Signal:
      return 'Signal'
    case FeatureType.Error:
      return 'Fehler'
    case FeatureType.FMA:
      return 'Achszähler'
    case FeatureType.Platform:
    case FeatureType.Station:
      return 'Bahnsteig'
    case FeatureType.PZB:
    case FeatureType.PZBGU:
      return 'PZB'
    case FeatureType.SignalRouteMarker:
      return 'Signalkilometrierung'
    case FeatureType.RouteMarker:
      return 'Kilometermarker'
    case FeatureType.Route:
      return 'Strecke'
    case FeatureType.Track:
      return 'Gleis'
    case FeatureType.TrackDesignationMarker:
      return 'Gleisbezeichner'
    case FeatureType.TrackSectionMarker:
      return 'Gleisbezeichner'
    case FeatureType.TrackLock:
      return 'Gleissperre'
    case FeatureType.TrackSwitchEndMarker:
    case FeatureType.TrackSwitch:
      return 'Weiche'
    case FeatureType.Collision:
      return 'Kollision'
    case FeatureType.TrackOutline:
      return 'Gleiskollision'
    case FeatureType.TrackClose:
      return 'Gleisabschluss'
    case FeatureType.ExternalElementControl:
      return 'Aussenelementansteuerung'
    case FeatureType.LockKey:
      return 'Schlüsselsperre'
    case FeatureType.SheetCut:
      return 'Blattschnitte'
    case FeatureType.Cant:
      return 'Überhöhungspunkt'
    case FeatureType.CantLine:
      return 'Überhöhungslinie'
    case FeatureType.Unknown:
      return 'Unbekannt'
    case FeatureType.TrackDirectionArrow:
      return 'GleisausrichtungsPfeil'
    default:
      console.error('Missing name for type: ' + type)
      return 'Unbenanntes Objekt'
  }
}

export function getFeatureMovePriority (feature: Feature<Geometry>) {
  const type = getFeatureType(feature)
  switch (type) {
    case FeatureType.Signal:
      return 1
    case FeatureType.Error:
    case FeatureType.FMA:
    case FeatureType.Platform:
    case FeatureType.Station:
    case FeatureType.PZB:
    case FeatureType.PZBGU:
    case FeatureType.SignalRouteMarker:
    case FeatureType.RouteMarker:
    case FeatureType.Route:
    case FeatureType.Track:
    case FeatureType.TrackDesignationMarker:
    case FeatureType.TrackSectionMarker:
    case FeatureType.TrackLock:
    case FeatureType.TrackSwitchEndMarker:
    case FeatureType.TrackSwitch:
    case FeatureType.Collision:
    case FeatureType.TrackOutline:
    case FeatureType.ExternalElementControl:
    case FeatureType.LockKey:
    case FeatureType.TrackDirectionArrow:
      return 0
    default:
      return -1
  }
}

export function getFeatureType (feature: Feature<Geometry>): FeatureType {
  return (feature.get('data') as FeatureMetadata).type
}

export function getFeatureBounds (feature: Feature<Geometry>): Polygon[] {
  return (feature.get('data') as FeatureMetadata).bounds
}

export function getFeatureBoundArea (feature: Feature<Geometry>): Rectangle[] {
  return (feature.get('data') as FeatureMetadata).boundRectangle
}

export function getFeatureLabel (feature: Feature<Geometry>): string {
  if (getFeatureType(feature) === FeatureType.Error) {
    return (getFeatureData(feature) as ModelError).message
  }

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
  return (feature.get('data') as FeatureMetadata)?.model
}

export function getFeatureGUID (feature: Feature<Geometry>): ModelType {
  return (feature.get('data') as FeatureMetadata)?.guid
}

export interface FlashFeatureData {
  guid: string
  refFeature: Feature<Geometry>
}
