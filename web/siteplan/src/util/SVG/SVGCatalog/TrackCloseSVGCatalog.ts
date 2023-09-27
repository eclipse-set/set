/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import TrackClose, { TrackCloseType } from '@/model/TrackClose'
import { OtherSVGCatalog } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class TrackCloseSVGCatalog extends AbstractSVGCatalog {
  public getTrackCloseSvg (trackClose: TrackClose) {
    switch (trackClose.trackCloseType) {
      case TrackCloseType.FrictionBufferStop:
      case TrackCloseType.FixedBufferStop:
      case TrackCloseType.HeadRamp:
        return this.getSVGFromCatalog('sprellbock')
      case TrackCloseType.ThresholCross:
        return this.getSVGFromCatalog('schwellenkreuz')
      case TrackCloseType.InfrastructureBorder:
        return this.getSVGFromCatalog('infrastruktur')
      default:
        return this.getSVGFromCatalog('sonstige')
    }
  }

  public catalogName (): string {
    return OtherSVGCatalog.TrackClose
  }
}
