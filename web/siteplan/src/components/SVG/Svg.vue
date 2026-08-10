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
<script lang="ts">
import {
  AndereSignalGroup,
  HauptVorSignalGroup,
  SVGMast
} from '@/util/SVG/SvgEnum'
import SvgKatalog from './SvgKatalog.vue'
import SvgSingleSignal from './SvgSingleSignal.vue'
import SvgService from '@/service/SvgService'
import SignalBruecker from './SignalBruecke.vue'
import { Options, Vue } from 'vue-class-component'
import { ISvgElement } from '@/model/SvgElement'

@Options({
  components: {
    SvgKatalog,
    SvgSingleSignal,
    SignalBruecker
  },
  created () {
    this.setMode()
  },
  watch: {
    $route () {
      this.setMode()
    },
    mode (value: string): void {
      this.mastList = []
      this.schirmList = []
      this.listSignalGroup = []
      if (value === 'katalog') {
        this.listSignalGroup = Object.assign(
          {},
          HauptVorSignalGroup,
          AndereSignalGroup
        )
      } else if (value === 'einzeln') {
        this.listSignalGroup = Object.values(HauptVorSignalGroup)
      }
    }
  }
})
export default class Svg extends Vue {
  svgService = new SvgService()
  mode = ''
  listSignalGroup = []
  mastList: ISvgElement[] | null | undefined = []
  schirmList: ISvgElement[] | null | undefined = []

  getSvgList (signalGroup: HauptVorSignalGroup & AndereSignalGroup): void {
    this.schirmList = this.svgService.getSvgElementInGroup(signalGroup)
    const hauptVotSignalGroup = Object.values(HauptVorSignalGroup)
    const andereSignalGroup = Object.values(AndereSignalGroup)
    if (hauptVotSignalGroup.includes(signalGroup)) {
      this.mastList = this.svgService.getSvgElementInGroup(SVGMast.LongMount)
    }

    if (andereSignalGroup.includes(signalGroup)) {
      this.mastList = this.svgService.getSvgElementInGroup(SVGMast.ShortMount)
    }
  }

  getZusatzSignal (): ISvgElement[] | null | undefined {
    return this.svgService.getSvgElementInGroup(AndereSignalGroup.ZusatzSignale)
  }

  getShortMast (): void {
    this.mastList = this.svgService.getSvgElementInGroup(SVGMast.ShortMount)
  }

  setMode (): void {
    const mode = this.$router.currentRoute.value.query.mode
    if (mode) {
      this.mode = mode.toString()
    }
  }
}
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
