<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <teleport to="body">
    <div id="featureInfoPopup">
      <span
        class="popup-close"
        @click="resetSelection"
      >X</span>
      <MenuPopup
        :key="featureSelectionKey"
        :features="getPopupAvaibleFeatures()"
        :mouse-button="mouseDownButton"
        @remove-popup="resetSelection"
      />
    </div>
  </teleport>
</template>
<script setup lang="ts">
import MenuPopup from '@/components/popup/MenuPopup.vue'
import { FeatureType, getFeatureData, getFeatureType } from '@/feature/FeatureInfo'
import { LeftRight } from '@/model/SiteplanModel'
import { store } from '@/store'
import { getFeatureGUIDs } from '@/util/FeatureExtensions'
import { compare } from '@/util/ObjectExtension'
import { Collection, Feature, MapBrowserEvent } from 'ol'
import Geometry from 'ol/geom/Geometry'
import Select, { SelectEvent } from 'ol/interaction/Select'
import Map from 'ol/Map'
import Overlay from 'ol/Overlay'
import { onMounted, ref } from 'vue'
/**
 * Container for feature info popups
 *
 * @author Stuecker
 */

const props = defineProps<{
  map: Map
}>()

const mouseDownButton = ref<LeftRight | null>(null)
const infoPopup = ref<Overlay | null>(null)
const selectedFeatures = new Collection<Feature<Geometry>>()
const featureSelectionKey = ref(0)
const existPopupFeature = [
  FeatureType.Signal,
  FeatureType.PZB,
  FeatureType.PZBGU,
  FeatureType.TrackDesignationMarker,
  FeatureType.TrackSectionMarker,
  FeatureType.Track,
  FeatureType.TrackOutline,
  FeatureType.TrackSwitchEndMarker,
  FeatureType.TrackSwitch,
  FeatureType.Error,
  FeatureType.TrackLock,
  FeatureType.LockKey,
  FeatureType.ExternalElementControl,
  FeatureType.Cant,
  FeatureType.CantLine,
  FeatureType.Unknown
]

onMounted(() => {
  // Allow feature selection
  const select = new Select({
    style: null,
    condition: onFeatureClick,
    hitTolerance: 10,
    multi: true,
    features: selectedFeatures
  })
  props.map.addInteraction(select)
  select.on('select', onFeatureSelect)
  store.subscribe(m => {
    if (m.type === 'setMeasureEnable') {
      select.setActive(!m.payload)
    }
  })
  // Add info window overlay
  const element = document.getElementById('featureInfoPopup')
  if (element === null) {
    return
  }

  const overlay = new Overlay({
    element
  })
  infoPopup.value = overlay
  props.map.addOverlay(overlay)
})

function getPopupAvaibleFeatures () {
  const popupAvavaibleFeatures = selectedFeatures.getArray().filter(feature => hasPopup(feature))
  const uniqueFeatures = filterSameFeature(popupAvavaibleFeatures)

  if (uniqueFeatures.length == 0) {
    resetSelection()
    return []
  }

  return uniqueFeatures
}

function filterSameFeature (features: Feature<Geometry>[]): Array<Feature<Geometry>> {
  const result: Feature<Geometry>[] = []
  features.forEach(feature => {
    const featureType = getFeatureType(feature)
    const featuresSameType = result.filter(ele =>
      getFeatureType(ele) === featureType)

    if (featuresSameType.length === 0) {
      result.push(feature)
      return
    }

    const isAlreadyAdded = !featuresSameType.every(ele => compare(getFeatureData(feature), getFeatureData(ele)))
    if (!isAlreadyAdded) {
      result.push(feature)
    }
  })
  return result
}

function hasPopup (feature: Feature<Geometry>): boolean {
  const existInfoPop = mouseDownButton.value === LeftRight.LEFT &&
    existPopupFeature.includes(getFeatureType(feature))
  const existGUID = mouseDownButton.value === LeftRight.RIGHT &&
    getFeatureGUIDs(feature).length > 0
  return existInfoPop || existGUID
}

function resetSelection (): void {
  if (infoPopup.value == null) {
    return
  }

  selectedFeatures.clear()
  infoPopup.value.setPosition(undefined)
}

function onFeatureSelect (event: SelectEvent): void {
  // Update popup
  featureSelectionKey.value++
  // Reset state if no features are selected
  const features = event.selected
  if (!features || features.length === 0) {
    resetSelection()
    return
  }

  infoPopup.value?.setPosition(event.mapBrowserEvent.coordinate)
}

function onFeatureClick (event: MapBrowserEvent): boolean {
  if (event.originalEvent instanceof PointerEvent &&
    event.originalEvent.type === 'pointerdown'
  ) {
    switch (event.originalEvent.buttons) {
      case 1: {
        mouseDownButton.value = LeftRight.LEFT
        break
      }
      case 2: {
        mouseDownButton.value = LeftRight.RIGHT
        break
      }
      default: {
        mouseDownButton.value = null
      }
    }
  }

  return (
    event.originalEvent instanceof PointerEvent &&
    event.originalEvent.type === 'pointerdown' &&
      (
        mouseDownButton.value === LeftRight.LEFT ||
        mouseDownButton.value === LeftRight.RIGHT
      )
  )
}
</script>

<style>
#featureInfoPopup {
  position: fixed;
  z-index: 100;
  width: 430px;
  background-color: #f3f3f3;
  border-radius: 8px;
  box-shadow: 8px 5px 5px grey;
}

#featureInfoPopup h2 {
  padding: 10px 30px;
  font-size: 20px;
  line-height: 30px;
  color: #f3f3f3;
  font-weight: 300;
  width: 100%;
  box-sizing: border-box;
  background-color: rgba(0, 60, 136, 0.7);
  border-radius: 6px 6px 0 0;
  margin-block-start: 0px;
}

.popup-close {
  position: absolute;
  top: 10px;
  right: 7px;
  z-index: 200;
  padding: 5px 10px;
  font-size: 18px;
  cursor: pointer;
}
</style>
