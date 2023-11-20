/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Signal } from '@/model/Signal'
import { Direction } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import { AndereSignalGroup } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class DirectionBoardSVGCatalog extends AbstractSVGCatalog {
  getDirectionBoard (signal: Signal, type:string): ISvgElement | null {
    const svgCatalog = type === 'So20'
      ? this.catalog.get(AndereSignalGroup.Zuordnungstafel)
      : this.catalog.get(AndereSignalGroup.Richtungspfeil)
    if (type === 'So20' && signal.lateralDistance.length > 1) {
      return svgCatalog?.find(tafel => tafel.id === 'beide') ?? null
    }

    const leftDirection = svgCatalog?.find(tafel => tafel.id === 'links') ?? null
    const rightDirection = svgCatalog?.find(tafel => tafel.id === 'rechts') ?? null

    if (type === 'LfPfL') {
      return leftDirection
    }

    if (type === 'LfPfR') {
      return rightDirection
    }

    switch (signal.signalDirection) {
      case Direction.FORWARD: {
        return signal.lateralDistance[0] > 0 ? leftDirection : rightDirection
      }
      case Direction.OPPOSITE: {
        return signal.lateralDistance[0] < 0 ? leftDirection : rightDirection
      }
      default: {
        return null
      }
    }
  }

  public catalogName (): string {
    return AndereSignalGroup.Zuordnungstafel
  }
}
