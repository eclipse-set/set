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
    <h2>Fehler</h2>
    <ul>
      <li>Betroffene GUIDs: {{ error.relevantGUIDs.join(', ') }}</li>
      <li>Fehler: {{ error.message }}</li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import Error from '@/model/Error'
import { getFeatureData } from '@/feature/FeatureInfo'

/**
 * Popup contents for Error features
 *
 * @author Stuecker
 */
@Options({
  props: {
    feature: Object
  },
  computed: {
    error: function () {
      return getFeatureData(this.feature)
    }
  }
})
export default class ErrorPopup extends Vue {
  error!: Error
}
</script>
