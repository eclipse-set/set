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

    <span v-html="html" />
  </div>
</template>

<script setup lang="ts">
import { ISvgElement } from '@/model/SvgElement'
import SvgDrawSignal from '@/util/SVG/Draw/SvgDrawSingleSignal'
import { AndereSignalGroup } from '@/util/SVG/SvgEnum'
import { computed, ref, watch } from 'vue'

/**
 * Draw selected Signal Katalog with or without Mast
 * @author Truong
 */

const props = defineProps<{
  listsignalGroup: Array<string>
  mastList: Array<ISvgElement> | null | undefined
  listSchirm: Array<ISvgElement> | null | undefined
}>()

const emit = defineEmits(['select-signal-group'])

const html = computed(() => drawElementInGroup())

const selectedSignalGroup = ref('')
const withMast = ref(false)
const haveMast = ref(false)
const listSignalwithoutMast = [
  AndereSignalGroup.Richtungspfeil.toString(),
  AndereSignalGroup.Zuordnungstafel.toString()
]

watch(selectedSignalGroup, (value: string): void => {
  emit('select-signal-group', value)
  withMast.value = false
  if (!isWithoutMast(value) && value) {
    haveMast.value = true
  } else {
    haveMast.value = false
  }
})

function drawElementInGroup (): string | null {
  if (!props.listSchirm) {
    return null
  }

  if (props.mastList && withMast.value) {
    return SvgDrawSignal.drawAllSignalInGroup(props.listSchirm, props.mastList).outerHTML
  }

  return SvgDrawSignal.drawAllElementInGroup(props.listSchirm).outerHTML
}

function isWithoutMast (value: string): boolean {
  return listSignalwithoutMast.includes(value)
}
</script>
