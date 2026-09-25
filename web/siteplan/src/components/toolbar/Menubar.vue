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
    <KatalogMenuBar v-if="$route.path === '/svg'" />
  </div>
</template>
<script setup lang="ts">
/**
 * Menu bar
 * @author Truong
 */
import KatalogMenuBar from './KatalogMenuBar.vue'
</script>
<style scoped>
#menubar {
  background: #666666;
}
</style>
