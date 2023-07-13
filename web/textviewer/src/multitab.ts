/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
export class Multitab {
  root!: HTMLElement
  init () {
    this.root = document.createElement('ul')
    this.root.style.margin = '0'
    this.root.style.padding = '0'
    this.root.style.overflow = 'hidden'
    this.root.style.display = 'flex'
    this.root.style.justifyContent = 'flex-start'
    this.root.style.alignItems = 'stretch'
    this.root.style.textAlign = 'center'
    this.root.style.background = 'gray'
    this.root.style.borderWidth = '1px 1px 0px 1px'
    this.root.style.borderStyle = 'solid'

    const contentElement = document.getElementById('editorContent')
    if (contentElement) {
      document.body.insertBefore(this.root, contentElement)
    }
  }

  createTabItem (tabName: string) {
    const tab = document.createElement('li')
    document.styleSheets[0].insertRule('li:hover { background: #646060;}')
    tab.setAttribute('class', 'editortab')
    tab.setAttribute('id', tabName)
    tab.textContent = tabName
    tab.style.cursor = 'pointer'
    tab.style.display = 'flex'
    tab.style.alignItems = 'center'
    tab.style.padding = '8px'
    tab.style.color = 'white'
    tab.style.borderRight = '0.5px solid rgb(190, 190, 190)'
    tab.onclick = () => {
      (window as any).planproSwitchModel(tabName)
    }
    this.root.appendChild(tab)
  }
}

export function seletedTabStyle (tabName: string) {
  const editorTabs = document.getElementsByClassName('editortab')
  if (editorTabs.length < 2) {
    return
  }
  for (const tab of editorTabs) {
    if (tab.getAttribute('id') === tabName) {
      (tab as HTMLElement).style.background = 'white';
      (tab as HTMLElement).style.color = 'black';
      (tab as HTMLElement).style.borderTop = '2px solid blue'
    } else {
      (tab as HTMLElement).style.background = 'none';
      (tab as HTMLElement).style.color = 'white';
      (tab as HTMLElement).style.borderTop = 'none'
    }
  }
}
