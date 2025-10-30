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
      :model="model"
    />
    <FeatureInfoPopup
      :map="map"
      :feature-layers="featureLayers"
    />
  </div>
</template>
<script lang="ts">
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
import { nextTick } from 'vue'
import { Options, Vue } from 'vue-class-component'
import { SubscribeOptions } from 'vuex'
import CollisionService from '../collision/CollisionService'
import SiteplanModel, { SiteplanColorValue } from '../model/SiteplanModel'
import SvgService from '../service/SvgService'
import Configuration from '../util/Configuration'
import CenterMainRouteControl from '../util/Controls/CenterMainRouteControl'
import ExtentControl from '../util/Controls/ExtentControl'
import NamedFeatureLayer from '../util/NamedFeatureLayer'

/**
 * Feature service to create open layers features for the siteplan model
 *
 * @author Stuecker
 */
@Options({
  created () {
    this.registerFeature(store.state.map)
    this.unsubscribe = store.subscribe(m => {
      if (m.type === 'setRouteVisible') {
        this.featureLayers.forEach((layer: NamedFeatureLayer) => {
          if (layer.getLayerType() === FeatureLayerType.Route) {
            layer.changed()
          }
        })
      } else if (m.type === 'setSessionState') {
        this.onModelChange(this.model)
      }
    })
  },
  components: {
    JumpToGuid,
    RouteFeature,
    FeatureInfoPopup
  },
  watch: {
    model (newmodel) {
      this.onModelChange(newmodel)
    }
  },

  beforeUnmount () {
    store.commit('setRouteVisible', false)
    this.unsubscribe()
  }
})
export default class FeatureService extends Vue {
  static readonly COLOR_UNCHANGED_VIEW = [140, 150, 157]
  static readonly COLOR_UNCHANGED_PLANNING = [0, 0, 0]
  static readonly COLOR_ADDED = [179, 40, 33] // RAL3016
  static readonly COLOR_REMOVED = [241, 221, 56] // RAL1016

  featureLayers: NamedFeatureLayer[] = []
  unsubscribe: SubscribeOptions | undefined
  map: Map = store.state.map
  model: SiteplanModel | null = null
  svgService: SvgService = new SvgService(axios)
  listFeature: ILageplanFeature[] = []
  inLODView = false
  collisionService = new CollisionService(this.map)

  mounted (): void {
    this.createLayers()
    this.loadModel()
    store.subscribe((m, s) => {
      if (m.type === 'setSheetCutCRS') {
        PlanProToolbox.changeLayoutCRS(s.sheetCutCRS)
        store.commit('setLoading', true)
        this.loadModel()
      }
    })
  }

  private loadModel () {
    // Download the current model
    const modelType = store.state.planproModelType
    axios
      .get<SiteplanModel>(`/${modelType}.json`)
      .then(response => {
        this.modelLoaded(response.data)
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

  unmounted (): void {
    // Reset stored Map
    store.commit('resetMap')
  }

  onModelChange (newValue: SiteplanModel) {
    try {
      // Remove all existing features
      this.featureLayers.forEach(layer => layer.getSource()?.clear())
      this.collisionService.reset()

      if (newValue.layoutInfo !== undefined && newValue.layoutInfo !== null && newValue.layoutInfo.length > 0) {
        store.commit('setSheetCutAvaiable', true)
      }

      // Load features
      this.listFeature
        .flatMap(feature => {
          if (feature instanceof LayoutInfoFeature) {
            return feature.getLayoutFeatures(newValue.layoutInfo)
          }

          return this.loadFeatureType(newValue, feature)
        })
        .groupBy(c => getFeatureLayer(c))
        .forEach((features, layerIndex) => {
          const layer = this.featureLayers.find(
            c => c.getLayerType() === layerIndex
          )
          layer?.getSource()?.addFeatures(features)
        })

      this.listFeature
        .sort((a, b) => a.getDelayedFeatureOrder() - b.getDelayedFeatureOrder())
        .flatMap(feature => this.loadDelayedFeatureType(newValue, feature))
        .groupBy(c => getFeatureLayer(c))
        .forEach((features, index) => {
          const layer = this.featureLayers.find(
            c => c.getLayerType() === index
          )
          layer?.getSource()?.addFeatures(features)
        })

      // Create bounding boxes
      CollisionFeature.addCollisionFeatures(this.featureLayers)
      // Start collision detection
      this.collisionService.processCollisions(this.featureLayers)
    } catch (exception) {
      console.error('Siteplan model update failed')
      console.error(exception)
    }
  }

  private loadFeatureType (
    model: SiteplanModel,
    featureClass: ILageplanFeature
  ): Feature<Geometry>[] {
    try {
      // eslint-disable-next-line default-case
      switch (store.state.sessionState) {
        case TableType.INITIAL:
          return [
            model.commonState,
            model.initialState,
            model.changedInitialState
          ]
            .map(state => featureClass.getFeatures(state))
            .flat()
            .map(feature => featureClass.setFeatureColor(feature))
        case TableType.FINAL:
          return [
            model.commonState,
            model.finalState,
            model.changedFinalState
          ]
            .map(state => featureClass.getFeatures(state))
            .flat()
            .map(feature => featureClass.setFeatureColor(feature))
        case TableType.DIFF:{
          const compareState = featureClass.compareChangedState(
            model.changedInitialState,
            model.changedFinalState
          )
          return [
            featureClass
              .getFeatures(model.commonState)
              .map(feature => featureClass.setFeatureColor(feature)),
            compareState,
            featureClass
              .getFeatures(model.finalState)
              .map(feature =>
                featureClass.setFeatureColor(
                  feature,
                  SiteplanColorValue.COLOR_ADDED
                )),
            featureClass
              .getFeatures(model.initialState)
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

  private loadDelayedFeatureType (
    model: SiteplanModel,
    featureClass: ILageplanFeature
  ): Feature<Geometry>[] {
    try {
      if (featureClass != null) {
        return featureClass
          .getDelayedFeatures(model.commonState, this.featureLayers)
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

  private createLayers (): void {
    const baseIndex = 2
    this.featureLayers = Object.values(FeatureLayerType)
      .filter(x => isNaN(Number(x)))
      .map((key, index) => new NamedFeatureLayer(baseIndex + index, index))
    this.featureLayers.forEach(layer => {
      this.map.addLayer(layer)
      if (layer.getLayerType() === FeatureLayerType.SheetCut && !Configuration.developmentMode()) {
        layer.setVisible(false)
      }
    })
    store.state.featureLayers = this.featureLayers
    store.commit('setFeatureLayers', this.featureLayers)
  }

  private registerFeature () {
    this.listFeature.push(new ErrorFeature(this.map))
    this.listFeature.push(new FMAFeature(this.map))
    this.listFeature.push(new PlatformFeature(this.map))
    this.listFeature.push(new PZBFeature(this.map))
    this.listFeature.push(new PZBGUFeature(this.map))
    this.listFeature.push(new RouteFeature(this.map))
    this.listFeature.push(new RouteMarkerFeature(this.map))
    this.listFeature.push(new SignalFeature(this.map))
    this.listFeature.push(new SignalRouteMarkerFeature(this.map))
    this.listFeature.push(new StationFeature(this.map))
    this.listFeature.push(new TrackDesignationMarkerFeature(this.map))
    this.listFeature.push(new TrackFeature(this.map))
    this.listFeature.push(new TrackLockFeature(this.map))
    this.listFeature.push(new TrackSectionMarkerFeature(this.map))
    this.listFeature.push(new TrackSwitchEndMarkerFeature(this.map))
    this.listFeature.push(new TrackSwitchFeature(this.map))
    this.listFeature.push(new TrackCloseFeature(this.map))
    this.listFeature.push(new ExternalElementControlFeature(this.map))
    this.listFeature.push(new LockKeyFeature(this.map))
    this.listFeature.push(new LayoutInfoFeature(this.map))
    this.listFeature.push(new CantFeature(this.map))
    this.listFeature.push(new CantLineFeature(this.map))
    this.listFeature.push(new UnknownObjectFeature(this.map))
    this.listFeature.push(new TrackDirectionFeature(this.map))
  }

  private modelLoaded (model: SiteplanModel) {
    this.model = model
    // console.log(JSON.stringify(model))
    store.commit('setModel', this.model)

    // Refresh the vector layer (including features) when the map is rotated
    const view = this.map.getView()
    view.on('change:rotation', () => {
      this.featureLayers.forEach(layer => layer.changed())
    })

    // Once the map is loaded, set up LOD limits
    // This cannot be applied immediately, as a view with a center is required
    this.map.once('rendercomplete', () => {
      const lodScale = Configuration.getLodScale()
      const lodResolution = getResolutionForScale(this.map.getView(), lodScale)
      const lodZoom = this.map.getView().getZoomForResolution(lodResolution)
      if (!lodZoom) {
        return
      }

      this.featureLayers
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
      !this.map
        .getControls()
        .getArray()
        .find(c => c instanceof ExtentControl)
    ) {
      const extentControl = new ExtentControl(view, this.featureLayers)
      this.map.addControl(extentControl)

      const centerControl = new CenterMainRouteControl(view, model)
      this.map.addControl(centerControl)

      this.map.addControl(new MeasureControl(this.map))

      // If a center position is available, apply it. Otherwise zoom to fit
      if (this.model.centerPosition === undefined) {
        nextTick(() => {
          extentControl.fit()
        })
      } else {
        nextTick(() => {
          centerControl.centerView()
        })
      }

      this.map.addInteraction(new FeatureClickInteraction())
    }
  }
}
</script>
