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
      <li>Bezeichung: {{ tracklock.label.text }}</li>
      <li v-if="tracklockType">
        Typ: {{ tracklockType }}
      </li>
      <li>Stellart: {{ operatingModeToText() }}</li>
      <RouteInfo :feature="tracklock" />
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>
<script lang="ts">
import RouteInfo from '@/components/popup/RouteInfo.vue'
import {
  getFeatureData,
  getFeatureGUID,
  getFeatureLabel
} from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'
import TrackLock from '@/model/TrackLock'
import { TurnoutOperatingMode } from '@/model/TrackSwitchComponent'
import { Options, Vue } from 'vue-class-component'

/**
 * Popup contents for track lock features
 *
 * @author Truong
 */
@Options({
  components: {
    RouteInfo
  },
  props: {
    feature: Object
  },
  computed: {
    tracklock: function () {
      return getFeatureData(this.feature)
    },

    planningObject: function () {
      return isPlanningObject(getFeatureGUID(this.feature))
        ? 'Ja'
        : 'Nein'
    },

    tracklockType: function () {
      return (getFeatureData(this.feature) as TrackLock).components[0].trackLockSignal
    },

    trackLockLabel: function () {
      return getFeatureLabel(this.feature)
    }

  }
})
export default class TrackLockPopup extends Vue {
  tracklock!: TrackLock
  planningObject!: boolean
  tracklockType!: string
  trackLockLabel!: string

  operatingModeToText (): string {
    if (!this.tracklock) {
      return 'Unbestimmt'
    }

    switch (this.tracklock.operatingMode) {
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
}
</script>
