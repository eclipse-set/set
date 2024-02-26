<template>
  <div
    v-if="!loading"
    id="root"
  >
    <Toolbar @show-menu="showMenu" />
    <div id="view">
      <router-view id="mainView" />
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Options } from 'vue-class-component'
import Toolbar from '@/components/toolbar/Toolbar.vue'
import axios from 'axios'
import { ToolboxConfiguration } from './util/ToolboxConfiguration'
import { store } from './store'
import PlanProToolboxTest from './util/PlanProToolboxTest'
import Configuration from './util/Configuration'
@Options({
  async created () {
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

    // Download the toolbox configuration
    await axios
      .get<ToolboxConfiguration>('/configuration.json')
      .then(response => {
        store.commit('setpptConfiguration', response.data)
        // run unit tests
        if (Configuration.developmentMode()) {
          PlanProToolboxTest.run()
        }

        this.loading = false
      })
  },
  components: {
    Toolbar
  }
})
export default class App extends Vue {
  isShowMenu = false
  altClientHeight = 0
  loading = true

  showMenu (value: boolean): void {
    this.isShowMenu = value
  }
}
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
