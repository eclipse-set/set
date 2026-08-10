/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { DBRef } from '@/model/Position'
import { store, TableType } from '@/store'

declare function planproSelectTableRow(guid: string): void;
declare function planproJumpToTextView(guid: string): void;
declare function planproSelectFolderDialog(key: string): void;
declare function planproGetSessionState(): TableType
declare function planproChangeLayoutCRS(crs: DBRef): void;
declare function planproSiteplanLoadingState(state: boolean): void;
declare function planproSiteplanExport(key: string, sheetcutCount: string, ppm: string): void;

// This callback function is called from PPT
declare global {
  interface Window {
    planproSelectFolderDialogCallback: (key: string, folder: string | null) => void
    planproJumpToElement: (guid: string) => void
    planproNewTableType: (state: TableType) => void
  }
}

/**
 * Interface for folder selection dialog callbacks
 */
interface SelectFolderCallback {
  (folder: string | null): void
}

/**
 * Class to provide additional functions to communicate with the PlanPro Toolbox
 *
 * If not running within PPT, this class falls back to logging a warning message
 *
 * @author Stuecker
 */
export default abstract class PlanProToolbox {
  private static planproSelectFolderDialogCallbacks = new Map<string, SelectFolderCallback>()
  /**
   * Returns true when called from within a PPT context
   * @returns true if executing witthin PPT
   */
  static inPPT (): boolean {
    return typeof (planproGetSessionState) !== 'undefined'
  }

  /**
   * Attempts to select a row in all table which contains a
   * specific object determined by its guid
   *
   * @param guid the guid of the object
   */
  static selectTableRow (guid: string): void {
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.selectTableRow called outside PPT')
      return
    }

    planproSelectTableRow(guid)
  }

  static jumpToTextView (guid: string): void {
    console.log('Jump to text view called for guid:', guid)
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.jumpToTextView called outside PPT')
      return
    }

    planproJumpToTextView(guid)
  }

  static changeLayoutCRS (crs: DBRef): void {
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.changelayoutCRS called outside PPT')
      return
    }

    planproChangeLayoutCRS(crs)
  }

  /**
   * Returns the session state
   */
  static getSessionState (): TableType {
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.getSessionState called outside PPT')
      return store.state.sessionState ?? TableType.DIFF
    }

    return planproGetSessionState()
  }

  /**
   * Callback for updating the session state
   */
  public static updateSessionState (state: TableType): void {
    store.commit('setSessionState', state ?? PlanProToolbox.getSessionState())
  }

  /**
   * Opens a dialog for selecting a folder.
   */
  static selectFolderDialog (callback: SelectFolderCallback): void {
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.planproSelectFolderDialog called outside PPT')
      return
    }

    // Generate a random key
    const key = Math.random.toString()
    PlanProToolbox.planproSelectFolderDialogCallbacks.set(key, callback)

    // Call the dialog
    planproSelectFolderDialog(key)
  }

  /**
   * Store Guid of selected Signal
   * @param guid signal guid
   */
  public static jumpToElement (guid: string): void {
    let intervalId = 0
    const jumpEvent = () => {
      if (!store.state.loading) {
        store.commit('selectFeature', guid)
        clearInterval(intervalId)
      }
    }
    intervalId = setInterval(jumpEvent, 200)
  }

  /**
   * Registers a callback for selectFolderDialog
   */
  public static selectFolderDialogCallback (key: string, folder: string | null): void {
    const callback = PlanProToolbox.planproSelectFolderDialogCallbacks.get(key)
    if (!callback) {
      console.error('SelectFolderDialog callback called with incorrect key')
      return
    }

    PlanProToolbox.planproSelectFolderDialogCallbacks.delete(key)
    callback(folder)
  }

  public static setSiteplanLoadingState (state: boolean): void {
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.planproSelectFolderDialog called outside PPT')
      return
    }

    planproSiteplanLoadingState(state)
  }

  public static exportSiteplan (callback: SelectFolderCallback, sheetcutCount: string, ppm: string): void {
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.planproSelectFolderDialog called outside PPT')
      return
    }

    // Generate a random key
    const key = Math.random.toString()
    PlanProToolbox.planproSelectFolderDialogCallbacks.set(key, callback)
    planproSiteplanExport(key, sheetcutCount, ppm)
  }
}

/**
 * Register select Signal function
 */
window.planproJumpToElement = PlanProToolbox.jumpToElement
window.planproSelectFolderDialogCallback = PlanProToolbox.selectFolderDialogCallback
window.planproNewTableType = PlanProToolbox.updateSessionState
