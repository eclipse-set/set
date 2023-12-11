<!--
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div>
    <h2>Überhöhungspunkt {{ cant.guid }}</h2>
    <ul>
      <li>GUID: {{ cant.guid }}</li>
      <li>Höhe: {{ cant.height }}</li>
      <li
        v-for="line in cantLines"
        :key="line.guid"
      >
        Überhöhungslinie {{ line.guid }} anzeigen: <input
          id="checkbox"
          :checked="checked(line)"
          type="checkbox"
          @change="(event) => showCantLine(line, event)"
        >
        <label for="checkbox">{{ checked(line) ? "Ja" : "Nein" }}</label>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { getFeatureData } from '@/feature/FeatureInfo'
import { computed } from 'vue'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'

import { store } from '@/store'
import { Cant, CantPoint } from '@/model/Cant'

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const cant = computed<CantPoint>(() => {
  return getFeatureData(props.feature)
})

const cantLines = computed(() => {
  const cantFeature = getFeatureData(props.feature)
  console.log(cantFeature)
  if (!('cants' in cantFeature)) {
    console.log('no lines')
    return []
  } else {
    return cantFeature.cants
  }
})

const checked = (line: Cant) => {
  return store.state.visibleCants[line.guid] ?? false
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const showCantLine = (line: Cant, event: any) => {
  if (event.target.checked) {
    store.commit('setCantVisible', line.guid)
  } else {
    store.commit('setCantInvisible', line.guid)
  }
}

</script>
