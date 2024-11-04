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
    <span
      v-if="isMultiFeature() && selectedFeature"
      id="popup-back"
      class="material-icons"
      @click="backToMenu"
    >arrow_back</span>
    <div v-if="isMultiFeature() &&!selectedFeature">
      <h2>Bitte ein Objekt auswählen</h2>
      <ul>
        <li
          v-for="feature in getFeatures()"
          id="menuItem"
          :key="feature.getId()"
          @click="selectedItem(feature)"
        >
          {{ getFeatureName(feature) }}: {{ getLabel(feature) }}
        </li>
      </ul>
    </div>

    <div
      v-else
      id="popup"
    >
      <component
        :is="selectedPopup"
        :feature="selectedFeature"
      />
    </div>
  </div>
</template>
<script lang="ts">
import CantLinePopup from '@/components/popup/CantLinePopup.vue'
import CantPopup from '@/components/popup/CantPopup.vue'
import EECPopup from '@/components/popup/EECPopup.vue'
import EmptyPopup from '@/components/popup/EmptyPopup.vue'
import ErrorPopup from '@/components/popup/ErrorPopup.vue'
import JumpMenuPopup from '@/components/popup/JumpMenuPopup.vue'
import LockKeyPopup from '@/components/popup/LockKeyPopup.vue'
import PZBPopup from '@/components/popup/PZBPopup.vue'
import SignalPopup from '@/components/popup/SignalPopup.vue'
import TrackLockPopup from '@/components/popup/TrackLockPopup.vue'
import TrackSectionPopup from '@/components/popup/TrackSectionPopup.vue'
import TrackSwitchPopup from '@/components/popup/TrackSwitchPopup.vue'
import UnknownPopup from '@/components/popup/UnknownPopup.vue'
import {
  createFeature,
  FeatureType, FlashFeatureData, getFeatureData, getFeatureLabel,
  getFeatureName,
  getFeatureType
} from '@/feature/FeatureInfo'
import {SignalMount} from '@/model/SignalMount'
import {LeftRight} from '@/model/SiteplanModel'
import 'material-design-icons/iconfont/material-icons.css'
import {Feature} from 'ol'
import Geometry from 'ol/geom/Geometry'
import {Options, Vue} from 'vue-class-component'

/**
 * Menu for select object
 *
 * @author Truong
 */
@Options({
  props: {
    features: Object,
    mouseButton: String
  },
  components: {
    SignalPopup,
    TrackSectionPopup,
    PZBPopup,
    TrackSwitchPopup,
    ErrorPopup,
    EmptyPopup,
    TrackLockPopup,
    LockKeyPopup,
    EECPopup,
    JumpMenuPopup,
    CantPopup,
    CantLinePopup,
    UnknownPopup
  },
  computed: {
    selectedPopup () {
      if (this.selectedFeature == null) {
        return this.$emit('removePopup')
      }

      if (getFeatureType(this.selectedFeature) === FeatureType.Flash) {
        this.selectedFeature = (getFeatureData(this.selectedFeature) as FlashFeatureData).refFeature
      }

      if (this.mouseButton === LeftRight.LEFT) {
        switch (getFeatureType(this.selectedFeature)) {
          case FeatureType.Signal:
            return SignalPopup
          case FeatureType.PZB:
          case FeatureType.PZBGU:
            return PZBPopup
          case FeatureType.TrackDesignationMarker:
          case FeatureType.TrackSectionMarker:
          case FeatureType.Track:
          case FeatureType.TrackOutline:
            return TrackSectionPopup
          case FeatureType.TrackSwitchEndMarker:
          case FeatureType.TrackSwitch:
            return TrackSwitchPopup
          case FeatureType.Error:
            return ErrorPopup
          case FeatureType.TrackLock:
            return TrackLockPopup
          case FeatureType.LockKey:
            return LockKeyPopup
          case FeatureType.ExternalElementControl:
            return EECPopup
          case FeatureType.Cant:
            return CantPopup
          case FeatureType.CantLine:
            return CantLinePopup
          case FeatureType.Unknown:
            return UnknownPopup
          default:
            return this.$emit('removePopup')
        }
      } else if (this.mouseButton === LeftRight.RIGHT) {
        return JumpMenuPopup
      }

      return this.$emit('removePopup')
    }
  },
  emits: ['removePopup']
})

export default class MenuPopup extends Vue {
  features!: Feature<Geometry>[]
  selectedPopup!: Vue
  mouseButton!: string
  selectedFeature: Feature<Geometry>|null = null

  getFeatures (): Feature<Geometry>[] {
    if (this.features === null || !this.features) {
      return []
    }

    return this.features.filter(ele => {
      const featureType = getFeatureType(ele)
      return featureType !== FeatureType.Collision &&
        featureType !== FeatureType.Flash
    }).flatMap(ele => {
      const featureType = getFeatureType(ele)
      const featureData = getFeatureData(ele)
      if (featureType === FeatureType.Signal) {
        return (featureData as SignalMount).attachedSignals.map(signal => createFeature(
          FeatureType.Signal,
          signal,
          undefined,
          signal.label?.text
        ))
      }

      return ele
    })
  }

  getFeatureName (feature: Feature<Geometry>): string {
    return getFeatureName(getFeatureType(feature))
  }

  getLabel (feature: Feature<Geometry>): string {
    return getFeatureLabel(feature)
  }

  isMultiFeature (): boolean {
    if (this.getFeatures().length > 1) {
      return true
    }

    this.selectedFeature = this.features[0]
    return false
  }

  backToMenu () {
    this.selectedFeature = null
  }

  selectedItem (feature: Feature<Geometry>) {
    this.selectedFeature = feature
  }
}
</script>
<style scoped>
#menu {
  padding-right: 5px;
  padding-bottom: 5px;
}

#menuItem {
  cursor: pointer;
  margin-bottom: 5px;
  border-radius: 10px;
  background-color: gainsboro;
  padding-left: 5px;
}

#menuItem:hover {
  background-color: darkgray;
}

#popup {
  min-height: 220px;
}

#popup-back {
  position: absolute;
  top: 10px;
  z-index: 200;
  padding: 5px 10px;
  font-size: 18px;
  cursor: pointer;
}
</style>
