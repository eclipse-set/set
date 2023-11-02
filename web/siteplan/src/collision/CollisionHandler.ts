/**
* Copyright (c) 2022 DB Netz AG and others.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v2.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*/
import { getFeatureBoundArea } from '@/feature/FeatureInfo'
import { Coordinate, isInstanceOfPosition, Position } from '@/model/Position'
import { Feature } from 'ol'
import { GeometryCollection, Polygon } from 'ol/geom'
import Geometry from 'ol/geom/Geometry'
import { getFeaturePosition, isFeaturesIntersect } from '../util/FeatureExtensions'
import { transformPointWithAngle } from '../util/Math'
import { getRectangleCenter, isLineIntersection, Line, Rectangle, translateRectangle } from './CollisionExtension'

/**
 * Handle Collision problem
 *
 * @author Truong
 */
export default class CollisionHandler {
  public static readonly MAX_VALUE = 30
  private readonly STEP = 0.5

  private trackFeatures: Line[][]
  private relevantTrackFeatures: Line[][] = []
  private originalGeometry!: Rectangle[]
  private originalCoordinate!: Coordinate
  private intersectFeatures: Feature<Geometry>[]
  private bboxFeatures: Feature<Geometry>[]
  private trackOutlineFeatures: Feature<Geometry>[]
  constructor (
    trackFeatures: Line[][],
    bboxFeatures: Feature<Geometry>[],
    trackOutlineFeautres: Feature<Geometry>[],
    intersectFeatures: Feature<Geometry>[]
  ) {
    this.trackFeatures = trackFeatures
    this.bboxFeatures = bboxFeatures
    this.trackOutlineFeatures = trackOutlineFeautres
    this.intersectFeatures = intersectFeatures
  }

  /**
   * Get translate value to advoid collision
   * @returns translate value
   */
  public getTranslateValue (moveFeature: Feature<Geometry>): number[] {
    this.originalGeometry = getFeatureBoundArea(moveFeature)
    this.relevantTrackFeatures = this.trackFeatures

    if (!this.checkIntersect(this.originalGeometry, false)) {
      return []
    }

    this.originalCoordinate = getFeaturePosition(moveFeature)[0] as Coordinate
    return this.doMove()
  }

  private checkIntersect (geometry: Rectangle[], considerTrackWidth: boolean): boolean {
    const featureGeometry = geometry.map(rect => {
      return new Polygon([rect.map(point => point)])
    })
    const moveFeature = new Feature({
      geometry: new GeometryCollection(featureGeometry)
    })
    if (this.checkIntersectFeatureGroup(moveFeature)) {
      return true
    } else if (this.checkIntersectAnotherFeature(moveFeature)) {
      return true
    } else {
      // Check if move feature intersection track width
      return this.checkIntersectTrackOutline(moveFeature, considerTrackWidth)
    }
  }

  private checkIntersectFeatureGroup (feature: Feature<Geometry>): boolean {
    return this.intersectFeatures.some(x => isFeaturesIntersect(feature, x))
  }

  private checkIntersectAnotherFeature (rectsPoinst: Feature<Geometry>): boolean {
    return this.bboxFeatures.some(x => isFeaturesIntersect(rectsPoinst, x))
  }

  private checkIntersectTrackOutline (rectsPoinst: Feature<Geometry>, considerTrackWidth: boolean): boolean {
    if (!considerTrackWidth) {
      return false
    } else {
      return this.trackOutlineFeatures.some(x => isFeaturesIntersect(rectsPoinst, x))
    }
  }

  // collision step result: offset, collides

  // Attempt to move the feature to a given offset
  public attemptMove (offset: number): [boolean, number[], Rectangle[]] {
    const [translateValue, geometry] = this.translateFeature([offset, 0])
    const intersect = this.checkIntersect(geometry, true)
    return [!intersect, translateValue, geometry]
  }

  private doMove () {
    let offsetLeft = this.STEP
    let offsetRight = this.STEP
    let trackLimit = 0
    let moveLeft = true
    let moveRight = true
    while (true) {
      if (moveLeft) {
        const [leftOk, translate, geometry] = this.attemptMove(offsetLeft)
        const moveLine = {
          points: [
            getRectangleCenter(this.originalGeometry[0]),
            getRectangleCenter(geometry[0])
          ]} as Line
        const leftTracks = this.getTrackCrossCount(moveLine)
        if (leftTracks > trackLimit) {
          moveLeft = false
        } else if (leftOk) {
          return translate
        }

        if (!leftOk) {
          offsetLeft += this.STEP
        }
      }

      if (moveRight) {
        const [rightOk, translate, geometry] = this.attemptMove(-offsetRight)
        const moveLine = {
          points: [
            getRectangleCenter(this.originalGeometry[0]),
            getRectangleCenter(geometry[0])
          ]} as Line
        const rightTracks = this.getTrackCrossCount(moveLine)

        if (rightTracks > trackLimit) {
          moveRight = false
        } else if (rightOk) {
          return translate
        }

        if (!rightOk) {
          offsetRight += this.STEP
        }
      }

      if (!moveLeft && !moveRight) {
        // Abort if we've moved too far
        if (offsetLeft > CollisionHandler.MAX_VALUE && offsetRight > CollisionHandler.MAX_VALUE) {
          return [0, 0]
        }

        trackLimit++
        moveLeft = true
        moveRight = true
      }
    }
  }

  /**
   * Translate feautre to new position, that parallel with old position
   * @param translateValue tranlsate value
   * @returns new translate value, that perpendicular with feature
   */
  private translateFeature (translateValue: number[]): [number[], Rectangle[]] {
    const featurePostion = this.originalCoordinate
    let newTranslateValue = translateValue
    if (featurePostion && isInstanceOfPosition(featurePostion)) {
      newTranslateValue = transformPointWithAngle(translateValue, (featurePostion as Position).rotation)
    }

    const translatedGeometry = this.originalGeometry.map(rect =>
      translateRectangle(rect, newTranslateValue[0], newTranslateValue[1]))
    return [newTranslateValue, translatedGeometry]
  }

  private getTrackCrossCount (moveLine: Line): number {
    let count = 0
    this.relevantTrackFeatures.forEach(feature => {
      if (feature.some(c => isLineIntersection(moveLine, c))) {
        count++
      }
    })
    return count
  }
}
