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
    <h2>Gleissperre: {{ trackLockLabel }}</h2>
    <ul>
      <li>GUID: {{ tracklock.guid }}</li>
      <li>Bezeichung: {{ tracklock.label?.text }}</li>
      <li v-if="tracklockType">
        Typ: {{ tracklockType }}
      </li>
      <li>Stellart: {{ operatingModeToText() }}</li>
      <RouteInfo :feature="tracklock" />
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
import { isPlanningObject } from '@/model/SiteplanModel'
import TrackLock from '@/model/TrackLock'
import { TurnoutOperatingMode } from '@/model/TrackSwitchComponent'

/**
 * Popup contents for track lock features
 *
 * @author Truong
 */
const props = defineProps<{
  feature: Feature<Geometry>
}>()

const tracklock = computed(() => {
  return getFeatureData(props.feature) as TrackLock
})

const planningObject = computed(() => {
  return isPlanningObject(getFeatureGUID(props.feature)) ? 'Ja' : 'Nein'
})

const tracklockType = computed(() => {
  return tracklock.value.components[0].trackLockSignal
})

const trackLockLabel = computed(() => {
  return getFeatureLabel(props.feature)
})

const operatingModeToText = (): string => {
  if (!tracklock.value) {
    return 'Unbestimmt'
  }

  switch (tracklock.value.operatingMode) {
    case TurnoutOperatingMode.ElectricRemote:
    case TurnoutOperatingMode.MechanicalRemote:
      return 'ferngestellt'
    case TurnoutOperatingMode.ElectricLocal:
    case TurnoutOperatingMode.MechanicalLocal:
      return 'ortsgestellt'
    default:
      return 'Unbestimmt'
  }
}
</script>
