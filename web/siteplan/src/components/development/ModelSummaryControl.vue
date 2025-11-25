<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <SideInfoControl :title="'Zusammenfassung'">
    <template #icon>
      <span class="material-icons">info</span>
    </template>
    <template #content>
      <div class="model-summary-control-content">
        <span style="font-size: 11px">[Hinzugefügt, Unverändert, Entfernt, Geändert]</span>
        <ul>
          <li>Achszähler: {{ getCounts((model) => model.fmaComponents) }}</li>
          <li>PZB Elemente: {{ getCounts((model) => model.pzb) }}</li>
          <li>PZB GÜs: {{ getCounts((model) => model.pzbGU) }}</li>
          <li>
            Strecken: {{ getCounts((model) => model.routes) }}
            <input
              id="checkbox-route"
              v-model="routeVisible"
              type="checkbox"
            >
            <label for="checkbox-route">anzeigen</label>
          </li>
          <li>Signale: {{ getCounts((model) => model.signals) }}</li>
          <li>
            Gleise: {{ getCounts((model) => model.tracks) }}
            <ul>
              <li v-if="isSiteplan()">
                <input
                  id="checkbox-trackoutline"
                  v-model="trackOutlineVisible"
                  type="checkbox"
                >
                <label for="checkbox-track">Gleisbreite anzeigen</label>
              </li>
              <li>
                <input
                  id="checkbox-trackoutline"
                  v-model="trackSectionColorVisbile"
                  type="checkbox"
                >
                <label for="checkbox-track">TOP_Kanten anzeigen</label>
              </li>
              <li v-if="isSiteplan()">
                <input
                  id="checkbox-track"
                  v-model="trackEndMarkerVisible"
                  type="checkbox"
                >
                <label for="checkbox-track">GEO_Kanten anzeigen</label>
              </li>
            </ul>
          </li>
          <li>Weichen: {{ getCounts((model) => model.trackSwitches) }}</li>
          <li>Gleissperren: {{ getCounts((model) => model.trackLock) }}</li>
          <li>Bahnsteige: {{ getCounts((model) => model.stations) }}</li>
        </ul>
      </div>
    </template>
  </SideInfoControl>
</template>

<script lang="ts">
/**
 * Overview of model data
 * @author Truong
 */
import SideInfoControl from '@/components/SideInfoControl.vue'
import SiteplanModel, { SiteplanState } from '@/model/SiteplanModel'
import { PlanProModelType, store } from '@/store'
import { Options, Vue } from 'vue-class-component'
import { SubscribeOptions } from 'vuex'

@Options({
  components: {
    SideInfoControl
  },
  created () {
    this.unsubscribe = store.subscribe((s, m) => {
      if (s.type === 'setModel') {
        this.model = m.model
      }
    })
  },
  watch: {
    routeVisible (value: boolean) {
      this.routeVisible = value
      store.commit('setRouteVisible', value)
    },
    trackEndMarkerVisible (value: boolean) {
      this.trackEndMarkerVisible = value
      store.commit('setTrackSectionMarkerVisible', value)
    },
    trackOutlineVisible (value: boolean) {
      this.trackOutlineVisible = value
      store.commit('setTrackOutlineVisible', value)
    },
    trackSectionColorVisbile (value: boolean) {
      this.trackSectionColorVisbile = value
      store.commit('setTrackSectionColorVisible', value)
    }
  },
  beforeUnmount () {
    this.unsubscribe()
  }
})
export default class ModelSummaryControl extends Vue {
  routeVisible = store.state.routeVisible
  trackEndMarkerVisible = store.state.trackSectionMarkerVisible
  trackOutlineVisible = store.state.trackOutlineVisible
  trackSectionColorVisbile = store.state.trackSectionColorVisible
  model: SiteplanModel | null = store.state.model
  unsubscribe: SubscribeOptions | undefined

  getCounts (fn: (model: SiteplanState) => unknown[]): number[] {
    if (!this.model) {
      return []
    }

    return [
      fn(this.model.initialState).length,
      fn(this.model.commonState).length,
      fn(this.model.initialState).length,
      fn(this.model.changedInitialState).length
      // changedFinalState is not shown as the size is the same as of
      // changedInitialState
    ]
  }

  isSiteplan () {
    return store.state.planproModelType === PlanProModelType.SITEPLAN
  }
}
</script>
