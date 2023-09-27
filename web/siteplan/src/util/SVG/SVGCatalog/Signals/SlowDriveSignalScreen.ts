/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { AndereSignalGroup } from '@/util/SVG/SvgEnum'
import SignalSVGCatalog from '../SignalSVGCatalog'

export default class SlowDriveSignalScreen extends SignalSVGCatalog {
  public catalogName (): string {
    return AndereSignalGroup.LangsamfahrSignale
  }
}
