<template>
  <div
    v-if="!loading"
    id="root"
  >
    <Toolbar @show-menu="showMenu" />
    <div id="view">
      <router-view id="mainView" />
      <Menubar
        v-if="isShowMenu"
        id="menubar"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import Menubar from '@/components/toolbar/Menubar.vue'
import Toolbar from '@/components/toolbar/Toolbar.vue'
import axios from 'axios'
import { onMounted, ref } from 'vue'
import SvgCatalogService from './service/SvgCatalogService'
import { store } from './store'
import Configuration from './util/Configuration'
import PlanProToolboxTest from './util/PlanProToolboxTest'
import { ToolboxConfiguration } from './util/ToolboxConfiguration'

const isShowMenu = ref(false)
const loading = ref(true)

function showMenu (value: boolean): void {
  isShowMenu.value = value
}

onMounted (async () => {
  store.commit('setLoading', true)
  // Download the siteplan font
  await axios({
    method: 'GET',
    url: 'font',
    responseType: 'blob'
  })
    .then(response => {
      const reader = new FileReader()
      reader.onload = () => {
        store.commit(
          'setSiteplanfont',
          `
        @font-face {
          font-family: 'siteplanfont';
          src: url('${reader.result}') format('truetype');
        }`
        )
      }
      reader.readAsDataURL(new Blob([response.data]))
    })
    .catch(() => {
      console.warn('Siteplan Font not available')
    })
  // Wait until the svg catalog service is ready
  await SvgCatalogService.getInstance().isReady()
  // Download the toolbox configuration
  await axios
    .get<ToolboxConfiguration>('/configuration.json')
    .then(response => {
      store.commit('setpptConfiguration', response.data)
      store.commit('setSheetCutCRS', response.data.defaultSheetCutCRS)
      store.commit('setPlanProModelType', response.data.planproModelType)
      // run unit tests
      if (Configuration.developmentMode()) {
        PlanProToolboxTest.run()
      }

      loading.value = false
    })
})

</script>
<style>
html,
body {
  display: block;
  font-family: Helvetica, Arial, sans-serif;
  height: 100%;
  margin: 0;
}

#root {
  display: flex;
  height: 100vh;
  flex-direction: column;
}

#view {
  display: flex;
  flex: 1;
}

#menubar {
  flex-grow: 1;
}

#mainView {
  flex-grow: 5;
}
</style>
