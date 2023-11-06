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
        :features="selectedFeatures"
        :mouse-button="mouseDownButton"
      />
    </div>
  </teleport>
</template>
<script lang="ts">
import MenuPopup from '@/components/popup/MenuPopup.vue'
import { LeftRight } from '@/model/SiteplanModel'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { Collection, Feature, MapBrowserEvent } from 'ol'
import Geometry from 'ol/geom/Geometry'
import Select, { SelectEvent } from 'ol/interaction/Select'
import Map from 'ol/Map'
import Overlay from 'ol/Overlay'
import { Options, Vue } from 'vue-class-component'

/**
 * Container for feature info popups
 *
 * @author Stuecker
 */
@Options({
  components: {
    MenuPopup
  },
  props: {
    map: Map,
    featureLayers: Object
  }
})
export default class FeatureInfoPopup extends Vue {
  map!: Map
  featureLayers!: NamedFeatureLayer[]
  mouseDownButton: LeftRight | null = null
  infoPopup!: Overlay
  selectedFeatures: Collection<Feature<Geometry>> = new Collection()
  featureSelectionKey = 0

  mounted (): void {
    // Allow feature selection
    const select = new Select({
      style: null,
      condition: this.onFeatureClick,
      hitTolerance: 10,
      multi: true,
      features: this.selectedFeatures
    })
    this.map.addInteraction(select)
    select.on('select', this.onFeatureSelect)

    // Add info window overlay
    const element = document.getElementById('featureInfoPopup')
    if (element === null) {
      return
    }

    this.infoPopup = new Overlay({
      element
    })
    this.map.addOverlay(this.infoPopup)
  }

  resetSelection (): void {
    this.selectedFeatures.clear()
    this.infoPopup.setPosition(undefined)
  }

  onFeatureSelect (event: SelectEvent): void {
    // Update popup
    this.featureSelectionKey++
    // Reset state if no features are selected
    const features = event.selected
    if (!features || features.length === 0) {
      this.resetSelection()
      return
    }

    this.infoPopup.setPosition(event.mapBrowserEvent.coordinate)
  }

  onFeatureClick (event: MapBrowserEvent<PointerEvent>): boolean {
    if (event.originalEvent.type === 'pointerdown') {
      switch (event.originalEvent.buttons) {
        case 1: {
          this.mouseDownButton = LeftRight.LEFT
          break
        }
        case 2: {
          this.mouseDownButton = LeftRight.RIGHT
          break
        }
        default: {
          this.mouseDownButton = null
        }
      }
    }

    return (
      event.originalEvent.type === 'pointerdown' &&
        (
          this.mouseDownButton === LeftRight.LEFT ||
          this.mouseDownButton === LeftRight.RIGHT
        )
    )
  }
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