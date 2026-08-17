<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div>
    <h2>Fehler</h2>
    <ul>
      <li>Betroffene GUIDs: {{ error.relevantGUIDs.join(', ') }}</li>
      <li>Fehler: {{ error.message }}</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'
import Error from '@/model/Error'
import { getFeatureData } from '@/feature/FeatureInfo'

/**
 * Popup contents for Error features
 *
 * @author Stuecker
 */

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const error = computed<Error>(() => getFeatureData(props.feature))
</script>
