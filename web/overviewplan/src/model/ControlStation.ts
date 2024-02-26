/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

export enum ControlStationType {
  DefaultControl = 'DefaultControl',
  EmergencyControl = 'EmergencyControl',
  EmergencyControlDispose = 'EmergencyControlDispose',
  DefaultControlDispose = 'DefaultControlDispose',
  OnlyRemoteControl = 'OnlyRemoteControl',
  WithoutControl = 'WithoutControl',
  Other = 'Other'
}
