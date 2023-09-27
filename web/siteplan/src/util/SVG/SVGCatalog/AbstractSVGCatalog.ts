/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { ISvgElement } from '@/model/SvgElement'

export default abstract class AbstractSVGCatalog {
  protected catalog: Map<string, ISvgElement[]>
  constructor (catalog: Map<string, ISvgElement[]>) {
    this.catalog = catalog
  }

  /**
    * Get all SVGs within this catalog
    * @returns all ISvgElements within the catalog
    */
  protected getScreenCatalog (): ISvgElement[] | null {
    return this.catalog?.get(this.catalogName()) ?? null
  }

  /**
    * Looks up a svg in the catalog
    *
    * @param id the svg to find
    * @returns a ISvgElement for the svg or null
    */
  public getSVGFromCatalog (id: string): ISvgElement | null {
    return this.getScreenCatalog()?.find(ele => ele.id === id) ?? null
  }

  /**
    * Returns the catalog name
    * @return catalog name
    */
  public abstract catalogName(): string
}
