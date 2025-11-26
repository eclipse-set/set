/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { setMapScale } from '@/util/MapScale'
import { Attribution, Rotate, Zoom } from 'ol/control'
import { createEmpty, Extent } from 'ol/extent'
import OlMap from 'ol/Map'
import { InjectionKey } from 'vue'
import { createStore, Store } from 'vuex'
import { DBRef } from './model/Position'
import SiteplanModel from './model/SiteplanModel'
import Configuration, { TrackWidth } from './util/Configuration'
import EmptyMap from './util/MapSources/EmptyMap'
import NamedFeatureLayer from './util/NamedFeatureLayer'
import PlanProToolbox from './util/PlanProToolbox'
import { ToolboxConfiguration } from './util/ToolboxConfiguration'

export interface IError {
  iserror: boolean
  msg: string
}

export enum PlanProModelType {
  SITEPLAN = 'siteplan',
  OVERVIEWPLAN = 'overviewplan'
}

export enum TableType
{
  INITIAL = 'initial',
  FINAL = 'final',
  DIFF = 'diff',
  SINGLE = 'single'
}

export interface State {
  planproModelType: PlanProModelType
  planproToolboxConfiguration: ToolboxConfiguration | null
  selectedFeatureGuid: string
  selectedFeatureOffset: number
  matchingFeatures: number
  map: OlMap
  model: SiteplanModel | null
  routeVisible: boolean
  trackSectionColorVisible: boolean
  trackOutlineVisible: boolean
  trackColorVisible: boolean
  loading: boolean
  error: IError
  selectedSourceMap: string
  featureLayers: NamedFeatureLayer[]
  mainTrackWidth: number
  sideTrackWidth: number
  otherTrackWidth: number
  trackOutlineWidth: number
  siteplanfont: string | null
  allFeaturesExtent: Extent
  boundingBoxScaleFactor: number
  sessionState: TableType
  collisionEnabled: boolean
  planningObjectGuids: { [key: string]: true, }
  sheetCutCRS: DBRef
  isSheetCutAvaiable: boolean
  visibleCants: { [key: string]: true, }
  measureEnable: boolean
  pixelPerMeterAtScale: Map<number, number>
  customPpm: number

}

export const state: InjectionKey<Store<State>> = Symbol('PlanProState')
export const store = createStore<State>({
  state: {
    planproToolboxConfiguration: null,
    selectedFeatureGuid: '',
    selectedFeatureOffset: 0,
    matchingFeatures: 0,
    map: new OlMap({
      controls: [
        new Rotate({
          autoHide: false,
          tipLabel: 'Drehung zurücksetzen'
        }),
        new Zoom({
          zoomInTipLabel: 'Vergrößern',
          zoomOutTipLabel: 'Verkleinern'
        }),
        new Attribution({
          tipLabel: 'Quellenverweis'
        })
      ]
    }),
    model: null,
    routeVisible: false,
    trackSectionColorVisible: false,
    trackOutlineVisible: false,
    trackColorVisible: false,
    loading: false,
    error: {
      iserror: false,
      msg: ''
    },
    selectedSourceMap: new EmptyMap().getIdentifier(),
    featureLayers: [],
    mainTrackWidth: 0,
    sideTrackWidth: 0,
    otherTrackWidth: 0,
    trackOutlineWidth: 0,
    siteplanfont: null,
    allFeaturesExtent: createEmpty(),
    boundingBoxScaleFactor: 90,
    sessionState: TableType.DIFF,
    collisionEnabled: true,
    planningObjectGuids: {},
    sheetCutCRS: DBRef.DR0,
    isSheetCutAvaiable: false,
    visibleCants: {},
    planproModelType: PlanProModelType.SITEPLAN,
    measureEnable: false,
    pixelPerMeterAtScale: new Map(),
    customPpm: 5
  },
  mutations: {
    setpptConfiguration (state, payload: ToolboxConfiguration) {
      state.planproToolboxConfiguration = payload
      state.collisionEnabled = payload.defaultCollisionsEnabled
    },
    selectFeature (state, payload: string) {
      state.selectedFeatureGuid = payload
      setMapScale(state.map.getView(), Configuration.getInternalDefaultLodScale())
    },
    setSelectFeatureOffset (state, payload: number) {
      state.selectedFeatureOffset = payload
    },
    setMatchingCount (state, payload: number) {
      state.matchingFeatures = payload
    },
    setModel (state, payload: SiteplanModel | null) {
      state.model = payload
      state.model?.objectManagement.forEach(objman =>
        objman.planningObjectIDs.forEach(id => {
          state.planningObjectGuids[id] = true
        }))
    },
    setRouteVisible (state, payload: boolean) {
      state.routeVisible = payload
      store.commit('refreshMap')
    },
    setTrackSectionColorVisible (state, payload: boolean) {
      state.trackSectionColorVisible = payload
      store.commit('refreshMap')
    },
    setTrackOutlineVisible (state, payload: boolean) {
      state.trackOutlineVisible = payload
      store.commit('refreshMap')
    },
    setTrackColorVisible (state, payload: boolean) {
      state.trackColorVisible = payload
      store.commit('refreshMap')
    },
    resetMap (state) {
      state.map = new OlMap({})
    },
    refreshMap (state) {
      state.map.getLayers().forEach(layer => layer.changed())
    },
    setLoading (state, payload: boolean) {
      state.loading = payload
      if (PlanProToolbox.inPPT() && state.planproModelType === PlanProModelType.SITEPLAN) {
        PlanProToolbox.setSiteplanLoadingState(payload)
      }
    },
    setError (state, payload: IError) {
      state.error = payload
    },
    setSourceMap (state, payload: string) {
      state.selectedSourceMap = payload
    },
    setFeatureLayers (state, payload: NamedFeatureLayer[]) {
      state.featureLayers = payload
    },
    setMainTrackWidth (state, payload: number) {
      state.mainTrackWidth = payload
      store.commit('refreshMap')
    },
    setSideTrackWidth (state, payload: number) {
      state.sideTrackWidth = payload
      store.commit('refreshMap')
    },
    setOtherTrackWidth (state, payload: number) {
      state.otherTrackWidth = payload
      store.commit('refreshMap')
    },

    setTrackOutlineWidth (state, payload:number) {
      state.trackOutlineWidth = payload
      store.commit('refreshMap')
    },
    setCollisionEnabled (state, payload: boolean) {
      state.collisionEnabled = payload
    },
    defaultTrackWidth (state, payload: TrackWidth) {
      state.mainTrackWidth = payload.main
      state.otherTrackWidth = payload.other
      state.sideTrackWidth = payload.side
      state.trackOutlineWidth = payload.outline
    },
    setSiteplanfont (state, payload: string) {
      state.siteplanfont = payload
    },
    setAllFeaturesExtent (state, payload: Extent) {
      state.allFeaturesExtent = payload
    },
    setBoundingBoxScaleFactor (state, payload: number) {
      state.boundingBoxScaleFactor = payload
    },
    setSessionState (state, payload: TableType) {
      state.sessionState = payload
    },
    setSheetCutCRS (state, payload: DBRef) {
      state.sheetCutCRS = payload
    },
    setSheetCutAvaiable (state, payload: boolean) {
      state.isSheetCutAvaiable = payload
    },
    setCantVisible (state, cant: string) {
      state.visibleCants[cant] = true
      store.commit('refreshMap')
    },
    setCantInvisible (state, cant: string) {
      delete state.visibleCants[cant]
      store.commit('refreshMap')
    },
    setPlanProModelType (state, type: PlanProModelType) {
      state.planproModelType = type
    },
    setMeasureEnable (state, value: boolean) {
      state.measureEnable = value
    },
    setPixelProMeter (state, payload: {
      scaleValue: number
      ppm: number
    }) {
      state.pixelPerMeterAtScale.set(payload.scaleValue, payload.ppm)
    },
    setCustomPpm (state, payload: number) {
      state.customPpm = payload
    }
  }
})
