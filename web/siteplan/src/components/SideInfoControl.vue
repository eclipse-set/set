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
    <teleport
      v-if="!collapsed"
      to="div.side-info-container"
    >
      <div class="side-info-control-content">
        <div class="side-info-header">
          <div class="side-info-header-icon">
            <slot name="icon" />
          </div>
          <div>
            <h1>{{ title }}</h1>
          </div>
          <div>
            <button
              title="Schlie&szlig;en"
              class="side-info-close"
              @click.capture.stop="setCollapsed(true)"
            >
              <span class="material-icons">close</span>
            </button>
          </div>
        </div>
        <slot name="content" />
      </div>
    </teleport>
    <div class="side-info-control">
      <button
        :title="title"
        @click.capture.stop="setCollapsed(!collapsed)"
      >
        <slot
          v-if="collapsed"
          name="icon"
        />
        <span v-else>&#187;</span>
      </button>
    </div>
  </div>
</template>

<script lang="ts">
/**
 * Map control that shows information information in a box besides the map
 * @author Peters
 */
import { store } from '@/store'
import { Options, Vue } from 'vue-class-component'
import { SubscribeOptions } from 'vuex'

@Options({
  props: {
    title: String
  }
})
export default class SideInfoControl extends Vue {
  unsubscribe: SubscribeOptions | undefined
  collapsed = true
  title!: string

  setCollapsed (val: boolean) {
    const contentContainer = document.getElementById('side-info-container')
    if (!contentContainer) {
      console.warn('side-info-container not found')
      return
    }

    if (!val) {
      // make sure other SideInfoControls are closed before displaying own content
      const closeButton = contentContainer.getElementsByClassName(
        'side-info-close'
      )[0] as HTMLElement
      if (closeButton) {
        closeButton.click()
      }
    }

    this.collapsed = val
    contentContainer.setAttribute(
      'class',
      this.collapsed ? 'side-info-container-closed' : 'side-info-container'
    )
    store.state.map.updateSize()
  }
}
</script>

<style scoped>
.side-info-control {
  position: static;
  display: flex;
  border-radius: 4px;
  padding: 2px;
  background-color: rgba(255, 255, 255, 0.4);
}

button {
  margin: 1px;
  padding: 0;
  font-size: 1.14em;
  font-weight: bold;
  text-decoration: none;
  text-align: center;
  height: 1.375em;
  width: 1.375em;
  line-height: 0.4em;
  border: none;
  border-radius: 2px;
  background-color: #f7f7f7;
}

button:hover {
  background-color: #848484;
}

.side-info-header>button {
  cursor: pointer;
}

.side-info-control-content {
  padding: 3px;
}

.side-info-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 7px;
}

.side-info-header>div {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.side-info-header h1 {
  margin: 3px;
  font-size: 1.14em;
  font-weight: bold;
}
</style>

<style>
.side-info-header-icon .material-icons {
  font-size: 30px;
  user-select: none;
}

.ol-attribution {
  max-width: calc(100% - 250px);
}
</style>
