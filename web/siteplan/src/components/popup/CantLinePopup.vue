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
    <h2>Überhöhungslinie {{ cant.guid }}</h2>
    <ul>
      <li>GUID: {{ cant.guid }}</li>
      <li>Form: {{ cant.form }}</li>
      <li>Länge: {{ cant.length }}</li>
      <li>
        Überhöhungslinie anzeigen: <input
          id="checkbox"
          :checked="checked"
          type="checkbox"
          @change="showCantLine"
        >
        <label for="checkbox">{{ checked ? "Ja" : "Nein" }}</label>
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
import { Cant } from '@/model/Cant'

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const cant = computed<Cant>(() => {
  return getFeatureData(props.feature)
})

const checked = computed(() => {
  return store.state.visibleCants[cant.value.guid] ?? false
})

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const showCantLine = (event: any) => {
  if (event.target.checked) {
    store.commit('setCantVisible', cant.value.guid)
  } else {
    store.commit('setCantInvisible', cant.value.guid)
  }
}

</script>
