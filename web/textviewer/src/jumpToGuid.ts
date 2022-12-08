/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import * as monaco from 'monaco-editor'

/**
 * If the editor is currently at a position within an ID-Reference, this utility
 * function jumps to the associated object definition
 *
 * @param editor The editor
 * @param xml The xml document
 */
export default function jumpToDefinition (editor: monaco.editor.IStandaloneCodeEditor, xml: Document) {
  // Extract the GUID at the editor position
  const position = editor.getPosition()
  const guid = getGUIDAt(editor, position)
  if (!guid) {
    return
  }

  // Find the corresponding line number for the definition
  const line = findObjectDefinitionLineByGUID(guid, editor, xml, isInStartContainer(editor, position))
  if (line) {
    // Jump to the line
    editor.revealLineInCenter(line)
    editor.setPosition({
      lineNumber: line,
      column: 0
    })
  }
}

/**
 * @param editor the editor
 * @param pos the position of the cursor
 * @returns whether the position is contained within a LST_Zustand_Start
 */
export function isInStartContainer (editor: monaco.editor.IStandaloneCodeEditor, pos: monaco.Position) {
  const lstState = editor.getModel().findPreviousMatch('<LST_Zustand_(Start|Ziel)>', pos, true, false, null, true)
  if (lstState) {
    return lstState.matches[1] === 'Start'
  }
}

/**
 * Extracts the GUID from an ID-Reference
 * @param editor the editor
 * @param pos the position. Must be at the start of the ID tag, otherwise the behavior is undefined
 * @returns the GUID or null on failure
 */
function getGUIDOfIDTag (editor: monaco.editor.IStandaloneCodeEditor, pos: monaco.Position) {
  // Use a regex search to extract the GUID
  const text = editor.getModel().findNextMatch(
    '<ID_.*>[\\s\\r]*<Wert>(.*)</Wert>[\\s\\r]*</ID_.*>',
    pos,
    true,
    false,
    null, true)
  if (text) { return text.matches[1] } else { return null }
}

/**
 * Finds the GUID of an ID-reference if pos is anywhere within the ID Reference XML tag
 *
 * @param editor the editor
 * @param pos the position
 * @returns the GUID of the ID-Reference or null if the position is not at any ID-Reference
 */
function getGUIDAt (editor: monaco.editor.IStandaloneCodeEditor, pos: monaco.Position): string | null {
  // Find the previous opening XML bracket
  const indexOpeningBracket = editor.getModel().findPreviousMatch('<', pos, false, false, null, true)
  if (!indexOpeningBracket) {
    return null
  }

  // Get the full line from the editor model
  const tag = editor.getModel().getValueInRange(
    {
      startLineNumber: indexOpeningBracket.range.startLineNumber,
      endLineNumber: indexOpeningBracket.range.startLineNumber + 1,
      startColumn: indexOpeningBracket.range.startColumn,
      endColumn: indexOpeningBracket.range.endColumn
    }
  )

  // If the tag is <Wert>, </Wert> or </ID_*>, we need to move to the previous opening bracket
  // therefore recurse
  if (tag.startsWith('<Wert>') || tag.startsWith('</Wert>') || tag.startsWith('</ID_')) {
    return getGUIDAt(editor, indexOpeningBracket.range.getStartPosition())
  }
  // If an ID-Reference has been found, extract the GUID
  if (tag.startsWith('<ID_')) {
    return getGUIDOfIDTag(editor, indexOpeningBracket.range.getStartPosition())
  }
  return null
}

/**
 *
 * @param guid the GUID to search for
 * @param editor the editor
 * @param xml  the XML document
 * @param startContainer whether to look in LST_Zustand_Start or LST_Zustand_Ziel
 * @returns the line of the object definition or null
 */
export function findObjectDefinitionLineByGUID (guid: string, editor: monaco.editor.IStandaloneCodeEditor, xml: Document, startContainer: boolean) {
  // Find the XML node via XPath
  const xpath = startContainer
    ? `.//LST_Zustand_Start/Container/*/Identitaet/Wert[text()="${guid}"]`
    : `.//LST_Zustand_Ziel/Container/*/Identitaet/Wert[text()="${guid}"]`
  const node = xml.evaluate(xpath, xml.getRootNode()).iterateNext().parentElement.parentElement
  const rawText = editor.getValue()

  // Iterate through nodes simultanously with regex and the node list to find the
  // raw offset within the file
  let offset = 0
  const regex = new RegExp(`<${node.tagName}\\W`, 'g')
  for (const item of xml.getElementsByTagName(node.tagName)) {
    offset = regex.exec(rawText).index
    if (item === node) {
      break
    }
  }

  // Count lines
  let line = 0
  for (let i = 0; i < rawText.substring(0, offset).length; i++) {
    if (rawText[i] === '\n') {
      line++
    }
  }
  return line + 1
}
