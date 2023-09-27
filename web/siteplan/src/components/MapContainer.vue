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
        <MapSourceSelection />
        <LayerControl v-if="isDevelopmentMode" />
        <ModelSummaryControl v-if="isDevelopmentMode" />
        <SettingEditor v-if="isDevelopmentMode" />
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
import OlView from 'ol/View'
import OlMap from 'ol/Map'
import 'ol/ol.css'
import { Vue, Options } from 'vue-class-component'
import RotateViewControl from '@/util/Controls/RotateViewControl'
import ScaleBarControl from '@/util/Controls/ScaleBarControl'
import { setMapScale } from '@/util/MapScale'
import { store } from '@/store'
import LayerControl from '@/components/development/LayerControl.vue'
import SettingEditor from '@/components/development/SettingEditor.vue'
import ModelSummaryControl from '@/components/development/ModelSummaryControl.vue'
import MapSourceSelection from '@/components/MapSourceSelection.vue'
import Configuration from '@/util/Configuration'
import ExportControl from '@/util/Controls/ExportControl'

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
    this.scaleLine = new ScaleBarControl(() => {
      this.scaleLocked = 1000
      setMapScale(this.map.getView(), this.scaleLocked)
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

    // PLANPRO-5078: Development mode only
    if (Configuration.developmentMode()) {
      this.map.addControl(new ExportControl(this.map))
    }

    this.map.setTarget(this.$refs[ 'map-root' ])
    this.map.setView(view)
  }

  getMapScale (): number {
    return this.scaleLine.getScaleForResolution()
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
  width: 250px;
  height: auto;
  overflow: auto;
}

div.side-info-container-closed {
  width: 0;
  display: none;
}
</style>
