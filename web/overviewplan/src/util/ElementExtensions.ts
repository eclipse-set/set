/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

// Export the global namespace
export {}

declare global {
    interface Element {
      setTranslate(x: number, y: number): void
      setTranslateScale(x: number, y: number, xscale: number, yscale: number): void
      setPosition(x: number, y: number): void
    }
  }

/**
 * Sets a transform: translate(x, y) attribute
 *
 * @param x the x transform to use
 * @param y the y transform to use
 */
Element.prototype.setTranslate = function (x: number, y: number) {
  this.setAttribute('transform', `translate(${x}, ${y})`)
}

/**
 * Sets a transform: translate(x, y) scale(xscale, yscale) attribute
 *
 * @param x the x transform to use
 * @param y the y transform to use
 * @param xscale the x scale to use
 * @param yscale the y scale to use
 */
Element.prototype.setTranslateScale = function (x: number, y: number, xscale: number, yscale: number) {
  this.setAttribute('transform', `translate(${x}, ${y}) scale(${xscale}, ${yscale})`)
}

/**
 * Sets the x and y position of the element
 *
 * @param x the x position to use
 * @param y the y position to use
 */
Element.prototype.setPosition = function (x: number, y: number) {
  this.setAttribute('x', `${x}`)
  this.setAttribute('y', `${y}`)
}
