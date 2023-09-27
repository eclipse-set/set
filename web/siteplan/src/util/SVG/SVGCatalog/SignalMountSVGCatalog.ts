/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { SignalMountType } from '@/model/SignalMount'
import { ISvgElement } from '@/model/SvgElement'
import { SVGMast } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class SignalMountSVGCatalog extends AbstractSVGCatalog {
  public catalogName (): string {
    return SVGMast.LongMount
  }

  getSignalMountSVG (mountType: SignalMountType): ISvgElement | null {
    switch (mountType) {
      case SignalMountType.Mast:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'Type1') ?? null
      case SignalMountType.MehrereMasten:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'Type2') ?? null
      case SignalMountType.Pfosten:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'Type3') ?? null
      case SignalMountType.Schienenfuss:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'Type5') ?? null
      case SignalMountType.Gleisabschluss:
        return this.catalog.get(SVGMast.ShortMount)?.find(c => c.id === 'Type3') ?? null
      case SignalMountType.MastNiedrig:
        return this.catalog.get(SVGMast.ShortMount)?.find(c => c.id === 'Type1') ?? null
      case SignalMountType.PfostenNiedrig:
        return this.catalog.get(SVGMast.ShortMount)?.find(c => c.id === 'Type3') ?? null
      case SignalMountType.Deckenkonstruktion:
        return this.catalog.get(SVGMast.CoverWallMount)?.find(c => c.id === 'Type6') ?? null
      case SignalMountType.Wandkonstruktion:
        return this.catalog.get(SVGMast.CoverWallMount)?.find(c => c.id === 'Type7') ?? null
      case SignalMountType.BSVRVA:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'BSVRVA') ?? null
      case SignalMountType.MsUESWdh:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'MsUESWdh') ?? null
      case SignalMountType.None:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'None') ?? null
      default:
        return this.catalog.get(SVGMast.LongMount)?.find(c => c.id === 'Type1') ?? null
    }
  }
}
