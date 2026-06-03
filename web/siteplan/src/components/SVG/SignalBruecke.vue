<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->

<!-- eslint-disable vue/no-v-html -->

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
    <span v-html="draw()" />
  </div>
</template>
<script setup lang="ts">
import { MountDirection } from '@/model/Signal'
import { SignalMountType } from '@/model/SignalMount'
import { ISvgElement, SvgBridgeSignal } from '@/model/SvgElement'
import SvgService from '@/service/SvgService'
import SvgDrawBridge, { SignalBridgePart } from '@/util/SVG/Draw/SvgDrawBridge'
import { HauptVorSignalGroup } from '@/util/SVG/SvgEnum'
import { ref } from 'vue'

/**
 * Draw Signal Bruecker with random count of Signal and Type of Signal
 * @author Truong
 */

const props = defineProps<{
  svgService: SvgService
}>()

const listMast = [
  SignalMountType.SignalauslegerLinks,
  SignalMountType.SignalauslegerMitte,
  SignalMountType.Signalbruecke
]

const selectedMast = ref<SignalMountType>(SignalMountType.SignalauslegerLinks)

function randomSelectSchirm (): ISvgElement[] {
  const result = new Array<ISvgElement>()
  const listschirm = props.svgService.getSvgElementInGroup(HauptVorSignalGroup.KsSys)
  if (listschirm) {
    const count = Math.floor(Math.random() * 5) + 2
    for (let i = 0; i < count; i++) {
      const schirmIndex = Math.floor(Math.random() * (listschirm.length - 1))
      result.push(listschirm[schirmIndex])
    }
  }

  return result
}

function draw (): string | null {
  const listSchirm = randomSelectSchirm()
  const bridgeScreen: SignalBridgePart[] = []
  listSchirm.forEach(screen => {
    bridgeScreen.push({
      guid: '',
      signal: SvgBridgeSignal.fromSvgElement(screen, 5, MountDirection.Down, undefined)
    })
  })
  const result = SvgDrawBridge.draw('', bridgeScreen, selectedMast.value)
  return result.content.outerHTML
}
</script>
