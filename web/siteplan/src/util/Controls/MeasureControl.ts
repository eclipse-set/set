/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 */

import { FeatureLayerType } from '@/feature/FeatureInfo'
import { store } from '@/store'
import { Feature, Map, Overlay, View } from 'ol'
import { Control } from 'ol/control'
import { EventsKey } from 'ol/events'
import { Geometry, LineString } from 'ol/geom'
import { Draw } from 'ol/interaction'
import { DrawEvent } from 'ol/interaction/Draw'
import { unByKey } from 'ol/Observable'
import { getLength } from 'ol/sphere'
import { Fill, Stroke, Style } from 'ol/style'
import CircleStyle from 'ol/style/Circle'
import NamedFeatureLayer from '../NamedFeatureLayer'

export default class MeasureControl extends Control {
  public static MEASURE_CONTROL_KEY = 'PLANPRO_MEASURE_CONTROL'
  public static MEASURE_OVERLAY_ID = 'measure-tooltip'
  private map: Map
  private view: View
  private drawLineInteraction: Draw | undefined
  private sketch: Feature<Geometry> | undefined | null
  private measureTooltipElement: HTMLElement | undefined | null
  private measureTooltip!: Overlay
  private listerner!: EventsKey
  private vectorLayer: NamedFeatureLayer
  private lineStyle = new Style({
    stroke: new Stroke({
      color: 'rgba(0, 0, 0, 0.5)',
      lineDash: [10, 10],
      width: 2
    }),
    image: new CircleStyle({
      radius: 5,
      stroke: new Stroke({
        color: 'rgba(0, 0, 0, 0.7)'
      }),
      fill: new Fill({
        color: 'rgba(255, 255, 255, 0.2)'
      })
    })
  })

  constructor (map: Map) {
    const button = document.createElement('button')
    button.innerHTML = '&#128207;' // HTML Encoding for "📏"
    button.className = 'measure-control-button'
    button.title = 'Messwerkzeug'

    const element = document.createElement('div')
    element.className = 'measure-control-container ol-unselectable ol-control'
    element.appendChild(button)

    super({ element })
    this.vectorLayer = this.createVectorLayer()
    this.view = map.getView()
    this.map = map
    this.view.set(MeasureControl.MEASURE_CONTROL_KEY, this)
    this.drawLineInteraction = this.getDrawMeasureLineInteraction()

    map.once('rendercomplete', () => {
      if (!this.drawLineInteraction) {
        button.disabled = true
        element.setAttribute('style', 'pointer-events:none; opacity: 0.5;')
      } else {
        this.map.addInteraction(this.drawLineInteraction)
        if (!store.state.measureEnable) {
          this.drawLineInteraction.setActive(false)
        }
      }
    })

    button.addEventListener('click', () => {
      if (store.state.measureEnable ) {
        this.onDeactiveMeasure()
      } else {
        this.onActiveMeasure()
      }

      store.commit('setMeasureEnable', !store.state.measureEnable)
    })
  }

  getDrawMeasureLineInteraction () {
    const vectorSource = this.vectorLayer.getSource()
    if (!vectorSource) {
      return
    }

    const drawInteraction = new Draw({
      source: vectorSource,
      type: 'LineString',
      style: feature => {
        const geometryType = feature.getGeometry()?.getType()
        if (geometryType == 'LineString' || geometryType === 'Point') {
          return this.lineStyle
        }
      }
    })

    drawInteraction.on('drawstart', event => this.onStartDraw(event))
    drawInteraction.on('drawend', () => this.onDrawEnd())
    return drawInteraction
  }

  onStartDraw (event: DrawEvent) {
    this.sketch = event.feature
    const geometry = this.sketch.getGeometry()
    if (!geometry) {
      return
    }

    this.listerner = geometry.on('change', e => {
      const geom = e.target
      let output
      let tooltipCoord
      if (geom instanceof LineString) {
        output = this.formatLength(geom)
        tooltipCoord = geom.getLastCoordinate()
      }

      if (!this.measureTooltipElement) {
        return
      }

      this.measureTooltipElement.innerHTML = output ?? 'length'
      this.measureTooltip.setPosition(tooltipCoord)
    })
  }

  onDrawEnd () {
    if (!this.listerner) {
      return
    }

    if (this.measureTooltipElement != null) {
      this.measureTooltipElement.className = 'ol-tooltip ol-tooltip-static'
    }

    this.measureTooltip?.setOffset([0, -7])
    this.sketch = null
    this.measureTooltipElement = null
    this.craeteMeasureTooltip()
    unByKey(this.listerner)
  }

  formatLength (line: LineString): string {
    const length = getLength(line)
    return Math.round(length * 100) / 100 + ' ' + 'm'
  }

  craeteMeasureTooltip () {
    if (this.measureTooltipElement){
      this.measureTooltipElement.remove()
    }

    this.measureTooltipElement = document.createElement('div')
    this.measureTooltipElement.className = 'ol-tooltip ol-tooltip-measure'
    this.measureTooltip = new Overlay({
      id: MeasureControl.MEASURE_OVERLAY_ID,
      element: this.measureTooltipElement,
      offset: [0, -15],
      positioning: 'bottom-center',
      stopEvent: false,
      insertFirst: false
    })
    this.map.addOverlay(this.measureTooltip)
  }

  createVectorLayer () {
    const layer = new NamedFeatureLayer(0, FeatureLayerType.Measure)
    layer.setStyle(new Style({
      fill: new Fill({
        color: 'rgba(255, 255, 255, 0.2)'
      }),
      stroke: new Stroke({
        color: '#ffcc33',
        width: 2
      })
    }))
    return layer
  }

  onActiveMeasure () {
    if (!this.drawLineInteraction) {
      return
    }

    this.map.addLayer(this.vectorLayer)
    this.map.getTargetElement().style.cursor = 'crosshair'
    this.drawLineInteraction.setActive(true)
    this.craeteMeasureTooltip()
  }

  onDeactiveMeasure () {
    this.map.getTargetElement().style.cursor = 'auto'
    this.map.removeInteraction(this.drawLineInteraction as Draw)
    this.drawLineInteraction?.setActive(false)
    this.sketch = undefined
    this.measureTooltipElement = null

    this.map.getLayers().forEach(layer => {
      if (layer instanceof NamedFeatureLayer && layer.getLayerType() === FeatureLayerType.Measure) {
        return this.map.removeLayer(layer)
      }
    })
    this.vectorLayer.clean()
    const tooltipOverlays = this.map.getOverlays().getArray()
      .filter(overlay => overlay.getId() === MeasureControl.MEASURE_OVERLAY_ID)
    tooltipOverlays.forEach(overlay => this.map.getOverlays().remove(overlay))
  }
}

