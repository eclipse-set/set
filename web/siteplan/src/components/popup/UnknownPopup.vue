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
    <h2>Objekt {{ object.guid }}</h2>
    <ul>
      <li>Typ: {{ object.objectType }}</li>
      <li>GUID: {{ object.guid }}</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { getFeatureData } from '@/feature/FeatureInfo'
import { computed } from 'vue'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const object = computed(() => {
  return getFeatureData(props.feature)
})

</script>
