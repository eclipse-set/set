/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Label } from '@/model/Label'
import { SignalPart } from '@/model/Signal'
import { ISvgElement, ISvgPoint, SvgElement, SvgPoint, ZusatzSignal } from '@/model/SvgElement'
import '@/util/ElementExtensions'
import { translateExtent } from '@/util/ExtentExtension'
import { Extent, isEmpty } from 'ol/extent'
import { AnchorPoint } from '../SvgEnum'
import SvgDraw from './SvgDraw'

/**
 * Draw Signal SVG
 * @author Truong
 */
export default class SvgDrawSingleSignal {
  // Area to use for drawing signals
  static SVG_DRAWAREA = 500
  static SVG_DRAWAREA_CENTER = SvgDrawSingleSignal.SVG_DRAWAREA / 2

  // Offsets for placing the label below signals
  static SVG_LABEL_OFFSET_X = 0
  static SVG_LABEL_OFFSET_Y = 5
  // Label font size
  static LABEL_FONT_SIZE = 25
  /**
   * Draw svg element from one SVG-Group
   * @param element svg element in group
   * @returns svg
   */
  public static drawAllElementInGroup (elements: ISvgElement[]): HTMLElement {
    const svg = SvgDraw.createSvgWithHead(elements.length * 80 + 20, 300)
    let i = 0
    for (const item of elements) {
      const clone = item.content.cloneNode(true) as Element
      clone.setTranslate(i * 80 + 20, 20)
      svg.appendChild(clone)
      i++
    }
    return svg
  }

  /**
   * Draw all Signal from Signal Schrim group und Signal Mast group
   * @param schirm schirm group
   * @param mast mast group
   * @return svg
   */
  public static drawAllSignalInGroup (schirmList: ISvgElement[], mastList: ISvgElement[]): HTMLElement {
    const svg = SvgDraw.createSvgWithHead(mastList.length * 100 + 50, schirmList.length * 140 + 100)
    let schirmIndex = 0
    for (const schirm of schirmList) {
      let mastIndex = 0
      if (schirm === null || !schirm) {
        continue
      }

      const group = document.createElement('g')
      group.setTranslate(50, schirmIndex * 140 + 100)

      for (const mast of mastList) {
        if (mast === null || !mast) {
          continue
        }

        const signal = this.svgConnector(schirm, mast)
        signal.content.setTranslate(mastIndex * 100, 0)
        group.appendChild(signal.content)
        mastIndex++
      }
      svg.appendChild(group)
      schirmIndex++
    }
    return svg
  }

  /**
   * Draw a Signal
   * @param schirm signal schirm
   * @param mast signal mast
   * @param zusatzSignale list of additional signals
   * @returns signal
   */
  public static drawSignal (
    schirm: ISvgElement | null,
    mast: ISvgElement | null,
    zusatzSignale: ZusatzSignal[],
    label: Label | null = null
  ): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(SvgDrawSingleSignal.SVG_DRAWAREA, SvgDrawSingleSignal.SVG_DRAWAREA)
    // add the signal content if schirm and mast are present
    if (schirm && mast) {
      const signal = this.svgConnector(schirm, mast, zusatzSignale)
      this.addSignalContent(signal, svg)

      // Add label if present
      if (label != null) {
        this.addSignalLabel(signal, svg, label)
      }

      return new SvgElement('Signal', svg, signal.anchor, signal.nullpunkt, signal.boundingBox)
    }

    return new SvgElement('Empty_Signal', svg, [], null, [])
  }

  /**
   * Signal build from schirm and mast
   * @param schirm Schirm
   * @param mast Mast
   * @returns svg element
   */
  public static svgConnector (schirm: ISvgElement, mast: ISvgElement, zusatzSignale?: ZusatzSignal[]): ISvgElement {
    const group = document.createElement('g')
    const anchorPointofMast = mast.anchor[0]
    const bboxContainer: Extent[] = new Array<Extent>().concat(schirm.boundingBox)
    const schirmClone = schirm.content.cloneNode(true) as HTMLElement
    schirmClone.setAttribute('class', SignalPart.Schirm)
    schirmClone.setAttribute('id', SignalPart.Schirm)
    group.appendChild(schirmClone)
    let anchorPointToMast = schirm.anchor.find(ele => ele.id === anchorPointofMast.id)
    if (zusatzSignale && zusatzSignale.length > 0) {
      anchorPointToMast = this.connectZusatzSignal(
        group,
        bboxContainer,
        zusatzSignale,
        schirm.anchor,
        anchorPointofMast.id
      )
    }

    const signalNullPunkt = this.connectMast(group, mast, anchorPointToMast, bboxContainer)
    let signalName = ''
    if (anchorPointofMast.id === AnchorPoint.top) {
      signalName = 'Signal_With_TopMast'
    } else {
      signalName = 'Signal_With_BottomMast'
    }

    return new SvgElement(signalName, group, [], signalNullPunkt, bboxContainer)
  }

  /**
   * Connect ZusatzSignal to Haupt- und Vorsignal
   * @param signal the Signal
   * @param zusatzSignale Zusatzsignale
   * @param signalAnchorPoints anchor point of the Signal
   * @returns anchor point to Mast
   */
  private static connectZusatzSignal (
    signal: Element,
    bboxContainer: Extent[],
    zusatzSignale: ZusatzSignal[],
    signalAnchorPoints: ISvgPoint[],
    mastAnchorTyp: string
  ): ISvgPoint | undefined {
    let signalAnchorPointTop = signalAnchorPoints.find(ele => ele.id === AnchorPoint.top)
    let signalAnchorPointBottom = signalAnchorPoints.find(ele => ele.id === AnchorPoint.bottom)
    for (const zusatz of zusatzSignale) {
      const anchorPosition = zusatz.isTopPosition() ? AnchorPoint.top : AnchorPoint.bottom
      const anchorToSignal = zusatz.anchor.find(ele => ele.id !== anchorPosition)
      const anchorToNextZusatz = zusatz.anchor.find(ele => ele.id === anchorPosition)
      const signalAnchor = zusatz.isTopPosition() ? signalAnchorPointTop : signalAnchorPointBottom
      if (anchorToSignal && anchorToNextZusatz && signalAnchor) {
        const translatePoint: ISvgPoint = new SvgPoint(
          'translatePoint',
          signalAnchor.x - anchorToSignal.x,
          signalAnchor.y - (anchorToSignal.y + (zusatz.isTopPosition() ? 5 : -5))
        )
        const clone = zusatz.content.cloneNode(true) as Element
        // IMPROVE: Here should guid of additive signal instead of index
        clone.setAttribute('class', SignalPart.Additive)
        clone.setAttribute('id', zusatz.id)
        clone.setTranslate(translatePoint.x, translatePoint.y)
        this.expandSignalBoundBox(zusatz, translatePoint, bboxContainer)
        if (zusatz.isTopPosition()) {
          signalAnchorPointTop = new SvgPoint(
            'nextAnchorTop',
            anchorToNextZusatz.x + translatePoint.x,
            anchorToNextZusatz.y + translatePoint.y
          )
        } else {
          signalAnchorPointBottom = new SvgPoint(
            'nextAnchortBottom',
            anchorToNextZusatz.x + translatePoint.x,
            anchorToNextZusatz.y + translatePoint.y
          )
        }

        signal.appendChild(clone)
      }
    }
    switch (mastAnchorTyp) {
      case AnchorPoint.bottom:
        return signalAnchorPointBottom
      case AnchorPoint.top:
        return signalAnchorPointTop
      default:
        throw new Error('Invalid anchor point')
    }
  }

  /**
   * Connect Signal Schirm to Mast
   * @param signal the Signal
   * @param mast the Mast
   * @param anchorPointToMast anchor Point of the Signal
   */
  private static connectMast (
    signal: Element,
    mast: ISvgElement,
    anchorPointOfMast: ISvgPoint | undefined,
    bboxContainer: Extent[]
  ): ISvgPoint | null {
    const anchorPointToMast = mast.anchor[0]
    if (anchorPointOfMast && anchorPointToMast) {
      const translatePoint = new SvgPoint(
        'translatePoint',
        anchorPointOfMast.x - anchorPointToMast.x,
        anchorPointOfMast.y - anchorPointToMast.y
      )
      const mastClone = mast.content.cloneNode(true) as Element
      mastClone.setAttribute('class', SignalPart.Mast)
      mastClone.setAttribute('id', SignalPart.Mast)
      mastClone.setTranslate(translatePoint.x, translatePoint.y)
      signal.insertBefore(mastClone, signal.children[0])
      this.expandSignalBoundBox(mast, translatePoint, bboxContainer)

      if (mast.nullpunkt) {
        return new SvgPoint('SignalNullpunkt', mast.nullpunkt.x + translatePoint.x, mast.nullpunkt.y + translatePoint.y)
      }
    }

    return null
  }

  private static expandSignalBoundBox (
    svgElement: ISvgElement,
    translatePoint: ISvgPoint,
    bboxContainer: Extent[]
  ): void {
    for (const elementBoundBox of svgElement.boundingBox) {
      if (!isEmpty(elementBoundBox)) {
        bboxContainer.push(translateExtent(elementBoundBox, [translatePoint.x, translatePoint.y]))
      }
    }
  }

  private static addSignalContent (signal: ISvgElement, svg: Element) {
    // Translate the signal foot to the center point
    let centerXOffset = SvgDrawSingleSignal.SVG_DRAWAREA_CENTER
    let centerYOffset = SvgDrawSingleSignal.SVG_DRAWAREA_CENTER

    if (signal.nullpunkt !== null) {
      centerXOffset -= signal.nullpunkt.x
      centerYOffset -= signal.nullpunkt.y
    }

    signal.content.setTranslate(centerXOffset, centerYOffset)
    svg.appendChild(signal.content)
  }

  public static addSignalLabel (signal: ISvgElement, svg: Element, label: Label): void {
    const isTopMast = signal.id === 'Signal_With_TopMast'
    const labelX = SvgDrawSingleSignal.SVG_DRAWAREA_CENTER + SvgDrawSingleSignal.SVG_LABEL_OFFSET_X
    let labelY = SvgDrawSingleSignal.SVG_DRAWAREA_CENTER
    if (isTopMast) {
      labelY -= SvgDrawSingleSignal.SVG_LABEL_OFFSET_Y
    } else {
      labelY += SvgDrawSingleSignal.SVG_LABEL_OFFSET_Y
    }

    const labelsvg = SvgDraw.drawLabelAt(label, labelX, labelY, isTopMast, false, this.LABEL_FONT_SIZE)
    svg.appendChild(labelsvg)
  }
}
