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

<script lang="ts">
import FeatureSearch from '@/components/development/FeatureSearch.vue'
import Configuration from '@/util/Configuration'
import { Options, Vue } from 'vue-class-component'
import { SubscribeOptions } from 'vuex'

/**
 * Lageplan Toolbar
 * @author Truong
 */
@Options({
  props: {
    model: Object
  },
  components: {
    FeatureSearch
  },
  watch: {
    $route () {
      this.resetBtnColor()
      const currentpath = this.$router.currentRoute.value.path
      switch (currentpath) {
        case '/':
          this.homebtncolor = this.onSelectBtnColor
          break
        case '/svg':
          this.symbolbtncolor = this.onSelectBtnColor
          break
        default:
          break
      }
    }
  },
  emits: ['show-menu']
})
export default class Toolbar extends Vue {
  developmentOptions = false
  homeBtnColor = ''
  symbolBtnColor = ''
  menuBtnColor = ''
  onSelectBtnColor = '#848484'
  unsubscribe: SubscribeOptions | undefined
  mounted (): void {
    this.developmentOptions = Configuration.developmentMode()
  }

  toSvgCatalog (): void {
    this.$emit('show-menu', false)
    this.menuBtnColor = ''
    this.$router.push({
      path: '/svg',
      query: {
        mode: 'katalog'
      }
    })
  }

  isCatalogPage (): boolean {
    const currentpath = this.$router.currentRoute.value.path
    return currentpath === '/svg'
  }

  goHome (): void {
    this.$emit('showMenu', false)
    this.menuBtnColor = ''
    this.$router.push('/')
  }

  resetBtnColor (): void {
    this.homeBtnColor = ''
    this.symbolBtnColor = ''
  }

  onMenuClick (): void {
    if (this.menuBtnColor !== this.onSelectBtnColor) {
      this.menuBtnColor = this.onSelectBtnColor
      this.$emit('showMenu', true)
    } else {
      this.menuBtnColor = ''
      this.$emit('showMenu', false)
    }
  }
}
</script>
<style scope>
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
