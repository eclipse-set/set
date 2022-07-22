/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { App } from './app'

let app : App | null = null;
(window as any).initApp = () => {
  app = new App()
  app.init()
}

(window as any).planproJumpToLine = (line: number) => {
  app?.jumpToLine(line)
}

(window as any).planproUpdateProblems = () => {
  app?.updateProblems()
}
