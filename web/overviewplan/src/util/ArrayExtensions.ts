/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

// Export the global namespace
export {}

declare global {
    interface Array<T> {
      groupBy(by: ((value: T) => number)) : Array<T>[]
    }
  }

/**
 * Groups elements from an array into new arrays according to a function
 *
 * @param by the function to group by
 * @returns a new array containing the grouped elements
 */
// eslint-disable-next-line no-extend-native
Array.prototype.groupBy = function (by: ((value: unknown) => number)) {
  return this.reduce((result, element) => {
    const layer = by(element)
    result[layer] = result[layer] ?? []
    result[layer].push(element)
    return result
  }, [])
}
