<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div id="lageplan">
    <div
      :style="{ visibility: loadingIndicatorState(), 'animation-play-state': loadingIndicatorAnimationState()}"
      class="loading"
    />
    <div
      v-if="error.iserror"
      v-on="alertBox()"
    />
    <div>
      <FeatureService />
    </div>
    <MapContainer />
  </div>
</template>

<script setup lang="ts">
import { store } from '@/store'
import 'material-design-icons/iconfont/material-icons.css'
import FeatureService from './FeatureService.vue'
import MapContainer from './MapContainer.vue'
import { onBeforeUnmount, ref } from 'vue'

const loading = ref(true)

const error = ref({
  iserror: false,
  msg: ''
})

let unsubscribe: (() => void) | undefined

unsubscribe = store.subscribe((m, s) => {
  if (m.type === 'setLoading') {
    loading.value = s.loading
  }

  if (m.type === 'setError') {
    error.value = s.error
  }
})

onBeforeUnmount(() => {
  unsubscribe?.()
})

function alertBox () {
  alert(error.value.msg)
}

function loadingIndicatorState () {
  return loading.value ? 'visible' : 'hidden'
}

function loadingIndicatorAnimationState () {
  return loading.value ? 'running' : 'paused'
}

</script>
<style>
#lageplan {
  height: 100%;
  display: flex;
  flex-flow: column;
}

.loading {
  border: 16px solid #f3f3f3;
  border-radius: 50%;
  border-top: 16px solid #3498db;
  width: 120px;
  height: 120px;
  animation: spin 2s linear infinite;
  position: absolute;
  top: calc(50% - 60px);
  left: calc(50% - 60px);
  transform: translate(-50%, -50%);
  z-index: 500;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

.ol-control button {
  background-color: #f7f7f7;
  color: black;
}

.ol-control button:hover,
.ol-control button:focus {
  background-color: #848484;
}
</style>
