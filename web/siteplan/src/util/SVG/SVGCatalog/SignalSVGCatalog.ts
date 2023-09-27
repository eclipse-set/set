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

export default abstract class SignalSVGCatalog extends AbstractSVGCatalog {
  /**
     * Find Screen of the Signal
     * @param signal the signal
     * @returns first screen of this signal
     */
  public getSignalScreen (signal: Signal): ISvgElement | null {
    for (const screen of signal.screen) {
      const result = this.getSVGFromCatalog(screen.screen)
      if (result !== null) {
        return result
      }
    }
    return null
  }

  public abstract catalogName (): string
}
