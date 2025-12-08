/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { RahmenArt, Signal } from '@/model/Signal'
import { ISvgElement } from '@/model/SvgElement'
import { AndereSignalGroup } from '../../SvgEnum'
import SignalSVGCatalog from '../SignalSVGCatalog'

export default class AdditiveSignalScreen extends SignalSVGCatalog {
  private readonly alleinstehendeScreens = ['Zs2', 'Zs2v', 'Zs3', 'Zs3v', 'Zs6', 'Zp9', 'Zp9Ls', 'Zp10', 'Zp10Ls', 'Ne2']

  public getSignalScreen (signal: Signal): ISvgElement | null {
    return this.getStandingAloneAdditiveSignal(signal)
  }

  public catalogName (): string {
    return AndereSignalGroup.ZusatzSignale
  }

  private isStandingAloneAdditiveSignal (signal: Signal): boolean {
    if (signal.screen.length !== 1) {
      return false
    }

    const screen = signal.screen[0]
    return this.alleinstehendeScreens.includes(screen.screen)
  }

  private getStandingAloneAdditiveSignalName (signal: Signal): string | null {
    if (!this.isStandingAloneAdditiveSignal(signal)) {
      return null
    }

    const screen = signal.screen[0]
    switch (screen.screen) {
      case 'Zs2':
      case 'Zs2v':
        return screen.screen
      case 'Zs6':
      {
        const switched = (
          screen.switched ||
          screen.rahmenArt === RahmenArt.Schirm ||
          screen.rahmenArt === RahmenArt.Zusatzanzeiger
        )
        return screen.screen + (switched ? '_switched' : '')
      }
      case 'Zs3':
      case 'Zs3v':
        return screen.screen + (screen.switched ? '_switched' : '')
      case 'Zp9':
      case 'Zp9Ls':
      case 'Zp10':
      case 'Zp10Ls':
        return 'Zp9_10'
      default:
        return null
    }
  }

  private getStandingAloneAdditiveSignal (signal: Signal): ISvgElement | null {
    const screenName = this.getStandingAloneAdditiveSignalName(signal)
    if (!screenName) {
      return null
    }

    return this.getSVGFromCatalog(screenName)
  }
}
