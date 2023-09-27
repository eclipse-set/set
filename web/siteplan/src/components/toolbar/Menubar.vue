<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div id="menubar">
    <KatalogMenuBar v-if="showCatalogMenu" />
  </div>
</template>
<script lang="ts">
/**
 * Menu bar
 * @author Truong
 */
import { Options, Vue } from 'vue-class-component'
import KatalogMenuBar from './KatalogMenuBar.vue'

@Options({
  components: {
    KatalogMenuBar
  },
  created () {
    const currentpath = this.$router.currentRoute.value.path
    switch (currentpath) {
      case '/':
        this.showLageplanMenu = true
        break
      case '/svg':
        this.showCatalogMenu = true
        break
      default:
        break
    }
  }
})
export default class Menubar extends Vue {
  showLageplanMenu = false
  showCatalogMenu = false

  resetState (): void {
    this.showLageplanMenu = false
    this.showCatalogMenu = false
  }
}
</script>
<style scoped>
#menubar {
  background: #666666;
}
</style>
