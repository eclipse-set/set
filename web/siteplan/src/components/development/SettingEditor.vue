<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <SideInfoControl :title="''">
    <template #icon>
      <span class="material-icons">settings</span>
    </template>
    <template #content>
      <h4>Einstellungen</h4>
      <div class="model-summary-control-content">
        <ul>
          <div v-if="isDevelopmentMode()">
            <li>
              Hauptgleise
              <input
                v-model.number="mainTrackWidth"
                type="number"
                :max="getMaxValue('Main')"
                :min="getMinValue('Main')"
              >
            </li>
            <li>
              Nebengleise
              <input
                v-model.number="sideTrackWidth"
                type="number"
                :max="getMaxValue('Side')"
                :min="getMinValue('Side')"
              >
            </li>
            <li>
              Sonstige
              <input
                v-model.number="otherTrackWidth"
                type="number"
                :max="getMaxValue('Other')"
                :min="getMinValue('Other')"
              >
            </li>
            <li>
              Gleis Abdeckungsbreite
              <input
                v-model.number="trackOutlineWidth"
                type="number"
                :max="getMaxValue('Outline')"
                :min="getMinValue('Outline')"
              >
            </li>
            <li>
              Skalierungsfaktor für Überdeckungen
              <input
                v-model.number="boundingBoxScale"
                type="number"
                :min="1"
              >
            </li>
            <li>
              Zustand
              <select v-model="viewState">
                <option value="initial">
                  Start
                </option>
                <option value="final">
                  Ziel
                </option>
                <option value="diff">
                  Start/Ziel
                </option>
              </select>
            </li>
            <li>
              <input
                id="checkbox-collision"
                v-model="collisionEnabled"
                type="checkbox"
              >
              <label for="checkbox-collision">Kollisionsvermeidung aktiv</label>
            </li>
          </div>
          <li>
            Koordinatenreferenzsystem für Blattschnitte: <br>
            <select
              v-model="sheetCutCRS"
              :disabled="!isSheetCutAvaiable()"
            >
              <option
                v-for="sys in getCRSList()"
                :key="sys"
              >
                {{ sys }}
              </option>
            </select>
          </li>
          <li>
            Export PPM (Pixel pro meter) ({{ minPpm }} ~ {{ maxPpm }}): <br>
            <input
              v-model="ppm"
              type="number"
              max:="maxPpm"
              min:="minPpm"
              step="0.5"
            >
          </li>
        </ul>
      </div>
      <button
        id="resetButton"
        @click="reset()"
      >
        Werte Zurücksetzen
      </button>
    </template>
  </SideInfoControl>
</template>

<script setup lang="ts">
import SideInfoControl from '@/components/SideInfoControl.vue'
import { DBRef } from '@/model/Position'
import { store, TableType } from '@/store'
import Configuration from '@/util/Configuration'
import { onBeforeUnmount, ref, watch } from 'vue'

/**
 * Temporary widget for experimentation with different track widths
 * @author Stuecker
 */

const mainTrackWidth = ref(store.state.mainTrackWidth)
const sideTrackWidth = ref(store.state.sideTrackWidth)
const otherTrackWidth = ref(store.state.otherTrackWidth)
const trackOutlineWidth = ref(store.state.trackOutlineWidth)
const boundingBoxScale = ref(store.state.boundingBoxScaleFactor)
const viewState = ref(store.state.sessionState)
const collisionEnabled = ref(store.state.collisionEnabled)
const sheetCutCRS = ref(store.state.sheetCutCRS)
const ppm = ref(store.state.customPpm)
const trackWidth = Configuration.getTrackWidth()
const maxPpm = Configuration.MAX_EXPORT_PPM
const minPpm = Configuration.MIN_EXPORT_PPM

const unsubscribe = store.subscribe((m, s) => {
  if (m.type === 'defaultTrackWidth') {
    mainTrackWidth.value = s.mainTrackWidth
    sideTrackWidth.value = s.sideTrackWidth
    otherTrackWidth.value = s.otherTrackWidth
    trackOutlineWidth.value = s.trackOutlineWidth
  }

  if (m.type === 'setSheetCutCRS') {
    sheetCutCRS.value = s.sheetCutCRS
  }

  if (m.type === 'setCustomPpm') {
    ppm.value = s.customPpm
  }
})

onBeforeUnmount(() => {
  unsubscribe()
})

watch(viewState, (value: TableType) => {
  store.commit('setSessionState', value)
})

watch(mainTrackWidth, (value: number) => {
  store.commit('setMainTrackWidth', value)
})

watch(sideTrackWidth, (value: number) => {
  store.commit('setSideTrackWidth', value)
})

watch(otherTrackWidth, (value: number) => {
  store.commit('setOtherTrackWidth', value)
})

watch(trackOutlineWidth, (value: number) => {
  store.commit('setTrackOutlineWidth', value)
})

watch(boundingBoxScale, (value: number) => {
  if (value >= 1) {
    store.commit('setBoundingBoxScaleFactor', value)
  }
})

watch(collisionEnabled, (value: boolean) => {
  store.commit('setCollisionEnabled', value)
})

watch(sheetCutCRS, (value: DBRef) => {
  store.commit('setSheetCutCRS', value)
})

watch(ppm, (value: number) => {
  if (value > Configuration.MAX_EXPORT_PPM) {
    value = Configuration.MAX_EXPORT_PPM
  } else if (value < Configuration.MIN_EXPORT_PPM) {
    value = Configuration.MIN_EXPORT_PPM
  }

  ppm.value = value
  store.commit('setCustomPpm', value)
})

const reset = (): void => {
  store.commit('defaultTrackWidth', trackWidth)
  boundingBoxScale.value = 90
  store.commit('setBoundingBoxScaleFactor', boundingBoxScale.value)
  store.commit('setSessionState', TableType.DIFF)
  store.commit('setCollisionEnabled', true)
}

const getMaxValue = (track: string): number => {
  switch (track) {
    case 'Main':
      return trackWidth.intervall_main.max
    case 'Side':
      return trackWidth.intervall_side.max
    case 'Other':
      return trackWidth.intervall_other.max
    case 'Outline':
      return trackWidth.intervall_outline.max
    default:
      return 0
  }
}

const getMinValue = (track: string): number => {
  switch (track) {
    case 'Main':
      return trackWidth.intervall_main.min
    case 'Side':
      return trackWidth.intervall_side.min
    case 'Other':
      return trackWidth.intervall_other.min
    case 'Outline':
      return trackWidth.intervall_outline.min
    default:
      return 0
  }
}

const getCRSList = () => {
  return Object.values(DBRef)
}

const isSheetCutAvaiable = () => {
  return store.state.isSheetCutAvaiable
}

const isDevelopmentMode = () => {
  return Configuration.developmentMode()
}
</script>

<style scoped>
#resetButton {
  float: right;
  margin: 5px;
}
</style>
