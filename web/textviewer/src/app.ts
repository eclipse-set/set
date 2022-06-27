/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { Model, ProblemMessage } from './model'
import * as monaco from 'monaco-editor'

/**
 * Implementation of the text view
 *
 * @author Stuecker
 */
export class App {
  model: Model = new Model()
  editor: monaco.editor.IStandaloneCodeEditor
  problems: ProblemMessage[]

  init () {
    this.editor = monaco.editor.create(document.body, {
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
    this.editor.focus();

    // Set sort order for problems
    (this.editor as any)._configurationService.updateValue('problems.sortOrder', 'position')

    const model = this.editor.getModel()

    model.onDidChangeContent(() => this.updateErrors())
    model.onDidChangeContent(() => this.editor.focus())

    this.model.fetchFile().then(value => this.editor.setValue(value))
    this.model.fetchProblems().then(value => { this.problems = value; this.updateErrors() })
  }

  jumpToLine (line: number) {
    // Move screen to line
    this.editor.revealLineInCenter(line)
    // Move cursor to line
    this.editor.setPosition({
      lineNumber: line,
      column: 0
    })

    // Open the next error view
    this.editor.trigger('', 'editor.action.marker.next', {})
  }

  updateErrors () {
    const model = this.editor.getModel()
    if (!model || !this.problems) {
      return
    }
    const markers = this.problems.map(entry => {
      return {
        severity: (1 << entry.severity) as monaco.MarkerSeverity,
        startLineNumber: entry.lineStart,
        startColumn: entry.columnStart,
        endLineNumber: entry.lineEnd,
        endColumn: entry.columnEnd,
        message: entry.message,
        source: entry.type
      }
    })

    monaco.editor.setModelMarkers(model, 'Validation', markers)
  }
}
