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
        v-if="developmentOptions"
        id="featureSearch"
        ref="featureSearch"
      >
        <FeatureSearch />
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
 * Uebersichtplan Toolbar
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
        default:
          break
      }
    }
  },
  emits: ['show-menu']
})
export default class Toolbar extends Vue {
  developmentOptions = false
  onSelectBtnColor = '#848484'
  unsubscribe: SubscribeOptions | undefined
  mounted (): void {
    this.developmentOptions = Configuration.developmentMode()
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

#featureSearch {
  margin-left: auto;
  margin-right: 10px;
}
</style>
