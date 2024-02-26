/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Uebersichtplan from '../components/Uebersichtplan.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Uebersichtplan',
    component: Uebersichtplan
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
