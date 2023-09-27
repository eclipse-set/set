/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Signal, SignalRole, SignalSystem } from '@/model/Signal'
import { ISvgElement } from '@/model/SvgElement'
import SignalSVGCatalog from '../SignalSVGCatalog'

import { getSystem } from '@/util/SignalExtension'

export default abstract class MainSignalScreen extends SignalSVGCatalog {
  public getSignalScreen (signal: Signal): ISvgElement | null {
    if (getSystem(signal.system) !== this.catalogName()) {
      return null
    }

    const screenName = this.getSignalScreenName(signal)
    if (screenName === null) {
      return null
    }

    return this.getSVGFromCatalog(screenName)
  }

  public abstract catalogName(): string

  private getSignalScreenName (signal: Signal) {
    if (signal.system === SignalSystem.HV) {
      // Special case: The HV-System has multiple Pre/Main screen options
      switch (signal.role) {
        case SignalRole.MultiSection: return 'HpVr'
        case SignalRole.Pre:
        {
          if (signal.screen.find(screen => screen.screen === 'Vr1')) {
            return 'Vr1'
          }

          if (signal.screen.find(screen => screen.screen === 'Vr2')) {
            return 'Vr2'
          }

          return null
        }
        case SignalRole.Main:
        {
          if (signal.screen.find(screen => screen.screen === 'Hp1')) {
            return 'Hp1'
          }

          if (signal.screen.find(screen => screen.screen === 'Hp2')) {
            return 'Hp2'
          }

          return null
        }
        default:
          return null
      }
    }

    // All other systems consistently map to HpVr/Hp/Vr
    switch (signal.role) {
      case SignalRole.MultiSection: return 'HpVr'
      case SignalRole.Pre: return 'Vr'
      case SignalRole.Main: return 'Hp'
      default: return null
    }
  }
}
