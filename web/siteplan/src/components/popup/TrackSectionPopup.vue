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

<script setup lang="ts">
import { getFeatureData, getFeatureLabel } from '@/feature/FeatureInfo'
import { TrackSectionFeatureData } from '@/feature/TrackFeature'
import TrackSection, { TrackShape } from '@/model/TrackSection'
import TrackSegment, { TrackType } from '@/model/TrackSegment'
import Configuration from '@/util/Configuration'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import { computed } from 'vue'

/**
 * Popup contents for track features
 *
 * @author Peters
 */

const props = defineProps<{
  feature: Feature<Geometry>
}>()

function getData (): TrackSectionFeatureData | undefined {
  return getFeatureData(props.feature) as TrackSectionFeatureData | undefined
}

const trackSection = computed<TrackSection>(() => {
  return getData()?.section as TrackSection
})

const trackSegment = computed<TrackSegment>(() => {
  return getData()?.segment as TrackSegment
})

const trackLabel = computed(() => {
  return getFeatureLabel(props.feature)
})

const trackGuid = computed(() => {
  return getData()?.guid as string
})

const isDevelopmentMode = Configuration.developmentMode()

function trackTrackShapeToText (): string {
  switch (trackSection.value.shape) {
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

function trackTypeToText (type: TrackType): string {
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

function trackType (): string {
  if (trackSegment.value.type.length === 0) {
    return 'Unbekannt'
  }

  return trackSegment.value.type
    .map(type => trackTypeToText(type))
    .join(', ')
}
</script>
