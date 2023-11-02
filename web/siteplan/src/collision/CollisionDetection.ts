/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Collision } from '@/collision/CollisionService'
import { getFeatureData, getFeatureGUID } from '@/feature/FeatureInfo'
import { isFeaturesIntersect } from '@/util/FeatureExtensions'
import { distanceSq } from '@/util/Math'
import { Feature } from 'ol'
import { getCenter } from 'ol/extent'
import { Circle, LineString } from 'ol/geom'
import Geometry from 'ol/geom/Geometry'
import { fromCircle } from 'ol/geom/Polygon'
import VectorSource from 'ol/source/Vector'
import { isImmovableFeature, isIntersectionLineString, Line } from './CollisionExtension'

/**
 * Detection all Collision in Siteplan
 *
 * @author Truong
 */
export default class CollisionDetection {
  /**
   * @returns a list of collisions between groups of features
   */
  public getCollisions (bboxFeatures: Feature<Geometry>[]): Collision[] {
    const layer = new VectorSource<Geometry>()
    layer.addFeatures(bboxFeatures)
    const featuresIntersectExtent = new Array<Feature<Geometry>[]>()
    const collisions: Collision[] = []

    bboxFeatures.forEach(feature => {
      // Find all features which could intersect with this feature (by extent)
      const featuresIntersect = this.getIntersectFeaturesByExtent(layer, feature)

      // If only one feature intersects (self) there is no collision
      if (featuresIntersect.length === 1) {
        return
      }

      // If this feature is already considered within another collision,
      // merge the collisions
      const existingCollision = this.getCollisionForFeature(feature, featuresIntersectExtent)
      if (existingCollision) {
        featuresIntersect.filter(feature => !existingCollision.includes(feature))
          .forEach(feature => existingCollision.push(feature))
        return
      }

      featuresIntersectExtent.push(featuresIntersect)
    })

    featuresIntersectExtent.forEach(featuresIntersect => {
      const innerIntersect = this.getIntersectFeatures(featuresIntersect)

      // Make sure at least two features  make up this collision
      const featureCount = new Set(innerIntersect.map(c => getFeatureData(c).refFeature)).size
      if (featureCount < 2) {
        return
      }

      // Make sure at least one feature is movable
      const hasMovableFeatures = innerIntersect.some(c => !isImmovableFeature(c))
      if (!hasMovableFeatures) {
        return
      }

      if (!this.isInExistingCollision(collisions, innerIntersect)) {
        collisions.push(innerIntersect)
      }
    })

    return collisions
  }

  /**
   * Get features which intersect the extent of a given features
   *
   * @param layer the layer of features
   * @param feature feature
   * @returns outer intersection features
   */
  private getIntersectFeaturesByExtent (
    layer: VectorSource<Geometry>,
    feature: Feature<Geometry>
  ) : Feature<Geometry>[] {
    const overlapFeatures = new Array<Feature<Geometry>>()
    if (feature.getGeometry()) {
      layer.forEachFeatureInExtent((feature.getGeometry() as Geometry).getExtent(), ele => {
        overlapFeatures.push(ele)
      })
    }

    return overlapFeatures.filter((ele, index) => overlapFeatures.indexOf(ele) === index)
  }

  private getCollisionForFeature (feature: Feature<Geometry>, collisions: Collision[]) {
    return collisions.find(c => c.includes(feature))
  }

  /**
   * Get features which intersect other features
   *
   * @param extentIntersection outer intersection features
   * @returns inner intersection features
   */
  private getIntersectFeatures (extentIntersection: Collision): Collision {
    const innerIntersect = new Array<Feature<Geometry>>()
    extentIntersection.forEach((currentfeature, index) => {
      if (index + 1 >= extentIntersection.length) {
        return
      }

      for (let i = index + 1; i < extentIntersection.length; i++) {
        const nextFeature = extentIntersection[i]
        const isIntersect = isFeaturesIntersect(currentfeature, nextFeature)
        if (isIntersect) {
          innerIntersect.push(currentfeature, nextFeature)
        }
      }
    })
    // Remove duplicate features
    return innerIntersect.filter((feature, index) => innerIntersect.indexOf(feature) === index)
  }

  private isInExistingCollision (intersectFeatures: Collision[], newIntersectGroup: Collision): boolean {
    return intersectFeatures
      .filter(ele => ele.length === newIntersectGroup.length)
      .some(ele => !ele.map(feature => newIntersectGroup.includes(feature))
        .includes(false))
  }

  /**
   * Determines which features are relevant for collision detection
   * A feature is considered relevant if:
   * - A point is sufficiently close to the geometry
   * @param features
   * @param center
   * @param radius
   * @returns a list of relevant features
   */
  public getRelevantFeaturesInRadius (features: Feature<Geometry>[], center: number[], radius: number) {
    return features.filter(x => {
      const extent = x.getGeometry()?.getExtent()
      if (!extent) {
        return false
      }

      const extentCenter = getCenter(extent)
      const inside = distanceSq(center, extentCenter) < radius * radius
      return inside
    })
  }

  /**
   * Determines which track features are relevant for collision detection
   * A track feature is considered relevant if:
   * - A point is sufficiently close to the geometry
   * @param trackFeatures the geometry of the feature to check
   * @param centerPoint
   * @param radius
   * @returns a list of track features which are relevant
   */
  public getRelevantTrackInRadius (trackFeatures: Line[][], centerPoint: number[], radius: number): Line[][] {
    const circlePolygon = fromCircle(new Circle(centerPoint, radius))
    return trackFeatures.filter(x => {
      let isRelevant = false
      isRelevant = x.filter((_, i) => i % 10 === 0).some(c => (
        distanceSq(centerPoint, c.points[0]) < radius * radius ||
          distanceSq(centerPoint, c.points[1]) < radius * radius
      ))
      if (isRelevant) {
        return true
      }

      if (x.length === 1) {
        isRelevant = isIntersectionLineString(circlePolygon, new LineString([x[0].points[0], x[0].points[1]]))
      }

      return isRelevant
    })
  }

  /**
   * Determines which track outline features are relevant for collision detection
   * A track feature is considered relevant if:
   * - A outline feature is sufficiently intersect to the radius-geometry
   * @param outlineFeatures
   * @param relevantTrackFeatures
   * @param point
   * @param radius
   * @returnsa list of outline features which are relevant
   */
  public getRelevantOutlineFeaturesInRadius (
    outlineFeatures: Feature<Geometry>[],
    relevantTrackFeatures: Line[][],
    point: number[],
    radius: number
  ) {
    const circleFeature = new Feature({
      name: 'CircleFeature',
      geometry: fromCircle(new Circle(point, radius))
    })
    const trackGuids = relevantTrackFeatures.map(x => x[0].guid)
    return outlineFeatures.filter(x => {
      if (!trackGuids.includes(getFeatureGUID(x))) {
        return false
      }

      const intersect = isFeaturesIntersect(circleFeature, x)
      return intersect
    })
  }
}
