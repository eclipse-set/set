/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { Label } from '@/model/Label'
import { MountDirection, SignalRole } from '@/model/Signal'
import { SignalMount, SignalMountType } from '@/model/SignalMount'
import { ISvgElement, MAX_BRIDGE_DIRECTION_OFFSET, SvgBridgeSignal, ZusatzSignal } from '@/model/SvgElement'
import { distance } from '@/util/Math'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import SvgDrawBridge, { SignalBridgePart } from '@/util/SVG/Draw/SvgDrawBridge'
import SvgDrawSingleSignal from '@/util/SVG/Draw/SvgDrawSingleSignal'
import AbstractDrawSVG from './AbstractDrawSVG'

/**
 * Draw SVG for {@link SignalMount}
 *
 * @author Truong
 */
export default class SvgDrawSignal extends AbstractDrawSVG {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement {
    const signalMount = data as SignalMount
    const isValidate = this.validateSignal(signalMount)
    if (!isValidate) {
      return SvgDraw.getErrorSVG()
    }

    if (this.isMultiSignal(signalMount)) {
      return this.getMultiSignalScreen(signalMount)
    }

    const signal = signalMount.attachedSignals[0]
    const mount = this.catalogService.getSignalMountSVGCatalog()
      .getSignalMountSVG(this.getMountType(signalMount))
    if (mount === null) {
      return SvgDraw.getErrorSVG()
    }

    const extra = this.getDirectionScreens(signalMount).map(screen => {
      const directionBoard = this.catalogService.getDirectionBoardSVGCatalog()
        .getDirectionBoard(signal, screen)
      if (directionBoard) {
        return new ZusatzSignal(
          directionBoard.id,
          directionBoard.content,
          directionBoard.anchor,
          directionBoard.nullpunkt,
          'top',
          directionBoard.boundingBox
        )
      }

      return null
    })
      .filter(ele => ele !== null) as ZusatzSignal[]
    const svg = this.catalogService.getSignalSVGCatalog().map(signalcatalog => {
      const screenSvg = signalcatalog.getSignalScreen(signal)
      if (screenSvg === null) {
        return null
      }

      return SvgDrawSingleSignal.drawSignal(screenSvg, mount, extra, signal.label)
    })
      .filter(ele => ele !== null)

    if (svg.length === 0) {
      return SvgDraw.getErrorSVG()
    }

    return svg[0] as ISvgElement
  }

  public validateSignal (signalMount: SignalMount) {
    if (signalMount.attachedSignals.length === 0) {
      console.warn('Cannot display signal mount with zero attached signals.')
      return false
    }

    if (this.isMultiSignal(signalMount)) {
      return true
    }

    const signal = signalMount.attachedSignals[0]

    const forcedErrorSignalScreens = ['LfPf']
    if (signal.screen.find(s => forcedErrorSignalScreens.find(e => e === s.screen))
      || signal.role === SignalRole.None) {
      return false
    }

    return true
  }

  private getMultiSignalScreen (signalMount: SignalMount) {
    const bridgeParts: SignalBridgePart[] = []
    signalMount.attachedSignals.forEach(signal => {
      for (const catalog of this.catalogService.getSignalSVGCatalog()) {
        const screen = signal.role === SignalRole.None
          ? SvgDraw.getErrorSVG()
          : catalog.getSignalScreen(signal)
        if (screen !== null) {
          const offset = distance(
            [signalMount.position.x, signalMount.position.y],
            [signal.mountPosition.x, signal.mountPosition.y]
          )
          const direction = Math.abs(
            signalMount.position.rotation - signal.mountPosition.rotation
          ) < MAX_BRIDGE_DIRECTION_OFFSET
            ? MountDirection.Up
            : MountDirection.Down
          bridgeParts.push({
            guid: signal.guid,
            signal: SvgBridgeSignal.fromSvgElement(screen, offset, direction, signal.label ?? null)
          })
        }
      }
    })
    return SvgDrawBridge.drawParts(signalMount.guid, bridgeParts, signalMount.mountType)
  }

  public isMultiSignal (signalMount: SignalMount): boolean {
    return signalMount.mountType === SignalMountType.SignalauslegerLinks ||
      signalMount.mountType === SignalMountType.SignalauslegerMitte ||
      signalMount.mountType === SignalMountType.Signalbruecke
  }

  public getMountType (signalMount: SignalMount): SignalMountType {
    const signal = signalMount.attachedSignals[0]
    let mountType = signalMount.mountType
    if (signal.screen.find(screen => screen.screen === 'BSVRVA')) {
      mountType = SignalMountType.BSVRVA
    } else if (signal.screen.find(screen => screen.screen === 'MsUESWdh')) {
      mountType = SignalMountType.MsUESWdh
    } else if (signal.screen.find(screen => screen.screen === 'So14')) {
      mountType = SignalMountType.None
    }

    return mountType
  }

  public getDirectionScreens (signalMount: SignalMount): string[] {
    const signal = signalMount.attachedSignals[0]
    const extra: string[] = []

    // The order defines the placement position.
    // First element will be placed nearest to the main screen.
    const directionScreens = ['So20', 'LfPfL', 'LfPfR']

    for (const directionScreen of directionScreens) {
      const screen = signal.screen.find(ele => ele.screen === directionScreen)
      if (screen) {
        extra.push(screen.screen)
      }
    }
    return extra
  }

  public getFeatureType (): FeatureType {
    return FeatureType.Signal
  }
}
