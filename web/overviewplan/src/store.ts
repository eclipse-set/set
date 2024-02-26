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
import NamedFeatureLayer from './util/NamedFeatureLayer'
import { Attribution, Rotate, Zoom } from 'ol/control'
import { ToolboxConfiguration } from './util/ToolboxConfiguration'
import { createEmpty, Extent } from 'ol/extent'

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
  loading: boolean
  error: IError
  featureLayers: NamedFeatureLayer[]
  siteplanfont: string | null
  allFeaturesExtent: Extent
  sessionState: TableType
  trackSectionColorVisible: boolean
  planningObjectGuids: { [key: string]: true, }
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
    loading: false,
    error: {
      iserror: false,
      msg: ''
    },
    featureLayers: [],
    siteplanfont: null,
    allFeaturesExtent: createEmpty(),
    sessionState: TableType.DIFF,
    trackSectionColorVisible: false,
    planningObjectGuids: {}
  },
  mutations: {
    setpptConfiguration (state, payload: ToolboxConfiguration) {
      state.planproToolboxConfiguration = payload
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
    setFeatureLayers (state, payload: NamedFeatureLayer[]) {
      state.featureLayers = payload
    },
    setSiteplanfont (state, payload: string) {
      state.siteplanfont = payload
    },
    setAllFeaturesExtent (state, payload: Extent) {
      state.allFeaturesExtent = payload
    },
    setSessionState (state, payload: TableType) {
      state.sessionState = payload
    },
    setTrackSectionColorVisible (state, payload: boolean) {
      state.trackSectionColorVisible = payload
      store.commit('refreshMap')
    }
  }
})
