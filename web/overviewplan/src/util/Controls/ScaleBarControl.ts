
/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { ScaleLine } from 'ol/control'

/**
 * Scale Bar with the ability to register a click handler
 *
 * @author Stuecker
 */
export default class ScaleBarControl extends ScaleLine {
  /**
     * Constructor
     *
     * @param rotationCallback A callback to call when the scale bar is clicked.
     */
  constructor (clickCallback: () => void) {
    super({
      units: 'metric',
      bar: true,
      steps: 4,
      text: true,
      minWidth: 140
    })
    this.element.addEventListener('click', () => {
      clickCallback()
    }, false)
    this.element.style.cursor = 'pointer'
    this.element.style.pointerEvents = 'auto'
  }
}
