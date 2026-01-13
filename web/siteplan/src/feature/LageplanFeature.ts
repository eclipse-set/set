/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Rectangle } from '@/collision/CollisionExtension'
import SiteplanObject from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import Configuration from '@/util/Configuration'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { compare } from '@/util/ObjectExtension'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import { Feature, Map as OlMap } from 'ol'
import { Extent, getHeight, getWidth } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import Polygon, { fromExtent } from 'ol/geom/Polygon'
import { Position } from '../model/Position'
import { isPlanningObject, SiteplanColorValue, SiteplanState } from '../model/SiteplanModel'
import SvgService from '../service/SvgService'
import {
  createFeature,
  getFeatureBoundArea,
  getFeatureBounds,
  getFeatureData,
  getFeatureGUID,
  getFeatureType
} from './FeatureInfo'

/**
 * Lageplan Feature interface
 * @author Truong
 */
export interface ILageplanFeature {
  svgService: SvgService
  map: OlMap
  getDelayedFeatures(model: SiteplanState, layers: NamedFeatureLayer[]): Feature<Geometry>[]
  getDelayedFeatureOrder(): number
  getFeatures(model: SiteplanState): Feature<Geometry>[]
  compareChangedState(initial: SiteplanState, final: SiteplanState): Feature<Geometry>[]
  setFeatureColor(feature: Feature<Geometry>, color?: number[]): Feature<Geometry>
}

export default abstract class LageplanFeature<T extends SiteplanObject> implements ILageplanFeature {
  map: OlMap
  svgService = new SvgService()
  constructor (map: OlMap) {
    this.map = map
  }

  protected abstract getObjectsModel(model: SiteplanState): T[]

  getDelayedFeatureOrder (): number {
    return 0
  }

  // Default implementation
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  getDelayedFeatures (model: SiteplanState, layers: NamedFeatureLayer[]): Feature<Geometry>[] {
    return []
  }

  // Default implementation
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return []
  }

  // Default implementation
  compareChangedState (
    initial: SiteplanState,
    final: SiteplanState,
    compareValue?: {prop: string, partID?: string,}[],
    svgFunction?: ((object: T) => ISvgElement),
    objectPart?: string[]
  ): Feature<Geometry>[] {
    // When position of object is different, then return initail and final features
    const diffPosition = this.compareChangeModel(initial, final, [
      {
        prop: 'position'
      }
    ])

    if (diffPosition) {
      return [this.getFeatures(initial), this.getFeatures(final)].flat()
    }

    const diffModel = this.compareChangeModel(initial, final, compareValue ?? [])
    let diffSvg = false
    if (svgFunction) {
      diffSvg = this.compareChangedSvg(initial, final, svgFunction, objectPart)
    }

    if (diffModel || diffSvg) {
      return this.createCompareFeatures(initial, final)
    }

    return this.getFeatures(final)
  }

  /**
   * Compare SVG of two object from changeinitialstate and changefinalstate.
   * When they are different, then set color for object of changefinalstate
   * @param initial changeinitalstate
   * @param final changefinalstate
   * @param svgFunction getSvg of object
   * @param objectPart part of object, that want to set color
   * @returns [changeInitialState, changeFinalState]
   */
  protected compareChangedSvg (
    initial: SiteplanState,
    final: SiteplanState,
    svgFunction: ((object: T) => ISvgElement),
    objectPart?: string[]
  ): boolean {
    const initialModels = this.getObjectsModel(initial)
    const finalModels = this.getObjectsModel(final)
    let isDiff = false
    initialModels.forEach(initialEle => {
      const finalEle = finalModels.find(x => x.guid === initialEle.guid)
      if (!finalEle) {
        return
      }

      const initialSvg = svgFunction(initialEle).content
      const finalSvg = svgFunction(finalEle).content
      if (objectPart) {
        objectPart.forEach(part => {
          const initialPartSvgs = Array.from(initialSvg.getElementsByClassName(part))
          const finalPartSvgs = Array.from(finalSvg.getElementsByClassName(part))
          if (initialPartSvgs.length > finalPartSvgs.length) {
            const missingNodes = this.getMissingElements<Element>(
              initialPartSvgs,
              finalPartSvgs,
              x => x.getAttribute('id')
            )
            this.coloringMissingElements(initialEle, missingNodes, SiteplanColorValue.COLOR_REMOVED)
            isDiff = true
          } else if (initialPartSvgs.length < finalPartSvgs.length) {
            const missingNodes = this.getMissingElements<Element>(
              finalPartSvgs,
              initialPartSvgs,
              x => x.getAttribute('id')
            )
            this.coloringMissingElements(finalEle, missingNodes, SiteplanColorValue.COLOR_ADDED)
            isDiff = true
          }

          finalPartSvgs.forEach(finalPart => {
            const finalPartID = finalPart.getAttribute('id')
            const initialPart = initialPartSvgs.find(x => x.getAttribute('id') === finalPartID)
            if (initialPart && !finalPart.isEqualNode(initialPart)) {
              this.setObjectColor(finalEle, finalPartID ?? '', SiteplanColorValue.COLOR_ADDED)
              this.setObjectColor(initialEle, finalPartID ?? '', SiteplanColorValue.COLOR_REMOVED)
              isDiff = true
            }
          })
        })
      } else if (initialSvg && finalSvg && !initialSvg.isEqualNode(finalSvg)) {
        this.setObjectColor(finalEle, 'feature', SiteplanColorValue.COLOR_ADDED)
        isDiff = true
      }
    })
    return isDiff
  }

  /**
   * Find elements of first list, which second list not contain
   * @param firstList
   * @param secondList
   * @param getIDFunction
   * @returns [
   *            index of missing element in first list,
   *            missing elements id,
   *            missing element
   *          ]
   */
  protected getMissingElements <T> (
    firstList: Array<T>,
    secondList: Array<T>,
    getIDFunction:((value: T) => string | number | null)
  ): [number, string, T][] | null {
    const missingNode = firstList.filter(x =>
      !secondList.find(y => getIDFunction(x) === getIDFunction(y)))

    if (missingNode.length > 0) {
      return missingNode.map(x => {
        const missingID = getIDFunction(x)
        const index = firstList.indexOf(x)
        return [index, missingID ? `${missingID}` : '', x]
      })
    }

    return null
  }

  protected coloringMissingElements <T> (
    siteplanObject: SiteplanObject,
    missingElments: [number, string, T][] | null,
    color: number[]
  ) {
    if (missingElments) {
      missingElments.forEach(x =>
        this.setObjectColor(siteplanObject, x[1], color))
    }
  }

  /**
   * Compare Model of two state,
   * then set color for different object
   * @param initial initial changed state
   * @param final final chahnge state
   * @param compareObjects propery of object, that want to compare
   * @param objectPartId part id of the object
   * @returns [initial changed state, final change state]
   */
  protected compareChangeModel (
    initial: SiteplanState,
    final: SiteplanState,
    compareObjects: {
      prop: string
      partID?: string
    }[]
  ): boolean {
    if (compareObjects.length === 0) {
      return false
    }

    const initialObjects = this.getObjectsModel(initial)
    const finalObjects = this.getObjectsModel(final)
    let isDiff = false
    initialObjects.forEach(initialObject => {
      const finalObject = finalObjects.find(x => x.guid === initialObject.guid)
      if (!finalObject) {
        return
      }

      compareObjects.forEach(compareObject => {
        let initialPropValue = null
        let finalPropValue = null
        initialPropValue = Object.entries(initialObject).find(([k]) => k === compareObject.prop)?.[1]
        finalPropValue = Object.entries(finalObject).find(([k]) => k === compareObject.prop)?.[1]
        if (!finalPropValue && !initialPropValue) {
          return
        }

        if (!initialPropValue) {
          isDiff = true
          this.setObjectColor(finalObject, compareObject.partID ?? 'feature', SiteplanColorValue.COLOR_ADDED)
        } else if (!finalPropValue) {
          isDiff = true
          this.setObjectColor(initialObject, compareObject.partID ?? 'feature', SiteplanColorValue.COLOR_REMOVED)
        }

        if (compare(initialPropValue, finalPropValue)) {
          isDiff = true
          this.setObjectColor(finalObject, compareObject.partID ?? 'feature', SiteplanColorValue.COLOR_ADDED)
          this.setObjectColor(initialObject, compareObject.partID ?? 'feature', SiteplanColorValue.COLOR_REMOVED)
        }
      })
    })
    return isDiff
  }

  protected createCompareFeatures (initial: SiteplanState, final: SiteplanState) : Feature<Geometry>[] {
    const initialFeatures = this.getFeatures(initial)
    const finalFeatures = this.getFeatures(final)
    return initialFeatures.map(initialFeature => {
      const finalFeature = finalFeatures.find(x => getFeatureGUID(x) === getFeatureGUID(initialFeature))
      if (!finalFeature) {
        return initialFeature
      }

      return this.mergeFeatures(initialFeature, finalFeature)
    }).filter(x => x !== null) as Feature<Geometry>[]
  }

  /**
   * Combine two feature, which representation same element
   * @param initial the initial feature
   * @param final the final features
   * @returns combine feature
   */
  protected mergeFeatures (initial: Feature<Geometry>, final: Feature<Geometry>): Feature<Geometry> | null {
    if (getFeatureType(initial) !== getFeatureType(final) ||
    getFeatureGUID(initial) !== getFeatureGUID(final)) {
      return null
    }

    const geometry = final.getGeometry()
    if (!geometry) {
      return null
    }

    const initialStyleFunction = initial.getStyleFunction()
    const finalStyleFunction = final.getStyleFunction()
    if (initialStyleFunction && finalStyleFunction) {
      const result = createFeature(getFeatureType(final), getFeatureData(final), geometry)
      result.setStyle((_, resolution) => {
        const initialStyle = initialStyleFunction(initial, resolution)
        const finalStyle = finalStyleFunction(final, resolution)
        if (initialStyle && finalStyle) {
          return [initialStyle, finalStyle].flat()
        }
      })

      const resultBounds = getFeatureBounds(initial)
      getFeatureBounds(final).forEach(finalBound => {
        const isContain = resultBounds.some(bound =>
          finalBound.getCoordinates()[0].some(point =>
            bound.containsXY(point[0], point[1])))
        if (!isContain) {
          resultBounds.push(finalBound)
        }
      })
      getFeatureBounds(result).push(...resultBounds)
      return result
    }

    return final
  }

  resetFeatureColor (feature: Feature<Geometry>): Feature<Geometry> {
    const object = getFeatureData(feature) as SiteplanObject
    object.objectColors = []
    return feature
  }

  // Default implementation
  setFeatureColor (feature: Feature<Geometry>, color?: number[], partID?: string): Feature<Geometry> {
    this.setObjectColor(
      getFeatureData(feature) as SiteplanObject,
      partID ?? 'feature',
      color ?? this.getRegionColor(feature)
    )
    return feature
  }

  // Default implementation
  protected setFeatureRegionColor (feature: Feature<Geometry>, featurePart?: string): Feature<Geometry> {
    this.setObjectColor(
      getFeatureData(feature) as SiteplanObject,
      featurePart ?? 'feature',
      this.getRegionColor(feature)
    )
    return feature
  }

  /**
   * Get region color of the feature
   * @param feature the feature
   * @returns color of feature
   */
  protected getRegionColor (feature: Feature<Geometry>, guid?: string): number[] {
    return !isPlanningObject(guid ?? getFeatureGUID(feature)) &&
      Configuration.developmentMode()
      ? SiteplanColorValue.COLOR_UNCHANGED_VIEW
      : SiteplanColorValue.COLOR_UNCHANGED_PLANNING
  }

  protected setObjectColor (object: SiteplanObject, id: string, color: number[]): void {
    const objColor = { id, color }
    if (!object.objectColors) {
      object.objectColors = [objColor]
      return
    }

    const existingObjectColor = object.objectColors.find(objectColor => objectColor.id === id)
    if (existingObjectColor) {
      existingObjectColor.color = color
    } else {
      object.objectColors.push(objColor)
    }
  }

  protected setLabelColor (object: SiteplanObject, color: number[]): void {
    this.setObjectColor(object, 'label', color)
  }

  private static getBoxPolygon (position: Position, bbox: Extent, translate: number[], scale: number[]): Polygon {
    const extentPolygon = fromExtent([
      position.x - getWidth(bbox) / 2,
      position.y - getHeight(bbox) / 2,
      position.x + getWidth(bbox) / 2,
      position.y + getHeight(bbox) / 2
    ])

    extentPolygon.scale(
      scale[0] * SvgDraw.SVG_OFFSET_SCALE_PIXEL_TO_METER_FACTOR,
      scale[1] * SvgDraw.SVG_OFFSET_SCALE_PIXEL_TO_METER_FACTOR
    )
    extentPolygon.translate(
      translate[0] * SvgDraw.SVG_OFFSET_SCALE_PIXEL_TO_METER_FACTOR,
      translate[1] * SvgDraw.SVG_OFFSET_SCALE_PIXEL_TO_METER_FACTOR
    )

    extentPolygon.rotate(-(position.rotation * Math.PI) / 180, [position.x, position.y])
    return extentPolygon
  }

  protected static createBBox (
    feature:Feature<Geometry>,
    position: Position,
    bbox: Extent,
    translate: number[],
    scale: number[],
    polygon?: Polygon
  ) {
    const extentPolygon = this.getBoxPolygon(position, bbox, translate, scale)
    if (!polygon) {
      polygon = extentPolygon
    }

    getFeatureBounds(feature).push(polygon)
    const coords = extentPolygon.getCoordinates()[0].map(c => [c[0], c[1]])
    getFeatureBoundArea(feature).push(coords as Rectangle)
  }
}
