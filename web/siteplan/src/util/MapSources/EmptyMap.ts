/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import TileLayer from 'ol/layer/Tile'
import TileSource from 'ol/source/Tile'
import { MapSource, MapSourceType } from '../MapSource'

/**
 * Map source for an empty map. Used to display an empty background for features
 *
 * @author Stuecker
 */
export default class EmptyMap extends MapSource {
  constructor () {
    super('', '', MapSourceType.Plan)
  }

  isEnabled (): boolean {
    /* Always enabled */
    return true
  }

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    tileLayer.setSource(null as unknown as TileSource)
  }
}
