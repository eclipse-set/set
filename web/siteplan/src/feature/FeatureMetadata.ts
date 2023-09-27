/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Rectangle } from '@/collision/CollisionExtension'
import { Polygon } from 'ol/geom'
import { FeatureType } from './FeatureInfo'

// The metadata may contain any model data as type
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type ModelType = any

export default class FeatureMetadata {
  model: ModelType
  type: FeatureType
  label: string | undefined
  guid = ''
  bounds: Polygon[] = []
  boundRectangle: Rectangle[] = []

  constructor (model: ModelType, type: FeatureType, label: string | undefined) {
    this.model = model
    this.label = label
    this.type = type
    if ('guid' in model) {
      this.guid = model.guid
    }
  }
}
