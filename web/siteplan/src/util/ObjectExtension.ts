/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

/**
 * Convert property name  from object to string
 * @param x object
 * @param propFunction object property, that want to String convert
 * @returns property name in string
 */
export function getpropertypeName<T extends object> (
  x: T,
  propFunction: ((prop: {[Property in keyof T]: () => string }) => () => string)
): string {
  const res = {} as { [Property in keyof T]: () => string}
  Object.keys(x).forEach(k => {
    res[k as keyof T] = () => k
  })
  return propFunction(res)()
}

export function checkInstance<T extends object> (x: any, defaultObj: T): boolean {
  if (typeof x === typeof defaultObj &&
    (typeof x === 'string' || typeof x === 'number')) {
    return true
  }

  if (Array.isArray(x) && x.length > 0) {
    return checkInstance(x[0], defaultObj)
  }

  if (!x || x === 0 || typeof x !== 'object') {
    return false
  }

  return Object.entries(defaultObj).every(([k, v]) => {
    if (!(k in x)) {
      return false
    }

    if (typeof v !== typeof x[k]) {
      return false
    }

    if (Array.isArray(v) && Array.isArray(x[k]) &&
      v.length > 0 && x[k].length > 0) {
      return checkInstance(v[0], x[k][0])
    } else if (typeof v === 'object') {
      return checkInstance(v, x[k])
    }

    return true
  })
}

/**
 * Get the first value of @param propName.
 * When value of property have type string or number,
 * then don't define @param defaultObj
 * @param x the object
 * @param propName property Name
 * @param defaultObj default object of result value instance
 * @returns the value
 */
export function getFirstValue<T extends object> (x: any, propName: string, defaultObj?: T): T | T[] | undefined {
  const result = Object.entries(x).map(([k, v]) => {
    if (!v && v !== 0) {
      return null
    }

    if (k === propName &&
      !defaultObj && (typeof v === 'string' || typeof v === 'number')) {
      return v
    }

    if (defaultObj && checkInstance<T>(v, defaultObj)) {
      return v
    }

    if (typeof v === 'object') {
      return getFirstValue(v, propName, defaultObj)
    }

    return null
  })
    .filter(ele => ele !== null && ele !== undefined)[0]
  if (result === undefined) {
    return undefined
  }

  return Array.isArray(result) ? result as T[] : result as T
}

/**
 * Get all values of @param propName in object
 * @param x the object
 * @param propName property Name
 * @returns all values of this property in Object
 */
export function getAllValuesOf<T extends object> (
  x: any,
  propName: string,
  defaultObj?: T
): (T | string | number | null)[] {
  return Object.entries(x).map(([k, v]) => {
    if (!v && v !== 0) {
      return null
    }

    if (k === propName) {
      if (defaultObj && checkInstance(v, defaultObj)) {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        return v as any
      } else if ((typeof v === 'string') || (typeof v === 'number')) {
        return v
      }
    }

    if (typeof v === 'object') {
      if (!Array.isArray(v) && !(propName in v)) {
        return null
      } else {
        return getAllValuesOf<T>(v, propName)
      }
    }

    return null
  })
    .flat()
    .filter(ele => ele !== null)
}

/**
 * Check if two object are difference
 * @param x first object
 * @param y second object
 * @returns true, if two object are exsits difference
 */
export function compare (x: any, y: any): boolean {
  if (typeof x === typeof y && typeof x !== 'object') {
    return x !== y
  }

  if (Array.isArray(x) && Array.isArray(y)) {
    return compareArray(x, y)
  }

  return compareObject(x, y)
}

/**
 * Check difference two object (not array) with nested children
 * @param x first object
 * @param y second object
 * @returns true, if they are difference
 */
export function compareObject (x: any, y: any): boolean {
  return Object.keys(x).some(prop => {
    if (!Object.hasOwn(y, prop)) {
      return true
    }

    const xValue = x[prop]
    const yValue = y[prop]
    if (typeof xValue !== typeof yValue) {
      return true
    }

    return compare(xValue, yValue)
  })
}

/**
 * Check if two array are difference
 * @param x first array
 * @param y second array
 * @returns true, if they are difference
 */
export function compareArray (x: Array<any>, y: Array<any>): boolean {
  if (x.length !== y.length) {
    return true
  }

  if (x.length === 0) {
    return false
  }

  return x.every(xEle => {
    return y.every(yEle => {
      return Object.entries(xEle).every(([k, v]) => {
        return yEle[k] === undefined || compare(yEle[k], v)
      })
    })
  })
}
