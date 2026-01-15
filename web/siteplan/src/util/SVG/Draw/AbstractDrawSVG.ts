/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { Label } from '@/model/Label'
import { KMMarker } from '@/model/Route'
import { ISvgElement } from '@/model/SvgElement'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import SvgCatalogService from '../../../service/SvgCatalogService'

/**
 * Abstract class create Svg for Feature
 *
 * @author Truong
 */
export default abstract class AbstractDrawSVG {
  protected catalogService: SvgCatalogService
  constructor (catalogService: SvgCatalogService) {
    this.catalogService = catalogService
  }

  /**
   * Create a Svg for a feature. Return null, if this Drawer cannot draw that feature.
   * @param data feature data
   * @param label {@link Label}
   */
  public abstract drawSVG<T extends object>(data: T, label?: Label): ISvgElement | null

  public getSvgFromCatalog<T extends object> (data: T): ISvgElement | null {
    return this.catalogService.getObjSvg(data, this.getFeatureType())
  }

  /**
   * Create a Svg for a feature, that a part of another feature
   * @param data feature data
   * @param featureType {@link FeatureType}
   * @param label {@link Label}
   * @returns svg
   */
  public drawOtherSVG<T extends object> (data: T, featureType: FeatureType, label?: Label): ISvgElement | null {
    switch (featureType) {
      case FeatureType.SignalRouteMarker:
        return label ? SvgDraw.getRouteMarker(label) : SvgDraw.getErrorSVG()
      case FeatureType.TrackSectionMarker:
        return SvgDraw.getTrackSectionMarker()
      case FeatureType.TrackDesignationMarker:
        return label ? SvgDraw.getTrackMarker(label) : SvgDraw.getErrorSVG()
      case FeatureType.RouteMarker:
        return label ? SvgDraw.getKMMarker(data as KMMarker, label) : SvgDraw.getErrorSVG()
      default:
        return null
    }
  }

  /**
   * Return {@link FeatureType}
   */
  public abstract getFeatureType() : FeatureType
}
