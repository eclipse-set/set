/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { checkInstance } from '@/util/ObjectExtension'
import { defaultPositionObj, Position } from './Position'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'

export enum TrackCloseType {
  FrictionBufferStop = 'Bremsprellbock',
  FixedBufferStop = 'Festprellbock',
  HeadRamp = 'Kopframpe',
  ThresholCross = 'Schwellenkreuz',
  InfrastructureBorder = 'Infrastrukturgrenze',
  TurnTable = 'Drehscheibe',
  SlidingStage = 'Schiebebuehne',
  FerryDock = 'Faehranleger',
  Other = 'sonstige'
}

export default interface TrackClose extends SiteplanObject{
  guid: string
  trackCloseType: TrackCloseType
  position: Position
}

export function defaultTrackCloseObj (): TrackClose {
  return {
    guid: '0',
    trackCloseType: TrackCloseType.FixedBufferStop,
    position: defaultPositionObj(),
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceofTrackClose (obj: any): boolean {
  return checkInstance(obj, defaultTrackCloseObj())
}
