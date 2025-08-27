/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import UnknownObject from '@/model/UnknownObject'
import { OtherSVGCatalog } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class OthersSVGCatalog extends AbstractSVGCatalog{
  getUnknownSvg (obj: UnknownObject) {
    console.log(obj.objectType)
    switch (obj.objectType) {
      case 'Hoehenpunkt':
      case 'Sonstiger_Punkt':
        return this.getSVGFromCatalog(obj.objectType)
      case 'Gleisausrichtungsmarkierung':
        return this.trackDirectionMarker()
      default:
        return null
    }
  }

  public getCantSvg () {
    return this.getSVGFromCatalog('ueberhoehungspunkt')
  }

  public catalogName (): string {
    return OtherSVGCatalog.Others
  }
}
