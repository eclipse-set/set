/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Label } from '@/model/Label'
import { SiteplanColorValue } from '@/model/SiteplanModel'

import { store } from '@/store'
import Map from 'ol/Map'
import { clampAngle } from './Math'

/**
 * Flips a label if required by the current view rotation
 *
 * @param label the label to flip if needed
 * @param labelRotation the label rotation (or the parent object rotation)
 * @param map the map
 */
export function updateLabelOrientation (label: Label | null | undefined, labelRotation: number, map: Map): void {
  if (label == null || label === undefined || store.state.loading) {
    return
  }

  label.orientationInverted = isLabelFlipRequired(labelRotation, map)
}

/**
 * Colors a label
 *
 * @param label the label to flip if needed
 * @param color the color
 */
export function updateLabelColor (label: Label | null | undefined, color: number[] | undefined): void {
  if (label == null || label === undefined) {
    return
  }

  label.color = color ?? SiteplanColorValue.COLOR_UNCHANGED_PLANNING
}

/**
 * Returns true if flipping a label is required by the current view rotation
 *
 * @param labelRotation the label rotation (or the parent object rotation)
 * @param map the map
 */
export function isLabelFlipRequired (labelRotation: number, map: Map): boolean {
  const mapRotation = map.getView().getRotation() * 180 / Math.PI
  const diff = clampAngle(mapRotation + labelRotation)
  return diff < 180
}
