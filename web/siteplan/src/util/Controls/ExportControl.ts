/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Control } from 'ol/control'
import PlanProToolbox from '../PlanProToolbox'
import { jsPDF } from 'jspdf'
import { Map, Feature } from 'ol'
import Configuration from '../Configuration'
import { store } from '@/store'
import { FeatureLayerType, FeatureType, getFeatureData } from '@/feature/FeatureInfo'
import { SheetCutFeatureData } from '@/feature/LayoutInfoFeature'
import { getCenter } from 'ol/extent'
import { Geometry } from 'ol/geom'
import { isGeometryIntersection } from '@/collision/CollisionExtension'
import NamedFeatureLayer from '../NamedFeatureLayer'
import { toRad } from '../Math'

/**
 * Control to export the map to a PDF file
 *
 * @author Stuecker
 */
export default class ExportControl extends Control {
  // Size of the Paper (A3)
  private static readonly PAPER_SIZE = [420, 297]
  private map: Map

  /**
   * Constructor
   *
   * @param map the map
   */
  constructor (map: Map) {
    // Create button
    const button = document.createElement('button')
    button.innerHTML = 'Export'
    button.className = 'print-control-button'
    button.title = 'Export'

    // Create container
    const element = document.createElement('div')
    element.className = 'print-control-container ol-unselectable ol-control'
    element.appendChild(button)

    super({
      element
    })

    button.addEventListener('click', () => {
      this.onClick()
    }, false)
    this.map = map
  }

  private onClick () {
    if (PlanProToolbox.inPPT()) {
      PlanProToolbox.selectFolderDialog((folder: string | null) => {
        if (folder === null)
          return

        this.exportSheetCut()
      })
    } else {
      this.exportSheetCut()
    }
  }

  private async exportSheetCut () {
    this.lockMapDuringExport(true)
    const pdf = new jsPDF('landscape', undefined, 'A3')
    const layoutInfoLayer = store.state.featureLayers.find(layer => layer.getLayerType() === FeatureLayerType.SheetCut)
    const sheetCutFeatures = layoutInfoLayer?.getFeaturesByType(FeatureType.SheetCut)
    const sleep = (ms: number) => new Promise(res => setTimeout(res, ms))
    if (!layoutInfoLayer || !sheetCutFeatures || sheetCutFeatures.length === 0) {
      this.exportWithoutSheetCut(pdf)
      return
    }

    const visbleLayer = store.state.featureLayers.filter(layer => layer.getVisible() === true)
    visbleLayer.forEach(layer => layer.setVisible(false))
    const originalRotation = this.map.getView().getRotation()
    const originalZoomLvl = this.map.getView().getZoom()
    const originalViewCenter = this.map.getView().getCenter()

    for (const feature of sheetCutFeatures) {
      this.map.getView().setRotation(0)
      const featureData: SheetCutFeatureData = getFeatureData(feature)
      const extent = (feature.getGeometry()?.getExtent())
      if (extent) {
        const sheetLayer = this.getFeatureInSheet(visbleLayer, feature)
        this.map.getLayers().push(sheetLayer)
        const directionLineCoors = featureData.directionLine.getCoordinates()
        const rotation = Math.atan2(
          directionLineCoors[1][0] - directionLineCoors[0][0],
          directionLineCoors[1][1] - directionLineCoors[0][1]
        )

        if (sheetCutFeatures.indexOf(feature) !== 0) {
          pdf.addPage('A3', 'landscape')
        }

        this.map.getView().fit(extent)
        this.map.getView().setCenter(getCenter(extent))
        this.map.getView().setRotation((-rotation + toRad(90)))
        this.renderToCanvas(pdf, featureData.sheetIndex)
        await sleep(5000)
        this.map.getLayers().pop()
      }
    }
    const firstSheetCutData: SheetCutFeatureData = getFeatureData(sheetCutFeatures[0])
    pdf.save(this.getExportFileName(firstSheetCutData))
    visbleLayer.forEach(layer => layer.setVisible(true))
    this.map.getView().setRotation(originalRotation)
    this.map.getView().setZoom(originalZoomLvl ?? 10)
    this.map.getView().setCenter(originalViewCenter)
    store.commit('refreshMap')
    this.lockMapDuringExport(false)
  }

  private async exportWithoutSheetCut (pdf: jsPDF) {
    const sleep = (ms: number) => new Promise(res => setTimeout(res, ms))
    this.renderToCanvas(pdf, '1')
    await sleep(5000)
    pdf.save(this.getExportFileName())
    this.lockMapDuringExport(false)
  }

  private getExportFileName (featureData?: SheetCutFeatureData): string {
    if (featureData?.label && featureData.label.trim().length > 0) {
      return featureData.label + '.pdf'
    }

    return 'Sl.pdf'
  }

  private lockMapDuringExport (lock: boolean) {
    const visibilityState = lock ? 'hidden' : 'visible'
    // Disable pointer event and hidden all element expect openlayer map by export
    document.body.style.pointerEvents = lock ? 'none' : 'auto'
    document.body.style.visibility = visibilityState
    this.map.getViewport().style.visibility = 'visible'
    this.map.getOverlayContainerStopEvent().style.visibility = visibilityState

    this.map.getInteractions().forEach(i => i.setActive(!lock))
    store.commit('setLoading', lock)
  }

  private getFeatureInSheet (layers: NamedFeatureLayer[], sheetFeature: Feature<Geometry>) {
    const allFeatures = layers
      .filter(layer =>
        layer.getLayerType() !== FeatureLayerType.Collision
        && layer.getLayerType() !== FeatureLayerType.SheetCut)
      .flatMap(layer => layer.getSource()?.getFeatures())
      .filter(feature => feature !== undefined || feature !== null)
      .filter(feature => isGeometryIntersection(sheetFeature.getGeometry(), feature?.getGeometry()))
    const sheetLayer = new NamedFeatureLayer(this.map.getLayers().getLength(), FeatureLayerType.SheetCut)
    sheetLayer.getSource()?.addFeatures(allFeatures as Feature<Geometry>[])
    return sheetLayer
  }

  private renderToCanvas (pdf: jsPDF, index: string) {
    // Render resolution in dots per inch
    const RESOLUTION_DPI = Configuration.getExportDPI()
    // Render resolution in dots per mm
    const RENDER_RESOLUTION = Math.round(RESOLUTION_DPI / 25.4)

    // Calculate render size
    const size = this.map.getSize()
    const viewResolution = this.map.getView().getResolution()
    const width = Math.round((ExportControl.PAPER_SIZE[0] * RESOLUTION_DPI) / 25.4)
    const height = Math.round((ExportControl.PAPER_SIZE[1] * RESOLUTION_DPI) / 25.4)

    // Draw layers to canvas
    const canvas = document.createElement('canvas')
    canvas.width = ExportControl.PAPER_SIZE[0] * RENDER_RESOLUTION
    canvas.height = ExportControl.PAPER_SIZE[1] * RENDER_RESOLUTION
    const context = canvas.getContext('2d')

    if (context == null) {
      return
    }

    context.fillStyle = 'white'
    context.fillRect(0, 0, canvas.width, canvas.height)

    this.map.once('rendercomplete', () => {
      const canvasLayers = document.querySelectorAll('.ol-layer canvas')
      canvasLayers.forEach(layerelement => {
        const layer = layerelement as HTMLCanvasElement
        if (layer.width <= 0) {
          return
        }

        const context = canvas.getContext('2d')
        if (context === null) {
          return
        }

        context.drawImage(layer, 0, 0)
      })

      // Restore map view
      this.map.setSize(size)
      this.map.getView().setResolution(viewResolution)

      // Fill a page with the canvas image
      pdf.addImage(
        (canvas as HTMLCanvasElement).toDataURL('image/jpeg', 1.0),
        'JPEG',
        0,
        0,
        ExportControl.PAPER_SIZE[0],
        ExportControl.PAPER_SIZE[1],
        index
      )
    })

    if (!size || !viewResolution) {
      return
    }

    this.map.setSize([width, height])
    const scaling = Math.min(width / size[0], height / size[1])
    this.map.getView().setResolution(viewResolution / scaling)
  }
}
