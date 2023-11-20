/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { defaultLabelObj, Label } from './Label'
import { Coordinate, defaultCoordinateObj, defaultPositionObj, Position } from './Position'
import { defaultRouteLocationObj, RouteObject } from './RouteObject'
import { LeftRight } from './SiteplanModel'

export enum TurnoutOperatingMode {
    Undefined = 'Undefined',
    ElectricRemote = 'ElectricRemote',
    ElectricLocal = 'ElectricLocal',
    MechanicalRemote = 'MechanicalRemote',
    MechanicalLocal = 'MechanicalLocal',
    NonOperational = 'NonOperational',
    Trailable = 'Trailable',
    Other = 'Other',
    DeadLeft = 'DeadLeft',
    DeadRight = 'DeadRight'
}

export interface TrackSwitchLeg {
  connection: LeftRight
  coordinates: Coordinate[]
}

export function defaultTrackSwitchLegObj (): TrackSwitchLeg {
  return {
    connection: LeftRight.LEFT,
    coordinates: [defaultCoordinateObj()]
  }
}

export default interface TrackSwitchComponent extends RouteObject
{
    guid: string
    pointDetectorCount: number
    start: Position
    labelPosition: Position
    label: Label
    operatingMode: TurnoutOperatingMode
    mainLeg: TrackSwitchLeg
    sideLeg: TrackSwitchLeg
    preferredLocation: LeftRight
}

export function defaultTrackSwitchComponentObj (): TrackSwitchComponent {
  return {
    guid: 'abc',
    label: defaultLabelObj(),
    labelPosition: defaultPositionObj(),
    mainLeg: defaultTrackSwitchLegObj(),
    operatingMode: TurnoutOperatingMode.DeadLeft,
    pointDetectorCount: 123,
    preferredLocation: LeftRight.LEFT,
    routeLocations: [defaultRouteLocationObj()],
    sideLeg: defaultTrackSwitchLegObj(),
    start: defaultPositionObj()
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackSwitchComponentObj (object: any): boolean {
  return checkInstance(object, defaultTrackSwitchComponentObj())
}
