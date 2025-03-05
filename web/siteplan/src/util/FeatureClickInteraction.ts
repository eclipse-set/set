
import { store } from '@/store'
import { MapBrowserEvent } from 'ol'
import Feature from 'ol/Feature'
import Geometry from 'ol/geom/Geometry'
import { Interaction } from 'ol/interaction'
import { getFeatureGUIDs } from './FeatureExtensions'
import PlanProToolbox from './PlanProToolbox'

/**
 * Interaction for clicking features, which selects the matching row
 * in the PlanPro Toolbox tables
 *
 * @author Stuecker
 */
export default class FeatureClickInteraction extends Interaction {
  lastClickedFeature: Feature<Geometry> | null = null
  lastGUIDIndex = 0

  handleEvent (mapBrowserEvent: MapBrowserEvent<PointerEvent>): boolean {
    // Only on mouse down
    if (mapBrowserEvent.type !== 'pointerdown') {
      return true
    }

    // Only on left mouse actions
    if (mapBrowserEvent.originalEvent.buttons !== 1) {
      return true
    }

    const map = mapBrowserEvent.map
    const features = map.getFeaturesAtPixel(mapBrowserEvent.pixel)
    if (features.length === 0) {
      return true
    }

    this.handleFeatureClick(features[0] as Feature<Geometry>)
    return true
  }

  handleFeatureClick (feature: Feature<Geometry>): void {
    if (store.state.measureEnable) {
      return
    }

    const guids = getFeatureGUIDs(feature)
    if (guids.length === 0) return

    // As some features may have multiple GUIDs
    // (e.g. Signals with multiple attached screens)
    // rotate through the GUIDs of a clicked feature
    if (feature === this.lastClickedFeature) {
      this.lastGUIDIndex++
    } else {
      this.lastGUIDIndex = 0
      this.lastClickedFeature = feature
    }

    // Reset index if we've reached the end
    if (this.lastGUIDIndex === guids.length) {
      this.lastGUIDIndex = 0
    }

    PlanProToolbox.selectTableRow(guids[this.lastGUIDIndex])
  }
}
