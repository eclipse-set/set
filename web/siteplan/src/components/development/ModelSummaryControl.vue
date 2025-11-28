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
                  v-model="trackColorVisible"
                  type="checkbox"
                >
                <label for="checkbox-track">TOP_Kanten anzeigen</label>
              </li>
              <li v-if="isSiteplan()">
                <input
                  id="checkbox-track"
                  v-model="trackSectionColorVisible"
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

<script setup lang="ts">
import SideInfoControl from '@/components/SideInfoControl.vue'
import SiteplanModel, { SiteplanState } from '@/model/SiteplanModel'
import { PlanProModelType, store } from '@/store'
import { onBeforeMount, onBeforeUnmount, ref, watch } from 'vue'

const routeVisible = ref(store.state.routeVisible)
const trackSectionColorVisible = ref(store.state.trackSectionColorVisible)
const trackOutlineVisible = ref(store.state.trackOutlineVisible)
const trackColorVisible = ref(store.state.trackColorVisible)
let model: SiteplanModel | null = store.state.model
let unsubscribe: VoidFunction | undefined

onBeforeMount(() => {
  console.log('TEST')
  unsubscribe = store.subscribe((s, m) => {
    if (s.type === 'setModel') {
      model = m.model
    }
  })
})

onBeforeUnmount(() => {
  if (unsubscribe) {
    unsubscribe()
  }
})

watch(routeVisible, (value:boolean) => {
  store.commit('setRouteVisible', value)
})

watch(trackSectionColorVisible, (value: boolean) => {
  if (value && trackColorVisible.value.valueOf()) {
    trackColorVisible.value = false
    store.commit('setTrackColorVisible', false)
  }

  store.commit('setTrackSectionColorVisible', value)
})

watch(trackColorVisible, (value: boolean) => {
  if (value && trackSectionColorVisible.value.valueOf()) {
    trackSectionColorVisible.value = false
    store.commit('setTrackSectionColorVisible', false)
  }

  store.commit('setTrackColorVisible', value)
})

watch(trackOutlineVisible, (value: boolean) => {
  store.commit('setTrackOutlineVisible', value)
})

function getCounts (fn: (model: SiteplanState) => unknown[]): number[] {
  if (!model) {
    return []
  }

  return [
    fn(model.initialState).length,
    fn(model.commonState).length,
    fn(model.initialState).length,
    fn(model.changedInitialState).length
    // changedFinalState is not shown as the size is the same as of
    // changedInitialState
  ]
}

function isSiteplan () {
  return store.state.planproModelType === PlanProModelType.SITEPLAN
}
</script>
