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
import { AndereSignalGroup } from '@/util/SVG/SvgEnum'
import SignalSVGCatalog from '../SignalSVGCatalog'

export default class CrossingSignalScreen extends SignalSVGCatalog {
  public catalogName (): string {
    return AndereSignalGroup.BahnuebergangsSignale
  }

  public getSignalScreen (signal: Signal): ISvgElement | null {
    for (const screen of signal.screen) {
      // Bue0/Bue1 variants
      if ([
        'Bue01S', 'Bue02S', 'Bue02Lp', 'Bue01Lp',
        'Bue00Lp', 'Bue11SBli', 'Bue12Sst',
        'Bue12Lpst', 'Bue11Lpbli'
      ].includes(screen.screen)) {
        return this.getSVGFromCatalog('Bue0')
      }

      // Bue3 and So15 use the same symbol
      if (screen.screen === 'Bue3') {
        return this.getSVGFromCatalog('So15')
      }

      // For BueAT add the kilometer if it's not a repeating  signal
      if (screen.screen === 'BueAT') {
        return this.getBueAT(this.getSVGFromCatalog('BueAT'), signal)
      }

      const result = this.getSVGFromCatalog(screen.screen)
      if (result !== null) {
        return result
      }
    }
    return null
  }

  private getBueAT (svg: ISvgElement | null, signal: Signal) : ISvgElement | null {
    if (svg === null) {
      return null
    }

    // Do not display the kilometer if MsUEWdh is set
    if (signal.screen.find(screen => screen.screen === 'MsUESWdh')) {
      return this.getSVGFromCatalog('BueATWdh')
    }

    // Location on first route
    if (signal.routeLocations.length === 0) {
      return svg
    }

    const location = signal.routeLocations[0].km.split(',')

    // Limit kilometer to one decimal place
    let km = location[0]
    if (location[1]) {
      km += ',' + location[1][0]
    } else {
      km += ',0'
    }

    const content = svg.content.cloneNode(true) as HTMLElement
    const textSelector = content.querySelector('#km')
    if (textSelector === null) {
      return svg
    }

    textSelector.textContent = km
    return {
      content,
      id: svg.id + '-' + location,
      anchor: svg.anchor,
      nullpunkt: svg.nullpunkt,
      boundingBox: svg.boundingBox
    }
  }
}
