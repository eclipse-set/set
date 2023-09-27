/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Coordinate, defaultCoordinateObj } from './Position'
import SiteplanObject from './SiteplanObject'

export interface SheetCut extends SiteplanObject {
  label: string
  polygonDirection: Coordinate[]
  polygon: Coordinate[]
}

export function defaultSheetCutObj (): SheetCut {
  return {
    guid: '0',
    label: 'SheetCut',
    polygon: [defaultCoordinateObj()],
    polygonDirection: [defaultCoordinateObj()],
    objectColors: []
  }
}
export interface LayoutInfo extends SiteplanObject {
  label: string
  sheetsCut: SheetCut[]
}

export function defaultLayoutInfoObj () :LayoutInfo {
  return {
    guid: '0',
    label: 'LayoutInfo',
    sheetsCut: [defaultSheetCutObj()],
    objectColors: []
  }
}
