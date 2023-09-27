/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import axios from 'axios'
import TileLayer from 'ol/layer/Tile'
import WMTS, { optionsFromCapabilities } from 'ol/source/WMTS'
import WMTSCapabilities from 'ol/format/WMTSCapabilities'
import { MapSource, MapSourceType } from '../MapSource'
import TileSource from 'ol/source/Tile'

/**
 * Map source for TopPlusOpen-based topological maps offered by
 * the Bundesamt für Kartographie und Geodäsie (BKG)
 *
 * @author Stuecker
 */
export default class TopPlusOpen extends MapSource {
  source: WMTS | null = null

  constructor () {
    super('BKG_TOPPLUS', 'TopPlusOpen', MapSourceType.Topological)
  }

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    if (this.source !== null) {
      tileLayer.setSource(this.source)
    } else {
      axios.get('https://sgx.geodatenzentrum.de/wmts_topplus_open/1.0.0/WMTSCapabilities.xml').then(
        response => {
          const result = new WMTSCapabilities().read(response.data)
          const options = optionsFromCapabilities(result, {
            layer: 'web',
            matrixSet: 'EPSG:3857'
          })
          if (options == null) {
            console.warn('Unable to read WMTS capabilities')
            return
          }

          // Set attribution according to license requirements
          options.attributions =
              '<a target="_blank" href="http://bkg.bund.de">Bundesamt f&uuml;r Kartographie und Geod&auml;sie</a> ' +
              new Date().getFullYear() +
              ', <a target="_blank" href="http://sg.geodatenzentrum.de/web_public/Datenquellen_TopPlus_Open.pdf">Datenquellen</a>'
          options.attributionsCollapsible = false
          options.crossOrigin = 'anonymous'
          this.source = new WMTS(options)
          tileLayer.setSource(this.source)
        }
      )
    }
  }
}
