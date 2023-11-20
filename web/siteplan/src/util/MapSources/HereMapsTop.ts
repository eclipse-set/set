/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { MapSourceType } from '../MapSource'
import Configuration from '../Configuration'
import XYZ from 'ol/source/XYZ'
import XHRMapBase from './XHRMapBase'

/**
 * Map source for Here Maps topological maps maps
 * Requries a Mapbox api token
 *
 * @author Stuecker
 */
export default class HereMapsTop extends XHRMapBase {
  source: XYZ | null = null
  constructor () {
    super('HERE_TOP', 'Here Maps', MapSourceType.Topological, Configuration.getToolboxConfiguration().hereApiKey)
  }

  getAPIUrl (): string {
    return Configuration.getHereMapsURL()
      .replace('{mapType}', 'maptile-base')
      .replace('{mapScheme}', 'normal.day')
  }

  getAPIHeaders (): [string, string][] {
    return [['key', this.getAPIKey()]]
  }
}
