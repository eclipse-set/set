/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import BoundingBoxesHandler from '@/collision/BoundingBoxesHandler'
import CollisionDetection from '@/collision/CollisionDetection'
import { isImmovableFeature, Line } from '@/collision/CollisionExtension'
import { CollisionFeatureData } from '@/collision/CollisionFeature'
import CollisionHandler from '@/collision/CollisionHandler'
import CollisionMoveIndicators from '@/collision/CollisionMoveIndicators'
import TrackOutlineHandler from '@/collision/TrackOutlineHandler'
import { store } from '@/store'
import Configuration from '@/util/Configuration'
import { getExtent } from '@/util/ExtentExtension'
import { sortFeaturesByPrio, toLines } from '@/util/FeatureExtensions'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { Feature, Map as OlMap } from 'ol'
import BaseEvent from 'ol/events/Event'
import { getCenter } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import LineString from 'ol/geom/LineString'
import { FeatureLayerType, FeatureType, getFeatureBoundArea, getFeatureData, getFeatureGUID } from '../feature/FeatureInfo'

/**
 * A collision is a group of features which collide
 */
export type Collision = Feature<Geometry>[]

/**
 * Container for all collision features
 *
 * @author Truong
 */
export default class CollisionService {
  // Distance for find relevant features
  private readonly RELEVANT_DISTANCE = CollisionHandler.MAX_VALUE + 10

  private featuresTranslated = new Map<Feature<Geometry>, number[]>()
  private featuresIntersectInArea: Feature<Geometry>[][] = []
  protected bboxFeatures = new Array<Feature<Geometry>>()
  protected scale = store.state.boundingBoxScaleFactor
  private eventListenerAdded = false
  protected trackOutlineFeatures: Feature<Geometry>[] = []
  private trackFeatures: Line[][] = []
  private map: OlMap
  private collisionDetection = new CollisionDetection()
  private boundingBoxesHandler = new BoundingBoxesHandler()
  private moveIndicator = new CollisionMoveIndicators()
  private trackOutlineHandler = new TrackOutlineHandler()

  constructor (map: OlMap) {
    this.map = map
  }

  processCollisions (layers: NamedFeatureLayer[]) {
    const collisionLayer = layers.find(c => c.getLayerType() === FeatureLayerType.Collision)
    const trackLayer = layers.find(c => c.getLayerType() === FeatureLayerType.Track)
    if (!collisionLayer || !trackLayer) {
      return
    }

    if (!this.eventListenerAdded) {
      collisionLayer?.addEventListener('changeIndex', event => this.onChangeConfigurationListener(event))
      this.eventListenerAdded = true
    }

    const bboxFeatures = collisionLayer.getFeaturesByType(FeatureType.Collision)
    const trackOutlineFeatures = collisionLayer.getFeaturesByType(FeatureType.TrackOutline)
    const trackFeatures = trackLayer.getFeaturesByType(FeatureType.Track)
    if (!bboxFeatures || !trackOutlineFeatures || !trackFeatures) {
      return
    }

    this.trackOutlineFeatures = trackOutlineFeatures
    this.trackFeatures = trackFeatures.map(c => toLines(
      getFeatureGUID(c),
      c.getGeometry() as LineString
    ))
    this.bboxFeatures =  this.boundingBoxesHandler.rescaleBoundingBoxes(
      bboxFeatures,
      100,
      this.scale
    )

    this.featuresIntersectInArea = this.collisionDetection.getCollisions(this.bboxFeatures)
    this.featuresIntersectInArea.forEach(collision => this.collisionDisplacement(collision))

    this.subscribeConfiguration()
  }

  public reset () {
    this.featuresTranslated.clear()
    this.featuresIntersectInArea = []
    this.bboxFeatures = new Array<Feature<Geometry>>()
    this.scale = store.state.boundingBoxScaleFactor
  }

  /**
   * Translate features, that overlap
   * @param collision list overlap features
   */
  private collisionDisplacement (collision: Collision): void {
    // Order features by priority
    let collisionFeatures = Array<Feature<Geometry>>().concat(sortFeaturesByPrio(collision))
    // The feature with the highest priority won't move
    collisionFeatures.pop()

    // Remove immovable features
    collisionFeatures = collisionFeatures
      .filter(feature => !isImmovableFeature(feature))

    // Find relevant features near center of collision group
    const midPointOfCollision = getCenter(getExtent(collisionFeatures))
    const relevantFeatures = this.collisionDetection
      .getRelevantFeaturesInRadius(this.bboxFeatures, midPointOfCollision, this.RELEVANT_DISTANCE)
    const relevantTrackFeatures = this.collisionDetection
      .getRelevantTrackInRadius(this.trackFeatures, midPointOfCollision, this.RELEVANT_DISTANCE)
    const relevantOutlineFeature = this.collisionDetection
      .getRelevantOutlineFeaturesInRadius(
        this.trackOutlineFeatures,
        relevantTrackFeatures,
        midPointOfCollision,
        this.RELEVANT_DISTANCE
      )

    collisionFeatures.forEach(feature => {
      if (this.featuresTranslated.has(feature)) {
        return
      }

      // Disable collision if required
      const data = getFeatureData(feature) as CollisionFeatureData
      const refFeature = data.refFeature
      if (!store.state.collisionEnabled) {
        // Reset style to default
        refFeature.setStyle(data.originalStyleFunction)
        return
      }

      const collisionHandler = new CollisionHandler(
        relevantTrackFeatures,
        relevantFeatures.filter(x => x !== feature || !collision.find(c => c === x)),
        relevantOutlineFeature,
        collision.filter(ele => ele !== feature)
      )
      const translate = collisionHandler.getTranslateValue(feature)
      if (translate.length === 0) {
        // Reset style to default
        refFeature.setStyle(data.originalStyleFunction)
        return
      }

      getFeatureBoundArea(feature).forEach(rect => rect.forEach(point => {
        point[0] += translate[0]
        point[1] += translate[1]
      }))
      const oldExtend = refFeature.getGeometry()?.getExtent()
      const oldCenter = oldExtend ? getCenter(oldExtend) : null
      refFeature.getGeometry()?.translate(translate[0], translate[1])
      feature.getGeometry()?.translate(translate[0], translate[1])
      this.featuresTranslated.set(feature, translate)
      const newExtend = refFeature.getGeometry()?.getExtent()
      const newCenter = newExtend ? getCenter(newExtend) : null

      // Update the style so that movement indicators are added dynamically
      refFeature.setStyle((feature, resolution) => {
        if (!data.originalStyleFunction) {
          return
        }

        const originalStyle = data.originalStyleFunction(feature, resolution)
        const baseResolution = this.map
          .getView()
          .getResolutionForZoom(
            Configuration.getToolboxConfiguration().baseZoomLevel
          )
        const scale: number = baseResolution / resolution

        return this.moveIndicator.createStyle(
          feature as Feature<Geometry>,
          [oldCenter, newCenter],
          originalStyle,
          scale
        )
      })
    })
  }

  /**
   * Collision displacememt again, when configuration parameter changed
   */
  subscribeConfiguration () {
    store.subscribe((m, s) => {
      if (m.type === 'setTrackOutlineWidth' && s.trackOutlineWidth !== Configuration.getTrackWidth().outline) {
        const newtrackOutline = this.trackOutlineHandler.getTrackOutlineLookUp(s.featureLayers)
        if (newtrackOutline) {
          this.trackOutlineFeatures = newtrackOutline
          this.onChangeConfigurationListener()
        }
      }

      if (m.type === 'setBoundingBoxScaleFactor' && s.boundingBoxScaleFactor) {
        this.bboxFeatures = this.boundingBoxesHandler.rescaleBoundingBoxes(
          this.bboxFeatures,
          this.scale,
          s.boundingBoxScaleFactor
        )
        this.scale = s.boundingBoxScaleFactor
        this.onChangeConfigurationListener()
      }

      if (m.type === 'setCollisionEnabled') {
        if (s.collisionEnabled) {
          this.onChangeConfigurationListener()
        } else {
          this.resetDisplacement()
        }
      }
    })
  }

  /**
   * Collision displacement again, when configuration parameter changed
   * @param event the Event
   */
  private onChangeConfigurationListener (event?: Event|BaseEvent): void {
    this.resetDisplacement()

    this.featuresIntersectInArea = []
    // Collision displacement again
    setTimeout(() => {
      this.featuresIntersectInArea = this.collisionDetection.getCollisions(this.bboxFeatures)
      this.featuresIntersectInArea.forEach(intersectFeatures => this.collisionDisplacement(intersectFeatures))
    }, 100)

    if (event) {
      event.stopPropagation()
    }
  }

  private resetDisplacement () {
    // Translated feature zu original position
    this.featuresTranslated.forEach((translateValue, feature) => {
      const featureData = getFeatureData(feature)
      if (!featureData) {
        return
      }

      featureData.refFeature.getGeometry()?.translate(-translateValue[0], -translateValue[1])
      feature.getGeometry()?.translate(-translateValue[0], -translateValue[1])
      getFeatureBoundArea(feature).forEach(rect => rect.forEach(point => {
        point[0] -= translateValue[0]
        point[1] -= translateValue[1]
      }))

      featureData.refFeature.setStyle(featureData.originalStyleFunction)
    })
    this.featuresTranslated.clear()
  }
}
