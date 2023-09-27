/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { HauptVorSignalGroup } from '@/util/SVG/SvgEnum'
import MainSignalScreen from './MainSignalScreen'

export default class KsSystemScreen extends MainSignalScreen {
  public catalogName (): string {
    return HauptVorSignalGroup.KsSys
  }
}
