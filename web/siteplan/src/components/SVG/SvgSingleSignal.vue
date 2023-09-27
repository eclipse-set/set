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
      <div>
        <label>Signalsystem auswählen</label>
        <select v-model="selectedSignalGroup">
          <option
            v-for="group in listsignalGroup"
            :key="group"
            :value="group"
          >
            {{ group }}
          </option>
        </select>
      </div>
      <div v-if="selectedSignalGroup">
        <label>Signalschirm auswählen</label>
        <select v-model="seletecSignalSchirm">
          <option
            v-for="schirm in listSchirm"
            :key="schirm.id"
            :value="schirm"
          >
            {{ schirm.id }}
          </option>
        </select>
      </div>
      <div v-if="selectedSignalGroup">
        <label>Signalbefestigung auswählen</label>
        <select v-model="selectedMast">
          <option
            v-for="mast in mastList"
            :key="mast.id"
            :value="mast"
          >
            {{ mast.id }}
          </option>
        </select>
      </div>
      <div
        v-if="selectedSignalGroup"
        style="display: grid; grid-template-columns: auto auto auto auto"
      >
        <div
          v-for="zusatz in zusatzSignal"
          :key="zusatz.id"
        >
          <input
            id="selectZusatz"
            v-model="selectedZusatzSignal"
            type="checkbox"
            :value="zusatz"
          >
          <label for="selectZusatz">{{ zusatz.id }}</label>
        </div>
      </div>
    </div>
    <span>{{ drawEinzelnSignal() }}</span>
  </div>
</template>
<script lang="ts">
import { ISvgElement, ZusatzSignal } from '@/model/SvgElement'
import SvgDraw from '@/util/SVG/Draw/SvgDrawSingleSignal'
import { ZusatzSignalBottom } from '@/util/SVG/SvgEnum'
import { Vue, Options } from 'vue-class-component'

@Options({
  props: {
    listsignalGroup: Object,
    mastList: Object,
    listSchirm: Object,
    zusatzSignal: Object
  },
  watch: {
    selectedSignalGroup (value: string): void {
      this.$emit('select-signal-group', value)
    },

    selectedZusatzSignal (value: ISvgElement[]): void {
      this.selectedZusatzSignal = value
      const isContainBottomSignal =
        value.filter(ele =>
          Object.values(ZusatzSignalBottom).includes(
            ele.id as ZusatzSignalBottom
          )).length > 0
      if (isContainBottomSignal) {
        this.$emit('get-short-mast', '')
      }
    },

    mastList (value: ISvgElement[]): void {
      if (this.selectedMast) {
        this.selectedMast = value.find(ele => ele.id === this.selectedMast.id)
      }
    }
  },
  emits: ['select-signal-group', 'get-short-mast']
})

/**
 * Draw Signal with selected Schirm, Mast and ZusatzSignal
 * @author Truong
 */
export default class SvgSingleSignal extends Vue {
  listsignalGroup!: Array<string>
  mastList!: Array<ISvgElement>
  listSchirm!: Array<ISvgElement>
  zusatzSignal!: Array<ZusatzSignal>
  selectedSignalGroup = ''
  seletecSignalSchirm = null
  selectedMast = null
  selectedZusatzSignal: ZusatzSignal[] = []

  public drawEinzelnSignal (): string | null {
    return SvgDraw.drawSignal(
      this.seletecSignalSchirm,
      this.selectedMast,
      this.selectedZusatzSignal
    ).content.outerHTML
  }
}
</script>
