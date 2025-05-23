/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Map } from 'ol'
import { getTopLeft, getTopRight } from 'ol/extent'
import 'ol/ol.css'
import { transform } from 'ol/proj'
import { getDistance } from 'ol/sphere'
import View from 'ol/View'

/**
 * Sets the map scale to 1:scale for the center of the view
 *
 * @param view the view to scale
 * @param scale the scale to apply
 */
export function setMapScale (view: View, scale: number): void {
  // Set an arbitrary zoom level to ensure the view has a resolution
  view.setZoom(10)
  const center = view.getCenter()
  const resolution = view.getResolution()
  const mpu = view.getProjection().getMetersPerUnit()
  if (mpu === undefined || resolution === undefined || center === undefined) {
    return
  }
  /* As OpenLayers does not directly support setting the view scale,
   * we need to calculate the required resolution for the scale.
   * This requires multiple steps:
   *
   * 1) Get the resolution of the center of the view
   * Due to map projections mapping a globe to a plane the map resolution
   * slightly differs between points within the view. As a reference we use
   * the center.
   *
   * 2) As the previous step results in a resolution in a wrong projection,
   * transform the resolution.
   *
   * 3) Apply the inverse to the result of 1)
   */

  // Get the resoultion at the center of the map view in the view projection
  const viewCenterResolution = getResolutionForScale(view, scale)

  // Apply resolution
  view.setResolution(viewCenterResolution)
}

/**
 *
 * @param view the map view
 * @returns the scale of the map
 */
export function getMapScale (view: View): number {
  // Get resolution at center
  const resolution = view.getResolution()
  // DEFAULT_DPI setting from OpenLayers
  const dpi = 25.4 / 0.28
  const mpu = view.getProjection().getMetersPerUnit()
  const inchesPerMeter = 1000 / 25.4
  const center = view.getCenter()
  if (!center || !resolution || !mpu) {
    throw Error('No view center')
  }

  const resolutionScaleFactor = view.getProjection().getPointResolutionFunc()(1, center)
  if (!resolutionScaleFactor) {
    throw Error('No resolutionScaleFactor')
  }

  return resolution * mpu * inchesPerMeter * dpi * resolutionScaleFactor
}

/**
 * Calculates the unit resolution for a given scale
 */
export function getResolutionForScale (view: View, scale: number): number {
  // DEFAULT_DPI setting from OpenLayers
  // IMPROVE: Do we need to handle high dpi screens?
  const dpi = 25.4 / 0.28
  const inchesPerMeter = 1000 / 25.4
  const mpu = view.getProjection().getMetersPerUnit()
  const center = view.getCenter()
  if (!center || !mpu) {
    throw Error('No view center')
  }

  const resolutionScaleFactor = view.getProjection().getPointResolutionFunc()(1, center)
  if (!resolutionScaleFactor) {
    throw Error('No resolutionScaleFactor')
  }

  return (scale / (mpu * inchesPerMeter * dpi)) / resolutionScaleFactor
}

export async function getPixelProMeterAtScale (map: Map, view: View, scale: number) : Promise<number | undefined>{
  const currentResolution = view.getResolution() ?? 1
  setMapScale(view, scale)
  return await new Promise(resolve => {
    map.once('rendercomplete', () => {
      const viewportSize = map.getSize()
      if (!viewportSize) {
        view.setResolution(currentResolution)
        resolve(undefined)
        return
      }

      const viewportExtent = view.calculateExtent(viewportSize)
      const topLeftSphereCoord = transform(getTopLeft(viewportExtent), 'EPSG:3857', 'EPSG:4326')
      const topRightSphereCoord = transform(getTopRight(viewportExtent), 'EPSG:3857', 'EPSG:4326')
      const distanceInMeter = getDistance(topLeftSphereCoord, topRightSphereCoord)
      view.setResolution(currentResolution)
      const currentPpm = viewportSize[0] / distanceInMeter
      resolve(currentPpm)
    })
  })
}

export function getScaleForPpm (view: View, ppm: number): number {
  const center = view.getCenter()
  const mpu = view.getProjection().getMetersPerUnit()
  if (!center || !mpu) {
    throw Error('No view center')
  }

  const resolutionScaleFactor = view.getProjection().getPointResolutionFunc()(1, center)
  if (!resolutionScaleFactor) {
    throw Error('No resolutionScaleFactor')
  }

  const resolution = (1 / ppm) / resolutionScaleFactor
  const dpi = 25.4 / 0.28
  const inchesPerMeter = 1000 / 25.4
  return resolution * mpu * inchesPerMeter * dpi * resolutionScaleFactor
}
