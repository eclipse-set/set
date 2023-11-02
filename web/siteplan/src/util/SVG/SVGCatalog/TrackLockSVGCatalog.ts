/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { ISvgElement } from '@/model/SvgElement'
import TrackLock from '@/model/TrackLock'
import { OtherSVGCatalog } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class TrackLockSVGCatalog extends AbstractSVGCatalog {
  public getTrackLockSVG (trackLock: TrackLock): ISvgElement | null {
    let svgID = 'sperren'

    if (trackLock.components.length > 1) {
      svgID += '_doppelte'
    }

    return svgID === '' ? null : this.getSVGFromCatalog(svgID)
  }

  public getLockCircleSVG (): ISvgElement | null {
    return this.getSVGFromCatalog('kreis')
  }

  public catalogName (): string {
    return OtherSVGCatalog.TrackLock
  }
}
