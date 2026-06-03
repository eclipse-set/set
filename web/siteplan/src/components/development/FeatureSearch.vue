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
    <div
      id="searchInput"
      ref="searchInputContainer"
    >
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

<script setup lang="ts">
import { store } from '@/store'
import { onBeforeUnmount, onMounted, ref, useTemplateRef, watch } from 'vue'

/**
 * Search Result Viewer
 * @author Peters
 */

const posTop = ref('0px')
const posLeft = ref('0px')
const suggestionsVisible = ref(false)
const searchInput = ref('')
const lastSearchInput = ref('')
const matchingCount = ref(0)
const selectedFeatureOffset = ref(0)
const elements = ref<string[]>([])
const searchInputContainer = useTemplateRef('searchInputContainer')

const unsubscribe = store.subscribe((m, s) => {
  if (m.type === 'setMatchingCount') {
    matchingCount.value = s.matchingFeatures
  }
})

onBeforeUnmount(() => {
  unsubscribe()
})

function setPosition (): void {
  const elementSearch = searchInputContainer.value
  if (!elementSearch) {
    console.warn('no searchInput found')
    return
  }

  const box = elementSearch.getBoundingClientRect()
  posLeft.value = Math.ceil(box.left) + 'px'
  posTop.value = Math.ceil(box.bottom) + 'px'
}

onMounted(() => {
  setPosition()
})

function searchSubmit (): void {
  suggestionsVisible.value = false
  if (searchInput.value === lastSearchInput.value) {
    selectedFeatureOffset.value++
  }

  store.commit('setSelectFeatureOffset', selectedFeatureOffset.value)
  store.commit('selectFeature', searchInput.value)
  lastSearchInput.value = searchInput.value
}

function updateSearchInput (value: string): void {
  selectedFeatureOffset.value = 0
  if (!value) {
    suggestionsVisible.value = false
  }
}

function getResultText (): string {
  if (matchingCount.value === 0) {
    return '0 Treffer'
  } else {
    return `${(selectedFeatureOffset.value % matchingCount.value) + 1} von ${matchingCount.value} Treffern`
  }
}

watch(searchInput, value => {
  updateSearchInput(value)
})
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
