/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { OtherSVGCatalog } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class CantSVGCatalog extends AbstractSVGCatalog{
  public getCantSvg () {
    return this.getSVGFromCatalog('ueberhoehungspunkt')
  }

  public catalogName (): string {
    return OtherSVGCatalog.Others
  }
}
