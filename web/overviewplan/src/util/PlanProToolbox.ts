/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { store, TableType } from '@/store'

declare function planproJumpToTextView(guid: string): void;
declare function planproGetSessionState(): TableType

// This callback function is called from PPT
declare global {
  interface Window {
    planproSelectFolderDialogCallback: (key: string, folder: string | null) => void
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

  static jumpToTextView (guid: string): void {
    if (!this.inPPT()) {
      console.warn('PlanProToolbox.jumpToTextView called outside PPT')
      return
    }

    planproJumpToTextView(guid)
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
}

/**
 * Register select Signal function
 */
window.planproNewTableType = PlanProToolbox.updateSessionState
