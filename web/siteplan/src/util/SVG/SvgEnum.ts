/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

export enum SVGMast {
    ShortMount = 'KurzMast',
    LongMount = 'LangMast',
    CoverWallMount = 'Wand-DeckenMast'
}

export enum HauptVorSignalGroup {
    KsSys = 'KS-System',
    HlSvSys = 'Hl-SV-System',
    HvSys = 'HV-System'
}

export enum AndereSignalGroup {
    NebenSignale = 'Nebensignale',
    ZusatzSignale = 'Zusatzsignale',
    LangsamfahrSignale = 'Langsamfahrsignale',
    RangierdienstSignale = 'Rangierdienstsignale',
    SchutzSignale = 'Schutzsignale',
    TsSignale = 'TsSignale',
    WeichenSignale = 'Weichensignale',
    Orientierungszeichen = 'Orientierungszeichen',
    Zuordnungstafel = 'Zuordnungstafel',
    Richtungspfeil = 'Richtungspfeil',
    BahnuebergangsSignale = 'BUE'
}

export enum OtherSVGCatalog {
  FMAComponent = 'FMAKomponente',
  PZB = 'PZB',
  TrackLock = 'Gleissperren',
  TrackClose = 'Gleisabschuluesse',
  ExternalElementControl = 'AEA',
  LockKey = 'Schluesselsperren',
  Others = 'Sonstige'
}

export enum AnchorPoint {
    top = 'mastAnkerTop',
    bottom = 'mastAnkerBottom'
}

export enum SignalBrueckeudAusleger {
    bruecker = 'SignalBruecker',
    ausleger = 'SignalAusleger'
}

export enum ZusatzSignalBottom {
    zs2 = 'Zs2v',
    zs31 = 'Zs3_1v',
    zs32 = 'Zs3_2v',
    zp9 = 'Zp9'
}
