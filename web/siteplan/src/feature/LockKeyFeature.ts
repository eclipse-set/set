/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Label } from '@/model/Label'
import LockKey from '@/model/LockKey'
import { Position } from '@/model/Position'
import { SiteplanState } from '@/model/SiteplanModel'
import { getLabelColor } from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'
import LageplanFeature from './LageplanFeature'

/**
 * Contains all feature of lock key
 */
export default class LockKeyFeature extends LageplanFeature<LockKey> {
  protected getObjectsModel (model: SiteplanState): LockKey[] {
    return model.lockkeys
  }

  protected getObjectSvg (lockKey: LockKey, label?: Label | null): ISvgElement {
    return this.svgService.getFeatureSvg(
      lockKey,
      FeatureType.LockKey,
      label ?? undefined
    )
  }

  public getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(lockkey => this.createLockKeyFeature(lockkey))
  }

  private createLockKeyFeature (lockKey: LockKey) : Feature<Geometry> {
    const feature = createFeature(
      FeatureType.LockKey,
      lockKey,
      new OlPoint([lockKey.position.x, lockKey.position.y]),
      lockKey.label.text
    )
    const svg = this.getObjectSvg(lockKey, lockKey.label)

    // The Lockkey should turn upwards
    let rotation = lockKey.position.rotation % 90
    rotation += Math.abs(rotation) > 55
      ? rotation > 0
        ? -90
        : 90
      : 0
    this.createLockKeyBBox(feature, lockKey, svg, rotation)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      updateLabelOrientation(lockKey.label, rotation, this.map)
      updateLabelColor(lockKey.label, getLabelColor(lockKey))
      const style = this.svgService.getFeatureStyle(lockKey, FeatureType.LockKey, lockKey.label)
      style.getImage().setScale(scale)
      style.getImage().setRotation((rotation * Math.PI) / 180)
      return style
    })
    return feature
  }

  private createLockKeyBBox (feature: Feature<Geometry>, lockKey: LockKey, svg: ISvgElement, rotation: number) {
    const position: Position = {
      rotation,
      x: lockKey.position.x,
      y: lockKey.position.y
    }
    svg.boundingBox.forEach(bbox => {
      LageplanFeature.createBBox(feature, position, bbox, [0, 0], [1, 1])
    })
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], lockkey => this.getObjectSvg(lockkey))
  }
}
