/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { InjectionKey } from 'vue'
import { createStore, Store } from 'vuex'
import OlMap from 'ol/Map'
import SiteplanModel from './model/SiteplanModel'
import EmptyMap from './util/MapSources/EmptyMap'
import NamedFeatureLayer from './util/NamedFeatureLayer'
import { Attribution, Rotate, Zoom } from 'ol/control'
import { ToolboxConfiguration } from './util/ToolboxConfiguration'
import { TrackWidth } from './util/Configuration'
import { createEmpty, Extent } from 'ol/extent'
import { DBRef } from './model/Position'

export interface IError {
  iserror: boolean
  msg: string
}

export enum TableType
{
  INITIAL = 'initial',
  FINAL = 'final',
  DIFF = 'diff',
  SINGLE = 'single'
}

export interface State {

  planproToolboxConfiguration: ToolboxConfiguration | null
  selectedFeatureGuid: string
  selectedFeatureOffset: number
  matchingFeatures: number
  map: OlMap
  model: SiteplanModel | null
  routeVisible: boolean
  trackSectionMarkerVisible: boolean
  trackOutlineVisible: boolean
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
    trackSectionMarkerVisible: false,
    trackOutlineVisible: false,
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
    isSheetCutAvaiable: false
  },
  mutations: {
    setpptConfiguration (state, payload: ToolboxConfiguration) {
      state.planproToolboxConfiguration = payload
      state.collisionEnabled = payload.defaultCollisionsEnabled
    },
    selectFeature (state, payload: string) {
      state.selectedFeatureGuid = payload
    },
    setSelectFeatureOffset (state, payload: number) {
      state.selectedFeatureOffset = payload
    },
    setMatchingCount (state, payload: number) {
      state.matchingFeatures = payload
    },
    setModel (state, payload: SiteplanModel| null) {
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
    setTrackSectionMarkerVisible (state, payload: boolean) {
      state.trackSectionMarkerVisible = payload
      store.commit('refreshMap')
    },
    setTrackOutlineVisible (state, payload: boolean) {
      state.trackOutlineVisible = payload
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
    }
  }
})
