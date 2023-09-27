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
import { getCenter } from 'ol/extent'
import { Circle as CircleStyle, Stroke, Style } from 'ol/style'
import { easeOut } from 'ol/easing'
import { unByKey } from 'ol/Observable'
import Geometry from 'ol/geom/Geometry'
import { SubscribeOptions } from 'vuex'
import { getFeatureGUIDs } from '@/util/FeatureExtensions'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { EventsKey } from 'ol/events'
import { FeatureLayerType } from '@/feature/FeatureInfo'

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
    this.featureLayers
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
      const extent = matchingFeatures[ offset ].getGeometry()?.getExtent()
      if (extent !== undefined) {
        this.map.getView().setCenter(getCenter(extent))
        this.map.getView().setZoom(19)
        this.feature = new Feature(new OlPoint(getCenter(extent)))
        this.flash(this.feature)
      }
    }
  }

  private isDeselected (guid: string): boolean {
    return guid === '' || guid === 'DESELECTED'
  }

  private flash (feature: Feature<Geometry>) {
    const filteredLayers = this.featureLayers.filter(
      layer => layer.getLayerType() === FeatureLayerType.Flash
    )
    if (filteredLayers.length === 0) {
      console.warn('Flashing layer missing')
      return
    }

    const flashLayer = filteredLayers[ 0 ]
    flashLayer.getSource()?.addFeature(feature)
    let elapsed = 0
    let now = new Date().getTime()

    this.listernerKey = flashLayer.on('postrender', e => {
      const framState = e.frameState ?? null
      if (framState === null) {
        return
      }

      elapsed = framState.time - now
      if (elapsed >= 5000 || elapsed === 0) {
        now = new Date().getTime()
      }

      if (this.isDeselected(this.guid)) {
        unByKey(this.listernerKey)
        flashLayer.getSource()?.removeFeature(feature)
      }

      const elapsedRatio = elapsed / 5000
      const radius = easeOut(elapsedRatio) * 25 + 5
      const opacity = easeOut(1 - elapsedRatio)
      const style = new Style({
        image: new CircleStyle({
          radius,
          stroke: new Stroke({
            color: 'rgba(255, 0, 0, ' + opacity + ')',
            width: 0.25 + opacity
          })
        })
      })
      feature.setStyle(style)
    })
  }

  private featureHasGuid (
    feature: Feature<Geometry>,
    guid: string | null
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
