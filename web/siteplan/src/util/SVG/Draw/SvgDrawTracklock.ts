/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { Label } from '@/model/Label'
import { LeftRight } from '@/model/SiteplanModel'
import { ISvgElement, ISvgPoint, SvgElement, SvgPoint } from '@/model/SvgElement'
import TrackLock, { TrackLockLocation, TrackLockSignal } from '@/model/TrackLock'
import { TurnoutOperatingMode } from '@/model/TrackSwitchComponent'
import { translateExtent } from '@/util/ExtentExtension'
import AbstractDrawSVG from '@/util/SVG/Draw/AbstractDrawSVG'
import { Extent, getCenter, getHeight, getWidth } from 'ol/extent'
import { AnchorPoint } from '../SvgEnum'
import SvgDraw from './SvgDraw'

/**
 * Draw SVGs for Track Lock
 * @author Truong
 */
export default class SvgDrawTracklock extends AbstractDrawSVG {
  // Area to use for drawing track lock
  SVG_DRAWAREA = 500
  SVG_DRAWAREA_CENTER = this.SVG_DRAWAREA / 2
  SVG_DRAWAREA_OFFSET_Y = 20

  // Label font size
  LABEL_FONT_SIZE = 14

  // Offsets for placing the label along track lock elements
  SVG_LABEL_OFFSET_X = -30
  SVG_LABEL_OFFSET_Y = -60

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement | null {
    const tracklock = data as TrackLock
    const svg = this.getSvgFromCatalog(data)
    if (svg === null) {
      console.warn('Unsupported PZB ' + tracklock.guid)
      return SvgDraw.getErrorSVG()
    }

    const circle = tracklock.components[0].trackLockSignal
      ? this.catalogService.getTrackLockSVGCatalog().getLockCircleSVG()
      : null
    return this.drawTrackLock(svg, tracklock, circle ?? undefined)
  }

  /**
   * Prepares a Track Lock for rendering
   * @param trackLockSvgElement the Track Lock SVG
   * @param arrowLine the Arrow Line SVG
   * @param trackLock the {@link TrackLock} elemente for the TrackLock
   * @returns a {@link ISvgElement} containing the final SVG for rendering
   */
  drawTrackLock (trackLockSvgElement: ISvgElement, trackLock: TrackLock, circle?: ISvgElement) {
    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA, this.SVG_DRAWAREA)
    const tracklockSvg = this.fillTrackLockMain(trackLock, trackLockSvgElement.content.cloneNode(true) as Element)
    const bboxContainer = new Array<Extent>().concat(trackLockSvgElement.boundingBox)
    const centerPointOfSvg = getCenter(trackLockSvgElement.boundingBox[0])
    if (circle) {
      const lockCircle = []
      switch (trackLock.components[0].trackLockSignal) {
        case TrackLockSignal.oneside:
          const anchorPoint = trackLockSvgElement.anchor.find(ele =>
            ele.id === (
              trackLock.preferredLocation === TrackLockLocation.besideTrack
                ? AnchorPoint.bottom
                : AnchorPoint.top
            ))
          lockCircle.push(this.connectCircle(trackLock.preferredLocation, circle, bboxContainer, anchorPoint))
          break
        case TrackLockSignal.bothside:
          trackLockSvgElement.anchor.forEach(anchor => {
            lockCircle.push(this.connectCircle(trackLock.preferredLocation, circle, bboxContainer, anchor))
          })
          break
        default:
          break
      }

      lockCircle.filter(ele => ele !== null).forEach(ele => tracklockSvg.appendChild(ele as Element))
    }

    const trackLockTransform = this.getTrackLockTransform(trackLock, trackLockSvgElement, bboxContainer)
    tracklockSvg.setAttribute('transform', trackLockTransform)
    svg.appendChild(tracklockSvg)
    const isOntrack = trackLock.preferredLocation === TrackLockLocation.onTrack
    const offsetLabelX = getWidth(bboxContainer[0]) / 2 + 10
    svg.appendChild(SvgDraw.drawLabelAt(
      trackLock.label,
      this.SVG_DRAWAREA_CENTER + (isOntrack ? -offsetLabelX : offsetLabelX),
      this.SVG_DRAWAREA_CENTER - getHeight(bboxContainer[0]) / 2 - 15,
      false,
      true,
      this.LABEL_FONT_SIZE,
      isOntrack ? -90 : 0
    ))

    return new SvgElement(
      'TrackLock',
      svg,
      [],
      new SvgPoint('TrackLockCenterPoint', centerPointOfSvg[0], centerPointOfSvg[1]),
      bboxContainer
    )
  }

  private fillTrackLockMain (tracklock: TrackLock, svg: Element) {
    if (tracklock.operatingMode == TurnoutOperatingMode.MechanicalRemote ||
       tracklock.operatingMode === TurnoutOperatingMode.ElectricRemote) {
      return svg
    }

    const tracklockMain = svg.querySelector('#main')
    if (!tracklockMain) {
      return svg
    }

    const diagonalPattern = SvgDraw.diagonalPattern(45)
    tracklockMain.setAttribute('fill', `url(%23${SvgDraw.DIAGONAL_LINE_ID})`)
    svg.appendChild(diagonalPattern)
    return svg
  }

  private getTrackLockTransform (trackLock: TrackLock, trackLockSvgElement: ISvgElement, bboxContainer: Extent[]) {
    const offsetX = SvgDrawTracklock.getOffsetX(trackLock, bboxContainer)
    let translateX = this.SVG_DRAWAREA_CENTER - offsetX
    let translateY = this.SVG_DRAWAREA_CENTER - getHeight(trackLockSvgElement.boundingBox[0]) / 2
    const scaleFactor = this.scaleFactorbyEjectionDirection(trackLock)
    let scale = [1, scaleFactor]

    if (trackLock.preferredLocation === TrackLockLocation.besideTrack) {
      scale = [scaleFactor, -1]
      if (scale[0] < 0) {
        translateX += getWidth(trackLockSvgElement.boundingBox[0])
      }
    }

    if (scale[1] < 0) {
      translateY += getHeight(trackLockSvgElement.boundingBox[0])
    }

    return `translate(${translateX}, ${translateY}) scale(${scale[0]}, ${scale[1]})`
  }

  private scaleFactorbyEjectionDirection (trackLock: TrackLock) {
    switch (trackLock.components[0].ejectionDirection) {
      case LeftRight.RIGHT:
        return -1
      case LeftRight.LEFT:
      default:
        return 1
    }
  }

  private connectCircle (
    preferredLocation: TrackLockLocation,
    circle: ISvgElement,
    bboxContainer: Extent[],
    anchorPoint?: ISvgPoint
  ) {
    const circleAnchorPoint = circle.anchor[0]
    if (!circleAnchorPoint || !anchorPoint) {
      return null
    }

    const circleClone = circle.content.cloneNode(true) as Element
    let transform = ''
    switch (preferredLocation) {
      case TrackLockLocation.onTrack:
        transform = this.transformCircle(circle, anchorPoint, [1, 1], [-1, -1], bboxContainer)
        break
      case TrackLockLocation.besideTrack:
        transform = this.transformCircle(circle, anchorPoint, [-1, 1], [1, -1], bboxContainer)
        break
      default:
        break
    }

    circleClone.setAttribute('transform', transform)
    return circleClone
  }

  private transformCircle (
    circle: ISvgElement,
    anchorPoint: ISvgPoint,
    topCircleScale: number[],
    bottomCircleScale: number[],
    bboxContainer: Extent[]
  ) {
    const circleAnchorPoint = circle.anchor[0]
    if (!circleAnchorPoint || !anchorPoint) {
      return ''
    }

    const circleClone = circle.content.cloneNode(true) as Element
    let translate = [0, 0]
    let scale = [1, 1]
    switch (anchorPoint.id) {
      case AnchorPoint.bottom:
        circleClone.setAttribute('id', AnchorPoint.bottom)
        translate = [anchorPoint.x - bottomCircleScale[0] * circleAnchorPoint.x, anchorPoint.y + circleAnchorPoint.y]
        scale = bottomCircleScale
        break
      case AnchorPoint.top:
        translate = [anchorPoint.x - topCircleScale[0] * circleAnchorPoint.x, anchorPoint.y - circleAnchorPoint.y]
        scale = topCircleScale
        break
      default:
        break
    }

    for (const bbox of circle.boundingBox) {
      bboxContainer.push(translateExtent(bbox, translate))
    }
    return `translate(${translate[0]}, ${translate[1]}) scale(${scale[0]} ${scale[1]})`
  }

  public static getOffsetX (trackLock: TrackLock, bboxContainer: Extent[]): number {
    if (trackLock.preferredLocation === TrackLockLocation.besideTrack) {
      if (trackLock.components[0].trackLockSignal === TrackLockSignal.bothside) {
        const offset = getWidth(bboxContainer[1])
        return -getWidth(bboxContainer[0]) / 4 + offset / 2
      }
    }

    return getWidth(bboxContainer[0]) / 2
  }

  public getFeatureType (): FeatureType {
    return FeatureType.TrackLock
  }
}
