/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { FeatureLayerType } from '@/feature/FeatureInfo'
import { store } from '@/store'
import { getExtent } from '@/util/ExtentExtension'
import { Feature } from 'ol'
import { Control } from 'ol/control'
import { isEmpty } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import View from 'ol/View'
import NamedFeatureLayer from '../NamedFeatureLayer'

/**
 * Control to reset the map extent to include all features
 *
 * @author Stuecker
 */
export default class ExtentControl extends Control {
  public static EXTENT_CONTROL_KEY = 'PLANPRO_EXTENT_CONTROL'
  private layers: NamedFeatureLayer[]
  private view: View

  /**
     * Constructor
     *
     * @param view The view to change
     * @param layer The layer to inspect for features
     */
  constructor (view: View, layers: NamedFeatureLayer[]) {
    // Create button
    const button = document.createElement('button')
    button.innerHTML = '&#x25A3;' // HTML Encoding for "▣"
    button.className = 'extent-control-button'
    button.title = 'Gesamtbereich darstellen'
    // Create container
    const element = document.createElement('div')
    element.className = 'extent-control-container ol-unselectable ol-control'
    element.appendChild(button)

    super({
      element
    })

    this.view = view
    this.layers = layers
    this.view.set(ExtentControl.EXTENT_CONTROL_KEY, this)
    button.addEventListener('click', () => {
      this.fit()
      button.blur()
    }, false)
  }

  /**
     * Fits the view to the features contained in the vector layer
     */
  public fit (): void {
    let extent = store.state.allFeaturesExtent
    if (isEmpty(extent)) {
      const features : Feature<Geometry>[] = []
      this.layers.filter(layer =>
        layer.getLayerType() !== FeatureLayerType.Collision
          && layer.getLayerType() !== FeatureLayerType.SheetCut)
        .forEach(layer => layer.getSource()?.getFeatures()
          .forEach(feature => features.push(feature)))
      // if there is no feature available we set extent to whole germany
      if (features.length === 0) {
        extent = [
          // south western corner of germany's bounding box
          789544.62, 5986273.25,
          // north eastern corner of germany's bounding box
          1671640.27, 7372664.43
        ]
        alert('Es konnten keine Lageplanelemente dargestellt werden. Bitte PlaZ-Modell prüfen.')
      } else {
        extent = getExtent(features)
      }

      store.commit('setAllFeaturesExtent', extent)
    }

    this.view.fit(extent)
  }
}
