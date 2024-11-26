/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import * as monaco from 'monaco-editor'
import { App } from './app'
import { Model, TextFileModel } from './model'
import { Multitab } from './multitab'

export enum TableType
{
  INITIAL = 'initial',
  FINAL = 'final',
  DIFF = 'diff',
  SINGLE= 'single'
}
let app !: App
(window as any).initApp = async () => {
  const model = new Model()
  app = new App()
  app.init()

  await model.fetchFont().then(res => {
    const reader = new FileReader()
    reader.onload = () => {
      const fontStyle = document.createElement('style')
      fontStyle.innerHTML = `@font-face {
        font-family: 'siteplanfont';
        src: url('${reader.result}') format('truetype');
      }`
      document.body.appendChild(fontStyle)
    }
    reader.readAsDataURL(new Blob([res.data]))
  }).catch(() => {
    console.warn('Font not available')
  })

  const viewModels = new Map<string, monaco.editor.ITextModel>()
  await model.fetchFile().then(value => {
    viewModels.set(TextFileModel.MODEL, monaco.editor.createModel(value, 'xml'))
  })

  await model.fetchLayout().then(value => {
    viewModels.set(TextFileModel.LAYOUT, monaco.editor.createModel(value, 'xml'))
  }).catch(erro => {})
  if (viewModels.size > 1) {
    const multitab = new Multitab()
    multitab.init()
    viewModels.forEach((_, modelName) => multitab.createTabItem(modelName))
  }
  app.setViewModels(model, viewModels)
  app.setDefaultViewModel()
}

(window as any).planproJumpToLine = (line: number) => {
  app?.jumpToLine(line)
}

(window as any).planproJumpToGuid = (guid: string, sessionState: TableType) => {
  app?.jumpToGuid(guid, sessionState)
}

(window as any).planproUpdateProblems = () => {
  app?.updateProblems()
}

(window as any).planproSwitchModel = (tabName: string) => {
  app?.switchTab(tabName)
}
