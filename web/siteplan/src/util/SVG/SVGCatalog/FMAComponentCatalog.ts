/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FMAComponent, FMAComponentType } from '@/model/FMAComponent'
import { ISvgElement } from '@/model/SvgElement'
import { OtherSVGCatalog } from '@/util/SVG/SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class FMAComponentCatalog extends AbstractSVGCatalog {
  public getSVG (fma: FMAComponent): ISvgElement | null {
    if (fma.type === FMAComponentType.Axle) {
      return this.getSVGFromCatalog('Achszaehltechnik')
    }

    return null
  }

  public catalogName (): string {
    return OtherSVGCatalog.FMAComponent
  }
}
