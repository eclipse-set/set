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
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import Feature from 'ol/Feature'
import OlPoint from 'ol/geom/Point'
import Map from 'ol/Map'
import { store } from '@/store'
import { Extent, getCenter } from 'ol/extent'
import { Circle as CircleStyle, Fill, Stroke, Style } from 'ol/style'
import { easeOut, upAndDown } from 'ol/easing'
import { unByKey } from 'ol/Observable'
import Geometry from 'ol/geom/Geometry'
import { SubscribeOptions } from 'vuex'
import { getFeatureGUIDs } from '@/util/FeatureExtensions'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { EventsKey } from 'ol/events'
import { FeatureLayerType, FeatureType, FlashFeatureData, createFeature, getFeatureLayerByType, getFeatureType } from '@/feature/FeatureInfo'
import Configuration from '@/util/Configuration'
import TrackFeature from '@/feature/TrackFeature'
import {getMapScale} from '@/util/MapScale'

/**
 * Development utility to quickly jump to signals by GUID
 *
 * @author Stuecker
 */
@Options({
  created () {
    this.unsubscribe = store.subscribe((m, s) => {
      if (m.type === 'selectFeature') {
        this.jumpToFeature(s.selectedFeatureGuid)
      } else if (m.type === 'setSelectFeatureOffset') {
        this.setSelectFeatureOffset(s.selectedFeatureOffset)
      }
    })
  },
  props: {
    map: Map,
    featureLayers: Object,
    model: Object
  },
  beforeUnmount () {
    this.unsubscribe()
  }
})
export default class JumpToGuid extends Vue {
  featureLayers!: NamedFeatureLayer[]
  map!: Map
  unsubscribe: SubscribeOptions | undefined
  selectFeatureOffset = 0
  feature!: Feature<Geometry>
  listernerKey!: EventsKey
  guid = ''
  readonly FLASH_DURATION = 5000
  readonly FLASH_LINE_STROKE_WIDTH = 5
  readonly FLASH_CIRCLE_STROKE_WIDTH = 0.25
  readonly FLASH_CIRCLE_RADIUS_MIN = 5
  readonly FLASH_CIRCLE_RADIUS_MAX = 25
  readonly FLASH_COLOR = 'rgba(255, 0, 0, {})'

  setSelectFeatureOffset (offset: number): void {
    this.selectFeatureOffset = offset
  }

  public jumpToFeature (guid: string): void {
    if (this.isDeselected(guid)) {
      this.deselected()
      this.guid = ''
      return
    }

    if (this.guid === guid && this.selectFeatureOffset === 0) {
      return
    }

    if (this.guid !== guid || this.selectFeatureOffset > 0) {
      this.deselected()
      this.guid = guid
    }

    const matchingFeatures: Feature<Geometry>[] = []
    this.featureLayers.filter(layer => layer.getLayerType() !== FeatureLayerType.Collision)
      .map(layer =>
        layer
          .getSource()
          ?.getFeatures()
          .filter(c => this.featureHasGuid(c, guid)))
      .forEach(list =>
        list?.forEach(feature => matchingFeatures.push(feature)))
    const matchingCount = matchingFeatures.length
    store.commit('setMatchingCount', matchingCount)
    if (matchingCount > 0) {
      const offset = this.selectFeatureOffset % matchingCount
      const matchFeature = matchingFeatures[ offset ]

      const flashFeature = this.createFlashFeature(
        matchFeature
      )
      if (flashFeature !== null) {
        const extent = flashFeature.getGeometry()?.getExtent() as Extent
        this.activeLayer(matchFeature)
        this.map.getView().setCenter(getCenter(extent))
        this.feature = flashFeature
        this.flash(flashFeature)
      }
    } else {
      alert('Das ausgewählte Element exsistiert nicht im Lageplan.')
    }
  }

  private createFlashFeature (originalFeature: Feature<Geometry>) {
    const geometryType = originalFeature.getGeometry()?.getType()
    const featureData: FlashFeatureData = {
      guid: this.guid,
      refFeature: originalFeature
    }
    if (geometryType && geometryType === 'Point') {
      const extent = originalFeature.getGeometry()?.getExtent()
      return extent !== undefined
        ? createFeature(FeatureType.Flash, featureData,new OlPoint(getCenter(extent)))
        : null
    }

    return createFeature(FeatureType.Flash, featureData ,originalFeature.getGeometry())
  }

  private activeLayer (feature: Feature<Geometry>) {
    const featureType = getFeatureType(feature)
    const layerType = getFeatureLayerByType(featureType)
    const matchLayer = this.featureLayers.find(layer => layer.getLayerType() === layerType)
    if (!matchLayer || matchLayer.isVisible()) {
      return
    }

    matchLayer.setVisible(true)
  }

  private isDeselected (guid: string): boolean {
    return guid === '' || guid === 'DESELECTED'
  }

  private flash (feature: Feature<Geometry>) {
    const flashLayer = this.featureLayers.find(
      layer => layer.getLayerType() === FeatureLayerType.Flash
    )
    if (!flashLayer) {
      console.warn('Flashing layer missing')
      return
    }

    flashLayer.getSource()?.addFeature(feature)
    let elapsed = 0
    let now = new Date().getTime()

    this.listernerKey = flashLayer.on('postrender', e => {
      const framState = e.frameState ?? null
      if (framState === null) {
        return
      }

      elapsed = framState.time - now
      if (elapsed >= this.FLASH_DURATION || elapsed === 0) {
        now = new Date().getTime()
      }

      const elapsedRatio = elapsed / this.FLASH_DURATION
      if (this.isDeselected(this.guid)) {
        unByKey(this.listernerKey)
        flashLayer.getSource()?.removeFeature(feature)
      }

      feature.setStyle((_, resolution) => {
        const style = this.createFlashStyle(feature, elapsedRatio, resolution)
        return style != null ? style : []
      })
    })
  }

  private createFlashStyle (feature: Feature<Geometry>, elapsedRatio: number, resolution: number) {
    const type = feature.getGeometry()?.getType()
    switch (type) {
      case 'Point':{
        const radius = easeOut(elapsedRatio) * this.FLASH_CIRCLE_RADIUS_MAX + this.FLASH_CIRCLE_RADIUS_MIN
        const opacity = easeOut(1 - elapsedRatio)

        return new Style({
          image: new CircleStyle({
            radius,
            stroke: new Stroke({
              color: this.FLASH_COLOR.replace('{}', opacity.toString()),
              width: this.FLASH_CIRCLE_STROKE_WIDTH + opacity
            })
          })
        })}
      case 'LineString':
      case 'Polygon':
      case 'GeometryCollection':{
        const baseResolution = this.map.getView()
          .getResolutionForZoom(Configuration.getToolboxConfiguration().baseZoomLevel)
        let scale: number = baseResolution / resolution
        if (getMapScale(this.map.getView()) >= Configuration.getLodScale()) {
          scale = TrackFeature.LOD_VIEW_SCALE
        }

        const opacity = upAndDown(elapsedRatio)
        return new Style({
          geometry: feature.getGeometry(),
          fill: new Fill({
            color: this.FLASH_COLOR.replace('{}', opacity.toString())
          }),
          stroke: new Stroke({
            color: this.FLASH_COLOR.replace('{}', opacity.toString()),
            width: this.FLASH_LINE_STROKE_WIDTH * scale
          })
        })}
      default:
        return null
    }
  }

  private featureHasGuid (
    feature: Feature<Geometry>,
    guid: string|null
  ): boolean {
    if (!guid) {
      return false
    }

    const guids = getFeatureGUIDs(feature).map(guid => guid?.toUpperCase())
    const searchGuid = guid?.toUpperCase()
    return guids.some(guid => guid.search(searchGuid) !== -1)
  }

  private deselected () {
    try {
      if (this.listernerKey) {
        unByKey(this.listernerKey)
        const flashLayer = this.featureLayers.find(
          layer => layer.getLayerType() === FeatureLayerType.Flash
        )
        if (flashLayer && flashLayer.getSource()?.hasFeature(this.feature)) {
          flashLayer.getSource()?.removeFeature(this.feature)
        }
      }
    } catch (e) {
      console.error(e)
    }
  }
}
</script>
