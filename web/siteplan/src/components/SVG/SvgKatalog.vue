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
      <label>Symbolgruppe auswählen</label>
      <select v-model="selectedSignalGroup">
        <option
          v-for="group in listsignalGroup"
          :key="group"
          :value="group"
        >
          {{ group }}
        </option>
      </select>
      <div v-if="haveMast">
        <input
          id="withMast"
          v-model="withMast"
          type="checkbox"
        >
        <label for="withMast">Mit Befestigung verbinden: </label>
      </div>
    </div>

    <span v-html="drawElementInGroup()" />
  </div>
</template>

<script lang="ts">
import SvgDrawSignal from '@/util/SVG/Draw/SvgDrawSingleSignal'
import { AndereSignalGroup } from '@/util/SVG/SvgEnum'
import { Vue, Options } from 'vue-class-component'
import { ISvgElement } from '@/model/SvgElement'
@Options({
  props: {
    listsignalGroup: Object,
    mastList: Object,
    listSchirm: Object
  },
  watch: {
    selectedSignalGroup (value: string): void {
      this.$emit('select-signal-group', value)
      this.withMast = false
      if (!this.isWithoutMast(value) && value) {
        this.haveMast = true
      } else {
        this.haveMast = false
      }
    },

    selectedZusatzSignal (value: ISvgElement[]): void {
      this.selectedZusatzSignal = value
    }
  },
  emits: ['select-signal-group']
})

/**
 * Draw selected Signal Katalog with or without Mast
 * @author Truong
 */
export default class SvgKatalog extends Vue {
  listsignalGroup!: Array<string>
  mastList!: Array<ISvgElement>
  listSchirm!: Array<ISvgElement>
  selectedSignalGroup = ''
  withMast = false
  haveMast = false
  listSignalwithoutMast = [
    AndereSignalGroup.Richtungspfeil.toString(),
    AndereSignalGroup.Zuordnungstafel.toString()
  ]

  public drawElementInGroup (): string | null {
    if (!this.listSchirm) {
      return null
    }

    if (this.mastList && this.withMast) {
      return SvgDrawSignal.drawAllSignalInGroup(this.listSchirm, this.mastList)
        .outerHTML
    }

    return SvgDrawSignal.drawAllElementInGroup(this.listSchirm).outerHTML
  }

  public isWithoutMast (value: string): boolean {
    return this.listSignalwithoutMast.includes(value)
  }
}
</script>
