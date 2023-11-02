/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LockKey, { LockKeyType } from '@/model/LockKey'
import { OtherSVGCatalog } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class LockKeySVGCatalog extends AbstractSVGCatalog{
  public getLockKeySvg (lockkey: LockKey) {
    switch (lockkey.type) {
      case LockKeyType.Inside:
        return this.getSVGFromCatalog('innen')
      case LockKeyType.Outside:
        return this.getSVGFromCatalog('aussen')
      default:
        return null
    }
  }

  public catalogName (): string {
    return OtherSVGCatalog.LockKey
  }
}
