/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { Label } from './Label'
import { defaultPositionObj, Position } from './Position'
import { defaultRouteLocationObj, RouteObject } from './RouteObject'
import { defaultSignalScreenObj, SignalScreen } from './SignalScreen'

export enum SignalRole
{
    Main = 'Main',
    Pre = 'Pre',
    MultiSection = 'MultiSection',
    None = 'None',
    TrainCover = 'TrainCover',
    Lock = 'Lock'
}

export enum SignalSystem
{
    HL = 'HL',
    HV = 'HV',
    KS = 'KS',
    SV = 'SV',
    None = 'None'
}

export enum MountDirection
{
    None = 'None',
    Up = 'Up',
    Down = 'Down'
}

export enum RahmenArt {
    Bezeichnungsschild = 'Bezeichnungsschild',
    Blechtafel = 'Blechtafel',
    Keramikkoerper = 'Keramikkoerper',
    Schirm = 'Schirm',
    Zusatzanzeiger = 'Zusatzanzeiger',
    sonstige = 'sonstige'
}

export enum SignalPriority {
    Main = 0,
    Pre = 1,
    TrainCover = 2,
    StandingAlone_Ne14 = 3,
    Lock = 4,
    StandingAlone = 5
}

export enum SignalPart {
    Schirm = 'schirm',
    Mast = 'mast',
    Additive = 'additive',
    RouteMarker = 'marker',
    Label = 'label'
}

export interface Signal extends RouteObject
{
    guid: string
    role: SignalRole
    system: SignalSystem
    screen: SignalScreen[]
    label?: Label
    mountPosition: Position
    lateralDistance: number[]
    signalDirection: string
}

export function defaultSignalObj (): Signal {
  return {
    guid: '0',
    role: SignalRole.Lock,
    system: SignalSystem.HL,
    screen: [defaultSignalScreenObj()],
    mountPosition: defaultPositionObj(),
    lateralDistance: [100],
    signalDirection: 'left',
    routeLocations: [defaultRouteLocationObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfSignal (object: any): boolean {
  return checkInstance(object, defaultSignalObj())
}
