/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Extent } from 'ol/extent'
import { Label } from './Label'
import { MountDirection } from './Signal'

export interface ISvgPoint {
  id: string
  x: number
  y: number
}

export class SvgPoint implements ISvgPoint {
  id: string
  x: number
  y: number
  constructor (id: string, x: string | number, y: string | number) {
    this.id = id
    this.x = typeof x === 'number' ? x : Number.parseFloat(x)
    this.y = typeof y === 'number' ? y : Number.parseFloat(y)
  }
}

export interface ISvgElement {
    id: string
    anchor: ISvgPoint[]
    nullpunkt: ISvgPoint | null
    content: Element
    boundingBox: Extent[]
}

export class SvgElement implements ISvgElement {
  id: string
  anchor: ISvgPoint[]
  nullpunkt: ISvgPoint | null
  boundingBox: Extent[]
  content: Element
  constructor (
    id: string,
    element: Element,
    anchor: ISvgPoint[],
    nullpunk: ISvgPoint | null,
    boundingBox: Extent[]
  ) {
    this.id = id
    this.content = element
    this.anchor = anchor
    this.nullpunkt = nullpunk
    this.boundingBox = boundingBox
  }
}

export class ZusatzSignal extends SvgElement {
  position: string
  constructor (
    id: string,
    element: Element,
    anchor: ISvgPoint[],
    nullpunk: ISvgPoint | null,
    position: string,
    boundingBox: Extent[]
  ) {
    super(id, element, anchor, nullpunk, boundingBox)
    this.position = position
  }

  public isTopPosition (): boolean {
    if (this.position === null || this.position === '') {
      return true
    }

    return this.position === 'top'
  }
}
export const MAX_BRIDGE_DIRECTION_OFFSET = 15.0
export class SvgBridgeSignal extends SvgElement {
  /** right = positive, left = negative */
  mountSignedOffset: number
  mountDirection: MountDirection
  label: Label | null

  constructor (
    id: string,
    element: Element,
    anchor: ISvgPoint[],
    nullpunkt: ISvgPoint | null,
    mountSignedOffset: number,
    mountDirection: MountDirection,
    signalLabel: Label | null,
    boundingBox: Extent[]
  ) {
    super(id, element, anchor, nullpunkt, boundingBox)
    this.mountSignedOffset = mountSignedOffset
    this.label = signalLabel
    this.mountDirection = mountDirection
  }

  static fromSvgElement (
    element: ISvgElement,
    signedOffset: number,
    mountDirection: MountDirection,
    signalLabel: Label | null
  ): SvgBridgeSignal {
    return new SvgBridgeSignal(
      element.id,
      element.content,
      element.anchor,
      element.nullpunkt,
      signedOffset,
      mountDirection,
      signalLabel,
      element.boundingBox
    )
  }
}
