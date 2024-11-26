/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import axios from 'axios'
import MVT from 'ol/format/MVT'
import TileLayer from 'ol/layer/Tile'
import VectorTileLayer from 'ol/layer/VectorTile'
import TileSource from 'ol/source/Tile'
import VectorTileSource from 'ol/source/VectorTile'
import Configuration from '../Configuration'
import { MapSource, MapSourceType } from '../MapSource'

/**
 * Minimal interface to allow typed access to a Mapbox hosted style definition
 */
interface MapboxStyle {
  sources: { [ key: string ]: { url: string, }, }
  glyphs: string
  sprite: string
}

/**
 * Map source for Mapbox-based topological maps
 * Requries a Mapbox api token
 *
 * @author Stuecker
 */
export default class MapboxTop extends MapSource {
  source: VectorTileSource | null = null
  layer!: VectorTileLayer

  constructor () {
    super('MAPBOX_TOP', 'Mapbox', MapSourceType.Topological, Configuration.getToolboxConfiguration().mapboxApiKey)
  }

  setAsSource (tileLayer: TileLayer<TileSource>, vectorTileLayer: VectorTileLayer): void {
    this.layer = vectorTileLayer
    if (this.source === null) {
      const apiKey = this.getAPIKey()
      /* Load mapbox style definition */
      axios
        .get<MapboxStyle>(`https://api.mapbox.com/styles/v1/mapbox/streets-v11/?access_token=${apiKey}`)
        .then(response => {
          this.onStyleLoad(response.data)
        })
    } else {
      vectorTileLayer.setSource(this.source)
    }
  }

  /* Transformation functions for the mapbox custom url scheme.
   * See Mapbox API docs.
  */
  transformSourceUrl (url: string): string {
    // Remove leading mapbox://
    const mapboxPath = url.slice('mapbox://'.length)
    return `https://{a-d}.tiles.mapbox.com/v4/${mapboxPath}/{z}/{x}/{y}.vector.pbf?access_token=${this.getAPIKey()}`
  }

  transformSpriteUrl (url: string): string {
    // Remove leading mapbox://sprites/
    const style = url.slice('mapbox://sprites/'.length)
    return `https://api.mapbox.com/styles/v1/${style}/sprite?access_token=${this.getAPIKey()}`
  }

  transformFontsUrl (url: string): string {
    // Remove leading mapbox://fonts/
    const style = url.slice('mapbox://fonts/'.length)
    return `https://api.mapbox.com/fonts/v1/${style}/0-255.pbf?access_token=${this.getAPIKey()}`
  }

  onStyleLoad (style: MapboxStyle): void {
    const sourceId = Object.keys(style.sources)[0]
    this.source = new VectorTileSource({
      attributions: [
        '© <a target="_blank" href="https://www.mapbox.com/map-feedback/">Mapbox</a> ',
        '© <a target="_blank" href="https://www.openstreetmap.org/copyright">',
        'OpenStreetMap contributors</a>'
      ],
      url: this.transformSourceUrl(style.sources[sourceId].url),
      format: new MVT()
    })
    this.layer.setSource(this.source)
    style.sprite = this.transformSpriteUrl(style.sprite)
    style.glyphs = this.transformFontsUrl(style.glyphs)
    // IMPROVE: ol-mapbox-style is not available in typed form
    // eslint-disable-next-line @typescript-eslint/no-require-imports
    require('ol-mapbox-style').applyStyle(this.layer, style, sourceId)
  }
}
