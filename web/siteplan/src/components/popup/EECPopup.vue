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
    <h2>Aussenelementansteuerung: {{ eccLabel }}</h2>
    <ul>
      <li>GUID: {{ eec.guid }}</li>
      <li>Bezeichnung: {{ eec.label?.text }}</li>
      <li>Typ: {{ eec.elementType }}</li>
      <RouteInfo :feature="eec" />
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import { ExternalElementControl } from '@/model/ExternalElementControl'
import { getFeatureData, getFeatureGUID, getFeatureLabel } from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for external element control features
 *
 * @author Stuecker
 */

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const eec = computed<ExternalElementControl>(() => getFeatureData(props.feature))

const planningObject = computed(() =>
  isPlanningObject(getFeatureGUID(props.feature)) ? 'Ja' : 'Nein')

const eccLabel = computed(() => getFeatureLabel(props.feature))
</script>
