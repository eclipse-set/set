/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { PZB, PZBEffectivity, PZBElement, PZBType } from '@/model/PZB'
import { ISvgElement } from '@/model/SvgElement'
import { OtherSVGCatalog } from '@/util/SVG/SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class PZBCatalog extends AbstractSVGCatalog {
  public getSVG (pzb: PZB): ISvgElement | null {
    if (pzb.type === PZBType.GUE_GSA || pzb.type === PZBType.GUE_GSE) {
      return this.getSVGFromCatalog('GUE')
    }

    if (pzb.element === PZBElement.None) {
      return this.getSVGFromCatalog('Error')
    }

    switch (pzb.effectivity) {
      case PZBEffectivity.Always:
      {
        if (pzb.element === PZBElement.F1000Hz2000Hz) {
          return this.getSVGFromCatalog('PermanentDouble')
        } else {
          return this.getSVGFromCatalog('PermanentSingle')
        }
      }
      case PZBEffectivity.Signal:
      {
        if (pzb.element === PZBElement.F1000Hz2000Hz) {
          return this.getSVGFromCatalog('SwitchedDouble')
        } else {
          return this.getSVGFromCatalog('SwitchedSingle')
        }
      }
      case PZBEffectivity.Route:
      {
        return this.getSVGFromCatalog('SwitchedSingle')
      }
      case PZBEffectivity.None:
      default:
      {
        return this.getSVGFromCatalog('Error')
      }
    }
  }

  public catalogName (): string {
    return OtherSVGCatalog.PZB
  }
}
