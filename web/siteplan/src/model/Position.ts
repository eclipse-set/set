import { checkInstance } from '@/util/ObjectExtension'

/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
export enum DBRef {
  CR0 = 'CR0',
  DR0 = 'DR0',
  ER0 = 'ER0',
  FR0 = 'FR0'
}
export interface Coordinate {
    x: number
    y: number
}

export function defaultCoordinateObj () : Coordinate {
  return {
    x: 0,
    y: 0
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfCoordinate (object: any): boolean {
  return checkInstance(object, defaultCoordinateObj())
}

export interface Position extends Coordinate {
    rotation: number
}

export function defaultPositionObj () : Position {
  return {
    x: 0,
    y: 0,
    rotation: 0
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfPosition (object: any): boolean {
  return checkInstance(object, defaultPositionObj())
}
