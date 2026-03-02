/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Control } from 'ol/control'
import View from 'ol/View'

/**
 * Controls to rotate the view
 *
 * @author Stuecker
 */
export default class RotateViewControl extends Control {
  private rotateAmount: number
  private view: View

  /**
     * Constructor
     *
     * @param rotateRight Whether this is the right button
     * @param rotateAmount The amount (in degrees 0-360) to rotate the view by
     * @param view The view to rotate
     * @param rotationCallback A callback to call upon each movement step.
     *                         This should be used to trigger a 'changed'-Event
     *                         on feature layers to apply rotations
     */
  constructor (rotateRight: boolean, rotateAmount: number, view: View) {
    const directionClass = rotateRight ? 'rotate-control-right' : 'rotate-control-left'

    // Create button
    const button = document.createElement('button')
    button.innerHTML = '&#x2939;' // HTML Encoding for "⤹"
    button.className = 'rotate-control-button ' + directionClass

    // Create container
    const element = document.createElement('div')
    element.className = 'rotate-control-container ol-unselectable ol-control ' + directionClass
    element.appendChild(button)

    super({
      element
    })

    this.rotateAmount = rotateAmount
    this.view = view
    button.addEventListener('click', () => {
      this.onClick()
      button.blur()
    }, false)
  }

  private onClick () {
    const rotation = this.view.getRotation()
    const animDuration = 250
    this.view.animate({
      rotation: rotation + this.rotateAmount * Math.PI / 180,
      duration: animDuration
    }, rotatedSuccessfully => {
      if (!rotatedSuccessfully) {
        this.view.setRotation(rotation + this.rotateAmount * Math.PI / 180)
      }
    })
  }
}
