/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { ControlStationType } from '@/model/ControlStation'
import { ExternalElementControl, ExternalElementControlArt } from '@/model/ExternalElementControl'
import { Label } from '@/model/Label'
import { ISvgElement, SvgElement } from '@/model/SvgElement'
import { getHeight, getWidth } from 'ol/extent'
import AbstractDrawSVG from './AbstractDrawSVG'
import SvgDraw from './SvgDraw'

export default class SvgDrawExternalElementControl extends AbstractDrawSVG {
  SVG_DRAWAREA = 500
  LABEL_FONT_SIZE = 16

  public drawSVG<T extends object> (data: T, label?: Label): ISvgElement | null {
    const eec = data as ExternalElementControl
    const svg = this.getSvgFromCatalog(eec)
    if (svg === null) {
      console.warn('Unsupported Aussenelementansteuerungen: ' + eec.guid)
      return SvgDraw.getErrorSVG()
    }

    return this.drawExternalElementControl(svg, eec, label)
  }

  private drawExternalElementControl (EECSvg: ISvgElement, eec: ExternalElementControl, label?: Label): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA, this.SVG_DRAWAREA)
    const clone = EECSvg.content.cloneNode(true) as Element

    if (eec.elementType === ExternalElementControlArt.Relaisstellwerk) {
      this.drawControlStation(clone, eec.controlStation)
    }

    clone.setTranslate(
      (this.SVG_DRAWAREA - getWidth(EECSvg.boundingBox[0])) / 2,
      (this.SVG_DRAWAREA - getHeight(EECSvg.boundingBox[0])) / 2
    )
    clone.setAttribute('fill', `url(%23${SvgDraw.CROSS_LINE_ID})`)
    svg.appendChild(SvgDraw.crossPattern('rotate(45, 7, 14)', 7 ,7, 1.3))
    svg.appendChild(clone)

    if (label) {
      svg.appendChild(SvgDraw.drawLabelAt(
        label,
        this.SVG_DRAWAREA / 2 + getWidth(EECSvg.boundingBox[0]) + 2,
        this.SVG_DRAWAREA / 2,
        false,
        true,
        this.LABEL_FONT_SIZE
      ))
    }

    return new SvgElement('ExternalElementControl', svg, [], null, EECSvg.boundingBox)
  }

  private drawControlStation (element: Element, stationType: ControlStationType) {
    switch (stationType) {
      case ControlStationType.DefaultControl:
        this.setControlStationColor(element, 'black', 'black')
        break
      case ControlStationType.DefaultControlDispose:
        this.setControlStationColor(element, 'white', 'black')
        break
      case ControlStationType.EmergencyControl:
        this.setControlStationColor(element, 'black', 'white')
        break
      case ControlStationType.EmergencyControlDispose:
        this.setControlStationColor(element, 'white', 'white')
        break
      case ControlStationType.OnlyRemoteControl:
        this.setControlStationColor(element, 'white', undefined)
        break
      case ControlStationType.Other:
      case ControlStationType.WithoutControl:
        this.setControlStationColor(element, undefined, undefined)
        break
      default:
        break
    }
  }

  private setControlStationColor (
    element: Element,
    controlStationColor: string | undefined,
    controllerColor: string | undefined
  ) {
    const controlStationSvg = element.querySelector('#bedien_platz')
    const controllerSvg = element.querySelector('#regler')
    if (!controlStationSvg || !controllerSvg) {
      return
    }

    if (controlStationColor !== undefined) {
      controlStationSvg.setAttribute('fill', controlStationColor)
      controlStationSvg.setAttribute('stroke', 'black')
    }

    if (controllerColor !== undefined) {
      controllerSvg.setAttribute('fill', controllerColor)
      controllerSvg.setAttribute('stroke', 'black')
    }
  }

  public getFeatureType (): FeatureType {
    return FeatureType.ExternalElementControl
  }
}
