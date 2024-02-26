<!--
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 -->
<template>
  <SideInfoControl :title="'Ebenen verwalten'">
    <template #icon>
      <span class="material-icons">layers</span>
    </template>
    <template #content>
      <div class="layer-control-container">
        <div
          v-for="layer in getEditableLayers()"
          :key="layer.getLayerType()"
          class="layer-drag-container"
          draggable="true"
          @dragenter.prevent
          @dragstart="dragStart($event, layer)"
          @dragend="dragEnd"
          @dragover="dragOver($event, layer)"
          @drop="dragDrop"
        >
          <div
            class="layer-control"
            :class="
              dragTargetLayer == layer
                ? dragInsertBefore
                  ? 'insert-before'
                  : 'insert-after'
                : ''
            "
          >
            <button
              title="Anzeigen/Verbergen"
              @click.capture="toggleShowLayer(layer)"
            >
              <span
                class="material-icons"
                :class="
                  layer.getVisible() ? 'layer-visible' : 'layer-not-visible'
                "
              >
                {{ layer.getVisible() ? 'visibility' : 'visibility_off' }}
              </span>
            </button>
            <div class="layer-description">
              {{ layer.getDisplayName() }}
            </div>
          </div>
        </div>
      </div>
      <div class="layer-control-hint">
        <h1>Hinweis:</h1>
        <p>
          Ebenen können durch Klick auf das
          <span
            class="material-icons"
            style="font-size: 15px"
          >visibility</span>-Symbol ein- bzw. ausgeblendet werden.
        </p>
        <p>
          Die Reihenfolge der Ebenen gibt an, in welcher Reihenfolge die Ebenen
          gezeichnet werden (unterste zuletzt). Die Reihenfolge kann via
          Drag&amp;Drop angepasst werden.
        </p>
      </div>
    </template>
  </SideInfoControl>
</template>

<script lang="ts">
import SideInfoControl from '@/components/SideInfoControl.vue'
import { store } from '@/store'
import 'material-design-icons/iconfont/material-icons.css'

/**
 * Layer (de-)selection control
 * @author Peters
 */
import { Options, Vue } from 'vue-class-component'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { FeatureLayerType } from '@/feature/FeatureInfo'

@Options({
  components: {
    SideInfoControl
  },
  beforeCreate () {
    this.unsubscribe = store.subscribe((m, s) => {
      if (m.type === 'setFeatureLayers') {
        this.featureLayers = s.featureLayers
      }
    })
  },
  beforeUnmount () {
    this.unsubscribe()
  }
})
export default class LayerControl extends Vue {
  featureLayers: NamedFeatureLayer[] = []
  dragTargetLayer: NamedFeatureLayer | null = null
  dragInsertBefore = false
  draggedLayer: NamedFeatureLayer | null = null

  getEditableLayers (): NamedFeatureLayer[] {
    // Do not allow to disable/edit the Layer for highlighting features
    const layers = this.featureLayers.filter(
      layer => layer.getLayerType() !== FeatureLayerType.Flash
    )
    const sorted = layers.sort((a, b) => a.getZIndex() - b.getZIndex())
    return sorted
  }

  toggleShowLayer (layer: NamedFeatureLayer): void {
    layer.setVisible(!layer.getVisible())
  }

  getLayerIndex (layer: NamedFeatureLayer): number {
    return this.getEditableLayers().findIndex(l => l === layer)
  }

  dragStart (evt: DragEvent, layer: NamedFeatureLayer): void {
    this.draggedLayer = layer
    if (evt.dataTransfer) {
      evt.dataTransfer.dropEffect = 'move'
      evt.dataTransfer.effectAllowed = 'move'
    }
  }

  dragEnd (): void {
    this.dragTargetLayer = null
    this.draggedLayer = null
  }

  dragOver (evt: DragEvent, layer: NamedFeatureLayer): void {
    if (this.draggedLayer === layer || !this.draggedLayer) {
      // The element cannot be dropped onto itself
      return
    }

    if (this.dragTargetLayer !== layer) {
      this.dragTargetLayer = layer
    }

    const target = evt.target as Element
    const layerControl = target
      ?.closest('.layer-drag-container')
      ?.getElementsByClassName('layer-control')[ 0 ]
    if (!target || !layerControl) {
      // Element dragged over something that is not a valid drop zone
      return
    }

    const targetY = target.getBoundingClientRect().top
    const parentHeight = layerControl.getBoundingClientRect().height
    const dropOffset = evt.clientY - targetY
    this.dragInsertBefore = dropOffset <= parentHeight / 2

    evt.preventDefault()
  }

  dragDrop (): void {
    if (!this.dragTargetLayer || !this.draggedLayer) {
      return
    }

    const targetIndex = this.getLayerIndex(this.dragTargetLayer)
    const sourceIndex = this.getLayerIndex(this.draggedLayer)
    let offset = targetIndex - sourceIndex
    if (sourceIndex < targetIndex && this.dragInsertBefore) {
      offset -= 1
    } else if (sourceIndex > targetIndex && !this.dragInsertBefore) {
      offset += 1
    }

    this.moveLayer(this.draggedLayer, offset)
  }

  moveLayer (source: NamedFeatureLayer, offset: number): void {
    if (offset === 0) {
      return
    }

    const layers = this.getEditableLayers()
    if (offset < 0) {
      layers.reverse()
      offset = -offset
    }

    const sourceIndex = layers.findIndex(l => l === source)
    const newSourceZIndex = layers[ sourceIndex + offset ].getZIndex()
    for (let i = sourceIndex + offset; i > sourceIndex; i--) {
      layers[ i ].setZIndex(layers[ i - 1 ].getZIndex())
    }

    source.setZIndex(newSourceZIndex)
  }
}
</script>
<style scoped>
.layer-control-container {
  margin: 3px;
  display: flex;
  flex-direction: column;
}

.layer-control {
  display: flex;
  align-items: stretch;
  justify-content: flex-start;
  background-color: #eeeeee;
  border: none;
  border-radius: 2px;
  margin: 2px;
}

.insert-before {
  border-top: 2px solid #000000;
  margin-top: 0px;
}

.insert-after {
  border-bottom: 2px solid #000000;
  margin-bottom: 0px;
}

.layer-control button {
  margin: 1px;
  padding: 0;
  line-height: 0.4em;
  border: none;
  background: none;
  cursor: pointer;
  user-select: none;
}

.layer-control button:focus {
  outline: 0;
}

.layer-control button>.layer-not-visible {
  color: #aaaaaa;
}

.layer-control button>.layer-visible {
  color: #555555;
}

.layer-description {
  padding: 2px;
  vertical-align: middle;
  user-select: none;
  width: 100vh;
  display: inline-block;
}

.layer-control .material-icons {
  font-size: 20px;
}

.layer-control-hint {
  margin: 3px;
}

.layer-control-hint h1 {
  font-size: 1em;
  font-weight: bold;
  margin-block-start: 1em;
  margin-block-end: 0.3em;
}

.layer-control-hint p {
  font-size: 1em;
  margin-block-start: 0.1em;
  margin-block-end: 0.5em;
}
</style>
