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
      <li>Typ: {{ getType(lockkey) }}</li>
      <RouteInfo :feature="lockkey" />
      <li>Planungsbereich: {{ planningObject }}</li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import RouteInfo from '@/components/popup/RouteInfo.vue'
import LockKey, { LockKeyType } from '@/model/LockKey'
import {
  getFeatureData, getFeatureGUID, getFeatureLabel
} from '@/feature/FeatureInfo'
import { isPlanningObject } from '@/model/SiteplanModel'

/**
 * Popup contents for Lockkey features
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
    lockkey: function () {
      return getFeatureData(this.feature)
    },

    planningObject: function () {
      return isPlanningObject(getFeatureGUID(this.feature))
        ? 'Ja'
        : 'Nein'
    },

    lockKeyLabel: function () {
      return getFeatureLabel(this.feature)
    }
  }
})
export default class LockKeyPopup extends Vue {
  lockkey!: LockKey
  planningObject!: string
  lockKeyLabel!: string
  getType (lockkey: LockKey) {
    switch (lockkey.type) {
      case LockKeyType.Inside:
        return 'innen'
      case LockKeyType.Outside:
        return 'aussen'
      default:
        return ''
    }
  }
}
</script>
