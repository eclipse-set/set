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
    <JumpToGuid
      :map="map"
      :feature-layers="featureLayers"
    />
    <FeatureInfoPopup
      :map="map"
      :feature-layers="featureLayers"
    />
  </div>
</template>
<script setup lang="ts">
import CollisionFeature from '@/collision/CollisionFeature'
import JumpToGuid from '@/components/development/JumpToGuid.vue'
import FeatureInfoPopup from '@/components/FeatureInfoPopup.vue'
import CantFeature from '@/feature/CantFeature'
import CantLineFeature from '@/feature/CantLineFeature'
import ErrorFeature from '@/feature/ErrorFeature'
import ExternalElementControlFeature from '@/feature/ExternalElementControlFeature'
import { FeatureLayerType, getFeatureLayer } from '@/feature/FeatureInfo'
import FMAFeature from '@/feature/FMAFeature'
import { ILageplanFeature } from '@/feature/LageplanFeature'
import LayoutInfoFeature from '@/feature/LayoutInfoFeature'
import LockKeyFeature from '@/feature/LockKeyFeature'
import PlatformFeature from '@/feature/PlatformFeature'
import PZBFeature from '@/feature/PZBFeature'
import PZBGUFeature from '@/feature/PZBGUFeature'
import RouteFeature from '@/feature/RouteFeature'
import RouteMarkerFeature from '@/feature/RouteMarkerFeature'
import SignalFeature from '@/feature/SignalFeature'
import SignalRouteMarkerFeature from '@/feature/SignalRouteMarkerFeature'
import StationFeature from '@/feature/StationFeature'
import TrackCloseFeature from '@/feature/TrackCloseFeature'
import TrackDesignationMarkerFeature from '@/feature/TrackDesignationMarkerFeature'
import TrackDirectionFeature from '@/feature/TrackDirectionFeature'
import TrackFeature from '@/feature/TrackFeature'
import TrackLockFeature from '@/feature/TrackLockFeature'
import TrackSectionMarkerFeature from '@/feature/TrackSectionMarkerFeature'
import TrackSwitchEndMarkerFeature from '@/feature/TrackSwitchEndMarkerFeature'
import TrackSwitchFeature from '@/feature/TrackSwitchFeature'
import UnknownObjectFeature from '@/feature/UnknownObjectFeature'
import { store, TableType } from '@/store'
import '@/util/ArrayExtensions'
import MeasureControl from '@/util/Controls/MeasureControl'
import FeatureClickInteraction from '@/util/FeatureClickInteraction'
import { getResolutionForScale } from '@/util/MapScale'
import PlanProToolbox from '@/util/PlanProToolbox'
import axios from 'axios'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import Map from 'ol/Map'
import { nextTick, onBeforeUnmount, onMounted, onUnmounted, ref, shallowRef, watch } from 'vue'
import CollisionService from '../collision/CollisionService'
import SiteplanModel, { SiteplanColorValue } from '../model/SiteplanModel'
import Configuration from '../util/Configuration'
import CenterMainRouteControl from '../util/Controls/CenterMainRouteControl'
import ExtentControl from '../util/Controls/ExtentControl'
import NamedFeatureLayer from '../util/NamedFeatureLayer'

/**
 * Feature service to create open layers features for the siteplan model
 *
 * @author Stuecker
 */
const map: Map = store.state.map
const featureLayers = shallowRef<NamedFeatureLayer[]>([])
const model = ref<SiteplanModel | null>(null)
const listFeature: ILageplanFeature[] = []
const collisionService = new CollisionService(map)
let unsubscribeRouteAndSession: (() => void) | undefined
let unsubscribeSheetCut: (() => void) | undefined

registerFeature()

unsubscribeRouteAndSession = store.subscribe(m => {
  if (m.type === 'setRouteVisible') {
    featureLayers.value.forEach(layer => {
      if (layer.getLayerType() === FeatureLayerType.Route) {
        layer.changed()
      }
    })
  } else if (m.type === 'setSessionState' && model.value !== null) {
    onModelChange(model.value)
  }
})

watch(model, newModel => {
  if (newModel !== null) {
    onModelChange(newModel)
  }
})

onMounted(() => {
  createLayers()
  loadModel()

  unsubscribeSheetCut = store.subscribe((m, s) => {
    if (m.type === 'setSheetCutCRS') {
      PlanProToolbox.changeLayoutCRS(s.sheetCutCRS)
      store.commit('setLoading', true)
      loadModel()
    }
  })
})

onBeforeUnmount(() => {
  store.commit('setRouteVisible', false)
  unsubscribeRouteAndSession?.()
  unsubscribeSheetCut?.()
})

onUnmounted(() => {
  // Reset stored Map
  store.commit('resetMap')
})

function loadModel () {
  // Download the current model
  const modelType = store.state.planproModelType
  axios
    .get<SiteplanModel>(`/${modelType}.json`)
    .then(response => {
      modelLoaded(response.data)
      store.commit('setLoading', false)
    })
    .catch(e => {
      console.error(`Could not load ${modelType}.json`)
      console.error(e)
      store.commit('setLoading', false)
      store.commit('setError', {
        iserror: true,
        msg: `Could not load ${modelType}.json`
      })
    })
}

function onModelChange (newValue: SiteplanModel) {
  try {
    // Remove all existing features
    featureLayers.value.forEach(layer => layer.getSource()?.clear())
    collisionService.reset()

    if (newValue.layoutInfo !== undefined && newValue.layoutInfo !== null && newValue.layoutInfo.length > 0) {
      store.commit('setSheetCutAvaiable', true)
    }

    // Load features
    listFeature
      .flatMap(feature => {
        if (feature instanceof LayoutInfoFeature) {
          return feature.getLayoutFeatures(newValue.layoutInfo)
        }

        return loadFeatureType(newValue, feature)
      })
      .groupBy(c => getFeatureLayer(c))
      .forEach((features, layerIndex) => {
        const layer = featureLayers.value.find(
          c => c.getLayerType() === layerIndex
        )
        layer?.getSource()?.addFeatures(features)
      })

    listFeature
      .sort((a, b) => a.getDelayedFeatureOrder() - b.getDelayedFeatureOrder())
      .flatMap(feature => loadDelayedFeatureType(newValue, feature))
      .groupBy(c => getFeatureLayer(c))
      .forEach((features, index) => {
        const layer = featureLayers.value.find(
          c => c.getLayerType() === index
        )
        layer?.getSource()?.addFeatures(features)
      })

    // Create bounding boxes
    CollisionFeature.addCollisionFeatures(featureLayers.value)
    // Start collision detection
    collisionService.processCollisions(featureLayers.value)
  } catch (exception) {
    console.error('Siteplan model update failed')
    console.error(exception)
  }
}

function loadFeatureType (
  loadedModel: SiteplanModel,
  featureClass: ILageplanFeature
): Feature<Geometry>[] {
  try {
    // eslint-disable-next-line default-case
    switch (store.state.sessionState) {
      case TableType.INITIAL:
        return [
          loadedModel.commonState,
          loadedModel.initialState,
          loadedModel.changedInitialState
        ]
          .map(state => featureClass.getFeatures(state))
          .flat()
          .map(feature => featureClass.setFeatureColor(feature))
      case TableType.FINAL:
        return [
          loadedModel.commonState,
          loadedModel.finalState,
          loadedModel.changedFinalState
        ]
          .map(state => featureClass.getFeatures(state))
          .flat()
          .map(feature => featureClass.setFeatureColor(feature))
      case TableType.DIFF: {
        const compareState = featureClass.compareChangedState(
          loadedModel.changedInitialState,
          loadedModel.changedFinalState
        )
        return [
          featureClass
            .getFeatures(loadedModel.commonState)
            .map(feature => featureClass.setFeatureColor(feature)),
          compareState,
          featureClass
            .getFeatures(loadedModel.finalState)
            .map(feature =>
              featureClass.setFeatureColor(
                feature,
                SiteplanColorValue.COLOR_ADDED
              )),
          featureClass
            .getFeatures(loadedModel.initialState)
            .map(feature =>
              featureClass.setFeatureColor(
                feature,
                SiteplanColorValue.COLOR_REMOVED
              ))
        ].flat()
      }
    }
  } catch (e) {
    console.error(e)
    console.error('Cannot load feature ' + featureClass.constructor.name)
    store.commit('setLoading', false)
    store.commit('setError', {
      iserror: true,
      msg: 'Cannot load ' + featureClass.constructor.name
    })
  }
  return []
}

function loadDelayedFeatureType (
  loadedModel: SiteplanModel,
  featureClass: ILageplanFeature
): Feature<Geometry>[] {
  try {
    if (featureClass != null) {
      return featureClass
        .getDelayedFeatures(loadedModel.commonState, featureLayers.value)
        .map(feature => featureClass.setFeatureColor(feature))
    }
  } catch (e) {
    console.error(e)
    console.error('Cannot load feature')
    store.commit('setLoading', false)
    store.commit('setError', {
      iserror: true,
      msg: 'Cannot load feature'
    })
  }
  return []
}

function createLayers (): void {
  const baseIndex = 2
  featureLayers.value = Object.values(FeatureLayerType)
    .filter(x => isNaN(Number(x)))
    .map((key, index) => new NamedFeatureLayer(baseIndex + index, index))
  featureLayers.value.forEach(layer => {
    map.addLayer(layer)
    if (layer.getLayerType() === FeatureLayerType.SheetCut && !Configuration.developmentMode()) {
      layer.setVisible(false)
    }
  })
  store.state.featureLayers = featureLayers.value
  store.commit('setFeatureLayers', featureLayers.value)
}

function registerFeature () {
  listFeature.push(new ErrorFeature(map))
  listFeature.push(new FMAFeature(map))
  listFeature.push(new PlatformFeature(map))
  listFeature.push(new PZBFeature(map))
  listFeature.push(new PZBGUFeature(map))
  listFeature.push(new RouteFeature(map))
  listFeature.push(new RouteMarkerFeature(map))
  listFeature.push(new SignalFeature(map))
  listFeature.push(new SignalRouteMarkerFeature(map))
  listFeature.push(new StationFeature(map))
  listFeature.push(new TrackDesignationMarkerFeature(map))
  listFeature.push(new TrackFeature(map))
  listFeature.push(new TrackLockFeature(map))
  listFeature.push(new TrackSectionMarkerFeature(map))
  listFeature.push(new TrackSwitchEndMarkerFeature(map))
  listFeature.push(new TrackSwitchFeature(map))
  listFeature.push(new TrackCloseFeature(map))
  listFeature.push(new ExternalElementControlFeature(map))
  listFeature.push(new LockKeyFeature(map))
  listFeature.push(new LayoutInfoFeature(map))
  listFeature.push(new CantFeature(map))
  listFeature.push(new CantLineFeature(map))
  listFeature.push(new UnknownObjectFeature(map))
  listFeature.push(new TrackDirectionFeature(map))
}

function modelLoaded (loadedModel: SiteplanModel) {
  model.value = loadedModel
  // console.log(JSON.stringify(model))
  store.commit('setModel', loadedModel)

  // Refresh the vector layer (including features) when the map is rotated
  const view = map.getView()
  view.on('change:rotation', () => {
    featureLayers.value.forEach(layer => layer.changed())
  })

  // Once the map is loaded, set up LOD limits
  // This cannot be applied immediately, as a view with a center is required
  map.once('rendercomplete', () => {
    const lodScale = Configuration.getLodScale()
    const lodResolution = getResolutionForScale(map.getView(), lodScale)
    const lodZoom = map.getView().getZoomForResolution(lodResolution)
    if (!lodZoom) {
      return
    }

    featureLayers.value
      .filter(
        c =>
          c.getLayerType() !== FeatureLayerType.Track &&
          c.getLayerType() !== FeatureLayerType.Collision &&
          c.getLayerType() !== FeatureLayerType.SheetCut
      )
      .forEach(c => {
        c.setMinZoom(lodZoom)
      })
  })

  // Move the map view to show all features and add the control for it
  // Avoid doing this during hot reloads
  if (
    !map
      .getControls()
      .getArray()
      .find(c => c instanceof ExtentControl)
  ) {
    const extentControl = new ExtentControl(view, featureLayers.value)
    map.addControl(extentControl)

    const centerControl = new CenterMainRouteControl(view, loadedModel)
    map.addControl(centerControl)

    map.addControl(new MeasureControl(map))

    // If a center position is available, apply it. Otherwise zoom to fit
    if (model.value?.centerPosition === undefined) {
      nextTick(() => {
        extentControl.fit()
      })
    } else {
      nextTick(() => {
        centerControl.centerView()
      })
    }

    map.addInteraction(new FeatureClickInteraction())
  }
}
</script>
