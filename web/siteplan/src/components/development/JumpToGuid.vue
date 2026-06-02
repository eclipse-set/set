<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div />
</template>

<script setup lang="ts">
import Feature from 'ol/Feature'
import OlPoint from 'ol/geom/Point'
import Map from 'ol/Map'
import { store } from '@/store'
import { Extent, getCenter } from 'ol/extent'
import { Circle as CircleStyle, Fill, Stroke, Style } from 'ol/style'
import { easeOut, upAndDown } from 'ol/easing'
import { unByKey } from 'ol/Observable'
import Geometry from 'ol/geom/Geometry'
import { getFeatureGUIDs } from '@/util/FeatureExtensions'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { EventsKey } from 'ol/events'
import { FeatureLayerType, FeatureType, FlashFeatureData, createFeature, getFeatureLayerByType, getFeatureType } from '@/feature/FeatureInfo'
import Configuration from '@/util/Configuration'
import TrackFeature from '@/feature/TrackFeature'
import { getMapScale } from '@/util/MapScale'
import { onBeforeUnmount, ref } from 'vue'

/**
 * Development utility to quickly jump to signals by GUID
 *
 * @author Stuecker
 */

const props = defineProps<{
  map: Map
  featureLayers: NamedFeatureLayer[]
}>()

const selectFeatureOffset = ref(0)
const guid = ref('')

let feature: Feature<Geometry>
let listernerKey: EventsKey

const FLASH_DURATION = 5000
const FLASH_LINE_STROKE_WIDTH = 5
const FLASH_CIRCLE_STROKE_WIDTH = 0.25
const FLASH_CIRCLE_RADIUS_MIN = 5
const FLASH_CIRCLE_RADIUS_MAX = 25
const FLASH_COLOR = 'rgba(255, 0, 0, {})'

function setSelectFeatureOffset (offset: number): void {
  selectFeatureOffset.value = offset
}

function isDeselected (guid: string): boolean {
  return guid === '' || guid === 'DESELECTED'
}

function deselected () {
  try {
    if (listernerKey) {
      unByKey(listernerKey)
      const flashLayer = props.featureLayers.find(
        layer => layer.getLayerType() === FeatureLayerType.Flash
      )
      if (flashLayer && flashLayer.getSource()?.hasFeature(feature)) {
        flashLayer.getSource()?.removeFeature(feature)
      }
    }
  } catch (e) {
    console.error(e)
  }
}

function featureHasGuid (
  feat: Feature<Geometry>,
  searchGuid: string | null
): boolean {
  if (!searchGuid) {
    return false
  }

  const guids = getFeatureGUIDs(feat)
    .filter((g): g is string => !!g)
    .map(g => g.toUpperCase())
  const upperSearchGuid = searchGuid?.toUpperCase()
  return guids.some(g => g.search(upperSearchGuid) !== -1)
}

function activeLayer (feat: Feature<Geometry>) {
  const featureType = getFeatureType(feat)
  const layerType = getFeatureLayerByType(featureType)
  const matchLayer = props.featureLayers.find(layer => layer.getLayerType() === layerType)
  if (!matchLayer || matchLayer.isVisible()) {
    return
  }

  matchLayer.setVisible(true)
}

function createFlashFeature (originalFeature: Feature<Geometry>) {
  const geometryType = originalFeature.getGeometry()?.getType()
  const featureData: FlashFeatureData = {
    guid: guid.value,
    refFeature: originalFeature
  }
  if (geometryType && geometryType === 'Point') {
    const extent = originalFeature.getGeometry()?.getExtent()
    return extent !== undefined
      ? createFeature(FeatureType.Flash, featureData, new OlPoint(getCenter(extent)))
      : null
  }

  return createFeature(FeatureType.Flash, featureData, originalFeature.getGeometry())
}

function createFlashStyle (feat: Feature<Geometry>, elapsedRatio: number, resolution: number) {
  const type = feat.getGeometry()?.getType()
  switch (type) {
    case 'Point':{
      const radius = easeOut(elapsedRatio) * FLASH_CIRCLE_RADIUS_MAX + FLASH_CIRCLE_RADIUS_MIN
      const opacity = easeOut(1 - elapsedRatio)

      return new Style({
        image: new CircleStyle({
          radius,
          stroke: new Stroke({
            color: FLASH_COLOR.replace('{}', opacity.toString()),
            width: FLASH_CIRCLE_STROKE_WIDTH + opacity
          })
        })
      })}
    case 'LineString':
    case 'Polygon':
    case 'GeometryCollection':{
      const baseResolution = props.map.getView()
        .getResolutionForZoom(Configuration.getToolboxConfiguration().baseZoomLevel)
      let scale: number = baseResolution / resolution
      if (getMapScale(props.map.getView()) >= Configuration.getLodScale()) {
        scale = TrackFeature.LOD_VIEW_SCALE
      }

      const opacity = upAndDown(elapsedRatio)
      return new Style({
        geometry: feat.getGeometry(),
        fill: new Fill({
          color: FLASH_COLOR.replace('{}', opacity.toString())
        }),
        stroke: new Stroke({
          color: FLASH_COLOR.replace('{}', opacity.toString()),
          width: FLASH_LINE_STROKE_WIDTH * scale
        })
      })}
    default:
      return null
  }
}

function flash (feat: Feature<Geometry>) {
  const flashLayer = props.featureLayers.find(
    layer => layer.getLayerType() === FeatureLayerType.Flash
  )
  if (!flashLayer) {
    console.warn('Flashing layer missing')
    return
  }

  flashLayer.getSource()?.addFeature(feat)
  let elapsed = 0
  let now = new Date().getTime()

  listernerKey = flashLayer.on('postrender', e => {
    const framState = e.frameState ?? null
    if (framState === null) {
      return
    }

    elapsed = framState.time - now
    if (elapsed >= FLASH_DURATION || elapsed === 0) {
      now = new Date().getTime()
    }

    const elapsedRatio = elapsed / FLASH_DURATION
    if (isDeselected(guid.value)) {
      unByKey(listernerKey)
      flashLayer.getSource()?.removeFeature(feat)
    }

    feat.setStyle((_, resolution) => {
      const style = createFlashStyle(feat, elapsedRatio, resolution)
      return style != null ? style : []
    })
  })
}

function jumpToFeature (searchGuid: string): void {
  if (isDeselected(searchGuid)) {
    deselected()
    guid.value = ''
    return
  }

  if (guid.value === searchGuid && selectFeatureOffset.value === 0) {
    return
  }

  if (guid.value !== searchGuid || selectFeatureOffset.value > 0) {
    deselected()
    guid.value = searchGuid
  }

  const matchingFeatures: Feature<Geometry>[] = []
  props.featureLayers.filter(layer => layer.getLayerType() !== FeatureLayerType.Collision)
    .map(layer =>
      layer
        .getSource()
        ?.getFeatures()
        .filter(c => featureHasGuid(c, searchGuid)))
    .forEach(list =>
      list?.forEach(f => matchingFeatures.push(f)))
  const matchingCount = matchingFeatures.length
  store.commit('setMatchingCount', matchingCount)
  if (matchingCount > 0) {
    const offset = selectFeatureOffset.value % matchingCount
    const matchFeature = matchingFeatures[offset]

    const flashFeature = createFlashFeature(matchFeature)
    if (flashFeature !== null) {
      const extent = flashFeature.getGeometry()?.getExtent() as Extent
      activeLayer(matchFeature)
      props.map.getView().setCenter(getCenter(extent))
      feature = flashFeature
      flash(flashFeature)
    }
  } else {
    alert('Das ausgewählte Element exsistiert nicht im Lageplan.')
  }
}

const unsubscribe = store.subscribe((m, s) => {
  if (m.type === 'selectFeature') {
    jumpToFeature(s.selectedFeatureGuid)
  } else if (m.type === 'setSelectFeatureOffset') {
    setSelectFeatureOffset(s.selectedFeatureOffset)
  }
})

onBeforeUnmount(() => {
  unsubscribe()
})
</script>
