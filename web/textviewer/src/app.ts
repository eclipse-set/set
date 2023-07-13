/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Model, ProblemMessage, TextFileModel } from './model'
import * as monaco from 'monaco-editor'
import jumpToDefinition, { ModelContainer, findObjectDefinitionLineByGUID, getGUIDAt, getModelContainer } from './jumpToGuid'
import { TableType } from '.'
import { seletedTabStyle } from './multitab'

/**
 * Implementation of the text view
 *
 * @author Stuecker
 */
export class App {
  model!: Model
  editor: monaco.editor.IStandaloneCodeEditor
  problems: ProblemMessage[]
  xml!: Document
  pendingSetLine: number | null = null
  viewModels!: Map<string, monaco.editor.ITextModel>
  currentModelName!: string
  setViewModels (model: Model, viewModel: Map<string, monaco.editor.ITextModel>) {
    this.model = model
    this.viewModels = viewModel
  }

  init () {
    // Set up a custom theme to recolor warnings to blue
    monaco.editor.defineTheme('set', {
      base: 'vs',
      inherit: true,
      rules: [],
      colors: {
        'minimap.warningHighlight': '#1f1fff',
        'editorOverviewRuler.warningForeground': '#1a85ff',
        'editorWarning.foreground': '#1a85ff',
        'problemsWarningIcon.foreground': '#1a85ff'
      }
    })
    monaco.editor.setTheme('set')
    const content = document.getElementById('editorContent')
    this.editor = monaco.editor.create(content, {
      value: 'Wird geladen...',
      language: 'xml',
      // Read only
      readOnly: true,
      // Disable large file optimizations
      // PlanPro files are almost always considered large files
      largeFileOptimizations: false,
      // Disable screen reader support
      accessibilitySupport: 'off',
      // Rescale on browser resize
      automaticLayout: true,
      // Increase maximum fold regions
      foldingMaximumRegions: 65000,
      // Disable glyph margin
      glyphMargin: false,
      // Do not make links clickable
      links: false,
      // Render decorations in read-only mode
      renderValidationDecorations: 'on',
      mouseWheelZoom: true,
      domReadOnly: true,
      padding: {
        top: 0,
        bottom: 0
      }
    })

    this.editor.addAction({
      id: 'jump-to-reference',
      label: 'Verweis folgen',
      keybindings: [monaco.KeyCode.F12],
      precondition: null,
      keybindingContext: null,
      contextMenuGroupId: 'navigation',
      contextMenuOrder: 1.5,
      run: () => jumpToDefinition(this.editor, this.viewModels, this.xml)
    })

    this.editor.focus();

    // Set sort order for problems
    (this.editor as any)._configurationService.updateValue('problems.sortOrder', 'position')

    const model = this.editor.getModel()
    this.editor.onDidChangeModel(() => this.editor.focus())

    // Allow access to a parsed XML
    this.editor.onDidChangeModel((e) => {
      const rawText = this.editor.getValue()
      this.xml = new DOMParser().parseFromString(rawText, 'text/xml')
      this.updateErrors()
    })

    // Handle delayed set lines
    model.onDidChangeContent(() => {
      if (this.pendingSetLine) {
        this.jumpToLine(this.pendingSetLine)
        this.pendingSetLine = null
      }
    })
  }

  setDefaultViewModel () {
    // Set defualt file model
    this.switchTab(TextFileModel.SCHNITTSTELLE)
    this.updateProblems()
  }

  jumpToLine (line: number) {
    // If the model is not loaded yet, delay the jump
    if (this.editor.getModel().getLineCount() <= 1) {
      this.pendingSetLine = line
      return
    }

    // Move screen to line
    this.editor.revealLineInCenter(line)
    // Move cursor to line
    this.editor.setPosition({
      lineNumber: line,
      column: 0
    })

    // Open the next error view
    this.editor.trigger('', 'closeMarkersNavigation', {})
    this.editor.trigger('', 'editor.action.marker.next', {})
  }

  jumpToGuid (guid: string, sessionState: TableType) {
    const line = findObjectDefinitionLineByGUID(guid, this.editor.getModel(), this.xml, this.getModelContainer(sessionState))
    if (line) {
      // Jump to the line
      this.editor.revealLineInCenter(line)
      this.editor.setPosition({
        lineNumber: line,
        column: 0
      })
    }
  }

  getModelContainer (sessionState: TableType): ModelContainer {
    const currentModel = this.editor.getModel()
    let currentModelName = ''
    this.viewModels.forEach((model, modelName) => {
      if (currentModel === model) {
        console.log(modelName)
        currentModelName = modelName
      }
    })
    if (currentModelName === TextFileModel.LAYOUT) {
      return ModelContainer.LAYOUT
    }
    switch (sessionState) {
      case TableType.INITIAL:
        return ModelContainer.INITIAL
      case TableType.DIFF:
        return getModelContainer(this.editor, this.editor.getPosition()) ? ModelContainer.INITIAL : ModelContainer.LAYOUT
      default:
        return ModelContainer.FINAL
    }
  }

  updateProblems () {
    this.model.fetchProblems().then(value => { this.problems = value; this.updateErrors() })
  }

  updateErrors () {
    const model = this.editor.getModel()
    if (!model || !this.problems) {
      return
    }
    const markers = this.problems
      .filter(entry => {
        if (this.currentModelName === TextFileModel.LAYOUT) {
          return entry.model === TextFileModel.LAYOUT
        }
        return entry.model !== TextFileModel.LAYOUT
      })
      .map(entry => {
        return {
          severity: (1 << entry.severity) as monaco.MarkerSeverity,
          startLineNumber: entry.line,
          startColumn: 0,
          endLineNumber: entry.line,
          endColumn: 99999,
          message: entry.message,
          source: entry.type
        }
      })

    monaco.editor.setModelMarkers(model, 'Validation', markers)
  }

  switchTab (tabName: string) {
    const selectedModel = this.viewModels.get(tabName)
    if (selectedModel !== undefined && this.editor.getModel() !== selectedModel) {
      this.currentModelName = tabName
      this.editor.setModel(selectedModel)
      seletedTabStyle(tabName)
    }
  }
}
