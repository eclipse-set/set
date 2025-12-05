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

<script lang="ts">
/**
 * Temporary widget for experimentation with different track widths
 * @author Stuecker
 */
import SideInfoControl from '@/components/SideInfoControl.vue'
import { DBRef } from '@/model/Position'
import { store, TableType } from '@/store'
import Configuration from '@/util/Configuration'
import { Options, Vue } from 'vue-class-component'
import { SubscribeOptions } from 'vuex'

@Options({
  components: {
    SideInfoControl
  },
  created () {
    this.unsubscribe = store.subscribe((m, s) => {
      if (m.type === 'defaultTrackWidth') {
        this.mainTrackWidth = s.mainTrackWidth
        this.sideTrackWidth = s.sideTrackWidth
        this.otherTrackWidth = s.otherTrackWidth
        this.trackOutlineWidth = s.trackOutlineWidth
      }

      if (m.type === 'setSheetCutCRS') {
        this.sheetCutCRS = s.sheetCutCRS
      }

      if (m.type === 'setCustomPpm') {
        this.ppm = s.customPpm
      }
    })
  },
  beforeUnmount () {
    this.unsubscribe()
  },
  watch: {
    viewState (value: TableType) {
      store.commit('setSessionState', value)
    },
    mainTrackWidth (value: number) {
      store.commit('setMainTrackWidth', value)
    },
    sideTrackWidth (value: number) {
      store.commit('setSideTrackWidth', value)
    },
    otherTrackWidth (value: number) {
      store.commit('setOtherTrackWidth', value)
    },
    trackOutlineWidth (value: number) {
      store.commit('setTrackOutlineWidth', value)
    },
    boundingBoxScale (value: number) {
      if (value >= 1) {
        store.commit('setBoundingBoxScaleFactor', value)
      }
    },
    collisionEnabled (value: boolean) {
      store.commit('setCollisionEnabled', value)
    },
    sheetCutCRS (value: DBRef) {
      store.commit('setSheetCutCRS', value)
    },
    ppm (value: number) {
      if (value > Configuration.MAX_EXPORT_PPM) {
        value = Configuration.MAX_EXPORT_PPM
      } else if (value < Configuration.MIN_EXPORT_PPM) {
        value = Configuration.MIN_EXPORT_PPM
      }

      this.ppm = value
      store.commit('setCustomPpm', value)
    }
  }
})
export default class SettingEditor extends Vue {
  mainTrackWidth = store.state.mainTrackWidth
  sideTrackWidth = store.state.sideTrackWidth
  otherTrackWidth = store.state.otherTrackWidth
  trackOutlineWidth = store.state.trackOutlineWidth
  boundingBoxScale = store.state.boundingBoxScaleFactor
  viewState = store.state.sessionState
  collisionEnabled = store.state.collisionEnabled
  sheetCutCRS = store.state.sheetCutCRS
  ppm = store.state.customPpm
  trackWidth = Configuration.getTrackWidth()
  unsubscribe: SubscribeOptions | undefined
  maxPpm = Configuration.MAX_EXPORT_PPM
  minPpm = Configuration.MIN_EXPORT_PPM

  reset (): void {
    store.commit('defaultTrackWidth', this.trackWidth)
    this.boundingBoxScale = 90
    store.commit('setBoundingBoxScaleFactor', this.boundingBoxScale)
    store.commit('setSessionState', TableType.DIFF)
    store.commit('setCollisionEnabled', true)
  }

  getMaxValue (track: string): number {
    switch (track) {
      case 'Main':
        return this.trackWidth.intervall_main.max
      case 'Side':
        return this.trackWidth.intervall_side.max
      case 'Other':
        return this.trackWidth.intervall_other.max
      case 'Outline':
        return this.trackWidth.intervall_outline.max
      default:
        return 0
    }
  }

  getMinValue (track: string): number {
    switch (track) {
      case 'Main':
        return this.trackWidth.intervall_main.min
      case 'Side':
        return this.trackWidth.intervall_side.min
      case 'Other':
        return this.trackWidth.intervall_other.min
      case 'Outline':
        return this.trackWidth.intervall_outline.min
      default:
        return 0
    }
  }

  getCRSList () {
    return Object.values(DBRef)
  }

  isSheetCutAvaiable () {
    return store.state.isSheetCutAvaiable
  }

  isDevelopmentMode () {
    return Configuration.developmentMode()
  }
}
</script>
<style scoped>
#resetButton {
  float: right;
  margin: 5px;
}
</style>
