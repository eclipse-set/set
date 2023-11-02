/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Feature } from 'ol'
import { Extent, getHeight, getWidth, getBottomLeft, createEmpty, extend, getTopLeft, boundingExtent, getCenter } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'

/**
 * Extension for {@link Extent} class for Svg Element
 * @author Truong
 */

/**
   * Get Bound from SVG Element
   * @param element svg element
   * @param needRemove remove tag from element, when true
   * @returns {@see Extent}
   */
export function fromHTMLElement (element: Element, needRemove: boolean): Extent[] {
  const content = needRemove ? element : element.cloneNode(true) as Element
  const boundingBox = content.children.namedItem('rectbound') || content.getElementsByTagNameNS('*', 'rectbound')
  if (boundingBox instanceof Element) {
    const result = createExtent(boundingBox)
    return result ? [result] : []
  } else if (boundingBox instanceof HTMLCollection) {
    const containerBBox = (boundingBox as HTMLCollectionOf<Element>)
    const result = []
    for (let i = 0; i < containerBBox.length; i++) {
      if (containerBBox.item(i)) {
        const elementBBox = createExtent(containerBBox.item(i) as Element)
        if (elementBBox) {
          result.push(elementBBox)
        }
      }
    }
    if (needRemove) {
      removeHTMLElement(content, boundingBox)
    }

    return result
  }

  return []
}

/**
   * Convert {@see Extent} to HTMLElement
   * @param boundBox {@see Extent}
   * @returns html element
   */
export function toHTMLElement (boundBox: Extent): Element {
  const boundBoxElement = document.createElementNS(null, 'rectbound')
  const bottomLeft = getBottomLeft(boundBox)
  boundBoxElement.setAttribute('x', bottomLeft[0].toString())
  boundBoxElement.setAttribute('y', bottomLeft[1].toString())
  boundBoxElement.setAttribute('width', getWidth(boundBox).toString())
  boundBoxElement.setAttribute('height', getHeight(boundBox).toString())
  return boundBoxElement
}

/**
   * Replace current bounding box node of element
   * @param element HTMLElement
   * @param boundBox new bounding box
   */
export function replaceHTMLElement (element: Element, boundBox: Extent): void {
  const boundBoxElement = element.getElementsByTagName('rectbound').item(0)
  if (boundBoxElement) {
    const bottomLeft = getBottomLeft(boundBox)
    boundBoxElement.setAttribute('x', bottomLeft[0].toString())
    boundBoxElement.setAttribute('y', bottomLeft[1].toString())
    boundBoxElement.setAttribute('width', getWidth(boundBox).toString())
    boundBoxElement.setAttribute('height', getHeight(boundBox).toString())
  }
}

export function fromCenterPointAndMasure (center: number[], width: number, height: number): Extent {
  return [
    center[0] - width / 2,
    center[1] - height / 2,
    center[0] + width / 2,
    center[1] + height / 2
  ]
}

export function createExtent (element :Element) : Extent | null {
  const x = element.getAttribute('x')
  const y = element.getAttribute('y')
  const width = element.getAttribute('width')
  const height = element.getAttribute('height')
  if (x && y && width && height) {
    return [
      parseFloat(x),
      parseFloat(y),
      parseFloat(x) + parseFloat(width),
      parseFloat(y) + parseFloat(height)
    ]
  }

  return null
}

export function removeHTMLElement (root: Element, elementToRemove: Element | HTMLCollection) : void {
  if (elementToRemove instanceof Element) {
    elementToRemove.remove()
  } else {
    const size = elementToRemove.length
    for (let i = 0; i < size; i++) {
      elementToRemove.item(0)?.remove()
    }
  }
}

export function getExtent (features: Feature<Geometry>[]): Extent {
  // Only fit if features exist
  if (features.length === 0) {
    return createEmpty()
  }

  // Determine an extent containing all features
  let extent = createEmpty()
  features.forEach(feature => {
    const featureExtent = feature.getGeometry()?.getExtent()
    if (featureExtent != null) {
      extent = extend(extent, featureExtent)
    }
  })
  return extent
}

export function gridExtent (extent: Extent, row: number, column: number): Extent[] {
  const gridWidth = getWidth(extent) / column
  const gridHeight = getHeight(extent) / row
  const topleft = getTopLeft(extent)
  const result: Extent[] = []
  for (let i = 0; i < row; i++) {
    for (let j = 0; j < column; j++) {
      const gridTopLeft = [topleft[0] + gridWidth * i, topleft[1] - gridHeight * j]
      const gridTopRight = [topleft[0] + gridWidth * (i + 1), topleft[1] - gridHeight * j]
      const gridBottomLeft = [topleft[0] + gridWidth * i, topleft[1] - gridHeight * (j + 1)]
      const gridBottomRight = [topleft[0] + gridWidth * (i + 1), topleft[1] - gridHeight * (j + 1)]
      result.push(boundingExtent([gridTopLeft, gridTopRight, gridBottomLeft, gridBottomRight]))
    }
  }
  return result
}

export function translateExtent (extent: Extent, tranlsate: number[]): Extent {
  const newCenter = [getCenter(extent)[0] + tranlsate[0], getCenter(extent)[1] + tranlsate[1]]
  return fromCenterPointAndMasure(newCenter, getWidth(extent), getHeight(extent))
}
