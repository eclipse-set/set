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

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import { ExternalElementControl } from '@/model/ExternalElementControl'
import {
  getFeatureData, getFeatureGUID, getFeatureLabel
} from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for external element control features
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
    eec: function () {
      return getFeatureData(this.feature)
    },

    planningObject: function () {
      return isPlanningObject(getFeatureGUID(this.feature))
        ? 'Ja'
        : 'Nein'
    },

    eccLabel: function () {
      return getFeatureLabel(this.feature)
    }
  }
})
export default class EECPopup extends Vue {
  eec!: ExternalElementControl
  planningObject!: string
  eccLabel!: string
}
</script>
