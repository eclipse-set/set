/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { checkInstance } from '@/util/ObjectExtension'

export interface SignalScreen
{
    screen: string
    switched: boolean
    rahmenArt: string
}

export function defaultSignalScreenObj () : SignalScreen {
  return {
    screen: 'HL',
    switched: true,
    rahmenArt: 'HL'
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfSignalScreen (object: any): boolean {
  return checkInstance(object, defaultSignalScreenObj())
}
