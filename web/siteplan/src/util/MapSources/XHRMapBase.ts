/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import TileLayer from 'ol/layer/Tile'
import XYZ from 'ol/source/XYZ'
import { MapSource } from '../MapSource'
import ImageTile from 'ol/ImageTile'
import Tile from 'ol/Tile'
import TileSource from 'ol/source/Tile'

/**
 * Base class for a map source based on XHR requests
 *
 * @author Stuecker
 */
export default abstract class XHRMapBase extends MapSource {
  source: XYZ | null = null
    abstract getAPIUrl (): string
    abstract getAPIHeaders (): [string, string][]

    setAsSource (tileLayer: TileLayer<TileSource>): void {
      if (this.source === null) {
        this.source = new XYZ({
          crossOrigin: 'anonymous',
          url: this.getAPIUrl()
        })

        const tileLoader = (tile: Tile, src: string) => {
          if (!(tile instanceof ImageTile)) {
            return
          }

          const client = new XMLHttpRequest()
          client.open('GET', src)
          client.responseType = 'blob'
          this.getAPIHeaders().forEach(([key, value]) => client.setRequestHeader(key, value))
          client.onload = () => {
            tile.getImage().setAttribute('src', URL.createObjectURL(client.response))
          }
          client.send()
        }

        this.source.setTileLoadFunction(tileLoader)
      }

      tileLayer.setSource(this.source)
    }
}
