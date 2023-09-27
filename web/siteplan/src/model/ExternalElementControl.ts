/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { checkInstance } from '@/util/ObjectExtension'
import { ControlStationType } from './ControlStation'
import { Label } from './Label'
import { defaultPositionObj, Position } from './Position'
import { defaultRouteLocationObj, RouteObject } from './RouteObject'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'

export enum ExternalElementControlArt {
  FeAk = 'FeAk',
  GFK = 'GFK',
  Gleisfreimelde_Innenanlage = 'Gleisfreimelde_Innenanlage',
  ESTW_A = 'ESTW_A',
  Objektcontroller = 'Objektcontroller',
  Relaisstellwerk = 'Relaisstellwerk',
  sonstige = 'sonstige',
  virtuelle_Aussenelementansteuerung= 'virtuelle_Aussenelementansteuerung'
}

export interface ExternalElementControl extends RouteObject, SiteplanObject {
  guid: string
  position: Position
  label?: Label
  elementType: ExternalElementControlArt
  controlStation: ControlStationType
}

export function defaultExternalElementControlObj (): ExternalElementControl {
  return {
    guid: '0',
    position: defaultPositionObj(),
    elementType: ExternalElementControlArt.ESTW_A,
    routeLocations: [defaultRouteLocationObj()],
    objectColors: [defaultObjectColorObj()],
    controlStation: ControlStationType.DefaultControl
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfExternalElementControl (obj: any): boolean {
  return checkInstance(obj, defaultExternalElementControlObj())
}

