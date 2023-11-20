/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { store } from '@/store'
import PlanProToolbox from './PlanProToolbox'
import { ToolboxConfiguration } from './ToolboxConfiguration'

/**
 * Tests for PlanProToolbox.
 */
export default class PlanProToolboxTest {
  result: ToolboxConfiguration | null = null

  static run () : void {
    try {
      if (PlanProToolbox.inPPT()) {
        const test = new PlanProToolboxTest()
        test.testGetToolboxConfiguration()
        // eslint-disable-next-line no-console
        console.log('PlanProToolboxTest passed.')
      } else {
        // eslint-disable-next-line no-console
        console.log('PlanProToolboxTest skipped outside of PPT.')
      }
    } catch (e: unknown) {
      console.error('PlanProToolboxTest failed.')
    }
  }

  testGetToolboxConfiguration () : void {
    this.whenGettingToolboxConfiguration()
    this.thenExpectMapSources('BKG_TOPPLUS|BKG_SENT')
    this.thenExpectMapboxApiKey('')
    this.thenExpectHereApiKey('')
    this.thenExpectDop20ApiKey('')
    this.thenExpectDevelopmentMode(true)
    this.thenExpectLodScale(10000)
  }

  whenGettingToolboxConfiguration () : void {
    this.result = store.state.planproToolboxConfiguration
  }

  thenExpectMapSources (expected : string) : void {
    this.assertEquals(expected, this.result?.mapSources)
  }

  thenExpectMapboxApiKey (expected : string) : void {
    this.assertEquals(expected, this.result?.mapboxApiKey)
  }

  thenExpectHereApiKey (expected : string) : void {
    this.assertEquals(expected, this.result?.hereApiKey)
  }

  thenExpectDop20ApiKey (expected : string) : void {
    this.assertEquals(expected, this.result?.dop20ApiKey)
  }

  thenExpectDevelopmentMode (expected : boolean) : void {
    this.assertEquals(expected, this.result?.developmentMode)
  }

  thenExpectLodScale (expected : number) : void {
    this.assertEquals(expected, this.result?.lodScale)
  }

  assertEquals<T> (expected: T, actual: T | undefined) : void {
    if (expected !== actual) {
      this.error('expected "' + expected + '" bus was "' + actual + '"')
    }
  }

  error (message: string) : void {
    console.error(message)
    throw new Error(message)
  }
}
