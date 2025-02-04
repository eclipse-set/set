/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType, getFeatureData, getFeatureType } from '@/feature/FeatureInfo'
import { lineString as turfLineString, polygon as turfPolygon } from '@turf/helpers'
import booleanWithin from '@turf/boolean-within'
import booleanDisjoint from '@turf/boolean-disjoint'

import { Feature } from 'ol'
import { getCenter } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import LinearRing from 'ol/geom/LinearRing'
import LineString from 'ol/geom/LineString'
import MultiLineString from 'ol/geom/MultiLineString'
import MultiPoint from 'ol/geom/MultiPoint'
import MultiPolygon from 'ol/geom/MultiPolygon'
import Point from 'ol/geom/Point'
import Polygon from 'ol/geom/Polygon'

export type LinePoint = [number, number]
export type Line = {
  guid: string
  points: [LinePoint, LinePoint]}
export type Rectangle = [LinePoint, LinePoint, LinePoint, LinePoint]
export type RectangleGroup = Rectangle[]

export function isLineIntersection (lineA: Line, lineB: Line): boolean {
  const helper = (a:number[], b:number[], c:number[]) => {
    return (c[1] - a[1]) * (b[0] - a[0]) > (b[1] - a[1]) * (c[0] - a[0])
  }
  const A = lineA.points[0]
  const B = lineA.points[1]
  const C = lineB.points[0]
  const D = lineB.points[1]

  return (helper(A, C, D) !== helper(B, C, D)) && (helper(A, B, C) !== helper(A, B, D))
}

export function getRectangleCenter (rect: Rectangle): LinePoint {
  const A = rect[0]
  const C = rect[2]

  return [(A[0] + C[0]) / 2, (A[1] + C[1]) / 2]
}

export function translateRectangle (rect: Rectangle, x: number, y:number): Rectangle {
  return rect.map(point => [point[0] + x, point[1] + y]) as Rectangle
}

export function isGeometryIntersection (a: Geometry | undefined, b: Geometry | undefined): boolean {
  if (!a || !b || a.getType() !== 'Polygon') {
    return false
  }

  switch (b.getType()) {
    case 'LineString':
      return isIntersectionLineString(a as Polygon, b as LineString)
    case 'MultiLineString':
      return (b as MultiLineString).getLineStrings()
        .some(line => isGeometryIntersection(a, line))
    case 'Point':
      return a.intersectsCoordinate((b as Point).getCoordinates())
    case 'MultiPoint':
      return (b as MultiPoint).getPoints()
        .some(point => isGeometryIntersection(a, point))
    case 'Polygon':
      return isPolygonIntersection(a as Polygon, b as Polygon)
    case 'MultiPolygon':
      return (b as MultiPolygon).getPolygons()
        .some(polygon => isGeometryIntersection(a as Polygon, polygon))
    case 'LinearRing':
      return (b as LinearRing).getCoordinates()
        .some(coor => a.intersectsCoordinate(coor))
    default:
      return false
  }
}

/**
 * Check if two polygon intersection
 * @param a first polygon
 * @param b second polygon
 * @returns true if two polygon intersection
 */
export function isPolygonIntersection (a: Polygon, b: Polygon): boolean {
  const turfA = transformPolygon(a)
  const turfB = transformPolygon(b)
  return booleanWithin(turfA, turfB) || !booleanDisjoint(turfA, turfB)
}

function transformPolygon (polygon: Polygon) {
  const coords = polygon.getCoordinates()[0]
  if (coords[0][0] !== coords[coords.length - 1][0] ||
    coords[0][1] !== coords[coords.length - 1][1]) {
    coords.push(coords[0])
  }

  return turfPolygon([coords])
}

/**
 * Check if two rectangle crossing
 * @param a first rectangle
 * @param b second rectangle
 * @returns true if two rectangle crossing
 */
export function isRectanglesCrossing (a: Polygon, b: Polygon): boolean {
  // Check if center point of this rectangle inside the other rectangle
  const centerA = getCenter(a.getExtent())
  const centerB = getCenter(b.getExtent())
  const isIntersectCenterPoint = a.intersectsCoordinate(centerB) || b.intersectsCoordinate(centerA)
  if (isIntersectCenterPoint) {
    return true
  }

  // Check if diagonals line intersect
  const coordinatesA = a.getCoordinates()[0]
  const coordinatesB = b.getCoordinates()[0]
  const isIntersectDiagonalsLine = isIntersectionLineString(a, new LineString([coordinatesB[0], coordinatesB[2]])) ||
  isIntersectionLineString(b, new LineString([coordinatesA[0], coordinatesA[2]]))
  return isIntersectDiagonalsLine
}

/**
 * Check if polygon intersect line
 * @param a the polygon
 * @param b the line
 * @returns true if intersect
 */
export function isIntersectionLineString (a: Polygon, b: LineString): boolean {
  const turfA = transformPolygon(a)
  const turfB = turfLineString(b.getCoordinates())
  return booleanWithin(turfB, turfA) || !booleanDisjoint(turfB, turfA)
}

/**
 * Check if the feature is immovable
 * @param feature the feature
 * @returns true, if the feature is immovable
 */
export function isImmovableFeature (feature: Feature<Geometry>): boolean {
  const featureData = getFeatureData(feature)
  if (featureData) {
    const type = getFeatureType(featureData.refFeature)
    return (
      type === FeatureType.PZB ||
        type === FeatureType.PZBGU ||
        type === FeatureType.TrackLock ||
        type === FeatureType.FMA ||
        type === FeatureType.TrackSwitch
    )
  }

  return false
}
