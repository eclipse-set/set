<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div id="root">
    <div v-if="mode === 'katalog'">
      <SvgKatalog
        :listsignal-group="listSignalGroup"
        :mast-list="mastList"
        :list-schirm="schirmList"
        @select-signal-group="getSvgList"
      />
    </div>
    <div v-if="mode === 'einzeln'">
      <SvgSingleSignal
        :listsignal-group="listSignalGroup"
        :mast-list="mastList"
        :list-schirm="schirmList"
        :zusatz-signal="getZusatzSignal()"
        @select-signal-group="getSvgList"
        @get-short-mast="getShortMast"
      />
    </div>
    <div v-if="mode === 'bruecke'">
      <SignalBruecker :svg-service="svgService" />
    </div>
  </div>
</template>
<script setup lang="ts">
import {
  AndereSignalGroup,
  HauptVorSignalGroup,
  SVGMast
} from '@/util/SVG/SvgEnum'
import SvgKatalog from './SvgKatalog.vue'
import SvgSingleSignal from './SvgSingleSignal.vue'
import SvgService from '@/service/SvgService'
import SignalBruecker from './SignalBruecke.vue'
import { ISvgElement, ZusatzSignal } from '@/model/SvgElement'
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

const svgService = new SvgService()
const mode = ref('')
const listSignalGroup = ref<string[]>([])
const mastList = ref<ISvgElement[] | null | undefined>([])
const schirmList = ref<ISvgElement[] | null | undefined>([])

const route = useRoute()

function getSvgList (signalGroup: string): void {
  schirmList.value = svgService.getSvgElementInGroup(signalGroup) ?? []
  const hauptVotSignalGroup: string[] = Object.values(HauptVorSignalGroup)
  const andereSignalGroup: string[] = Object.values(AndereSignalGroup)
  if (hauptVotSignalGroup.includes(signalGroup)) {
    mastList.value = svgService.getSvgElementInGroup(SVGMast.LongMount) ?? []
  }

  if (andereSignalGroup.includes(signalGroup)) {
    mastList.value = svgService.getSvgElementInGroup(SVGMast.ShortMount) ?? []
  }
}

function getZusatzSignal (): ZusatzSignal[] {
  return (svgService.getSvgElementInGroup(AndereSignalGroup.ZusatzSignale) ?? []) as ZusatzSignal[]
}

function getShortMast (): void {
  mastList.value = svgService.getSvgElementInGroup(SVGMast.ShortMount) ?? []
}

function setMode (): void {
  const m = route.query.mode
  if (m) {
    mode.value = m.toString()
  }
}

watch(route, () => {
  setMode()
}, { immediate: true })

watch(mode, value => {
  mastList.value = []
  schirmList.value = []
  listSignalGroup.value = []
  if (value === 'katalog') {
    listSignalGroup.value = Object.values({ ...HauptVorSignalGroup, ...AndereSignalGroup })
  } else if (value === 'einzeln') {
    listSignalGroup.value = Object.values(HauptVorSignalGroup)
  }
})

</script>
<style scoped>
.btn {
  font-size: 20px;
  cursor: pointer;
}

#root {
  margin: 5px;
}
</style>
