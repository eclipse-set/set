<!--
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div>
    <h2>{{ elementType }}: {{ elementLabel }}</h2>
    <ul>
      <li
        class="menuItem"
        @click="jumpToTextSicht"
      >
        Zeige in Textsicht
      </li>
    </ul>
  </div>
</template>
<script lang="ts">
import { getFeatureLabel, getFeatureName, getFeatureType } from '@/feature/FeatureInfo'
import { getFeatureGUIDs } from '@/util/FeatureExtensions'
import PlanProToolbox from '@/util/PlanProToolbox'
import { Options, Vue } from 'vue-class-component'

/**
 * Popup contents for signal features
 *
 * @author Stuecker
 */
@Options({
  props: {
    feature: Object
  },
  computed: {
    guid: function () {
      return getFeatureGUIDs(this.feature)[0]
    },
    elementType: function () {
      return getFeatureName(getFeatureType(this.feature))
    },
    elementLabel: function () {
      return getFeatureLabel(this.feature)
    }
  }
})
export default class JumpMenuPopup extends Vue {
  guid!: string
  elementType!: string
  elementLabel!: string

  jumpToTextSicht () {
    PlanProToolbox.jumpToTextView(this.guid)
  }
}
</script>
<style scoped>
.menuItem {
  cursor: pointer;
  margin-bottom: 5px;
  border-radius: 10px;
  background-color: gainsboro;
  padding-left: 5px;
}

.menuItem:hover {
  background-color: darkgray;
}

</style>
