<!--
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <ul>
    <li>
      Verortung
      <ul>
        <li
          v-for="info in entries"
          :key="info.km"
        >
          Strecke {{ info.route }}: {{ info.km }}
        </li>
      </ul>
    </li>
  </ul>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { RouteLocation, RouteObject } from '@/model/RouteObject'

/**
 * Route info for features
 *
 * @author Stuecker
 */
@Options({
  props: {
    feature: Object
  },
  computed: {
    entries: function () {
      return (this.feature as RouteObject).routeLocations
    }
  }
})
export default class RouteInfo extends Vue {
  entries!: RouteLocation[]
}
</script>
