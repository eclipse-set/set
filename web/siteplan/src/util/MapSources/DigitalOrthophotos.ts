/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import TileLayer from 'ol/layer/Tile'
import TileWMS from 'ol/source/TileWMS'
import { MapSource } from '../MapSource'
import TileSource from 'ol/source/Tile'

/**
 * Abstract map source for digital orthophotos with
 * a ground sample distance of 20cm offered
 * by the Bundesamt für Kartographie und Geodäsie (BKG)
 *
 * @author Peters
 */
export default abstract class DigitalOrthophotos extends MapSource {
  private source: TileWMS | null = null

  /**
   * Get the base URL
   *
   * @returns the base URL
   */
  protected getBaseURL (): string {
    return 'https://sg.geodatenzentrum.de/wms_dop'
  }

  /**
   * Get the URL for Tile requests
   *
   * @returns the URL
   */
  protected abstract getURL (): string;

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    if (this.source !== null) {
      tileLayer.setSource(this.source)
    } else {
      this.source = new TileWMS({
        url: this.getURL(),
        params: { LAYERS: 'rgb' },
        serverType: 'geoserver',
        crossOrigin: 'anonymous',
        attributions: 'Geobasisdaten: © GeoBasis-DE / BKG (' +
                      new Date().getFullYear() +
                      ')<br>' +
                      'Nutzungsbedingungen: <a target="_blank" ' +
                      'href="https://sg.geodatenzentrum.de/web_public/nutzungsbedingungen.pdf">' +
                      'https://sg.geodatenzentrum.de/web_public/nutzungsbedingungen.pdf</a>',
        attributionsCollapsible: false
      })
      tileLayer.setSource(this.source)
    }
  }
}
