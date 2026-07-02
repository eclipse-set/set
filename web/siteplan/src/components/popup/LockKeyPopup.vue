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
    <h2>Schlüsselspeere: {{ lockKeyLabel }}</h2>
    <ul>
      <li>GUID: {{ lockkey.guid }}</li>
      <li>Bezeichnung: {{ lockkey.label?.text }}</li>
      <li>Typ: {{ type }}</li>
      <RouteInfo :feature="lockkey" />
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import LockKey, { LockKeyType } from '@/model/LockKey'
import { getFeatureData, getFeatureGUID, getFeatureLabel } from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for Lockkey features
 *
 * @author Truong
 */

const props = defineProps<{
  feature: Feature<Geometry>
}>()

const lockkey = computed<LockKey>(() => getFeatureData(props.feature))

const planningObject = computed(() =>
  isPlanningObject(getFeatureGUID(props.feature)) ? 'Ja' : 'Nein')

const lockKeyLabel = computed(() => getFeatureLabel(props.feature))

const type = computed(() => {
  switch (lockkey.value.type) {
    case LockKeyType.Inside:
      return 'innen'
    case LockKeyType.Outside:
      return 'aussen'
    default:
      return ''
  }
})
</script>
