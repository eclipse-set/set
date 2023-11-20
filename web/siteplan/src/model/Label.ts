import { checkInstance } from '@/util/ObjectExtension'

/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
export interface Label {
  color: number[]
  text: string
  orientationInverted: boolean
}

export function defaultLabelObj () : Label {
  return {
    color: [0, 0, 0],
    text: 'Label',
    orientationInverted: true
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfLabel (obj: any): boolean {
  return checkInstance(obj, defaultLabelObj)
}
