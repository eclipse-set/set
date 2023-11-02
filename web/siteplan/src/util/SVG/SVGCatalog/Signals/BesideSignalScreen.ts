/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Signal } from '@/model/Signal'
import { ISvgElement } from '@/model/SvgElement'
import '@/util/ElementExtensions'
import { AndereSignalGroup } from '@/util/SVG/SvgEnum'
import SignalSVGCatalog from '../SignalSVGCatalog'

export default class BesideSignalScreen extends SignalSVGCatalog {
  public catalogName (): string {
    return AndereSignalGroup.NebenSignale
  }

  public getSignalScreen (signal: Signal): ISvgElement | null {
    for (const screen of signal.screen) {
      // Ne_13
      if (['Ne13a', 'Ne13b'].includes(screen.screen)) {
        return this.getSVGFromCatalog('Ne13')
      }

      // BS_vRVA
      if (['BSVRVA'].includes(screen.screen)) {
        // ignore this initially
        continue
      }

      // Ne_3_Xstr
      if (['Ne31str', 'Ne32str', 'Ne33str', 'Ne34str', 'Ne35str'].includes(screen.screen)) {
        if (signal.screen.find(screen => screen.screen === 'BSVRVA')) {
          // Ne_3_Xstr combined with BS_vRVA
          return this.getSVGFromCatalog(screen.screen + 'VRVA')
        }

        return this.getSVGFromCatalog(screen.screen)
      }

      const result = this.getSVGFromCatalog(screen.screen)
      if (result !== null) {
        return result
      }
    }
    return null
  }
}
