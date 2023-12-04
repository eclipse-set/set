/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { defaultPositionObj } from './Position'
import PositionedObject from './PositionedObject'
import { defaultObjectColorObj } from './SiteplanObject'

export default interface UnknownObject extends PositionedObject {
    objectType: string
}
export function defaultUnknownObj (): UnknownObject {
  return {
    guid: '0',
    position: defaultPositionObj(),
    objectColors: [defaultObjectColorObj()],
    objectType: ''
  }
}
