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
    <h2>Weiche: {{ trackSwitchLabel }}</h2>
    <ul>
      <li>GUID (Anlage): {{ trackSwitch.guid }}</li>
      <li>GUID (Element): {{ trackSwitchComponent.guid }}</li>
      <li>Bezeichnung: {{ trackSwitchComponent.label.text }}</li>
      <li>Stellart: {{ operatingModeToText() }}</li>
      <li>
        Anzahl Zungenprüfkontakte: {{ trackSwitchComponent.pointDetectorCount }}
      </li>
      <li>Bauform: {{ trackSwitch.design }}</li>
      <RouteInfo :feature="trackSwitchComponent" />
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import TrackSwitch from '@/model/TrackSwitch'
import TrackSwitchComponent, {
  TurnoutOperatingMode
} from '@/model/TrackSwitchComponent'
import { TrackSwitchFeatureData } from '@/feature/TrackSwitchFeature'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import {
  getFeatureData,
  getFeatureGUID,
  getFeatureLabel
} from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for track switch features
 *
 * @author Stuecker
 */
const props = defineProps<{
  feature: Feature<Geometry>
}>()

function getData (): TrackSwitchFeatureData | undefined {
  return getFeatureData(props.feature) as TrackSwitchFeatureData | undefined
}

const trackSwitch = computed<TrackSwitch>(() => {
  return getData()?.trackSwitch as TrackSwitch
})

const trackSwitchComponent = computed<TrackSwitchComponent>(() => {
  return getData()?.component as TrackSwitchComponent
})

const planningObject = computed(() => {
  return isPlanningObject(getFeatureGUID(props.feature)) ? 'Ja' : 'Nein'
})

const trackSwitchLabel = computed(() => {
  return getFeatureLabel(props.feature)
})

function operatingModeToText (): string {
  switch (trackSwitchComponent.value.operatingMode) {
    case TurnoutOperatingMode.ElectricRemote:
      return 'elektrisch ferngestellt'
    case TurnoutOperatingMode.ElectricLocal:
      return 'elektrisch ortsgestellt'
    case TurnoutOperatingMode.MechanicalRemote:
      return 'mechanisch ferngestellt'
    case TurnoutOperatingMode.MechanicalLocal:
      return 'mechanisch ortsgestellt'
    case TurnoutOperatingMode.NonOperational:
      return 'stillgelegt'
    case TurnoutOperatingMode.Trailable:
      return 'Rückfallweiche'
    case TurnoutOperatingMode.Other:
      return 'sonstige'
    case TurnoutOperatingMode.DeadLeft:
      return 'stillgelegt links'
    case TurnoutOperatingMode.DeadRight:
      return 'stillgelegt rechts'
    default:
      return 'Unbestimmt'
  }
}
</script>
