/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.messages;

import org.eclipse.osgi.service.localization.BundleLocalization;
import org.eclipse.set.core.AbstractMessageService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Translations.
 * 
 * @author Schaefer
 */
@Component(service = Messages.class)
public class Messages extends AbstractMessageService {

	@Reference
	private void bindBundleLocalization(
			final BundleLocalization bundleLocalization)
			throws IllegalArgumentException, IllegalAccessException {
		super.setupLocalization(bundleLocalization);
	}

	/**
	 * Ssbb (Bedieneinrichtungstabelle BÜ)
	 */
	public String SsbbDescriptionService_ViewName;

	/**
	 * Bedieneinrichtungstabelle BÜ
	 */
	public String SsbbDescriptionService_ViewTooltip;

	/**
	 * Bedieneinrichtungstabelle BÜ (Ssbb)
	 */
	public String SsbbTableView_Heading;

	/**
	 * Bedieneinrichtungstabelle BÜ
	 */
	public String ToolboxTableNameSsbbLong;

	/**
	 * 23/13
	 */
	public String ToolboxTableNameSsbbPlanningNumber;

	/**
	 * Ssbb
	 */
	public String ToolboxTableNameSsbbShort;

	/**
	 * 819.0102A27
	 */
	public String ToolboxTableNameSsbbRil;

	/**
	 * Ssit (Bedieneinrichtungstabelle Stw)
	 */
	public String SsitDescriptionService_ViewName;

	/**
	 * Bedieneinrichtungstabelle Stw
	 */
	public String SsitDescriptionService_ViewTooltip;

	/**
	 * Bedieneinrichtungstabelle Stw
	 */
	public String ToolboxTableNameSsitLong;

	/**
	 * 10/46
	 */
	public String ToolboxTableNameSsitPlanningNumber;

	/**
	 * 819.0102A26
	 */
	public String ToolboxTableNameSsitRil;

	/**
	 * Bedieneinrichtungstabelle Stw
	 */
	public String SsitTableView_Heading;

	/**
	 * Ssit
	 */
	public String ToolboxTableNameSsitShort;

	/**
	 * Sska (Elementansteuertabelle)
	 */
	public String SskaDescriptionService_ViewName;

	/**
	 * Elementansteuertabelle
	 */
	public String SskaDescriptionService_ViewTooltip;

	/**
	 * Elementansteuertabelle
	 */
	public String ToolboxTableNameSskaLong;

	/**
	 * 10/03
	 */
	public String ToolboxTableNameSskaPlanningNumber;

	/**
	 * Sska
	 */
	public String ToolboxTableNameSskaShort;

	/**
	 * Elementansteuertabelle (Sska)
	 */
	public String SskaTableView_Heading;

	/**
	 * 819.0102A24
	 */
	public String ToolboxTableNameSskaRil;

	/**
	 * Sskf (Freimeldetabelle)
	 */
	public String SskfDescriptionService_ViewName;

	/**
	 * Freimeldetabelle
	 */
	public String SskfDescriptionService_ViewTooltip;

	/**
	 * Freimeldetabelle
	 */
	public String ToolboxTableNameSskfLong;

	/**
	 * 10/19
	 */
	public String ToolboxTableNameSskfPlanningNumber;

	/**
	 * Sskf
	 */
	public String ToolboxTableNameSskfShort;

	/**
	 * Freimeldetabelle (Sskf)
	 */
	public String SskfTableView_Heading;

	/**
	 * 819.0102A12
	 */
	public String ToolboxTableNameSskfRil;

	/**
	 * Sskg (Gleisschaltmitteltabelle)
	 */
	public String SskgDescriptionService_ViewName;

	/**
	 * Gleisschaltmitteltabelle
	 */
	public String SskgDescriptionService_ViewTooltip;

	/**
	 * Gleisschaltmitteltabelle
	 */
	public String ToolboxTableNameSskgLong;

	/**
	 * 10/19
	 */
	public String ToolboxTableNameSskgPlanningNumber;

	/**
	 * Sskg
	 */
	public String ToolboxTableNameSskgShort;

	/**
	 * Gleisschaltmitteltabelle (Sskg)
	 */
	public String SskgTableView_Heading;

	/**
	 * 819.0102A13
	 */
	public String ToolboxTableNameSskgRil;

	/**
	 * Ssko (Schlosstabelle)
	 * 
	 */
	public String SskoDescriptionService_ViewName;

	/**
	 * Schlosstabelle
	 */
	public String SskoDescriptionService_ViewTooltip;

	/**
	 * Schlosstabelle
	 */
	public String ToolboxTableNameSskoLong;

	/**
	 * 10/27
	 */
	public String ToolboxTableNameSskoPlanningNumber;

	/**
	 * Ssko
	 */
	public String ToolboxTableNameSskoShort;

	/**
	 * Schlosstabelle (Ssko)
	 */
	public String SskoTableView_Heading;

	/**
	 * 819.0102A28
	 */
	public String ToolboxTableNameSskoRil;

	/**
	 * Sskp – PZB-Tabelle
	 */
	public String SskpDescriptionService_ViewName;

	/**
	 * PZB-Tabelle
	 */
	public String SskpDescriptionService_ViewTooltip;

	/**
	 * PZB-Tabelle
	 */
	public String ToolboxTableNameSskpLong;

	/**
	 * 10/38
	 */
	public String ToolboxTableNameSskpPlanningNumber;

	/**
	 * Sskp
	 */
	public String ToolboxTableNameSskpShort;

	/**
	 * PZB-Tabelle (Sskp)
	 */
	public String SskpTableView_Heading;

	/**
	 * 819.0102A30
	 */
	public String ToolboxTableNameSskpRil;

	/**
	 * Ssks (Signaltabelle)
	 */
	public String SsksDescriptionService_ViewName;

	/**
	 * Signaltabelle
	 */
	public String SsksDescriptionService_ViewTooltip;

	/**
	 * Signaltabelle
	 */
	public String ToolboxTableNameSsksLong;

	/**
	 * 10/18
	 */
	public String ToolboxTableNameSsksPlanningNumber;

	/**
	 * Ssks
	 */
	public String ToolboxTableNameSsksShort;

	/**
	 * Signaltabelle (Ssks)
	 */
	public String SsksTableView_Heading;

	/**
	 * 819.0102A11
	 */
	public String ToolboxTableNameSsksRil;

	/**
	 * Sskt (Tabelle der Technik- und Bedienstandorte)
	 */
	public String SsktDescriptionService_ViewName;

	/**
	 * Tabelle der Technik- und Bedienstandorte
	 */
	public String SsktDescriptionService_ViewTooltip;

	/**
	 * Tabelle der Technik- und Bedienstandorte
	 */
	public String ToolboxTableNameSsktLong;

	/**
	 * 10/03
	 */
	public String ToolboxTableNameSsktPlanningNumber;

	/**
	 * Sskt
	 */
	public String ToolboxTableNameSsktShort;

	/**
	 * Tabelle der Technik- und Bedienstandorte (Sskt)
	 */
	public String SsktTableView_Heading;

	/**
	 * 819.0102A29
	 */
	public String ToolboxTableNameSsktRil;

	/**
	 * Sskw (Weichentabelle)
	 */
	public String SskwDescriptionService_ViewName;

	/**
	 * Weichentabelle
	 */
	public String SskwDescriptionService_ViewTooltip;

	/**
	 * Weichentabelle
	 */
	public String ToolboxTableNameSskwLong;

	/**
	 * 10/23
	 */
	public String ToolboxTableNameSskwPlanningNumber;

	/**
	 * Sskw
	 */
	public String ToolboxTableNameSskwShort;

	/**
	 * Weichentabelle (Sskw)
	 */
	public String SskwTableView_Heading;

	/**
	 * 819.0102A20
	 */
	public String ToolboxTableNameSskwRil;

	/**
	 * Ssla (Tabelle der aneinandergereihten Fahrstraßen)
	 */
	public String SslaDescriptionService_ViewName;

	/**
	 * Tabelle der aneinandergereihten Fahrstraßen
	 */
	public String SslaDescriptionService_ViewTooltip;

	/**
	 * Tabelle der aneinandergereihten Fahrstraßen
	 */
	public String ToolboxTableNameSslaLong;

	/**
	 * 10/21
	 */
	public String ToolboxTableNameSslaPlanningNumber;

	/**
	 * Ssla
	 */
	public String ToolboxTableNameSslaShort;

	/**
	 * Aneinandergereihte Fahrstraßentabelle (Ssla)
	 */
	public String SslaTableView_Heading;

	/**
	 * 819.0102A16
	 */
	public String ToolboxTableNameSslaRil;

	/**
	 * Sslb (Streckenblocktabelle)
	 */
	public String SslbDescriptionService_ViewName;

	/**
	 * Streckenblocktabelle
	 */
	public String SslbDescriptionService_ViewTooltip;

	/**
	 * Streckenblocktabelle
	 */
	public String ToolboxTableNameSslbLong;

	/**
	 * 10/33
	 */
	public String ToolboxTableNameSslbPlanningNumber;

	/**
	 * Sslb
	 */
	public String ToolboxTableNameSslbShort;

	/**
	 * Streckenblocktabelle (Sslb)
	 */
	public String SslbTableView_Heading;

	/**
	 * Ssld (Durchrutschweg- und Gefahrpunkttabelle)
	 */
	public String SsldDescriptionService_ViewName;

	/**
	 * 819.0102A22
	 */
	public String ToolboxTableNameSslbRil;

	/**
	 * Durchrutschweg- und Gefahrpunkttabelle
	 */
	public String SsldDescriptionService_ViewTooltip;

	/**
	 * Durchrutschweg- und Gefahrpunkttabelle
	 */
	public String ToolboxTableNameSsldLong;

	/**
	 * 10/20
	 */
	public String ToolboxTableNameSsldPlanningNumber;

	/**
	 * Ssld
	 */
	public String ToolboxTableNameSsldShort;

	/**
	 * Durchrutschwegtabelle (Ssld)
	 */
	public String SsldTableView_Heading;

	/**
	 * 819.0102A14
	 */
	public String ToolboxTableNameSsldRil;

	/**
	 * Sslf (Flankenschutztabelle)
	 */
	public String SslfDescriptionService_ViewName;

	/**
	 * Flankenschutztabelle
	 */
	public String SslfDescriptionService_ViewTooltip;

	/**
	 * Flankenschutztabelle
	 */
	public String ToolboxTableNameSslfLong;

	/**
	 * 10/24
	 */
	public String ToolboxTableNameSslfPlanningNumber;

	/**
	 * Sslf
	 */
	public String ToolboxTableNameSslfShort;

	/**
	 * Flankenschutztabelle (Sslf)
	 */
	public String SslfTableView_Heading;

	/**
	 * 819.0102A19
	 */
	public String ToolboxTableNameSslfRil;

	/**
	 * Ssli (Inselgleistabelle)
	 */
	public String SsliDescriptionService_ViewName;

	/**
	 * Inselgleistabelle
	 */
	public String SsliDescriptionService_ViewTooltip;

	/**
	 * Inselgleistabelle
	 */
	public String ToolboxTableNameSsliLong;

	/**
	 * 10/21
	 */
	public String ToolboxTableNameSsliPlanningNumber;

	/**
	 * Ssli
	 */
	public String ToolboxTableNameSsliShort;

	/**
	 * Inselgleistabelle (Ssli)
	 */
	public String Ssli_Heading;

	/**
	 * 819.0102A18
	 */
	public String ToolboxTableNameSsliRil;

	/**
	 * Ssln (Nahbedienungstabelle)
	 */
	public String SslnDescriptionService_ViewName;

	/**
	 * Nahbedienungstabelle
	 */
	public String SslnDescriptionService_ViewTooltip;

	/**
	 * Nahbedienungstabelle
	 */
	public String ToolboxTableNameSslnLong;

	/**
	 * 10/30
	 */
	public String ToolboxTableNameSslnPlanningNumber;

	/**
	 * Ssln
	 */
	public String ToolboxTableNameSslnShort;

	/**
	 * Nahbedienungstabelle (Ssln)
	 */
	public String SslnTableView_Heading;

	/**
	 * 819.0102A25
	 */
	public String ToolboxTableNameSslnRil;

	/**
	 * Sslr (Rangierstraßentabelle)
	 */
	public String SslrDescriptionService_ViewName;

	/**
	 * Rangierstraßentabelle
	 */
	public String SslrDescriptionService_ViewTooltip;

	/**
	 * Rangierstraßentabelle
	 */
	public String ToolboxTableNameSslrLong;

	/**
	 * 10/22
	 */
	public String ToolboxTableNameSslrPlanningNumber;

	/**
	 * Sslr
	 */
	public String ToolboxTableNameSslrShort;

	/**
	 * Rangierstraßentabelle (Sslr)
	 */
	public String Sslr_Heading;

	/**
	 * 819.0102A17
	 */
	public String ToolboxTableNameSslrRil;

	/**
	 * Sslw (Zwieschutzweichentabelle)
	 */
	public String SslwDescriptionService_ViewName;

	/**
	 * Zwieschutzweichentabelle
	 */
	public String SslwDescriptionService_ViewTooltip;

	/**
	 * Zwieschutzweichentabelle
	 */
	public String ToolboxTableNameSslwLong;

	/**
	 * 10/25
	 */
	public String ToolboxTableNameSslwPlanningNumber;

	/**
	 * Sslw
	 */
	public String ToolboxTableNameSslwShort;

	/**
	 * Zwieschutzweichentabelle (Sslz)
	 */
	public String SslwTableView_Heading;

	/**
	 * 819.0102A21
	 */
	public String ToolboxTableNameSslwRil;

	/**
	 * Sslz (Zugstraßentabelle)
	 */
	public String SslzDescriptionService_ViewName;

	/**
	 * Zugstraßentabelle
	 */
	public String SslzDescriptionService_ViewTooltip;

	/**
	 * Zugstraßentabelle
	 */
	public String ToolboxTableNameSslzLong;

	/**
	 * 10/21
	 */
	public String ToolboxTableNameSslzPlanningNumber;

	/**
	 * Sslz
	 */
	public String ToolboxTableNameSslzShort;

	/**
	 * Zugstraßentabelle (Sslz)
	 */
	public String SslzTableView_Heading;

	/**
	 * 819.0102A15
	 */
	public String ToolboxTableNameSslzRil;

	/**
	 * Ssvu (Übertragungswegtabelle)
	 */
	public String SsvuDescriptionService_ViewName;

	/**
	 * Übertragungswegtabelle
	 */
	public String SsvuDescriptionService_ViewTooltip;

	/**
	 * Übertragungswegtabelle
	 */
	public String ToolboxTableNameSsvuLong;

	/**
	 * 10/39
	 */
	public String ToolboxTableNameSsvuPlanningNumber;

	/**
	 * Ssvu
	 */
	public String ToolboxTableNameSsvuShort;

	/**
	 * Übertragungswegtabelle (Ssvu)
	 */
	public String SsvuTableView_Heading;

	/**
	 * 819.0102A23
	 */
	public String ToolboxTableNameSsvuRil;

	/**
	 * Ssza (Datenpunkttabelle)
	 */
	public String SszaDescriptionService_ViewName;

	/**
	 * Datenpunkttabelle
	 */
	public String SszaDescriptionService_ViewTooltip;

	/**
	 * Datenpunkttabelle
	 */
	public String ToolboxTableNameSszaLong;

	/**
	 * 
	 */
	public String ToolboxTableNameSszaPlanningNumber;

	/**
	 * Ssza
	 */
	public String ToolboxTableNameSszaShort;

	/**
	 * Datenpunkttabelle - Ssza
	 */
	public String SszaTableView_Heading;

	/**
	 * 
	 */
	public String ToolboxTableNameSszaRil;

	/**
	 * ETCS Melde- und Kommandoanschaltung Signale
	 */
	public String ToolboxTableNameSszsLong;

	/**
	 * 42/09
	 */
	public String ToolboxTableNameSszsPlanningNumber;

	/**
	 * Sszs
	 */
	public String ToolboxTableNameSszsShort;

	/**
	 * ETCS Melde- und Kommandoanschaltung Signale - Sszs
	 */
	public String SszsTableView_Heading;

	/**
	 * Ssza (ETCS Melde- und Kommandoanschaltung Signale)
	 */
	public String SszsDescriptionService_ViewName;

	/**
	 * ETCS Melde- und Kommandoanschaltung Signale
	 */
	public String SszsDescriptionService_ViewTooltip;

	/**
	 * 
	 */
	public String ToolboxTableNameSszsRil;

	/**
	 * Sszw – ETCS Melde- und Kommandoanschaltung Weichen
	 */
	public String SszwDescriptionService_ViewName;

	/**
	 * ETCS Melde- und Kommandoanschaltung Weichen
	 */
	public String SszwDescriptionService_ViewTooltip;

	/**
	 * ETCS Melde- und Kommandoanschaltung Weichen
	 */
	public String ToolboxTableNameSszwLong;

	/**
	 * 42/10
	 */
	public String ToolboxTableNameSszwPlanningNumber;

	/**
	 * Sszw
	 */
	public String ToolboxTableNameSszwShort;

	/**
	 * ETCS Melde- und Kommandoanschaltung Weichen – Sszw
	 */
	public String SszwTableView_Heading;

	/**
	 * 
	 */
	public String ToolboxTableNameSszwRil;

	/**
	 * Sskz – Zuordnungstabelle FEAK/FEAS
	 */
	public String SskzDescriptionService_ViewName;

	/**
	 * Zuordnungstabelle FEAK/FEAS
	 */
	public String SskzDescriptionService_ViewTooltip;

	/**
	 * Sskz
	 */
	public String ToolboxTableNameSskzShort;

	/**
	 * Zuordnungstabelle FEAK/FEAS
	 */
	public String ToolboxTableNameSskzLong;

	/**
	 * 10/03
	 */
	public String ToolboxTableNameSskzPlanningNumber;

	/**
	 * 
	 */
	public String ToolboxTableNameSskzRil;

	/**
	 * Zuordnungstabelle FEAK/FEAS – Sskz
	 */
	public String SskzTableView_Heading;

	/**
	 * Sskp_dm – PZB-Tabelle
	 */
	public String SskpDmDescriptionService_ViewName;

	/**
	 * Sskp_dm
	 */
	public String ToolboxTableNameSskpDmShort;

	/**
	 * PZB-Tabelle (Sskp_dm)
	 */
	public String SskpDmTableView_Heading;

	/**
	 * Ssls – Signalisierungstabelle
	 */
	public String SslsDescriptionService_ViewName;

	/**
	 * Signalisierungstabelle
	 */
	public String SslsDescriptionService_ViewTooltip;

	/**
	 * Signalisierungstabelle – Ssls
	 */
	public String SslsTableView_Heading;

	/**
	 * Ssls
	 */
	public String ToolboxTableNameSslsShort;

	/**
	 * Signalisierungstabelle
	 */
	public String ToolboxTableNameSslsLong;

	/**
	 * ??
	 */
	public String ToolboxTableNameSslsPlanningNumber;

	/**
	 * 
	 */
	public String ToolboxTableNameSslsRil;

	/**
	 * Sxxx
	 */
	public String ToolboxTableNameSxxxShort;

	/**
	 * Tabelle der Bearbeitungsvermerke – Sxxx
	 */
	public String SxxxTableView_Heading;

	/**
	 * Tabelle der Bearbeitungsvermerke
	 */
	public String ToolboxTableNameSxxxLong;

	/**
	 * ???
	 */
	public String ToolboxTableNameSxxxPlanningNumber;

	/**
	 * Sxxx – Tabelle der Bearbeitungsvermerke
	 */
	public String SxxxDescriptionService_ViewName;

	/**
	 * Tabelle der Bearbeitungsvermerke
	 */
	public String SxxxDescriptionService_ViewTooltip;

	/**
	 * 
	 */
	public String ToolboxTableNameSxxxRil;

	/**
	 * Tabelle der Tafeln – Sskx
	 */
	public String SskxTableView_Heading;

	/**
	 * Tabelle der Tafeln
	 */
	public String ToolboxTableNameSskxLong;

	/**
	 * 10/18
	 */
	public String ToolboxTableNameSskxPlanningNumber;

	/**
	 * Sskx
	 */
	public String ToolboxTableNameSskxShort;

	/**
	 * Tabelle der Tafeln
	 */
	public String SskxDescriptionService_ViewTooltip;

	/**
	 * Sskx – Tabelle der Tafeln
	 */
	public String SskxDescriptionService_ViewName;

	/**
	 * 
	 */
	public String ToolboxTableNameSskxRil;

}
