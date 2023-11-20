/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { ExternalElementControl, ExternalElementControlArt } from '@/model/ExternalElementControl'
import { OtherSVGCatalog } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'

export default class ExternalElementControlSVGCatalog extends AbstractSVGCatalog{
  public getExternalElementControlSvg (eec: ExternalElementControl) {
    switch (eec.elementType) {
      case ExternalElementControlArt.ESTW_A:
      case ExternalElementControlArt.Relaisstellwerk:
        return this.getSVGFromCatalog('electric_stellwerk')
      case ExternalElementControlArt.FeAk:
        return this.getSVGFromCatalog('feak_single')
      case ExternalElementControlArt.GFK:
      case ExternalElementControlArt.Gleisfreimelde_Innenanlage:
      case ExternalElementControlArt.Objektcontroller:
      case ExternalElementControlArt.virtuelle_Aussenelementansteuerung:
      case ExternalElementControlArt.sonstige:
        return this.getSVGFromCatalog('sonstige')
      default:
        return null
    }
  }

  public catalogName (): string {
    return OtherSVGCatalog.ExternalElementControl
  }
}
