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
 * Requires no API key: only for access from within the internal DB network
 *
 * @author Peters
 */
export default class DigitalOrthophotosIntern extends DigitalOrthophotos {
  constructor () {
    super('DOP20_INTERN', 'DOP-20 (intern)', MapSourceType.Satellite)
  }

  override getURL (): string {
    return Configuration.getToolboxConfiguration().dop20InternUrl
  }
}
