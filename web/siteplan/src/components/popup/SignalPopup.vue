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
      <li
        v-for="screen in signal.attachedSignals"
        :key="screen.guid"
      >
        GUID: {{ screen.guid }}
        <RouteInfo :feature="screen" />
      </li>
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { SignalMount } from '@/model/SignalMount'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import {
  getFeatureData,
  getFeatureGUID,
  getFeatureLabel
} from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for signal features
 *
 * @author Stuecker
 */
@Options({
  components: {
    RouteInfo
  },
  props: {
    feature: Object
  },
  computed: {
    signal: function () {
      return getFeatureData(this.feature) as SignalMount
    },

    planningObject: function () {
      return isPlanningObject(getFeatureGUID(this.feature))
        ? 'Ja'
        : 'Nein'
    },

    signalLabel: function () {
      return getFeatureLabel(this.feature)
    }
  }
})
export default class SignalPopup extends Vue {
  static SIGNAL_TABLE = 'org.eclipse.set.feature.table.ssks'
  signal!: SignalMount
  planningObject!: string
  signalLabel!: string
}
</script>
