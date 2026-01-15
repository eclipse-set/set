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
import AbstractSVGCatalog from './AbstractSVGCatalog'

const notDrawnScreens: string[] = ['Zs1','Zs8']
const onlyDrawnAlleinstehend: string[] = ['Ne14', 'Zs2', 'Zs3', 'Zs3v', 'Zs6']

export default abstract class SignalSVGCatalog extends AbstractSVGCatalog {
  /**
     * Find Screen of the Signal
     * @param signal the signal
     * @returns first screen of this signal
     */
  public getSignalScreen (signal: Signal): ISvgElement | null {
    // console.log('screens:',signal.screen)
    if (signal.guid === '167D036D-6B63-4694-BD66-3141EAD98743') {
      console.log('7707')
    }

    let drawnSignals = signal.screen.filter(s => !notDrawnScreens.includes(s.screen))

    if (drawnSignals.length > 1) {
      // don't draw alleinstehende screens
      drawnSignals = drawnSignals.filter(s => !onlyDrawnAlleinstehend.includes(s.screen))
    }

    for (const screen of drawnSignals) {
      if (screen?.screen === 'Ne14') {
        console.log('...')
      }

      const result = this.getSVGFromCatalog(screen.screen)
      if (result !== null) {
        return result
      }
    }
    return null
  }

  public abstract catalogName (): string
}

