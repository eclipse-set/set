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
    <h2>PZB Element</h2>
    <ul>
      <li>GUID: {{ pzb.guid }}</li>
      <li>Typ: {{ pzbTypText() }}</li>
      <li>Art: {{ pzbArtText() }}</li>
      <RouteInfo :feature="pzb" />
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { PZB, PZBElement, PZBType } from '@/model/PZB'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import {
  getFeatureData, getFeatureGUID
} from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for PZB features
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
    pzb: function () {
      return getFeatureData(this.feature)
    },

    planningObject: function () {
      return isPlanningObject(getFeatureGUID(this.feature))
        ? 'Ja'
        : 'Nein'
    }
  }
})
export default class TrackSectionPopup extends Vue {
  pzb!: PZB
  planningObject!: string
  pzbArtText (): string {
    switch (this.pzb.element) {
      case PZBElement.F500Hz:
        return '500Hz'
      case PZBElement.F1000Hz:
        return '1000Hz'
      case PZBElement.F2000Hz:
        return '2000Hz'
      case PZBElement.F1000Hz2000Hz:
        return '1000Hz & 2000Hz'
      default:
        return 'Unbekannt'
    }
  }

  pzbTypText (): string {
    switch (this.pzb.type) {
      case PZBType.GM:
        return 'Gleismagnet'
      case PZBType.GUE_GSA:
        return 'Ausschaltmagnet'
      case PZBType.GUE_GSE:
        return 'Einschaltmagnet'
      default:
        return 'Unbekannt'
    }
  }
}
</script>
