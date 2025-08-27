<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div>
    <h2>Gleis: {{ trackLabel }}</h2>
    <ul>
      <li v-if="isDevelopmentMode">
        TOP_Kante: {{ trackGuid }}
      </li>
      <li>GUID: {{ trackSection.guid }}</li>
      <li>Form: {{ trackTrackShapeToText() }}</li>
      <li>Gleisart: {{ trackType() }}</li>
    </ul>
  </div>
</template>

<script lang="ts">
import { getFeatureData, getFeatureLabel } from '@/feature/FeatureInfo'
import { TrackSectionFeatureData } from '@/feature/TrackFeature'
import TrackSection, { TrackShape } from '@/model/TrackSection'
import TrackSegment, { TrackType } from '@/model/TrackSegment'
import Configuration from '@/util/Configuration'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import { Options, Vue } from 'vue-class-component'

/**
 * Popup contents for track features
 *
 * @author Peters
 */
@Options({
  props: {
    // As TrackSection is an interface, we cannot bind to the intended type
    feature: Object
  },
  computed: {
    trackSection: function () {
      return this.getData()?.section
    },
    trackSegment: function () {
      return this.getData()?.segment
    },
    trackLabel: function () {
      return getFeatureLabel(this.feature)
    },
    trackGuid: function () {
      return this.getData()?.guid
    }
  }
})
export default class TrackSectionPopup extends Vue {
  trackSection!: TrackSection
  trackSegment!: TrackSegment
  feature!: Feature<Geometry>
  trackLabel!: string
  trackGuid!: string
  isDevelopmentMode: boolean = Configuration.developmentMode()

  private getData (): TrackSectionFeatureData | undefined {
    return getFeatureData(this.feature)
  }

  trackTrackShapeToText (): string {
    switch (this.trackSection.shape) {
      case TrackShape.Straight:
        return 'Gerade'
      case TrackShape.Curve:
        return 'Bogen'
      case TrackShape.Clothoid:
        return 'Klothoide'
      case TrackShape.Blosscurve:
        return 'Blosskurve'
      case TrackShape.BlossCurvedSimple:
        return 'Bloss einfach geschwungen'
      case TrackShape.Other:
        return 'Sonstige'
      case TrackShape.DirectionalStraightKinkEnd:
        return 'Richtgerade Knick am Ende 200 gon'
      case TrackShape.KmJump:
        return 'Kilometersprung'
      case TrackShape.TransitionCurveSForm:
        return 'Übergangsbogen S-Form (nicht implementiert)'
      case TrackShape.SFormSimpleCurved:
        return 'S-Form einfach geschwungen (nicht implementiert)'
      default:
        return 'Unbekannt'
    }
  }

  trackType (): string {
    if (this.trackSegment.type.length === 0) {
      return 'Unbekannt'
    } else {
      return this.trackSegment.type
        .map(type => this.trackTypeToText(type))
        .join(', ')
    }
  }

  trackTypeToText (type: TrackType): string {
    switch (type) {
      case TrackType.Other:
        return 'Sonstige'
      case TrackType.SideTrack:
        return 'Nebengleis'
      case TrackType.ConnectingTrack:
        return 'Anschlussgleis'
      case TrackType.MainTrack:
        return 'Hauptgleis'
      case TrackType.PassingMainTrack:
        return 'Durchgehendes Hauptgleis'
      case TrackType.RouteTrack:
        return 'Streckengleis'
      default:
        return 'Unbekannt'
    }
  }
}
</script>
