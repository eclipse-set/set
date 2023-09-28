/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { Coordinate } from '@/model/Position'
import { SiteplanColorValue, SiteplanState } from '@/model/SiteplanModel'
import { getColor } from '@/model/SiteplanObject'
import Track, { defaultTrackObj } from '@/model/Track'
import TrackSection, { TrackShape } from '@/model/TrackSection'
import TrackSegment, { TrackType } from '@/model/TrackSegment'
import { store } from '@/store'
import Configuration from '@/util/Configuration'
import { getMapScale } from '@/util/MapScale'
import { compare, getpropertypeName } from '@/util/ObjectExtension'
import { Feature } from 'ol'
import { Coordinate as OlCoordinate } from 'ol/coordinate'
import { LineString } from 'ol/geom'
import Geometry from 'ol/geom/Geometry'
import Polygon from 'ol/geom/Polygon'
import { Fill, Stroke, Style } from 'ol/style'
import { createFeature, FeatureType, getFeatureData } from './FeatureInfo'

/**
 * Container for all track features
 *
 * @author Stuecker
 */
export interface TrackSectionFeatureData {
  guid: string
  track: Track
  section: TrackSection
  segment: TrackSegment
  outlineGeometry: Polygon
  outlineEdgeA: LineString
  outlineEdgeB: LineString
}
export default class TrackFeature extends LageplanFeature<Track> {
  // In LOD-mode, use a fixed scale equivalent to 1:1000
  static LOD_VIEW_SCALE = 0.337

  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    store.commit('defaultTrackWidth', Configuration.getTrackWidth())
    return model.tracks.flatMap(track =>
      track?.sections.flatMap(section =>
        section.segments?.flatMap(segment => this.createTrackFeatures(track, section, segment))))
  }

  private createTrackFeatures (
    track: Track,
    trackSection: TrackSection,
    trackSegment: TrackSegment
  ): Feature<Geometry>[] {
    return [
      this.createTrackSectionFeature(
        track,
        trackSection,
        trackSegment
      ), this.createTrackOutlineFeature(track.guid, trackSection, trackSegment)
    ]
  }

  private createTrackOutlineFeature (
    guid: string,
    trackSection: TrackSection,
    trackSegment: TrackSegment
  ): Feature<Geometry> {
    const coordinates: OlCoordinate[] = []
    trackSegment.positions.forEach((position: Coordinate) => coordinates.push([position.x, position.y]))
    if (coordinates.length < 1) {
      throw Error('Error creating track section with GUID ' + trackSection.guid)
    }

    let outlineGeometry = this.createTrackOutlineGeometry(coordinates, store.state.trackOutlineWidth)
    const data = {
      guid,
      outlineGeometry
    }

    store.subscribe((m, s) => {
      if (m.type === 'setTrackOutlineWidth') {
        outlineGeometry = this.createTrackOutlineGeometry(coordinates, s.trackOutlineWidth)
        if (data) {
          data.outlineGeometry = outlineGeometry
        }
      }
    })

    const feature = createFeature(FeatureType.TrackOutline, data, outlineGeometry)

    feature.setStyle(() => {
      if (store.state.trackOutlineVisible) {
        const outlineStyle = new Style({
          geometry: outlineGeometry,
          fill: new Fill({
            color: 'rgba(153,255,153 ,0.3)'
          }),
          stroke: new Stroke({
            width: 2,
            color: 'rgba(153, 255,153, 1)'
          })
        })
        return [outlineStyle]
      }

      return []
    })
    return feature
  }

  private createTrackSectionFeature (
    track: Track,
    trackSection: TrackSection,
    trackSegment: TrackSegment
  ): Feature<Geometry> {
    const coordinates: OlCoordinate[] = []
    trackSegment.positions.forEach((position: Coordinate) => coordinates.push([position.x, position.y]))
    if (coordinates.length < 1) {
      throw Error('Error creating track section with GUID ' + trackSection.guid)
    }

    const outlineWidth = store.state.trackOutlineWidth
    let outlineGeometry = this.createTrackOutlineGeometry(coordinates, store.state.trackOutlineWidth)
    const data: TrackSectionFeatureData = {
      guid: track.guid,
      track,
      section: trackSection,
      segment: trackSegment,
      outlineGeometry,
      outlineEdgeA: new LineString(coordinates.map(coor => [coor[0] + outlineWidth, coor[1] + outlineWidth])),
      outlineEdgeB: new LineString(coordinates.map(coor => [coor[0] - outlineWidth, coor[1] - outlineWidth]))
    }

    store.subscribe((m, s) => {
      if (m.type === 'setTrackOutlineWidth') {
        outlineGeometry = this.createTrackOutlineGeometry(coordinates, s.trackOutlineWidth)
        if (data) {
          data.outlineGeometry = outlineGeometry
          data.outlineEdgeA = new LineString(coordinates.map(coor => (
            [coor[0] + s.trackOutlineWidth, coor[1] + s.trackOutlineWidth]
          )))
          data.outlineEdgeB = new LineString(coordinates.map(coor => (
            [coor[0] - s.trackOutlineWidth, coor[1] - s.trackOutlineWidth]
          )))
        }
      }
    })

    const feature = createFeature(FeatureType.Track, data, new LineString(coordinates), track.designations?.at(0)?.name)

    if (trackSegment.type === undefined) {
      trackSegment.type = []
    }

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      let scale: number = baseResolution / resolution
      if (getMapScale(this.map.getView()) >= Configuration.getLodScale()) {
        scale = TrackFeature.LOD_VIEW_SCALE
      }

      const widthSet = new Set(trackSegment.type.map(type => this.getTrackSectionWidth(type)))
      const width = widthSet.size === 1 ? widthSet.values().next().value : this.getTrackSectionWidth(TrackType.Other)
      const style = new Style({
        stroke: new Stroke({
          color: this.getTrackColor(track, trackSection.shape, trackSection.guid),
          width: scale * width
        })
      })

      if (store.state.trackOutlineVisible) {
        const outlineStyle = new Style({
          geometry: outlineGeometry,
          fill: new Fill({
            color: 'rgba(153,255,153 ,0.3)'
          }),
          stroke: new Stroke({
            width: 2,
            color: 'rgba(153, 255,153, 1)'
          })
        })
        return [style, outlineStyle]
      }

      return style
    })

    return feature
  }

  private getTrackSectionWidth (sectionTrackType: TrackType): number {
    switch (sectionTrackType) {
      case TrackType.MainTrack:
      case TrackType.PassingMainTrack:
      case TrackType.RouteTrack:
        return store.state.mainTrackWidth
      case TrackType.SideTrack:
      case TrackType.ConnectingTrack:
        return store.state.sideTrackWidth
      case TrackType.Other:
      default:
        return store.state.otherTrackWidth
    }
  }

  private getTrackColor (track: Track, sectionType: TrackShape, sectionGUID: string): number[] {
    switch (sectionType) {
      case TrackShape.Straight:
      case TrackShape.Curve:
      case TrackShape.Clothoid:
      case TrackShape.Blosscurve:
      case TrackShape.BlossCurvedSimple:
      case TrackShape.DirectionalStraightKinkEnd:
      case TrackShape.KmJump:
      case TrackShape.Other:
      // use the track color or black for implemented types
        return getColor(track, sectionGUID) ?? [0, 0, 0]
      default:
      // blue for not yet implemented types
        return [0, 0, 255]
    }
  }

  private createTrackOutlineGeometry (coors: OlCoordinate[], outlineWidth: number): Polygon {
    const lineA: OlCoordinate[] = []
    const lineB: OlCoordinate[] = []
    coors.forEach(coor => {
      lineA.push([coor[0] + outlineWidth, coor[1] + outlineWidth])
      lineB.push([coor[0] - outlineWidth, coor[1] - outlineWidth])
    })
    const polygonCoors = lineA.concat([...lineB].reverse()).map(coor => coor)
    polygonCoors.push(polygonCoors[0])
    return new Polygon([polygonCoors])
  }

  setFeatureColor (
    feature: Feature<Geometry>,
    color?: number[] | undefined,
    partID?: string | undefined
  ): Feature<Geometry> {
    return color
      ? super.setFeatureColor(feature, color, partID)
      : this.setFeatureRegionColor(feature)
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected setFeatureRegionColor (feature: Feature<Geometry>): Feature<Geometry> {
    const trackfeatureData = getFeatureData(feature) as TrackSectionFeatureData
    if (!trackfeatureData.track) {
      return feature
    }

    const section = trackfeatureData.section
    this.setObjectColor(
      trackfeatureData.track,
      section.guid,
      this.getRegionColor(feature, section.guid)
    )
    return feature
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    const isDiffDesignation = this.compareChangeModel(initial, final, [
      {
        prop: getpropertypeName(defaultTrackObj(), x => x.designations),
        partID: 'label'
      }
    ])
    const initialTracks = this.getObjectsModel(initial)
    const finalTracks = this.getObjectsModel(final)
    const isDiffSection = initialTracks.some(initialTrack => {
      const finalTrack = finalTracks.find(x => x.guid === initialTrack.guid)
      if (!finalTrack) {
        return false
      }

      let isNotEqual = false
      const initialSections = initialTrack.sections
      const finalSections = finalTrack.sections
      if (initialSections.length > finalSections.length) {
        const missingSections = this.getMissingElements<TrackSection>(initialSections, finalSections, x => x.guid)
        this.coloringMissingElements(initialTrack, missingSections, SiteplanColorValue.COLOR_REMOVED)
        isNotEqual = true
      } else if (initialSections.length < finalSections.length) {
        const missingSections = this.getMissingElements<TrackSection>(finalSections, initialSections, x => x.guid)
        this.coloringMissingElements(finalTrack, missingSections, SiteplanColorValue.COLOR_ADDED)
        isNotEqual = true
      }

      finalTrack.sections.forEach(finalSection => {
        const initialSection = initialTrack.sections.find(x => x.guid === finalSection.guid)
        if (initialSection && compare(finalSection, initialSection)) {
          this.setObjectColor(initialTrack, finalSection.guid, SiteplanColorValue.COLOR_REMOVED)
          this.setObjectColor(finalTrack, finalSection.guid, SiteplanColorValue.COLOR_ADDED)
          isNotEqual = true
        }
      })
      return isNotEqual
    })

    return isDiffDesignation || isDiffSection
      ? this.createCompareFeatures(initial, final)
      : this.getFeatures(final)
  }
}
