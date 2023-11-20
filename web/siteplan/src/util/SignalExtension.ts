/**
* Copyright (c) 2022 DB Netz AG and others.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v2.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*/

import { Signal, SignalPriority, SignalSystem } from '@/model/Signal'
import { HauptVorSignalGroup } from './SVG/SvgEnum'

/**
 * Get priority of the signal
 * @param signal the signal
 * @returns priority lower is importanter
 */
export function getSignalPrio (signal: Signal | undefined): number {
  if (!signal) {
    return 100
  }

  const signalGroup = getSignalGroup(signal)
  switch (signalGroup) {
    case 'Main':
    case 'MultiSection':
      return SignalPriority.Main
    case 'Pre':
      return SignalPriority.Pre
    case 'TrainCover':
      return SignalPriority.Lock
    case 'StandingAlone':
      if (signal.screen[0].screen === 'Ne14') {
        return SignalPriority.StandingAlone_Ne14
      } else {
        return SignalPriority.StandingAlone
      }
    case 'Lock':
      return SignalPriority.Lock
    default:
      return 100
  }
}

function getSignalGroup (signal: Signal): string {
  const signalRole = signal.role
  if (getSystem(signal.system)) {
    return signalRole
  }

  if (signal.screen.length === 1) {
    return 'StandingAlone'
  }

  return ''
}

export function getSystem (system: SignalSystem): string | null {
  switch (system) {
    case SignalSystem.HL:
      return null
    case SignalSystem.SV:
      return HauptVorSignalGroup.HlSvSys
    case SignalSystem.HV:
      return HauptVorSignalGroup.HvSys
    case SignalSystem.KS:
      return HauptVorSignalGroup.KsSys
    default:
      return null
  }
}
