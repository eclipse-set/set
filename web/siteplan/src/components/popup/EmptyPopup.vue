<!--
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <div>
    <h2>Leer Popup</h2>
    <ul>
      <li>Es gibt keine Infomation über diese Objekt</li>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'

/**
 * Popup contents for signal features
 *
 * @author Stuecker
 */

defineProps<{
  feature: Feature<Geometry>
}>()
</script>
