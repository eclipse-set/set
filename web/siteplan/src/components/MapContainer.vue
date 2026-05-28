<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div class="map-container">
    <div
      id="map-root"
      ref="mapRoot"
    >
      <div class="custom-control-container">
        <MapSourceSelection v-if="isSiteplan" />
        <LayerControl v-if="isDevelopmentMode && isSiteplan" />
        <ModelSummaryControl v-if="isDevelopmentMode" />
        <SettingEditor v-if="isSiteplan" />
      </div>
    </div>
    <div
      id="side-info-container"
      ref="side-info-container"
      class="side-info-container-closed"
    />
  </div>
</template>

<script setup lang="ts">
import LayerControl from '@/components/development/LayerControl.vue'
import ModelSummaryControl from '@/components/development/ModelSummaryControl.vue'
import SettingEditor from '@/components/development/SettingEditor.vue'
import MapSourceSelection from '@/components/MapSourceSelection.vue'
import { PlanProModelType, store } from '@/store'
import Configuration from '@/util/Configuration'
import ExportControl from '@/util/Controls/ExportControl'
import RotateViewControl from '@/util/Controls/RotateViewControl'
import ScaleBarControl from '@/util/Controls/ScaleBarControl'
import { getPixelProMeterAtScale, setMapScale } from '@/util/MapScale'
import OlMap from 'ol/Map'
import 'ol/ol.css'
import OlView from 'ol/View'
import { computed, onMounted, ref } from 'vue'

/**
 * Container for the open layers map
 *
 * @author Stuecker
 */

const isDevelopmentMode = Configuration.developmentMode()
const map: OlMap = store.state.map
const mapRoot = ref<HTMLDivElement | null>(null)

const scaleLocked = ref(0)
let scaleLine: ScaleBarControl | undefined

const isSiteplan = computed(() =>
  store.state.planproModelType === PlanProModelType.SITEPLAN)

function getMapScale (): number {
  return scaleLine!.getScaleForResolution()
}

onMounted((): void => {
  const view = new OlView()
  view.setConstrainResolution(false)
  view.setRotation(0)

  if (store.state.planproModelType === PlanProModelType.SITEPLAN) {
    scaleLine = new ScaleBarControl(() => {
      scaleLocked.value = Configuration.getInternalDefaultLodScale()
      setMapScale(map.getView(), scaleLocked.value)
      getPixelProMeterAtScale(
        map,
        map.getView(),
        100
      )
    })

    map.addControl(scaleLine)

    map.on('rendercomplete', () => {
      const ALLOWED_OFFSET = 0.01

      if (!scaleLocked.value) {
        return
      }

      const scale = getMapScale()
      if (
        scale > scaleLocked.value + ALLOWED_OFFSET ||
        scale < scaleLocked.value - ALLOWED_OFFSET
      ) {
        setMapScale(map.getView(), scaleLocked.value)
      }
    })
  }

  map.once('rendercomplete', () => {
    const scaleValue = Configuration.getExportScaleValue() / 4
    if (store.state.pixelPerMeterAtScale.has(scaleValue)) {
      return
    }

    getPixelProMeterAtScale(
      map,
      map.getView(),
      scaleValue
    ).then(pixelProMeter => {
      if (pixelProMeter === undefined) {
        return
      }

      store.commit('setPixelProMeter', {
        scaleValue: Configuration.getExportScaleValue() / 4,
        ppm: Math.round(pixelProMeter)
      })
      console.log(pixelProMeter)
      store.commit('setCustomPpm', Math.round(pixelProMeter))
    })
  })

  map.on('movestart', e => {
    const previousZoom = e.frameState?.viewState.zoom
    const currentZoom = map.getView().getZoom()
    if (previousZoom !== currentZoom) {
      scaleLocked.value = 0
    }
  })

  const rotateLeftControl = new RotateViewControl(false, 30, view)
  const rotateRightControl = new RotateViewControl(true, -30, view)

  map.addControl(rotateLeftControl)
  map.addControl(rotateRightControl)
  map.addControl(new ExportControl(map))

  if (mapRoot.value) {
    map.setTarget(mapRoot.value)
  }

  map.setView(view)
})
</script>

<style>
div.map-container {
  height: 100%;
  width: 100%;
  display: flex;
}

div#map-root {
  height: 100%;
  flex-grow: 1;
  position: relative;
}

div.custom-control-container {
  position: absolute;
  right: 0.5em;
  top: 2.4275em;
  z-index: 1;
}

div.side-info-container {
  background-color: #f7f7f7;
  border-left: 1px solid #e7e7e7;
  border-top: 1px solid #e7e7e7;
  width: 400px;
  height: auto;
  overflow: auto;
}

div.side-info-container-closed {
  width: 0;
  display: none;
}
</style>
