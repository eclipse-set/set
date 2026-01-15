<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div style="margin: 5px">
    <div>
      <label>Signalbefestigung (Brücke und Ausleger): </label>
      <select v-model="selectedMast">
        <option
          v-for="mast in listMast"
          :key="mast"
          :value="mast"
        >
          {{ mast }}
        </option>
      </select>
    </div>
    <span>{{ draw() }}</span>
  </div>
</template>
<script lang="ts">
import { MountDirection } from '@/model/Signal'
import { SignalMountType } from '@/model/SignalMount'
import { ISvgElement, SvgBridgeSignal } from '@/model/SvgElement'
import SvgService from '@/service/SvgService'
import SvgDrawBridge, { SignalBridgePart } from '@/util/SVG/Draw/SvgDrawBridge'
import {
  HauptVorSignalGroup,
  SignalBrueckeudAusleger
} from '@/util/SVG/SvgEnum'
import { Options, Vue } from 'vue-class-component'

@Options({
  props: {
    svgService: Object
  },
  watch: {
    selectedMast (value: SignalBrueckeudAusleger): SignalBrueckeudAusleger {
      return value
    }
  }
})
/**
 * Draw Signal Bruecker with random count of Signal and Type of Signal
 * @author Truong
 */
export default class SignalBruecke extends Vue {
  svgService!: SvgService
  listMast = [
    SignalMountType.SignalauslegerLinks,
    SignalMountType.SignalauslegerMitte,
    SignalMountType.Signalbruecke
  ]

  selectedMast = SignalMountType.SignalauslegerLinks
  private randomSelectSchirm (): ISvgElement[] {
    const result = new Array<ISvgElement>()
    const listschirm = this.svgService.getSvgElementInGroup(
      HauptVorSignalGroup.KsSys
    )
    if (listschirm) {
      const count = Math.floor(Math.random() * 5) + 2
      for (let i = 0; i < count; i++) {
        const schirmIndex = Math.floor(Math.random() * (listschirm.length - 1))
        result.push(listschirm[schirmIndex])
      }
    }

    return result
  }

  public draw (): string | null {
    const listSchirm = this.randomSelectSchirm()
    const bridgeScreen: SignalBridgePart[] = []
    listSchirm.forEach(screen => {
      bridgeScreen.push({
        guid: '',
        signal: SvgBridgeSignal.fromSvgElement(
          screen,
          5,
          MountDirection.Down,
          null
        )
      })
    })
    const result = SvgDrawBridge.drawParts('',bridgeScreen, this.selectedMast)
    return result.content.outerHTML
  }
}
</script>
