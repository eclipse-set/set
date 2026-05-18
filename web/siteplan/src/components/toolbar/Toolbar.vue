<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div v-if="developmentOptions">
    <ul id="toolbar">
      <li
        class="toolbarItem"
        :style="{ background: homeBtnColor }"
        @click="goHome()"
      >
        <span class="material-icons">home</span>
      </li>
      <li
        id="symbolBtn"
        class="toolbarItem textBtn"
        :style="{ background: symbolBtnColor }"
        @click="toSvgCatalog()"
      >
        <div>Symbolkatalog</div>
      </li>
      <li
        v-if="developmentOptions && !isCatalogPage()"
        id="featureSearch"
        ref="featureSearch"
      >
        <FeatureSearch />
      </li>
      <li
        v-if="isCatalogPage()"
        id="menubtn"
        class="toolbarItem"
        :style="{ background: menuBtnColor }"
        @click="onMenuClick()"
      >
        <div>&#9776;</div>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import FeatureSearch from '@/components/development/FeatureSearch.vue'
import Configuration from '@/util/Configuration'
import { onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'

/**
 * Lageplan Toolbar
 * @author Truong
 */
defineProps({
  model:
  {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['show-menu'])

const router = useRouter()
const developmentOptions = ref(false)
const homeBtnColor = ref('')
const symbolBtnColor = ref('')
const menuBtnColor = ref('')
const onSelectBtnColor = ref('#848484')

onMounted(() => {
  developmentOptions.value = Configuration.developmentMode()
})

watch(router.currentRoute, currentRoute => {
  resetBtnColor()
  switch (currentRoute.path) {
    case '/':
      homeBtnColor.value = onSelectBtnColor.value
      break
    case '/svg':
      symbolBtnColor.value = onSelectBtnColor.value
      break
    default:
      break
  }
})

function toSvgCatalog (): void {
  emit('show-menu', false)
  menuBtnColor.value = ''
  router.push({
    path: '/svg',
    query: {
      mode: 'katalog'
    }
  })
}

function isCatalogPage (): boolean {
  const currentpath = router.currentRoute.value.path
  return currentpath === '/svg'
}

function goHome (): void {
  emit('show-menu', false)
  menuBtnColor.value = ''
  router.push('/')
}

function resetBtnColor (): void {
  homeBtnColor.value = ''
  symbolBtnColor.value = ''
}

function onMenuClick (): void {
  if (menuBtnColor.value !== onSelectBtnColor.value) {
    menuBtnColor.value = onSelectBtnColor.value
    emit('show-menu', true)
  } else {
    menuBtnColor.value = ''
    emit('show-menu', false)
  }
}
</script>
<style scoped>
.toolbarItem {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.toolbarItem div {
  display: inline-block;
  padding: 8px;
}

.toolbarItem:hover {
  background: #848484;
}

#toolbar {
  background: #f7f7f7;
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  display: flex;
  justify-content: flex-start;
  align-items: stretch;
  text-align: center;
}

#menubtn {
  margin-left: auto;
}

.textBtn {
  display: flex;
  align-items: center;
}

#featureSearch {
  margin-left: auto;
  margin-right: 10px;
}
</style>
