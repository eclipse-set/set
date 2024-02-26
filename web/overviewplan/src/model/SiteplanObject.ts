/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { checkInstance } from '@/util/ObjectExtension'

export interface ObjectColor {
    id: string
    color: number[]
}

export function defaultObjectColorObj () : ObjectColor {
  return {
    id: 'color',
    color: [0, 0, 0]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfObjectColor (x: any): boolean {
  return checkInstance(x, defaultObjectColorObj())
}

export default interface SiteplanObject {
    guid: string
    // Not sent from Java API
    objectColors: ObjectColor[]
}

export function defaultSiteplanObj (): SiteplanObject {
  return {
    guid: 'abc',
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfSiteplanOject (object: any): boolean {
  return checkInstance(object, defaultSiteplanObj())
}

export function getLabelColor (object: SiteplanObject | ObjectColor[]): number[] | undefined {
  return getColor(object, 'label')
}

export function getColor (object: SiteplanObject | ObjectColor[], id?: string): number[] | undefined {
  if (object === undefined) {
    return undefined
  }

  let colorArr: ObjectColor[] = []
  if (isInstanceOfObjectColor(object)) {
    colorArr = (object as ObjectColor[])
  } else if (isInstanceOfSiteplanOject(object)) {
    colorArr = (object as SiteplanObject).objectColors
  } else {
    return undefined
  }

  if (!id) {
    return colorArr.find(ele => ele.id === 'feature')?.color
  }

  return colorArr.find(ele => ele.id === id)?.color
}
