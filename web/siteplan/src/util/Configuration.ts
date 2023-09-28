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

  public static getHereMapsURL (): string {
    // Return the DB API for Here Maps
    return 'https://gateway.businesshub.deutschebahn.com/here-maps/trial/{mapType}/maptile/newest/{mapScheme}/{z}/{x}/{y}/256/png8'
  }

  public static developmentMode (): boolean {
    return Configuration.getToolboxConfiguration().developmentMode
  }

  public static getLodScale (): number {
    return Configuration.getToolboxConfiguration().lodScale
  }

  public static getExportDPI (): number {
    return Configuration.getToolboxConfiguration().exportDPI
  }

  public static getTrackWidth (): TrackWidth {
    const trackWidth = Configuration.getToolboxConfiguration().trackWidth.split('|')
    const trackWidthIntervall: Interval[] = []
    for (const intervall of Configuration.getToolboxConfiguration().trackWidthInterval.split('|')) {
      const split = intervall.split(',')
      trackWidthIntervall.push({
        min: parseFloat(split[0]),
        max: parseFloat(split[1])
      })
    }
    return {
      main: parseFloat(trackWidth[0]),
      intervall_main: trackWidthIntervall[0],
      side: parseFloat(trackWidth[1]),
      intervall_side: trackWidthIntervall[1],
      other: parseFloat(trackWidth[2]),
      intervall_other: trackWidthIntervall[2],
      outline: parseFloat(trackWidth[3]),
      intervall_outline: trackWidthIntervall[3]
    }
  }

  public static getSheetCutCRS () :DBRef {
    return Object
      .values(DBRef)
      .find(crs => crs === Configuration.getToolboxConfiguration().defaultSheetCutCRS) ?? DBRef.ER0
  }
}
