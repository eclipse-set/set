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

export default class SwitchSignalScreen extends SignalSVGCatalog {
  public catalogName (): string {
    return AndereSignalGroup.WeichenSignale
  }

  public getSignalScreen (signal: Signal): ISvgElement | null {
    for (const screen of signal.screen) {
      if (['Wn3', 'Wn4', 'Wn5', 'Wn6'].includes(screen.screen)) {
        return this.getSVGFromCatalog('Wn3_4_5_6')
      }

      const result = this.getSVGFromCatalog(screen.screen)
      if (result !== null) {
        return result
      }
    }
    return null
  }
}
