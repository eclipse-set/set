/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { Coordinate } from './Position'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'

export default interface ModelError extends SiteplanObject
{
    relevantGUIDs: string[]
    position?: Coordinate
    message: string
}

export function defaultModelErrorObj (): ModelError {
  return {
    guid: 'abc',
    relevantGUIDs: ['abc'],
    objectColors: [defaultObjectColorObj()],
    message: 'abc'
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackSwitchEndMarker (object: any): boolean {
  return checkInstance(object, defaultModelErrorObj())
}
