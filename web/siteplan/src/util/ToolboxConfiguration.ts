/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

/**
 * Present configuration values.
 */
export interface ToolboxConfiguration {
  mapSources: string
  mapboxApiKey: string
  hereApiKey: string
  dop20ApiKey: string
  dop20InternUrl: string
  developmentMode: boolean
  lodScale: number
  exportDPI: number
  trackWidth: string
  trackWidthInterval: string
  baseZoomLevel: number
  defaultCollisionsEnabled: boolean
  defaultSheetCutCRS: string
}
