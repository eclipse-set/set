/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { isGeometryIntersection } from '@/collision/CollisionExtension'
import { FeatureLayerType, FeatureType, getFeatureData } from '@/feature/FeatureInfo'
import { SheetCutFeatureData } from '@/feature/LayoutInfoFeature'
import { store } from '@/store'
import * as turf from '@turf/turf'
import { MultiPolygon as GeoJSONMultiPolygon, Position as GeoJSONPosition } from 'geojson'
import { Feature, Map } from 'ol'
import { Control } from 'ol/control'
import { getBottomLeft, getBottomRight, getCenter, getHeight, getTopLeft, getTopRight, getWidth } from 'ol/extent'
import { GeoJSONPolygon } from 'ol/format/GeoJSON'
import { Geometry, MultiPolygon, Polygon } from 'ol/geom'
import { Fill, Style } from 'ol/style'
import { setMapScale } from '../MapScale'
import { angle, pointRotate, toDeg, toRad } from '../Math'
import NamedFeatureLayer from '../NamedFeatureLayer'
import PlanProToolbox from '../PlanProToolbox'

interface ExportTileData {
  center: number[]
  tileExtent: number[]
  sheetCutFeature: Feature<Geometry>
  // The part of tile polygon, which not overlapp sheetcut polygon
  outsidePolygonFeature: Feature<Geometry>[]
}

interface RotateData {
  rad: number
  anchor: number[]
}

/**
 * Control to export the map to a PDF file.
 *
 * @author Stuecker
 */
export default class ExportControl extends Control {
  // Size of the Paper (A3)
  private static readonly PAPER_SIZE = [420, 297]
  private static readonly OUTSIDE_POLYGON_FEATURE = 'outsidepolygon'
  private static readonly OUTSIDE_POLYGON_COLOR = [0, 0, 255]
  private static readonly COLOR_VALUE_TOLERANCE = 100
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
    const sheetCutFeatures = this.getSheetcutFeatures()
    if (PlanProToolbox.inPPT()) {
      PlanProToolbox.exportSiteplan((folder: string | null) => {
        if (folder === null)
          return

        this.exportSiteplan(sheetCutFeatures ?? [])
      }, sheetCutFeatures?.length?.toString() ?? '0')
    } else {
      this.exportSiteplan(sheetCutFeatures ?? [])
    }
  }

  private getSheetcutFeatures (): Feature<Geometry>[] | undefined {
    const layoutInfoLayer = store.state.featureLayers.find(layer => layer.getLayerType() === FeatureLayerType.SheetCut)
    return layoutInfoLayer?.getFeaturesByType(FeatureType.SheetCut)
  }

  private async exportSiteplan (sheetCutFeatures: Feature<Geometry>[]) {
    this.lockMapDuringExport(true)
    const result: (HTMLCanvasElement | undefined)[] = []
    if (sheetCutFeatures.length !== 0) {
      const originalRotation = this.map.getView().getRotation()
      const originalZoomLvl = this.map.getView().getZoom()
      const originalViewCenter = this.map.getView().getCenter()
      const visibleLayers = store.state.featureLayers.filter(layer => layer.getVisible())
      visibleLayers.forEach(layer => layer.setVisible(false))
      setMapScale(this.map.getView(), 1000)
      this.map.getView().setRotation(0)
      const resolution = this.map.getView().getResolution()

      const exportCanvases = await this.getSiteplanSheetcutExportCanvas(
        sheetCutFeatures,
        visibleLayers,
        resolution ?? 1
      )

      visibleLayers.forEach(layer => layer.setVisible(true))
      this.map.getView().setRotation(originalRotation)
      this.map.getView().setZoom(originalZoomLvl ?? 10)
      this.map.getView().setCenter(originalViewCenter)

      result.push(...exportCanvases.map(canvas => this.exportCanvas(canvas)).filter(canvas => canvas !== null))
    } else {
      const currentCanvas = await this.getCurrentCanvas()
      result.push(currentCanvas)
    }

    const link = document.createElement('a')
    result.filter(c => c !== undefined).forEach((c, index) => {
      link.setAttribute('download', 'siteplan' + (index == 0 ? '' : index))
      if (link) {
        link.href = c.toDataURL()
        link.click()
      }
    })
    this.lockMapDuringExport(false)
  }

  private async getCurrentCanvas () {
    const viewportSize = this.map.getSize()
    if (!viewportSize) {
      return
    }

    const canvas = document.createElement('canvas')
    canvas.width = viewportSize[0]
    canvas.height = viewportSize[1]

    const context = canvas.getContext('2d')
    if (context == null) {
      return
    }

    context.fillStyle = 'white'
    context.fillRect(0, 0, canvas.width, canvas.height)
    const canvasLayers = document.querySelectorAll('.ol-layer canvas')
    canvasLayers.forEach(layer => {
      const mapCanvas = layer as HTMLCanvasElement
      if (mapCanvas.width < 0) {
        return
      }

      const matrix = this.getMapTransformMatrix(mapCanvas)
      context.transform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5])

      context.drawImage(mapCanvas, 0, 0)
      context.setTransform(1, 0, 0, 1, 0 ,0)
    })
    await new Promise(res => setTimeout(res, 2000))
    return canvas
  }

  private async getSiteplanSheetcutExportCanvas (
    sheetCutFeatures: Feature<Geometry>[],
    visibleLayers: NamedFeatureLayer[],
    resolution: number
  ) {
    const result: HTMLCanvasElement[] = []
    for (const feature of sheetCutFeatures) {
      const feautreData = getFeatureData(feature) as SheetCutFeatureData
      const directionLineCoords = feautreData.directionLine.getCoordinates()
      const directionLineAngle = angle(directionLineCoords[1], directionLineCoords[0])
      const sheetCutGeometry = feature.getGeometry()
      const anchor = directionLineCoords[1]
      if (!sheetCutGeometry) {
        return []
      }

      // To fit the size of the sheet cut, the sheet cut was rotated
      // to align with the North Pole and then split to match the size of the viewport.
      const rotation = toRad(90) - directionLineAngle
      const rotatedPolygon = sheetCutGeometry.clone()
      rotatedPolygon.rotate(rotation, anchor)
      const exportTilesData = this.getExportTilesData(
        feature,
      rotatedPolygon as Polygon
      )
      const tileCanvas = await this.createTileCanvas(
        exportTilesData,
        visibleLayers,
        {
          rad :directionLineAngle,
          anchor
        }
      )
      const exportCanvas = this.resizeTilesCanvas(
        tileCanvas,
        rotatedPolygon as Polygon,
        resolution ?? 1
      )
      if (!exportCanvas) {
        return []
      }

      result.push(exportCanvas)
    }
    return result
  }

  /**
   * Split the sheet cut into individual tiles that match
   * the viewport size at a scale of 1:1000,
   * and create a feature to visualize the portion of
   * the viewport area that lies outside the sheet cut polygon.
   *
   * @param sheetCutFeature the sheet cut feature
   * @param exportPolygon the sheetcut polygon, which algin with the nord pole
   * @returns {@type ExportTileData}
   */
  private getExportTilesData (
    sheetCutFeature: Feature<Geometry>,
    exportPolygon: Polygon
  ) : ExportTileData[] {
    const viewPortSize = this.getViewportSizePx()
    if (!viewPortSize) {
      return []
    }

    const featurePolygonCoordinates = exportPolygon.getCoordinates()[0]
    featurePolygonCoordinates.push(featurePolygonCoordinates[0])
    const featureTurfPolygon = turf.polygon([featurePolygonCoordinates])
    const [minX, minY, maxX, maxY] = exportPolygon.getExtent()
    const tilesScreenShotData: ExportTileData[] = []
    for (let tileX = minX; tileX < maxX; tileX += viewPortSize.width) {
      for (let tileY = minY; tileY < maxY; tileY += viewPortSize.height) {
        const tilePolygonExtent = [tileX, tileY, tileX + viewPortSize.width, tileY + viewPortSize.height]
        const tileTurfPolygon = turf.polygon([
          [
            getTopLeft(tilePolygonExtent),
            getTopRight(tilePolygonExtent),
            getBottomRight(tilePolygonExtent),
            getBottomLeft(tilePolygonExtent),
            getTopLeft(tilePolygonExtent)
          ]
        ])
        // Determine the portion of the viewport area that intersect the sheet cut polygon
        const intersectionPolygon = turf.intersect(turf.featureCollection([tileTurfPolygon, featureTurfPolygon]))
        const screenShotData: ExportTileData = {
          center: getCenter(tilePolygonExtent),
          tileExtent: tilePolygonExtent,
          outsidePolygonFeature: [],
          sheetCutFeature
        }
        tilesScreenShotData.push(screenShotData)
        if (intersectionPolygon == null) {
          screenShotData.outsidePolygonFeature.push(this.createOutsidePolygonFeature(tileTurfPolygon.geometry))
          continue
        }

        // Determine the portion of the viewport area
        // that lies outside the sheet cut polygon
        const outsidePolygon = turf.difference(turf.featureCollection([tileTurfPolygon, intersectionPolygon]))
        if (outsidePolygon == null) {
          continue
        }

        screenShotData.outsidePolygonFeature.push(this.createOutsidePolygonFeature(outsidePolygon.geometry))
      }
    }
    return tilesScreenShotData
  }

  private getViewportSizePx () {
    const viewportSize = this.map.getSize()
    if (!viewportSize) {
      return undefined
    }

    const resolution = this.map.getView().getResolution() ?? 1
    return {
      width: viewportSize[0] * resolution,
      height: viewportSize[1] * resolution
    }
  }

  private createOutsidePolygonFeature (polygon: GeoJSONMultiPolygon|GeoJSONPolygon) {
    let geometry = null
    if (polygon.type === 'MultiPolygon') {
      geometry = new MultiPolygon(polygon.coordinates as GeoJSONPosition[][][])
    } else {
      geometry = new Polygon(polygon.coordinates as GeoJSONPosition[][])
    }

    const outsidePolygonFeature = new Feature<Geometry>({
      name: ExportControl.OUTSIDE_POLYGON_FEATURE,
      geometry
    })

    const [r, g, b] = ExportControl.OUTSIDE_POLYGON_COLOR
    // Color the portion of
    // the viewport area that lies outside the sheet cut polygon.
    outsidePolygonFeature.setStyle(new Style({
      fill: new Fill({
        color: `rgb(${r}, ${g}, ${b})`
      })
    }))
    return outsidePolygonFeature
  }

  private async createTileCanvas (
    tilesdata: ExportTileData[],
    visibleLayers: NamedFeatureLayer[],
    rotationData?: RotateData
  ) {
    const viewsize = this.map.getSize()
    if (!viewsize) {
      return
    }

    const horizontalRotate: RotateData | undefined = rotationData ? {
      rad: rotationData.rad - toRad(90),
      anchor: rotationData.anchor
    } : undefined
    const resolution = this.map.getView().getResolution() as number
    this.map.getView().setRotation(horizontalRotate ? horizontalRotate.rad : 0)
    const sleep = (ms: number) => new Promise(res => setTimeout(res, ms))
    const canvas = document.createElement('canvas')
    const tileTotalSize = this.getTileTotalSize(tilesdata)
    canvas.width = tileTotalSize.width
    canvas.height = tileTotalSize.height
    const context = canvas?.getContext('2d')
    if (context == null ){
      return
    }

    context.fillStyle = 'white'
    context.fillRect(0, 0, canvas.width, canvas.height)
    for (const tileData of tilesdata) {
      const [tileMinX, tileMinY, tileMaxX, tileMaxY] = tileData.tileExtent
      const rotatedCenter = horizontalRotate
        ? pointRotate(tileData.center, toDeg(horizontalRotate.rad), horizontalRotate.anchor)
        : tileData.center
      const x = (tileMinX - tileTotalSize.minX) / resolution
      const y = (tileTotalSize.maxY - tileMaxY) / resolution
      const tileLayer = this.getTileLayer(
        tileData,
        visibleLayers,
        horizontalRotate
      )
      if (tileLayer == null) {
        continue
      }

      this.map.getLayers().push(tileLayer)
      this.map.getView().setCenter(rotatedCenter)

      this.map.once('rendercomplete', () => {
        const canvasLayers = document.querySelectorAll('.ol-layer canvas')
        canvasLayers.forEach(layer => this.drawTileLayer(layer as HTMLCanvasElement, canvas, x, y, viewsize))
      })
      await sleep(2000)
      this.map.getLayers().pop()
    }

    return canvas
  }

  private getTileTotalSize (tileDatas: ExportTileData[]) {
    const resolution = this.map.getView().getResolution() ?? 1
    const bottomLeftPoints = tileDatas.map(data => getBottomLeft(data.tileExtent))
    const topRightPoints = tileDatas.map(data => getTopRight(data.tileExtent))
    const minX = Math.min(...bottomLeftPoints.map(point => point[0]))
    const minY = Math.min(...bottomLeftPoints.map(point => point[1]))
    const maxX  = Math.max(...topRightPoints.map(point => point[0]))
    const maxY  = Math.max(...topRightPoints.map(point => point[1]))
    return {
      width: (maxX - minX) / resolution,
      height: (maxY - minY) / resolution,
      maxX,
      minX,
      maxY,
      minY
    }
  }

  private getTileLayer (
    tileData: ExportTileData,
    visbleLayer: NamedFeatureLayer[],
    rotateData?: RotateData
  ): NamedFeatureLayer | null{
    const featuresInsideSheetCut = visbleLayer
      .filter(layer =>
        layer.getLayerType() !== FeatureLayerType.Collision &&
        layer.getLayerType() !== FeatureLayerType.SheetCut)
      .flatMap(layer => layer.getSource()?.getFeatures())
      .filter(feature => feature !== undefined && feature !== null)
      .filter(feature => isGeometryIntersection(tileData.sheetCutFeature.getGeometry(), feature.getGeometry()))
    if (featuresInsideSheetCut.length == 0) {
      return null
    }

    tileData.outsidePolygonFeature.forEach(feature => {
      if (rotateData) {
        feature.getGeometry()?.rotate(rotateData.rad, rotateData.anchor)
      }

      featuresInsideSheetCut.push(feature)
    })
    const tileLayer = new NamedFeatureLayer(this.map.getLayers().getLength(), FeatureLayerType.SheetCut)
    tileLayer.getSource()?.addFeatures(featuresInsideSheetCut)
    return tileLayer
  }

  private drawTileLayer (
    mapLayer: HTMLCanvasElement,
    canvas: HTMLCanvasElement,
    x: number,
    y: number,
    viewsize: number[]
  ) {
    if (mapLayer.width <= 0) {
      return
    }

    const context = canvas.getContext('2d')
    if (context === null) {
      return
    }

    context.clearRect(x, y, viewsize[0], viewsize[1])
    context.fillStyle = 'white'
    context.fillRect(x, y, viewsize[0], viewsize[1])

    const matrix = this.getMapTransformMatrix(mapLayer)

    // Apply the transform to the export map context
    context.setTransform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5])

    const backgroundColor = (mapLayer.parentNode as HTMLHtmlElement | null)?.style.backgroundColor
    if (backgroundColor) {
      context.fillStyle = backgroundColor
      context.fillRect(0, 0, mapLayer.width, mapLayer.height)
    }

    const xOffset = x * matrix[0] + y * matrix[1]
    const yOffset = x * matrix[2] + y * matrix[3]
    context.drawImage(mapLayer, xOffset, yOffset)
    // Reset transform
    context.setTransform(1, 0, 0, 1, 0 ,0)
  }

  private getMapTransformMatrix (mapCanvas: HTMLCanvasElement) {
    let result = [1, 0, 0, 1, 0, 0]
    const transform = mapCanvas.style.transform
    if (transform) {
      // Get the transform parameters from the style's transform matrix
      const macht = transform.match(/^matrix\(([^\(]*)\)$/)
      if (macht && macht[1] !== null)
        result = macht[1].split(',').map(Number)
    }

    return result
  }

  /**
   * Resize tiles canvas to fit sheet cut polygon
   * @param tilesCanvas the canvas
   * @param sheetCutPolygon the sheet cut polygon, which rotation to the nord pole
   * @param resolution the map resolution
   */
  private resizeTilesCanvas (
    tilesCanvas: HTMLCanvasElement | undefined,
    sheetCutPolygon: Polygon,
    resolution: number
  ): HTMLCanvasElement | undefined {
    const exportCanvas = document.createElement('canvas')
    // Currently the polygon is rotation to the nord pole.
    // To export sheet cut in vertical, should change the width/height of polygon
    exportCanvas.width = getHeight(sheetCutPolygon.getExtent()) / resolution
    exportCanvas.height = getWidth(sheetCutPolygon.getExtent()) / resolution
    const exportCtx = exportCanvas.getContext('2d')
    if (!exportCtx || !tilesCanvas) {
      return
    }

    exportCtx.fillStyle = 'white'
    exportCtx.fillRect(0,0, exportCanvas.width, exportCanvas.height)

    // Rotate the tile canvas to 90
    exportCtx.rotate(-Math.PI / 2)
    const yOffset = exportCanvas.width - tilesCanvas.height

    exportCtx.drawImage(tilesCanvas, -exportCanvas.height, yOffset)
    return exportCanvas
  }

  private exportCanvas (canvas: HTMLCanvasElement) : HTMLCanvasElement | null {
    const ctx = canvas.getContext('2d')
    if (!ctx || canvas.width <= 0) {
      return null
    }

    const [r, g, b] = ExportControl.OUTSIDE_POLYGON_COLOR
    const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height)
    const compareColorWert = (actual: number, expect: number) =>
      Math.abs(actual - expect) <= ExportControl.COLOR_VALUE_TOLERANCE
    const data = imageData.data
    for (let i = 0; i < data.length; i += 4) {
      const [red, green, blue] = [data[i], data[i + 1], data[i + 2]]
      if (compareColorWert(red, r) && compareColorWert(green, g) && compareColorWert(blue, b)) {
        data[i + 3] = 0
      }
    }

    ctx.putImageData(imageData,0 ,0)
    return canvas
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
}
