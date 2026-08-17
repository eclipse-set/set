<!--
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div>
    <h2>{{ elementType }}: {{ elementLabel }}</h2>
    <ul>
      <li
        class="menuItem"
        @click="jumpToTextSicht"
      >
        Zeige in Textsicht
      </li>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { computed } from 'vue'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'
import { getFeatureLabel, getFeatureName, getFeatureType } from '@/feature/FeatureInfo'
import { getFeatureGUIDs } from '@/util/FeatureExtensions'
import PlanProToolbox from '@/util/PlanProToolbox'

/**
 * Popup contents for signal features
 *
 * @author Stuecker
 */

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const guid = computed(() => getFeatureGUIDs(props.feature)[0])
const elementType = computed(() => getFeatureName(getFeatureType(props.feature)))
const elementLabel = computed(() => getFeatureLabel(props.feature))

function jumpToTextSicht () {
  PlanProToolbox.jumpToTextView(guid.value)
}
</script>
<style scoped>
.menuItem {
  cursor: pointer;
  margin-bottom: 5px;
  border-radius: 10px;
  background-color: gainsboro;
  padding-left: 5px;
}

.menuItem:hover {
  background-color: darkgray;
}

</style>
