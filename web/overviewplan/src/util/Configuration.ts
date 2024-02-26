/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { store } from '@/store'
import PlanProToolbox from '@/util/PlanProToolbox'
import { ToolboxConfiguration } from './ToolboxConfiguration'
import { DBRef } from '@/model/Position'

export interface Interval {
  max: number
  min: number
}

export interface TrackWidth {
  main: number
  intervall_main: Interval
  side: number
  intervall_side: Interval
  other: number
  intervall_other: Interval
  outline: number
  intervall_outline: Interval
}
/**
 * Class to provide configuration parameters to the frontend application
 *
 * @author Stuecker
 */
export default abstract class Configuration {
  public static isMapSourceEnabled (mapSourceName: string): boolean {
    // If executing within PPT, use the configuration parameter
    if (PlanProToolbox.inPPT()) {
      const enabledSources = Configuration.getToolboxConfiguration().mapSources
      return enabledSources === '*' || enabledSources.split('|').includes(mapSourceName)
    }

    // If not executing in PPT, all map sources are enabled
    return true
  }

  public static getToolboxConfiguration (): ToolboxConfiguration {
    return store.state.planproToolboxConfiguration as ToolboxConfiguration
  }

  public static developmentMode (): boolean {
    return Configuration.getToolboxConfiguration().developmentMode
  }

  public static getLodScale (): number {
    return Configuration.getToolboxConfiguration().lodScale
  }
}
