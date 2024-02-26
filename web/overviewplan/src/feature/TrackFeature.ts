/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import UebersichtplanFeature from '@/feature/UebersichtplanFeature'
import { Coordinate } from '@/model/Position'
import { SiteplanColorValue, SiteplanState } from '@/model/SiteplanModel'
import { getColor } from '@/model/SiteplanObject'
import Track, { defaultTrackObj } from '@/model/Track'
import TrackSection, { TrackShape } from '@/model/TrackSection'
import { getMapScale } from '@/util/MapScale'
import { compare, getpropertypeName } from '@/util/ObjectExtension'
import { Feature } from 'ol'
import { Coordinate as OlCoordinate } from 'ol/coordinate'
import { LineString } from 'ol/geom'
import Geometry from 'ol/geom/Geometry'
import { Stroke, Style } from 'ol/style'
import { createFeature, FeatureType, getFeatureData } from './FeatureInfo'
import Configuration from '@/util/Configuration'
import { store } from '@/store'

/**
 * Container for all track features
 *
 * @author Stuecker
 */
export interface TrackSectionFeatureData {
  guid: string
  track: Track
  section: TrackSection
}
export default class TrackFeature extends UebersichtplanFeature<Track> {
  // In LOD-mode, use a fixed scale equivalent to 1:1000
  static LOD_VIEW_SCALE = 0.337
  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return model.tracks.flatMap(track =>
      track?.sections.flatMap(section => {
        return this.createTrackFeatures(track, section)
      }))
  }

  private createTrackFeatures (
    track: Track,
    trackSection: TrackSection
  ): Feature<Geometry>[] {
    return [
      this.createTrackSectionFeature(
        track,
        trackSection
      )
    ]
  }

  private createTrackSectionFeature (
    track: Track,
    trackSection: TrackSection
  ): Feature<Geometry> {
    const coordinates: OlCoordinate[] = []

    trackSection.segments.forEach(segment =>
      segment.positions.forEach((position:Coordinate) =>
        coordinates.push([position.x * 1000, position.y * 1000])))
    if (coordinates.length < 1) {
      throw Error('Error creating track section with GUID ' + trackSection.guid)
    }

    coordinates.sort((a, b) => a[0] - b[0])
    const data: TrackSectionFeatureData = {
      guid: track.guid,
      track,
      section: trackSection
    }

    const feature = createFeature(FeatureType.Track, data, new LineString(coordinates), track.designations?.at(0)?.name)

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(20)
      let scale: number = baseResolution / resolution
      if (getMapScale(this.map.getView()) >= Configuration.getLodScale()) {
        scale = TrackFeature.LOD_VIEW_SCALE
      }

      const style = new Style({
        stroke: new Stroke({
          color: this.getTrackColor(track, trackSection, trackSection.shape),
          width: scale * 5
        })
      })

      return style
    })

    return feature
  }

  private getTrackColor (
    track: Track,
    section: TrackSection,
    sectionType: TrackShape
  ): string|number[] {
    if (store.state.trackSectionColorVisible && section.color !== null) {
      return section.color
    }

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
        return getColor(track, section.guid) ?? [0, 0, 0]
      default:
      // blue for not yet implemented types
        return [0, 0, 255]
    }
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
