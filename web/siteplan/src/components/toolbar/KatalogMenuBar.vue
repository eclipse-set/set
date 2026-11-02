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
    <ul id="katalogMenu">
      <li
        id="katalog"
        tabindex="1"
        class="menuItem"
        :class="{ active: mode === 'katalog' }"
        @click="onSelected('katalog')"
      >
        <a>Katalog</a>
      </li>
      <li
        id="einzeln"
        tabindex="2"
        class="menuItem"
        :class="{ active: mode === 'einzeln' }"
        @click="onSelected('einzeln')"
      >
        <a>Einzelnes Signal</a>
      </li>
      <li
        id="bruecke"
        tabindex="3"
        class="menuItem"
        :class="{ active: mode === 'bruecke' }"
        @click="onSelected('bruecke')"
      >
        <a>Signalbrücke und -ausleger</a>
      </li>
    </ul>
  </div>
</template>
<script setup lang="ts">
/**
* Menu of Catalog Page
* @author Truong
*/

import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const mode = computed(() => route.query.mode)

function onSelected (value: string): void {
  router.push({
    path: '/svg',
    query: {
      mode: value
    }
  })
}
</script>
<style scoped>
#katalogMenu {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  display: flex;
  justify-content: flex-start;
  align-items: stretch;
  flex-direction: column;
  color: white;
}

.menuItem {
  cursor: pointer;
}

.menuItem:hover {
  background: #4a4a48;
}

.menuItem:focus,
.menuItem.active {
  background: #7a7a79;
}

.menuItem a {
  display: inline-block;
  padding: 8px;
}
</style>
