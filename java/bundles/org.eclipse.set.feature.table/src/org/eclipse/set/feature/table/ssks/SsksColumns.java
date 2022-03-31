/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssks;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Ssls-Columns.<br>
 * see {@link ColumnDescriptor}
 * 
 * @author rumpf
 *
 */
public final class SsksColumns extends AbstractTableColumns {

	/**
	 * Q: Ssks.konstruktive_Merkmale.Anordnung.Regelzeichnung
	 */
	public final ColumnDescriptor anordnung_regelzeichnung;

	/**
	 * AP: Ssks.Sonstiges.automatischer_Betrieb
	 */
	public final ColumnDescriptor automatischer_betrieb;

	/**
	 * P: Ssks.konstruktive_Merkmale.Anordnung.Befestigung
	 */
	public final ColumnDescriptor befestigung;

	/**
	 * AS: Ssks.Sonstiges.Besetzte_Ausfahrt
	 */
	public final ColumnDescriptor Besetzte_Ausfahrt;

	/**
	 * A: Ssks.Bezeichnung_Signal
	 */
	public final ColumnDescriptor bezeichnung_signal;

	/**
	 * Z: Ssks.Anschluss.Dauerhaft_Nacht
	 */
	public final ColumnDescriptor dauerhaft_nacht;

	/**
	 * AQ: Ssks.Sonstiges.Dunkelschaltung
	 */
	public final ColumnDescriptor dunkelschaltung;

	/**
	 * AR: Ssks.Sonstiges.Durchfahrt_erlaubt
	 */
	public final ColumnDescriptor durchfahrt_erlaubt;

	/**
	 * N: Ssks.Standortmerkmale.Ausrichtung.Entfernung
	 */
	public final ColumnDescriptor entfernung;

	/**
	 * C: Ssks.Signal_Art.Fiktives_Signal
	 */
	public final ColumnDescriptor fiktives_Signal;

	/**
	 * V: Ssks.konstruktive_Merkmale.Fundament.Hoehe
	 */
	public final ColumnDescriptor fundament_hoehe;

	/**
	 * U: Ssks.konstruktive_Merkmale.Fundament.Regelzeichnung
	 */
	public final ColumnDescriptor fundament_regelzeichnung;

	/**
	 * E: Ssks.Standortmerkmale.Standort.km
	 */
	public final ColumnDescriptor km;

	/**
	 * G: Ssks.Standortmerkmale.Lichtraumprofil
	 */
	public final ColumnDescriptor lichtraumprofil;

	/**
	 * AT: Ssks.Sonstiges.Loeschung_Zs_1__Zs_7
	 */
	public final ColumnDescriptor loeschung_Zs_1__Zs_7;

	/**
	 * I: Ssks.Standortmerkmale.Abstand_Mastmitte.links
	 */
	public final ColumnDescriptor mastmitte_links;

	/**
	 * J: Ssks.Standortmerkmale.Abstand_Mastmitte.rechts
	 */
	public final ColumnDescriptor mastmitte_rechts;

	/**
	 * AN: Ssks.Signalisierung.Mastschild
	 */
	public final ColumnDescriptor mastschild;

	/**
	 * AM: Ssks.Signalisierung.Nachgeordnetes_Signal
	 */
	public final ColumnDescriptor nachgeordnetes_Signal;

	/**
	 * R: Ssks.konstruktive_Merkmale.Obere_Lichtpunkthoehe
	 */
	public final ColumnDescriptor obere_lichtpunkthoehe;

	/**
	 * B: Ssks.Signal_Art.Reales_Signal
	 */
	public final ColumnDescriptor reales_Signal;

	/**
	 * O: Ssks.Standortmerkmale.Ausrichtung.Richtpunkt
	 */
	public final ColumnDescriptor richtpunkt;

	/**
	 * W: Ssks.Anschluss.Schaltkasten.Bezeichnung
	 */
	public final ColumnDescriptor schaltkasten_bezeichnung;

	/**
	 * X: Ssks.Anschluss.Schaltkasten.Entfernung
	 */
	public final ColumnDescriptor schaltkasten_entfernung;

	/**
	 * Y: Ssks.Anschluss.Schaltkasten_separat.Bezeichnung
	 */
	public final ColumnDescriptor schaltkasten_separat_bezeichnung;

	/**
	 * AA: Ssks.Signalisierung.Signalbegriffe_Schirm.Hp_Hl
	 */
	public final ColumnDescriptor schirm_hp_hl;

	/**
	 * AB: Ssks.Signalisierung.Signalbegriffe_Schirm.Ks_Vr
	 */
	public final ColumnDescriptor schirm_ks_vr;

	/**
	 * AD: Ssks.Signalisierung.Signalbegriffe_Schirm.Ra_Sh
	 */
	public final ColumnDescriptor schirm_ra_sh;

	/**
	 * AC: Ssks.Signalisierung.Signalbegriffe_Schirm.Zl_Kl
	 */
	public final ColumnDescriptor schirm_zl_kl;

	/**
	 * AE: Ssks.Signalisierung.Signalbegriffe_Schirm.Zs
	 */
	public final ColumnDescriptor schirm_zs;

	/**
	 * M: Ssks.Standortmerkmale.Sichtbarkeit.Ist
	 */
	public final ColumnDescriptor sichtbarkeit_ist;

	/**
	 * L: Ssks.Standortmerkmale.Sichtbarkeit.Mindest
	 */
	public final ColumnDescriptor sichtbarkeit_mindest;

	/**
	 * K: Ssks.Standortmerkmale.Sichtbarkeit.Soll
	 */
	public final ColumnDescriptor sichtbarkeit_soll;

	/**
	 * F: Ssks.Standortmerkmale.Sonstige_zulaessige_Anordnung
	 */
	public final ColumnDescriptor sonstige_zulaessige_anordnung;

	/**
	 * D: Ssks.Standortmerkmale.Standort.Strecke
	 */
	public final ColumnDescriptor strecke;

	/**
	 * S: Ssks.konstruktive_Merkmale.Streuscheibe.Art
	 */
	public final ColumnDescriptor streuscheibe_art;

	/**
	 * T: Ssks.konstruktive_Merkmale.Streuscheibe.Stellung
	 */
	public final ColumnDescriptor streuscheibe_stellung;

	/**
	 * H: Ssks.Standortmerkmale.Ueberhoehung
	 */
	public final ColumnDescriptor ueberhoehung;

	/**
	 * AU: Ssks.Sonstiges.Ueberwachung.Zs_2
	 */
	public final ColumnDescriptor ueberwachung_zs_2;

	/**
	 * AV: Ssks.Sonstiges.Ueberwachung.Zs_2v
	 */
	public final ColumnDescriptor ueberwachung_zs_2v;

	/**
	 * AO: Ssks.Signalisierung.Vorsignaltafel.Regelzeichnung
	 */
	public final ColumnDescriptor vorsignaltafel_regelzeichnung;

	/**
	 * AL: Ssks.Signalisierung.Signalbegriffe_Zusatzanzeiger.Kombination
	 */
	public final ColumnDescriptor zusatzanzeiger_kombination;

	/**
	 * AK: Ssks.Signalisierung.Signalbegriffe_Zusatzanzeiger.Zp
	 */
	public final ColumnDescriptor zusatzanzeiger_zp;

	/**
	 * AJ: Ssks.Signalisierung.Signalbegriffe_Zusatzanzeiger.Zs
	 */
	public final ColumnDescriptor zusatzanzeiger_zs;

	/**
	 * AF: Ssks.Signalisierung.Signalbegriffe_Zusatzanzeiger.Zs_2
	 */
	public final ColumnDescriptor zusatzanzeiger_zs_2;

	/**
	 * AG: Ssks.Signalisierung.Signalbegriffe_Zusatzanzeiger.Zs_2v
	 */
	public final ColumnDescriptor zusatzanzeiger_zs_2v;

	/**
	 * AH: Ssks.Signalisierung.Signalbegriffe_Zusatzanzeiger.Zs_3
	 */
	public final ColumnDescriptor zusatzanzeiger_zs_3;

	/**
	 * AI: Ssks.Signalisierung.Signalbegriffe_Zusatzanzeiger.Zs_3v
	 */
	public final ColumnDescriptor zusatzanzeiger_zs_3v;

	/**
	 * @param messages
	 *            the i8n messages
	 */
	public SsksColumns(final Messages messages) {
		super(messages);
		bezeichnung_signal = createNew(
				messages.SsksTableView_HeadingBezeichnungSignal);
		reales_Signal = createNew(
				messages.SsksTableView_HeadingSignalArtRealesSignal);
		fiktives_Signal = createNew(
				messages.SsksTableView_HeadingSignalArtFiktivesSignal);
		strecke = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleStrecke);
		km = createNew(messages.Common_UnitKilometer);
		sonstige_zulaessige_anordnung = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleSonstigeZulaessigeAnordnung);
		lichtraumprofil = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleLichtraumprofil);
		ueberhoehung = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleUeberhoehung);
		mastmitte_links = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleAbstandGleismitteMastmitteLinks);
		mastmitte_rechts = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleAbstandGleismitteMastmitteRechts);
		sichtbarkeit_soll = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleSichtbarkeitSoll);
		sichtbarkeit_mindest = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleSichtbarkeitMindest);
		sichtbarkeit_ist = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleSichtbarkeitIst);
		entfernung = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleAusrichtungEntfernung);
		richtpunkt = createNew(
				messages.SsksTableView_HeadingStandortmerkmaleRichtpunkt);
		befestigung = createNew(
				messages.SsksTableView_HeadingKonstruktivmerkmaleAnordnungBefestigung);
		anordnung_regelzeichnung = createNew(
				messages.SsksTableView_HeadingKonstruktivmerkmaleAnordnungRegelzeichnung);
		obere_lichtpunkthoehe = createNew(
				messages.SsksTableView_HeadingKonstruktivmerkmaleObereLichtpunkthoehe);
		streuscheibe_art = createNew(
				messages.SsksTableView_HeadingKonstruktivmerkmaleStreuscheibeArt);
		streuscheibe_stellung = createNew(
				messages.SsksTableView_HeadingKonstruktivmerkmaleStreuscheibeStellung);
		fundament_regelzeichnung = createNew(
				messages.SsksTableView_HeadingKonstruktivmerkmaleFundamentRegelzeichnung);
		fundament_hoehe = createNew(
				messages.SsksTableView_HeadingKonstruktivmerkmaleFundamentHoeheUSO);
		schaltkasten_bezeichnung = createNew(
				messages.SsksTableView_HeadingAnschlussSchaltkastenBezeichnung);
		schaltkasten_entfernung = createNew(
				messages.SsksTableView_HeadingAnschlussSchaltkastenEntfernung);
		schaltkasten_separat_bezeichnung = createNew(
				messages.SsksTableView_HeadingAnschlussSeparaterSchaltkastenBezeichnung);
		dauerhaft_nacht = createNew(
				messages.SsksTableView_HeadingAnschlussDauerhaftNachtschaltung);
		schirm_hp_hl = createNew(
				messages.SsksTableView_HeadingSignalisierungSchirmHpHl);
		schirm_ks_vr = createNew(
				messages.SsksTableView_HeadingSignalisierungSchirmKsVr);
		schirm_zl_kl = createNew(
				messages.SsksTableView_HeadingSignalisierungSchirmZlKl);
		schirm_ra_sh = createNew(
				messages.SsksTableView_HeadingSignalisierungSchirmRaSh);
		schirm_zs = createNew(
				messages.SsksTableView_HeadingSignalisierungSchirmZs);
		zusatzanzeiger_zs_2 = createNew(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeigerZs2);
		zusatzanzeiger_zs_2v = createNew(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeigerZs2v);
		zusatzanzeiger_zs_3 = createNew(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeigerZs3);
		zusatzanzeiger_zs_3v = createNew(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeigerZs3v);
		zusatzanzeiger_zs = createNew(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeigerZs);
		zusatzanzeiger_zp = createNew(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeigerZp);
		zusatzanzeiger_kombination = createNew(
				messages.SsksTableView_HeadingSignalisierungZusatzanzeigerKombination);
		nachgeordnetes_Signal = createNew(
				messages.SsksTableView_HeadingNachgeordnetesSignal);
		mastschild = createNew(messages.SsksTableView_HeadingMastschild);
		vorsignaltafel_regelzeichnung = createNew(
				messages.SsksTableView_HeadingVorsignaltafelRegelzeichnung);

		automatischer_betrieb = createNew(
				messages.SsksTableView_HeadingSonstigesAutomatischeFahrtstellung);
		dunkelschaltung = createNew(
				messages.SsksTableView_HeadingSonstigesDunkelschaltung);
		durchfahrt_erlaubt = createNew(
				messages.SsksTableView_HeadingSonstigesDurchfahrtErlaubt);
		loeschung_Zs_1__Zs_7 = createNew(
				messages.SsksTableView_HeadingSonstigesLoeschungZs1Zs7);
		ueberwachung_zs_2 = createNew(
				messages.SsksTableView_HeadingSonstigesUeberwachungZs2);
		ueberwachung_zs_2v = createNew(
				messages.SsksTableView_HeadingSonstigesUeberwachungZs2v);

		Besetzte_Ausfahrt = createNew(
				messages.SsksTableView_Sonstiges_Besetzte_Ausfahrt);
	}
}