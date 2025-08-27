import { checkInstance } from '@/util/ObjectExtension'
import OlPoint from 'ol/geom/Point'

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

export class Coord implements Coordinate {
  x: number
  y: number

  static EPSILON_DISTANCE_ZERO = 0.000001

  constructor (x:number,y:number) {
    this.x = x
    this.y = y
  }

  public static fromCoordinate (c: Coordinate): Coord {
    return new Coord(c.x,c.y)
  }

  public static distance (a: Coordinate, b:Coordinate): number {
    return Math.sqrt((a.x - b.x) ** 2 + (a.y - b.y) ** 2)
  }

  public distanceTo (other:Coordinate) : number {
    return Coord.distance(this,other)
  }

  public static normalizedDirection (a:Coord,b:Coord): Coord | undefined {
    const len = Coord.distance(a,b)
    if (len < Coord.EPSILON_DISTANCE_ZERO ) {
      return undefined
    }

    return new Coord((b.x - a.x) / len,(b.y - a.y) / len)
  }

  public exact_eq (other:Coordinate): boolean {
    return this.x === other.x && this.y === other.y
  }

  public scaledBy (scalar: number): Coord{
    return new Coord(this.x * scalar, this.y * scalar)
  }

  public plus (other: Coord): Coord{
    return new Coord(this.x + other.x, this.y + other.y)
  }

  public toOlPoint () {
    return new OlPoint([
      this.x,
      this.y
    ])
  }
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
