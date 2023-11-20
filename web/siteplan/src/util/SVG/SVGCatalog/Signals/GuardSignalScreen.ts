/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { RahmenArt, Signal, SignalRole } from '@/model/Signal'
import { ISvgElement } from '@/model/SvgElement'
import SignalSVGCatalog from '../SignalSVGCatalog'
import { AndereSignalGroup } from '@/util/SVG/SvgEnum'

export default class GuardSignalScreen extends SignalSVGCatalog {
  static readonly LICHTSIGNAL = 'Sh0_Licht'
  static readonly FORMSIGNAL = 'Sh0_Form'
  static readonly ZUGDECKUNG = 'Zugdeckungssignal'
  public getSignalScreen (signal: Signal): ISvgElement | null {
    if (signal.role === SignalRole.TrainCover &&
      signal.screen.filter(ele => ele.screen === 'Hp0' || ele.screen === 'Kl').length >= 2) {
      return this.getSVGFromCatalog(GuardSignalScreen.ZUGDECKUNG)
    }

    for (const screen of signal.screen) {
      const screenName = screen.screen
      if (screenName === 'Sh0' || screenName === 'Sh1') {
        if (screen.switched ||
          screen.rahmenArt === RahmenArt.Schirm ||
          screen.rahmenArt === RahmenArt.Zusatzanzeiger) {
          return this.getSVGFromCatalog(GuardSignalScreen.LICHTSIGNAL)
        }

        return this.getSVGFromCatalog(GuardSignalScreen.FORMSIGNAL)
      }

      const result = this.getSVGFromCatalog(screen.screen)
      if (result !== null) {
        return result
      }
    }

    return null
  }

  public catalogName (): string {
    return AndereSignalGroup.SchutzSignale
  }
}
