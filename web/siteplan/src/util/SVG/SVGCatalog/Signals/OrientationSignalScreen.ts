/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Signal } from '@/model/Signal'
import { Direction } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import { AndereSignalGroup } from '@/util/SVG/SvgEnum'
import SignalSVGCatalog from '../SignalSVGCatalog'

export default class OrientationSignalScreen extends SignalSVGCatalog {
  public catalogName (): string {
    return AndereSignalGroup.Orientierungszeichen
  }

  public getSignalScreen (signal: Signal): ISvgElement | null {
    for (const screen of signal.screen) {
      if (screen.screen === 'OzBk') {
        // Decide between new and old Blockkenzeichen
        if (signal.screen.find(ele => ele.screen === 'BSZusatz')) {
          let lateralDistance = signal.lateralDistance[0]
          // If mounted on the opposite side, invert the lateral distance
          if (signal.signalDirection === Direction.OPPOSITE) {
            lateralDistance = -lateralDistance
          }

          const leftDirection = this.getSVGFromCatalog('Blockkennzeichen_links')
          const rightDirection = this.getSVGFromCatalog('Blockkennzeichen_rechts')

          if (lateralDistance > 0) {
            return leftDirection
          } else {
            return rightDirection
          }
        } else {
          return this.getSVGFromCatalog('OzBk')
        }
      }

      const result = this.getSVGFromCatalog(screen.screen)
      if (result !== null) {
        return result
      }
    }
    return null
  }
}
