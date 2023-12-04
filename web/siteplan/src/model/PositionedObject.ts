/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { checkInstance } from '@/util/ObjectExtension'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'
import { Position, defaultPositionObj } from './Position'

export default interface PositionedObject extends SiteplanObject {
    position: Position
}

export function defaultPositionedObj (): PositionedObject {
  return {
    guid: '0',
    position: defaultPositionObj(),
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfPositionedObject (object: any): boolean {
  return checkInstance(object, defaultPositionedObj())
}
