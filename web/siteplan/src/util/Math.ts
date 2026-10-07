/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

/**
 * Clamps an angle to fit within [0, 360]
 * @param value the angle to clamp
 * @returns the clamped angle
 */
export function clampAngle (value: number): number {
  while (value > 360) value -= 360
  while (value < 0) value += 360
  return value
}

/**
 * @param origin point to start from
 * @param angle angle to consider
 * @param distance the distance from the origin point
 * @returns a point 'distance' away from 'orgin' at an angle of 'angle'
 */
export function pointInDistance (origin: number[], angle: number, distance: number): number[] {
  const rad = angle * Math.PI / 180
  const x = Math.sin(rad) * distance + origin[0]
  const y = Math.cos(rad) * distance + origin[1]
  return [x, y]
}

/**
 * @param origin point
 * @param angle angle
 * @returns a new point
 */
export function transformPointWithAngle (origin: number[], angle: number) {
  const rad = angle * Math.PI / 180
  const tan = Math.tan(rad)
  // When angle ~ 90°
  if (Math.abs(tan) > 100) {
    return [origin[1], origin[0]]
  }

  const x = origin[0] + origin[1] * Math.tan(rad)
  const y = origin[1] - origin[0] * Math.tan(rad)
  return [x, y]
}

export function pointRotate (origin: number[], angle: number, anchor?: number[]) {
  const anchorPoint = anchor ?? [0, 0]
  const deltaX = origin[0] - anchorPoint[0]
  const deltaY = origin[1] - anchorPoint[1]
  const sin = Math.sin(toRad(angle))
  const cos = Math.cos(toRad(angle))
  const x = anchorPoint[0] + deltaX * cos - deltaY * sin
  const y = anchorPoint[1] + deltaX * sin + deltaY * cos
  return [x, y]
}

/**
 * @param a first point
 * @param b second point
 * @returns the angle between the two points
 */
export function angle (a:number[], b:number[]): number {
  return Math.atan2(b[1] - a[1], b[0] - a[0])
}

/**
 * @param a first point
 * @param b second point
 * @returns the distance between the two points
 */
export function distance (a:number[], b:number[]): number {
  return Math.sqrt(distanceSq(a, b))
}

/**
 * @param a first point
 * @param b second point
 * @returns the squared distance between the two points
 */
export function distanceSq (a:number[], b:number[]): number {
  return Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2)
}

/**
 * @param a first point
 * @param b second point
 * @returns the midpoint between the two points
 */
export function midpoint (a:number[], b:number[]): number[] {
  return [a[0] + (b[0] - a[0]) / 2, a[1] + (b[1] - a[1]) / 2]
}

/**
 * @param arr an array of points (each being a 2-array of numbers)
 * @returns the centroid between the points
 */
export function centroid (arr:number[][]): number[] {
  return arr.reduce((prev, cur) => [prev[0] + cur[0], prev[1] + cur[1]], [0, 0])
    .map(v => v / arr.length)
}

/**
 * @param rad the angle in radians
 * @returns the angle in degrees
 */
export function toDeg (rad:number) {
  return rad * 180 / Math.PI
}

/**
 * @param deg the angle in degrees
 * @returns the angle in radians
 */
export function toRad (deg:number) {
  return deg / 180 * Math.PI
}
