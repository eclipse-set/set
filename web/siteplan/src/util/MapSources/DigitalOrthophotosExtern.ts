/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { MapSourceType } from '../MapSource'
import Configuration from '../Configuration'
import DigitalOrthophotos from './DigitalOrthophotos'

/**
 * Map source for digital orthophotos with
 * a ground sample distance of 20cm offered by
 * the Bundesamt für Kartographie und Geodäsie (BKG)
 *
 * Requires an API key: for access from everywhere
 *
 * @author Peters
 */
export default class DigitalOrthophotosExtern extends DigitalOrthophotos {
  constructor () {
    super('DOP20', 'DOP-20', MapSourceType.Satellite, Configuration.getToolboxConfiguration().dop20ApiKey)
  }

  override getURL (): string {
    return 'https://sg.geodatenzentrum.de/wms_dop__' + this.getAPIKey()
  }
}
