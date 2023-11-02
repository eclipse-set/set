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
        tabIndex="1"
        class="menuItem"
        @click="onSelected('katalog')"
      >
        <a>Katalog</a>
      </li>
      <li
        id="einzeln"
        tabIndex="2"
        class="menuItem"
        @click="onSelected('einzeln')"
      >
        <a>Einzelnes Signal</a>
      </li>
      <li
        id="bruecke"
        tabIndex="3"
        class="menuItem"
        @click="onSelected('bruecke')"
      >
        <a>Signalbrücke und -ausleger</a>
      </li>
    </ul>
  </div>
</template>
<script lang="ts">
/**
 * Menu of Catalog Page
 * @author Truong
 */
import { Vue } from 'vue-class-component'

export default class KatalogMenuBar extends Vue {
  mounted (): void {
    const mode = this.$router.currentRoute.value.query.mode
    if (mode) {
      const ele = document.getElementById(mode.toString())
      ele?.focus()
    }
  }

  onSelected (value: string): void {
    this.$router.push({
      path: '/svg',
      query: {
        mode: value
      }
    })
  }
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

.menuItem:focus {
  background: #7a7a79;
}

.menuItem a {
  display: inline-block;
  padding: 8px;
}
</style>
