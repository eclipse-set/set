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
import { getCenter, getHeight, getWidth, isEmpty } from 'ol/extent'
import { AnchorPoint } from '../SvgEnum'
import SvgDraw from './SvgDraw'
import SvgDrawSignal from './SvgDrawSignal'
import SvgDrawSingleSignal from './SvgDrawSingleSignal'

const EPS = 0.001

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

  static ATTACHED_SIGNAL_MOUNT_LENGTH = 15
  static BRIDGE_THICKNESS = 20
  static HALF_BRIDGE_THICKNESS = SvgDrawBridge.BRIDGE_THICKNESS / 2

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

  private drawAttachedSignal (signal: Signal, bridgeMountPosition: Position): SignalBridgePart | null {
    const signalAttachementDirection = Math.abs(
      bridgeMountPosition.rotation - signal.mountPosition.rotation
    ) % 360 < MAX_BRIDGE_DIRECTION_OFFSET
      ? MountDirection.Up
      : MountDirection.Down

    const right = {
      x: Math.sin(bridgeMountPosition.rotation),
      y: Math.cos(bridgeMountPosition.rotation)
    }
    const delta = {
      x: signal.mountPosition.x - bridgeMountPosition.x,
      y: signal.mountPosition.y - bridgeMountPosition.y
    }

    const signedLateralOffset =
      delta.x * right.x +
      delta.y * right.y

    let svgElement
    if (signal.role !== SignalRole.None) {
      for (const catalog of this.catalogService.getSignalSVGCatalog()) {
        const screen = catalog.getSignalScreen(signal)
        if (screen !== null) {
          svgElement = screen
          break // return the first screen != null
        }
      }
    }

    return {
      guid: signal.guid,
      signal: SvgBridgeSignal.fromSvgElement(
        svgElement ?? SvgDraw.getErrorSVG(),
        signedLateralOffset,
        signalAttachementDirection,
        signal.label ?? null
      )
    }
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
    const signalOffsets = parts.map(ele =>
      ele.signal.mountSignedOffset * SvgDraw.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR)
    const maxOffset = Math.max(...signalOffsets, 0)
    const minOffset = Math.min(...signalOffsets, 0)

    const width = maxOffset - minOffset

    const svgWidth = width + SvgDrawSingleSignal.SVG_DRAWAREA

    const svg = SvgDraw.createSvgWithHead(svgWidth, SvgDrawSingleSignal.SVG_DRAWAREA)
    const bridge = this.drawBridge(signalMountType, minOffset, maxOffset)
    bridge.setAttribute('class', SignalPart.Mast)
    bridge.setAttribute('id', `${SignalPart.Mast}_${guid}`)
    const g = document.createElement('g')
    g.appendChild(bridge)
    g.setTranslate(svgWidth / 2, SvgDrawSingleSignal.SVG_DRAWAREA_CENTER)

    for (const [, part] of parts.entries()) {
      SvgDrawBridge.addSignal(g, part)
    }

    svg.appendChild(g)
    const bridgeBBox = fromHTMLElement(g, true)
    return new SvgElement(
      'SignalBridge',
      svg,
      [],
      new SvgPoint('nulpunkt', 0, 0),
      bridgeBBox
    )
  }

  /**
   * draws the line symbolizing the point where the Arm/Bruecke is standing on the ground.
   * the ends point towards positive & negative y
   */
  private static drawBridgeFoundation (x: number, y: number) {
    const line1 = document.createElement('path')
    line1.setAttribute('stroke-width', '5')
    line1.setAttribute('d', `M${x},${y - 10} L${x},${y - 20}`)

    const line2 = document.createElement('path')
    line2.setAttribute('stroke-width', '5')
    line2.setAttribute('d', `M${x},${y + 10} L${x},${y + 20}`)

    const group = document.createElement('g')
    group.append(line1)
    group.append(line2)
    return group
  }

  /**
   * draws box for arm / bruecke.
   * @param signalMountType SignalMountType => if bruecke, draw two mounts, otherwise one
   * @param extentLeft includes START/END-WIDTH
   * @param extentRight includes START/END-WIDTH
   * @returns SVG
   */
  private static drawBridge (signalMountType: SignalMountType, extentLeft:number, extentRight:number) {
    let leftOverhang = 0
    let rightOverhang = 0

    if (signalMountType === SignalMountType.Signalbruecke) {
      leftOverhang = SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH
      rightOverhang = SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH
    } else {
      if (extentLeft < -EPS) { // has signals on the left of the foundation
        leftOverhang = SvgDrawBridge.SVG_BRIDGE_EXTRA_END_WIDTH
      } else {
        leftOverhang = SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH
      }

      if (extentRight > EPS) { // has signals on the right of the foundation
        rightOverhang = SvgDrawBridge.SVG_BRIDGE_EXTRA_END_WIDTH
      } else {
        rightOverhang = SvgDrawBridge.SVG_BRIDGE_EXTRA_START_WIDTH
      }
    }

    const width = extentRight - extentLeft + rightOverhang + leftOverhang
    const kombination = document.createElement('g')
    const rect = document.createElement('rect')
    rect.setAttribute('width', width.toString())
    rect.setAttribute('height', '20')
    rect.setAttribute('y', '-10')
    rect.setAttribute('x', (-extentRight - rightOverhang).toString())
    rect.setAttribute('fill', 'white')
    kombination.appendChild(rect)

    kombination.appendChild(this.drawBridgeFoundation(0,0))

    if (signalMountType === SignalMountType.Signalbruecke) {
      const x = width - this.SVG_BRIDGE_EXTRA_START_WIDTH
      kombination.appendChild(this.drawBridgeFoundation(x,0))
    }

    const bbox = fromCenterPointAndMasure([width / 2, 20], width, 40)
    kombination.appendChild(toHTMLElement(bbox))

    return kombination
  }

  private static addSignal (bridge: Element, part: SignalBridgePart) {
    const g = document.createElement('g')
    g.setAttribute('class', SignalPart.Schirm)
    g.setAttribute('id', `${SignalPart.Schirm}_${part.guid}`)

    // Draw signal mount
    const signalMount = this.drawSignalMount(part.signal) // TODO handle abs signedOffset
    g.appendChild(signalMount)

    // Draw signal screen
    const signalScreen = this.drawSignalScreen(part.signal)

    // Add signal label (if present)
    const signalAnchorPointBot = part.signal.anchor.find(ele => ele.id === AnchorPoint.bottom)
    if (part.signal.label != null && signalAnchorPointBot) {
      signalScreen.appendChild(
        SvgDraw.drawLabelAt(
          part.signal.label,
          15,
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

  private static drawSignalMount (signal: SvgBridgeSignal): Element {
    const signalOffset = signal.mountSignedOffset * SvgDraw.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR
    const x = -signalOffset // TODO why so negative?

    const mountDirectionSign = signal.mountDirection === MountDirection.Up ? 1 : -1

    const y = SvgDrawBridge.HALF_BRIDGE_THICKNESS * mountDirectionSign
    const y_end = y + SvgDrawBridge.ATTACHED_SIGNAL_MOUNT_LENGTH * mountDirectionSign

    const mount = document.createElement('path')
    mount.setAttribute('d', `M${x},${y} L${x},${y_end}`)

    const mountBBox = fromCenterPointAndMasure([1.25, 2.5], 2.5, 15)
    mount.appendChild(toHTMLElement(mountBBox))
    return mount
  }

  private static drawSignalScreen (signal: SvgBridgeSignal): Element {
    const signalOffset = -signal.mountSignedOffset * SvgDraw.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR

    const svg = signal.content.cloneNode(true) as Element
    const signalAnchorPointTop = signal.anchor.find(ele => ele.id === AnchorPoint.top)
    const signalAnchorPointBot = signal.anchor.find(ele => ele.id === AnchorPoint.bottom)
    if (signalAnchorPointTop === undefined || signalAnchorPointBot === undefined) {
      throw new Error('Invalid signal attached to bridge')
    }

    const anchorStart = (signal.mountDirection === MountDirection.Down) ?
      signalAnchorPointTop : signalAnchorPointBot

    const MAGIC_NUMBER = -50
    const y = anchorStart.y + MAGIC_NUMBER + this.HALF_BRIDGE_THICKNESS + this.ATTACHED_SIGNAL_MOUNT_LENGTH

    if (signal.mountDirection === MountDirection.Down) {
      const x = signalOffset + anchorStart.x
      // Flip the signal over, to preserve drawing orientation
      svg.setAttribute('transform', `translate(${x}, ${y}) rotate(180)`)// rotate(180)`)
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
      const x = signalOffset - anchorStart.x
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
