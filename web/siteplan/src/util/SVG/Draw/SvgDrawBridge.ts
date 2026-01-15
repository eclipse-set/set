/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Label } from '@/model/Label'
import { Position } from '@/model/Position'
import { MountDirection, Signal, SignalPart, SignalRole } from '@/model/Signal'
import { SignalMount, SignalMountType } from '@/model/SignalMount'
import { ISvgElement, MAX_BRIDGE_DIRECTION_OFFSET, SvgBridgeSignal, SvgElement, SvgPoint } from '@/model/SvgElement'
import '@/util/ElementExtensions'
import { fromCenterPointAndMasure, fromHTMLElement, toHTMLElement } from '@/util/ExtentExtension'
import { distance } from '@/util/Math'
import { getCenter, getHeight, getWidth, isEmpty } from 'ol/extent'
import { AnchorPoint } from '../SvgEnum'
import SvgDraw from './SvgDraw'
import SvgDrawSignal from './SvgDrawSignal'
import SvgDrawSingleSignal from './SvgDrawSingleSignal'

/**
 * Draws a signal bridge or a signal boom
 * @author Stuecker, Voigt
 */

export interface SignalBridgePart {
  guid: string
  signal: SvgBridgeSignal
}

export default class SvgDrawBridge extends SvgDrawSignal {
  // Extra width for signal bridges/signal booms after the final signal
  static SVG_BRIDGE_EXTRA_END_WIDTH = 10
  // Extra width for signal bridges/signal booms before the mounting point
  static SVG_BRIDGE_EXTRA_START_WIDTH = 5

  /**
     * Create a Svg for a feature
     * @param data feature data
     * @param label {@link Label}
     */
  public drawSVG<T extends object> (data: T, label?: Label): ISvgElement | null {
    const signalMount = data as SignalMount
    if (!this.validateSignal(signalMount)) return null

    if (!this.isMultiSignal(signalMount)) return null

    const bridgeParts: SignalBridgePart[] =
      signalMount.attachedSignals
        .map(signal => this.drawAttachedSignal(signal, signalMount.position))
        .filter(e => e !== null)

    return SvgDrawBridge.drawParts(signalMount.guid, bridgeParts, signalMount.mountType)
  }

  private drawAttachedSignal (signal: Signal, signalMountPosition: Position): SignalBridgePart | null {
    const offset = distance(
      [signalMountPosition.x, signalMountPosition.y],
      [signal.mountPosition.x, signal.mountPosition.y]
    )

    const direction = Math.abs(
      signalMountPosition.rotation - signal.mountPosition.rotation
    ) < MAX_BRIDGE_DIRECTION_OFFSET
      ? MountDirection.Up
      : MountDirection.Down

    if (signal.role === SignalRole.None) {
      return {
        guid: signal.guid,
        signal: SvgBridgeSignal.fromSvgElement(SvgDraw.getErrorSVG(), offset, direction, signal.label ?? null)
      }
    }

    for (const catalog of this.catalogService.getSignalSVGCatalog()) {
      const screen = catalog.getSignalScreen(signal)
      if (screen !== null) {
        return {
          guid: signal.guid,
          signal: SvgBridgeSignal.fromSvgElement(screen, offset, direction, signal.label ?? null)
        }
      }
    }
    return null
  }

  private getMultiSignalScreen (signalMount: SignalMount): ISvgElement | null {
    const bridgeParts: SignalBridgePart[] =
      signalMount.attachedSignals
        .map(signal => this.drawAttachedSignal(signal, signalMount.position))
        .filter(e => e !== null)

    return SvgDrawBridge.drawParts(signalMount.guid, bridgeParts, signalMount.mountType)
  }

  /**
   * Connect Signals to Signal -bruecke, -ausleger
   * @param guid GUID of the signalpart
   * @param parts the Signal
   * @param art bruecke or ausleger
   * @returns svg
   */
  public static drawParts (guid: string, parts: SignalBridgePart[], signalMountType: SignalMountType): ISvgElement {
    // Calculate the final bridge/boom width by finding the screens
    // with the largest absolute offset from the mount
    const signalOffsets = parts.map(ele => ele.signal.mountOffset * SvgDraw.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR)
    const maxOffset = Math.max(...signalOffsets, 0)
    const minOffset = Math.min(...signalOffsets, 0)

    const width = SvgDrawBridge.SVG_BRIDGE_EXTRA_END_WIDTH + maxOffset - minOffset
    const svgWidth = width + SvgDrawSingleSignal.SVG_DRAWAREA

    const svg = SvgDraw.createSvgWithHead(svgWidth, SvgDrawSingleSignal.SVG_DRAWAREA)
    const bridge = this.drawBridge(signalMountType, width)
    bridge.setAttribute('class', SignalPart.Mast)
    bridge.setAttribute('id', `${SignalPart.Mast}_${guid}`)
    const g = document.createElement('g')
    g.appendChild(bridge)
    g.setTranslate(svgWidth / 2 - 5, SvgDrawSingleSignal.SVG_DRAWAREA_CENTER)

    for (const [, part] of parts.entries()) {
      SvgDrawBridge.addSignal(g, part)
    }

    svg.appendChild(g)
    const bridgeBBox = fromHTMLElement(g, true)
    return new SvgElement(
      'SignalBridge',
      svg,
      [],
      new SvgPoint('nulpunkt', SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH, 0),
      bridgeBBox
    )
  }

  private static drawBridge (signalMountType: SignalMountType, width: number) {
    const kombination = document.createElement('g')
    const rect = document.createElement('rect')
    rect.setAttribute('width', width.toString())
    rect.setAttribute('height', '20')
    rect.setAttribute('y', '10')
    rect.setAttribute('fill', 'white')
    kombination.appendChild(rect)

    const sw = SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH
    const line1 = document.createElement('path')
    line1.setAttribute('stroke-width', '5')
    line1.setAttribute('d', `M${sw},10 L${sw},0`)
    kombination.appendChild(line1)

    const line2 = document.createElement('path')
    line2.setAttribute('stroke-width', '5')
    line2.setAttribute('d', `M${sw},30 L${sw},40`)
    kombination.appendChild(line2)

    if (signalMountType === SignalMountType.Signalbruecke) {
      const x = width - sw
      const line3 = document.createElement('path')
      line3.setAttribute('stroke-width', '5')
      line3.setAttribute('d', `M${x},10 L${x},0`)
      kombination.appendChild(line3)

      const line4 = document.createElement('path')
      line4.setAttribute('stroke-width', '5')
      line4.setAttribute('d', `M${x},30 L${x},40`)
      kombination.appendChild(line4)
    }

    const bbox = fromCenterPointAndMasure([width / 2, 20], width, 40)
    kombination.appendChild(toHTMLElement(bbox))

    return kombination
  }

  private static addSignal (bridge: Element, part: SignalBridgePart) {
    const signalOffset = part.signal.mountOffset * SvgDraw.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR
    const g = document.createElement('g')
    g.setAttribute('class', SignalPart.Schirm)
    g.setAttribute('id', `${SignalPart.Schirm}_${part.guid}`)
    // Draw signal mount
    const signalMount = this.drawSignalMount(signalOffset, part.signal.mountDirection)
    g.appendChild(signalMount)
    // Draw signal screen
    const signalScreen = this.drawSignalScreen(signalOffset, part.signal)
    // Add signal label (if present)
    const signalAnchorPointBot = part.signal.anchor.find(ele => ele.id === AnchorPoint.bottom)
    if (part.signal.label != null && signalAnchorPointBot) {
      signalScreen.appendChild(
        SvgDraw.drawLabelAt(
          part.signal.label,
          10,
          signalAnchorPointBot.y,
          false,
          false,
          SvgDrawSingleSignal.LABEL_FONT_SIZE
        )
      )
    }

    g.appendChild(signalScreen)
    bridge.appendChild(g)
  }

  private static drawSignalMount (signalOffset: number, mountDirection: MountDirection): Element {
    const mount = document.createElement('path')
    mount.setAttribute('d', 'M0,10 L0,-5')
    if (mountDirection === MountDirection.Down) {
      mount.setTranslate(signalOffset + SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH, 0)
    } else {
      mount.setTranslate(signalOffset + SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH, 35)
    }

    const mountBBox = fromCenterPointAndMasure([1.25, 2.5], 2.5, 15)
    mount.appendChild(toHTMLElement(mountBBox))
    return mount
  }

  private static drawSignalScreen (signalOffset: number, signal: SvgBridgeSignal): Element {
    const svg = signal.content.cloneNode(true) as Element
    const signalAnchorPointTop = signal.anchor.find(ele => ele.id === AnchorPoint.top)
    const signalAnchorPointBot = signal.anchor.find(ele => ele.id === AnchorPoint.bottom)
    if (signalAnchorPointTop === undefined || signalAnchorPointBot === undefined) {
      throw new Error('Invalid signal attached to bridge')
    }

    if (signal.mountDirection === MountDirection.Down) {
      const x = signalOffset + signalAnchorPointTop.x + SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH
      const y = signalAnchorPointTop.y - 5
      // Flip the signal over, to preserve drawing orientation
      svg.setAttribute('transform', `translate(${x}, ${y}) rotate(180)`)
      signal.boundingBox.forEach(bbox => {
        if (!isEmpty(bbox)) {
          const bboxCenter = getCenter(bbox)
          const bboxWidth = getWidth(bbox)
          const bboxHeight = getHeight(bbox)
          const bboxTranslateCenter = [bboxCenter[0] + x, bboxCenter[1] + y]
          const rotatePoint = [bboxTranslateCenter[0] - bboxWidth / 2, bboxTranslateCenter[1] - bboxHeight / 2]
          const bboxRotateCenter = [rotatePoint[0] - bboxWidth / 2, rotatePoint[1] - bboxHeight / 2]
          const newBbox = fromCenterPointAndMasure(bboxRotateCenter, bboxWidth, bboxHeight)
          svg.appendChild(toHTMLElement(newBbox))
        }
      })
    } else {
      const x = signalOffset - signalAnchorPointBot.x + SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH
      const y = signalAnchorPointBot.y - 5
      svg.setAttribute('transform', `translate(${x}, ${y})`)
      signal.boundingBox.forEach(bbox => {
        if (!isEmpty(bbox)) {
          const bboxCenter = getCenter(bbox)
          const bboxTranslateCenter = [bboxCenter[0] + x, bboxCenter[1] + y]
          const newBbox = fromCenterPointAndMasure(bboxTranslateCenter, getWidth(bbox), getHeight(bbox))
          svg.appendChild(toHTMLElement(newBbox))
        }
      })
    }

    return svg
  }
}
