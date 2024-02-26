/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'
import PositionedObject, { defaultPositionedObj } from './PositionedObject'

export interface CantPoint extends PositionedObject
{
  height: number
  // Not part of Java API
  // eslint-disable-next-line no-use-before-define
  cants: Set<Cant>
}

export interface Cant extends SiteplanObject
{
    form: string
    length: number
    pointA: CantPoint
    pointB: CantPoint
}

function defaultCantPointObj () : CantPoint {
  const obj = defaultPositionedObj() as CantPoint
  obj.cants = new Set()
  return obj
}

export function defaultCantObj (): Cant {
  return {
    guid: '0',
    form: '',
    length: 0,
    pointA: defaultCantPointObj(),
    pointB: defaultCantPointObj(),
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfCant (obj: any): boolean {
  return checkInstance(obj, defaultCantObj())
}
