<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div>
    <div id="searchInput">
      <form
        autocomplete="off"
        @submit.prevent="searchSubmit"
      >
        <input
          v-model.trim="searchInput"
          style="margin: 10px 10px"
          spellcheck="false"
          placeholder="Element suchen..."
        >
        <label>{{ getResultText() }}</label>
      </form>
    </div>
    <teleport to="body">
      <div
        v-if="suggestionsVisible"
        id="searchSuggestionContainer"
        :style="{ top: posTop, left: posLeft }"
      >
        <p
          v-for="element in elements"
          :key="element"
        >
          {{ element }}
        </p>
      </div>
    </teleport>
  </div>
</template>
<script lang="ts">
/**
 * Search Result Viewer
 * @author Peters
 */
import { Options, Vue } from 'vue-class-component'
import { store } from '@/store'
import JumpToGuid from '@/components/development/JumpToGuid.vue'

@Options({
  components: {
    JumpToGuid
  },
  watch: {
    searchInput (value) {
      this.updateSearchInput(value)
    }
  },
  created () {
    this.unsubscribe = store.subscribe((m, s) => {
      if (m.type === 'setMatchingCount') {
        this.matchingCount = s.matchingFeatures
      }
    })
  },
  beforeUnmount () {
    this.unsubscribe()
  }
})
export default class FeatureSearch extends Vue {
  posTop = '0px'
  posLeft = '0px'
  suggestionsVisible = false
  searchInput = ''
  lastSearchInput = ''
  matchingCount = 0
  selectedFeatureOffset = 0
  elements: string[] = []

  mounted (): void {
    this.setPosition()
  }

  searchSubmit (): void {
    this.suggestionsVisible = false
    if (this.searchInput === this.lastSearchInput) {
      this.selectedFeatureOffset++
    }

    store.commit('setSelectFeatureOffset', this.selectedFeatureOffset)
    store.commit('selectFeature', this.searchInput)
    this.lastSearchInput = this.searchInput
  }

  setPosition (): void {
    const elementSearch = document.getElementById('searchInput')
    if (!elementSearch) {
      console.warn('no searchInput found')
      return
    }

    const box = elementSearch.getBoundingClientRect()
    this.posLeft = Math.ceil(box.left) + 'px'
    this.posTop = Math.ceil(box.bottom) + 'px'
  }

  updateSearchInput (value: string): void {
    this.selectedFeatureOffset = 0
    if (!value) {
      this.suggestionsVisible = false
    }
  }

  getResultText (): string {
    if (this.matchingCount === 0) {
      return '0 Treffer'
    } else {
      return (
        (this.selectedFeatureOffset % this.matchingCount) +
        1 +
        ' von ' +
        this.matchingCount +
        ' Treffern'
      )
    }
  }
}
</script>
<style scoped>
#searchSuggestionContainer {
  background: #e0edff;
  position: absolute;
  z-index: 10;
  height: auto;
  width: 60%;
  padding: 8px;
  border-radius: 8px;
}

#searchInput>form>input {
  background: url('@/assets/ic_search_black_24dp_1x.png') no-repeat scroll;
  padding-left: 20px;
  height: 20px;
  background-color: white;
  border-radius: 10px;
}
</style>
