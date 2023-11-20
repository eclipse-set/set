/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { defaultPZBObj, PZB } from './PZB'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'

export interface PZBGU extends SiteplanObject
{
    pzbs: PZB[]
    length: number
}

export function defaultPZBGUObj (): PZBGU {
  return {
    pzbs: [defaultPZBObj()],
    length: 0,
    guid: 'abc',
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function instanceofPZBGU (object: any): boolean {
  return checkInstance(object, defaultPZBGUObj())
}
