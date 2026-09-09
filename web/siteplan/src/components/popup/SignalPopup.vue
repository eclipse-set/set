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
    <h2>Signal: {{ signalLabel }}</h2>
    <ul>
      <li>GUID: {{ signal.guid }}</li>
      <RouteInfo :feature="signal" />
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { computed } from 'vue'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import { getFeatureData, getFeatureGUID, getFeatureLabel } from '@/feature/FeatureInfo'
import { Signal } from '@/model/Signal'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for signal features
 *
 * @author Stuecker
 */

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const signal = computed<Signal>(() => getFeatureData(props.feature))

const planningObject = computed(() =>
  isPlanningObject(getFeatureGUID(props.feature)) ? 'Ja' : 'Nein')

const signalLabel = computed(() => getFeatureLabel(props.feature))
</script>
