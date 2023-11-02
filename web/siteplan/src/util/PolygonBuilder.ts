/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { pointInDistance } from './Math'

/**
 * Helper class to build a Polygon based on a list of coordinates from a
 * mixture of relative and absolute movements from each previous coordinate
 *
 * @author Stuecker
 */
export class PolygonBuilder {
  coordinates: number[][]
  referenceAngle
  previousCoordinate: number[]

  /**
   * @param x X coordinate to start from
   * @param y Y coordinate to start from
   * @param angle angle to use as a reference (optional, defaults to zero)
   */
  constructor (x: number, y:number, angle?: number) {
    this.previousCoordinate = [x, y]
    this.coordinates = []
    this.referenceAngle = angle ?? 0
  }

  /**
   * Moves to a specific coordinate
   *
   * @param x the X coordinate
   * @param y the Y coordinate
   * @returns this
   */
  moveTo (x: number, y:number): PolygonBuilder {
    this.previousCoordinate = [x, y]
    this.coordinates.push(this.previousCoordinate)
    return this
  }

  /**
   * Moves to a specific coordinate without adding the
   * coordinate to the polygon
   *
   * @param x the X coordinate
   * @param y the Y coordinate
   * @returns this
   */
  moveToVirtual (x: number, y:number): PolygonBuilder {
    this.previousCoordinate = [x, y]
    return this
  }

  /**
   * Moves relative to the previous coordinate using a distance and an angle
   *
   * @param distance the distance to move
   * @param angle the angle to move at
   * @returns this
   */
  moveDistance (distance: number, angle: number): PolygonBuilder {
    const coordinate = pointInDistance(this.previousCoordinate, this.referenceAngle + angle, distance)
    return this.moveTo(coordinate[0], coordinate[1])
  }

  /**
   * Moves relative to the previous coordinate using a distance and an angle
   * without adding the coordinate to the polygon
   *
   *
   * @param distance the distance to move
   * @param angle the angle to move at
   * @returns this
   */
  moveDistanceVirtual (distance: number, angle:number): PolygonBuilder {
    const coordinate = pointInDistance(this.previousCoordinate, this.referenceAngle + angle, distance)
    this.previousCoordinate = coordinate
    return this
  }

  /**
   * @returns the coordinates of the polygon
   */
  getCoordinates (): number[][] {
    return this.coordinates
  }
}
