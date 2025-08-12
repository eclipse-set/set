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
      ref="map-root"
    >
      <div class="custom-control-container">
        <MapSourceSelection v-if="isSiteplan()" />
        <LayerControl v-if="isDevelopmentMode && isSiteplan()" />
        <ModelSummaryControl v-if="isDevelopmentMode" />
        <SettingEditor v-if="isSiteplan()" />
      </div>
    </div>
    <div
      id="side-info-container"
      ref="side-info-container"
      class="side-info-container-closed"
    />
  </div>
</template>

<script lang="ts">
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
import { Options, Vue } from 'vue-class-component'

/**
 * Container for the open layers map
 *
 * @author Stuecker
 */
@Options({
  props: {},
  components: {
    MapSourceSelection,
    LayerControl,
    ModelSummaryControl,
    SettingEditor
  }
})
export default class MapContainer extends Vue {
  isDevelopmentMode = Configuration.developmentMode()
  map: OlMap = store.state.map
  $refs!: {
    [ 'map-root' ]: HTMLInputElement
  }

  scaleLocked = 0
  scaleLine!: ScaleBarControl

  mounted (): void {
    // Initialize view
    const view = new OlView()
    view.setConstrainResolution(false)
    view.setRotation(0)

    // Allow clicking the scale bar to force a 1:1000 scale
    if (store.state.planproModelType === PlanProModelType.SITEPLAN) {
      this.scaleLine = new ScaleBarControl(() => {
        this.scaleLocked = Configuration.getInternalDefaultLodScale()
        setMapScale(this.map.getView(), this.scaleLocked)
        getPixelProMeterAtScale(
          this.map,
          this.map.getView(),
          100
        )
      })
      this.map.addControl(this.scaleLine)
      // Reapply zoom level after moving
      this.map.on('rendercomplete', () => {
      // Allow a minimal offset to avoid constant rescaling
        const ALLOWED_OFFSET = 0.01
        if (!this.scaleLocked) {
          return
        }

        const scale = this.getMapScale()
        if (
          scale > this.scaleLocked + ALLOWED_OFFSET ||
        scale < this.scaleLocked - ALLOWED_OFFSET
        ) {
          setMapScale(this.map.getView(), this.scaleLocked)
        }
      })
    }

    this.map.once('rendercomplete', () => {
      // the export to image should have high resolution
      // than scale value to avoid blurred symbols.
      const scaleValue = Configuration.getExportScaleValue() / 4
      if (store.state.pixelPerMeterAtScale.has(scaleValue)) {
        return
      }

      getPixelProMeterAtScale(
        this.map,
        this.map.getView(),
        scaleValue
      ).then(pixelProMeter => {
        if (pixelProMeter === undefined) {
          return
        }

        // Set the pixel pro meter value in the store
        store.commit('setPixelProMeter', {
          scaleValue: Configuration.getExportScaleValue() / 4,
          ppm: Math.round(pixelProMeter)
        })
        console.log(pixelProMeter)
        store.commit('setCustomPpm', Math.round(pixelProMeter))
      })
    })

    // Disable locked scale after manually zooming
    this.map.on('movestart', e => {
      const previousZoom = e.frameState?.viewState.zoom
      const currentZoom = this.map.getView().getZoom()
      if (previousZoom !== currentZoom) {
        this.scaleLocked = 0
      }
    })
    const rotateLeftControl = new RotateViewControl(false, 30, view)
    const rotateRightControl = new RotateViewControl(true, -30, view)

    this.map.addControl(rotateLeftControl)
    this.map.addControl(rotateRightControl)
    this.map.addControl(new ExportControl(this.map))

    this.map.setTarget(this.$refs['map-root'])
    this.map.setView(view)
  }

  getMapScale (): number {
    return this.scaleLine.getScaleForResolution()
  }

  isSiteplan () {
    return store.state.planproModelType === PlanProModelType.SITEPLAN
  }
}
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
