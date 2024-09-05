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
import VectorTileLayer from 'ol/layer/VectorTile'
import Configuration from './Configuration'

/**
 * Enumeration of different map source types
 */
export enum MapSourceType {
  Plan = 'Planungsansicht',
  Topological = 'Kartenansicht',
  Satellite = 'Satellitenansicht'
}

/**
 * Class to define a selectable map source
 */
export abstract class MapSource {
  private apiKey: string[]
  private apiKeyRequired: boolean
  private name: string
  private configName: string
  private type: MapSourceType

  /**
     * Constructor
     *
     * @param configName The configuration name of the MapSource
     * @param name The specific name of the map source
     * @param type The type of the map source
     * @param apiKey The API key the MapSource should use.
     *               This parameter is optional.
     *               Omit this parameter if no API key is required.
     *               If this parameter is given it is assumed that
     *               this MapSource requires an API key.
     */
  constructor (configName: string, name: string, type: MapSourceType, apiKey?: string | string[]) {
    this.configName = configName
    this.name = name
    this.type = type
    if (apiKey !== undefined) {
      if (Array.isArray(apiKey))
        this.apiKey = apiKey
      else
        this.apiKey = [apiKey ?? '']

      this.apiKeyRequired = true
    } else {
      this.apiKey = []
      this.apiKeyRequired = false
    }
  }

  /**
     * Returns the configuration name of this MapSource
     */
  getConfigName (): string {
    return this.configName
  }

  /**
     * Returns the specific name of the map source. This will only be
     * displayed if multiple sources of the same type are present
     */
  getName (): string {
    return this.name
  }

  /**
     * Returns the type of the map source
     */
  getType (): MapSourceType {
    return this.type
  }

  /**
     * Returns a string that identifies this MapSource
     */
  getIdentifier (): string {
    return this.getType() + '.' + this.getName()
  }

  /**
  * True if a API key is required
  */
  isAPIKeyRequired (): boolean {
    return this.apiKeyRequired
  }

  /**
  * Returns the API key
  */
  getAPIKey (): string {
    return this.apiKey[0]
  }

  /**
  * Returns the API key
  */
  getAPIKeys (): string[] {
    return this.apiKey
  }

  /**
     * Whether this map source should be selectable
     */
  isEnabled (): boolean {
    if (!Configuration.isMapSourceEnabled(this.getConfigName())) {
      return false
    }

    if (!this.isAPIKeyRequired()) {
      return true
    }

    return this.getAPIKey() !== ''
  }

  /**
  * Sets the MapSource as source for a tile layer or vector layer
  * Sources should only use either layer but not both
  *
  * @param tileLayer the tile layer
  * @param vectorLayer the vector layer
  */
  abstract setAsSource(tileLayer: TileLayer<TileSource>, vectorLayer: VectorTileLayer): void;
}
