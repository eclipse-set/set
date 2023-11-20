<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <SideInfoControl :title="'Kartenquelle'">
    <template #icon>
      <span class="material-icons">map</span>
    </template>
    <template #content>
      <div class="map-source-selection-content">
        <div
          v-for="option in options"
          :key="option.getType()"
          class="map-source-option"
          :class="{
            'map-source-option-selected': selected === option.getIdentifier(),
          }"
          @click="selectedMap(option)"
        >
          <div class="map-source-radio">
            <input
              v-model="selected"
              type="radio"
              :value="option.getIdentifier()"
            >
          </div>
          <div class="map-source-description">
            <div class="map-source-name">
              {{ getDisplayName(option) }}
            </div>
            <div
              v-if="option.getName()"
              class="map-source-type"
            >
              {{ option.getType() }}
            </div>
          </div>
          <div class="map-source-option-icon">
            <span class="material-icons">
              {{ getOptionIcon(option) }}
            </span>
          </div>
        </div>
      </div>
    </template>
  </SideInfoControl>
</template>

<script lang="ts">
import OlMap from 'ol/Map'
import { Vue, Options } from 'vue-class-component'
import { MapSource, MapSourceType } from '../util/MapSource'
import OpenRailwayMap from '../util/MapSources/OpenRailwayMap'
import OpenStreetMap from '../util/MapSources/OpenStreetMap'
import ArcGisSatellite from '../util/MapSources/ArcGisSatellite'
import EmptyMap from '../util/MapSources/EmptyMap'
import TopPlusOpen from '../util/MapSources/TopPlusOpen'
import Sentinel2DE from '../util/MapSources/Sentinel2DE'
import DigitalOrthophotosExtern from '../util/MapSources/DigitalOrthophotosExtern'
import DigitalOrthophotosIntern from '../util/MapSources/DigitalOrthophotosIntern'
import MapboxTop from '../util/MapSources/MapboxTop'
import MapboxSat from '../util/MapSources/MapboxSat'
import HereMapsTop from '../util/MapSources/HereMapsTop'
import HereMapsSat from '../util/MapSources/HereMapsSat'
import VectorTileLayer from 'ol/layer/VectorTile'
import TileLayer from 'ol/layer/Tile'
import TileSource from 'ol/source/Tile'
import { store } from '@/store'
import SideInfoControl from '@/components/SideInfoControl.vue'
import 'material-design-icons/iconfont/material-icons.css'

/**
 * Selector for a tile layer with multiple sources
 *
 * @author Stuecker
 */
@Options({
  components: {
    SideInfoControl
  },
  watch: {
    selected (selectedMap: string) {
      const source = this.options.filter(
        (x: MapSource) => x.getIdentifier() === selectedMap
      )[ 0 ]
      this.imageLayer.setSource(null)
      this.vectorLayer.setSource(null)
      source.setAsSource(this.imageLayer, this.vectorLayer)
    }
  }
})
export default class MapSourceSelection extends Vue {
  map: OlMap = store.state.map
  emptyMap: EmptyMap = new EmptyMap()
  selected: string = store.state.selectedSourceMap
  vectorLayer: VectorTileLayer = new VectorTileLayer()
  imageLayer: TileLayer<TileSource> = new TileLayer()
  isOpenMenu!: boolean
  options: MapSource[] = [
    this.emptyMap,
    new OpenStreetMap(),
    new OpenRailwayMap(),
    new TopPlusOpen(),
    new MapboxTop(),
    new HereMapsTop(),
    new ArcGisSatellite(),
    new Sentinel2DE(),
    new DigitalOrthophotosIntern(),
    new DigitalOrthophotosExtern(),
    new MapboxSat(),
    new HereMapsSat()
  ].filter(x => x.isEnabled())

  mounted (): void {
    this.vectorLayer.setZIndex(0)
    this.imageLayer.setZIndex(0)
    this.map.addLayer(this.vectorLayer)
    this.map.addLayer(this.imageLayer)
  }

  getDisplayName (option: MapSource): string {
    return option.getName() ? option.getName() : option.getType()
  }

  getOptionIcon (option: MapSource): string {
    switch (option.getType()) {
      case MapSourceType.Topological:
        return 'public'
      case MapSourceType.Satellite:
        return 'satellite'
      default:
        return 'block'
    }
  }

  selectedMap (option: MapSource): void {
    this.selected = option.getIdentifier()
  }
}
</script>

<style scoped>
.map-source-selection-content {
  margin: 3px;
}

.map-source-option {
  background-color: #eeeeee;
  margin: 3px;
  border: none;
  border-radius: 2px;
  height: 40px;
  width: 100%;
  display: grid;
  grid-template-columns: 25px auto 35px;
  user-select: none;
  cursor: pointer;
}

.map-source-option-selected {
  background-color: #dfdfdf;
  cursor: default;
}

.map-source-radio {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 3px;
}

.map-source-description {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 1em;
}

.map-source-option-icon {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.map-source-option-icon .material-icons {
  font-size: 30px;
  color: rgba(0, 0, 0, 0.3);
  user-select: none;
}

.map-source-option-selected>.map-source-option-icon .material-icons {
  color: rgba(0, 0, 0, 0.6);
}

.map-source-name {
  font-weight: normal;
  font-size: 1.1em;
}

.map-source-type {
  font-style: italic;
}
</style>
