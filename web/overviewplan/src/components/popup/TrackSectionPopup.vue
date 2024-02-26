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
    <h2>Gleis: {{ trackLabel }}</h2>
    <ul>
      <li>GUID: {{ trackSection.guid }}</li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import TrackSection from '@/model/TrackSection'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import { TrackSectionFeatureData } from '@/feature/TrackFeature'
import { getFeatureData } from '@/feature/FeatureInfo'

/**
 * Popup contents for track features
 *
 * @author Peters
 */
@Options({
  props: {
    // As TrackSection is an interface, we cannot bind to the intended type
    feature: Object
  },
  computed: {
    trackSection: function () {
      return this.getData()?.section
    },
    trackLabel: function () {
      return this.getData()?.guid
    }
  }
})
export default class TrackSectionPopup extends Vue {
  trackSection!: TrackSection
  feature!: Feature<Geometry>
  trackLabel!: string

  private getData (): TrackSectionFeatureData | undefined {
    return getFeatureData(this.feature)
  }
}
</script>
