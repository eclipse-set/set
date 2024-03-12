/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import SiteplanModel from '@/model/SiteplanModel'
import { Control } from 'ol/control'
import View from 'ol/View'
import { setMapScale } from '../MapScale'
import { PlanProModelType, store } from '@/store'

/**
 * Control to reset the map view to the leading coordinate of
 * the main route as specified by the model
 *
 * @author Stuecker
 */
export default class CenterMainRouteControl extends Control {
  private view: View
  private model: SiteplanModel

  /**
     * Constructor
     *
     * @param view The view to update
     * @param model The model to use
     */
  constructor (view: View, model: SiteplanModel) {
    // Create button
    const button = document.createElement('button')
    button.innerHTML = '&#10683;' // HTML Encoding for "⦻"
    button.className = 'center-route-control-button'
    button.title = 'Zentrieren'

    // Gray out the button if the center position is not available
    if (model.centerPosition === undefined) {
      button.classList.add('disabled')
    } else {
      button.classList.remove('disabled')
    }

    // Create container
    const element = document.createElement('div')
    element.className = 'center-route-control-container ol-unselectable ol-control'
    element.appendChild(button)

    super({
      element
    })

    this.model = model
    this.view = view
    button.addEventListener('click', () => {
      this.centerView()
    }, false)
  }

  /**
     * Updates the view to show the specified position in the model at a scale of 1:1000
     */
  public centerView (): void {
    if (this.model.centerPosition === undefined) {
      return
    }

    this.view.setCenter([this.model.centerPosition.x, this.model.centerPosition.y])
    this.view.setRotation(((-this.model.centerPosition.rotation + 90) * Math.PI) / 180)
    const scale = store.state.planproModelType === PlanProModelType.SITEPLAN ? 1000 : 100000
    setMapScale(this.view, scale)
  }
}
