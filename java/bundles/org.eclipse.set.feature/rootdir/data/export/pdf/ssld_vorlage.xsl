<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2017 DB Netz AG and others.

All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v2.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v20.html
-->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

	<xsl:include href="common.xsl" />
	<xsl:include href="titlebox.xsl" />
	<xsl:include href="cells.xsl" />
	<xsl:include href="folding-marks.xsl" />

	<!-- CUSTOMIZE: Adjust the following defaults only if needed -->
	<xsl:attribute-set name="table-header-style">
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style" />
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="no-border-style">
		<xsl:attribute name="border">none</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="thin-border-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style" />
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="wide-border-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$wide-border-style" />
		</xsl:attribute>
	</xsl:attribute-set>

	<!-- Main page layout -->
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" language="de" linefeed-treatment="preserve" xsl:use-attribute-sets="default-font">
			<fo:layout-master-set>
				<fo:simple-page-master xsl:use-attribute-sets="table-master-style" master-name="table-master">
					<fo:region-body xsl:use-attribute-sets="region-body-style"/>
					<fo:region-before region-name="folding-mark-region" xsl:use-attribute-sets="folding-mark-region-style"/>
					<fo:region-after region-name="title-box-region" xsl:use-attribute-sets="title-box-region-style" />
				</fo:simple-page-master>
				<fo:simple-page-master xsl:use-attribute-sets="table-master-style" master-name="table-master-last">
					<fo:region-body xsl:use-attribute-sets="region-body-style"/>
					<fo:region-before region-name="folding-mark-region" xsl:use-attribute-sets="folding-mark-region-style"/>
					<fo:region-after region-name="title-box-region-last" xsl:use-attribute-sets="title-box-region-style" />
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="page-sequence-master">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference master-reference="table-master-last" page-position="last"/>
						<fo:conditional-page-master-reference master-reference="table-master"/>
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="page-sequence-master">
				<fo:static-content flow-name="folding-mark-region">
					<xsl:call-template name="FoldingMarksTop"/>
				</fo:static-content>
				<fo:static-content flow-name="title-box-region">
					<xsl:call-template name="TitleboxRegion">
						<xsl:with-param name="pagePostfix" select="'+'"/>
					</xsl:call-template>
					<xsl:call-template name="FoldingMarksBottom"/>
				</fo:static-content>
				<fo:static-content flow-name="title-box-region-last">
					<xsl:call-template name="TitleboxRegion">
						<xsl:with-param name="pagePostfix" select="'-'"/>
					</xsl:call-template>
					<xsl:call-template name="FoldingMarksBottom"/>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<!-- table with rows -->
	<xsl:template match="Table[Rows/Row]">
		<fo:table table-layout="fixed" width="100%">

			<!-- CUSTOMIZE: Adjust number of used columns and insert column width 
				from excel template. -->
			<fo:table-column column-number="1" column-width="0.61cm" />
			<!-- group number -->
			<fo:table-column column-number="2" column-width="1.27cm" />
			<!-- A -->
			<fo:table-column column-number="3" column-width="1.59cm" />
			<!-- B -->
			<fo:table-column column-number="4" column-width="1.59cm" />
			<!-- C -->
			<fo:table-column column-number="5" column-width="1.59cm" />
			<!-- D -->
			<fo:table-column column-number="6" column-width="1.69cm" />
			<!-- E -->
			<fo:table-column column-number="7" column-width="1.1cm" />
			<!-- F -->
			<fo:table-column column-number="8" column-width="0.64cm" />
			<!-- G -->
			<fo:table-column column-number="9" column-width="0.64cm" />
			<!-- H -->
			<fo:table-column column-number="10" column-width="1.75cm" />
			<!-- I -->
			<fo:table-column column-number="11" column-width="1.74cm" />
			<!-- J -->
			<fo:table-column column-number="12" column-width="1.53cm" />
			<!-- K -->
			<fo:table-column column-number="13" column-width="1.53cm" />
			<!-- L -->
			<fo:table-column column-number="14" column-width="1.27cm" />
			<!-- M -->
			<fo:table-column column-number="15" column-width="1.51cm" />
			<!-- N -->
			<fo:table-column column-number="16" column-width="1.38cm" />
			<!-- O -->
			<fo:table-column column-number="17" column-width="1.11cm" />
			<!-- P -->
			<fo:table-column column-number="18" column-width="2.2cm" />
			<!-- Q -->
			<fo:table-column column-number="19" column-width="2.2cm" />
			<!-- R -->
			<fo:table-column column-number="20" column-width="2.28cm" />
			<!-- S -->
			<fo:table-column column-number="21" column-width="9.76cm" />
			<!-- T -->

			<!-- CUSTOMIZE: Create the header row by row from top to bottom. Verify 
				the result after each row (or cell) with a batch-export. Row span can only 
				applied to existing rows. -->
			<fo:table-header xsl:use-attribute-sets="table-header-style">
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>A</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>B</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>C</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>D</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>E</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>F</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>G</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>H</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>I</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>J</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>K</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>L</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>M</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>N</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>O</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>P</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>Q</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>R</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>S</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>T</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row font-weight="bold" display-align="before">
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" number-columns-spanned="5" border-top="{$wide-border-style}">
						<fo:block>Grundsatzangaben</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-columns-spanned="5">
						<fo:block>Eigenschaften</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-columns-spanned="5">
						<fo:block>Abhängigkeiten</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-columns-spanned="4">
						<fo:block>Auflösung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-rows-spanned="3">
						<fo:block>Bemerkung</fo:block>
					</fo:table-cell>

				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2">
						<fo:block>von
(Signal)</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2">
						<fo:block>bis
(markanter
Punkt)</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>Gefahrpunkt</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>PZB-
Schutzpunkt</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}">
						<fo:block>Bezeichnung</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>v Ziel
möglich</fo:block>
					</fo:table-cell>
					<fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="thin-border-style">
						<fo:block>Länge</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}">
						<fo:block>Maßgebende
Neigung</fo:block>
					</fo:table-cell>
					<fo:table-cell number-columns-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>Weichen, Kreuzungen</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>relevante FmA</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>v-
Aufwertung
Verzicht</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}">
						<fo:block>Erlaubnis- abhängig</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>Manuell</fo:block>
					</fo:table-cell>
					<fo:table-cell number-columns-spanned="2" xsl:use-attribute-sets="thin-border-style">
						<fo:block>Zielgleisabschnitt</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}">
						<fo:block>Auflöseverzögerung bzw. Kennlicht- verzögerungszeit
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="1">
						<fo:block>Soll</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-columns-spanned="1">
						<fo:block>Ist</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-columns-spanned="1">
						<fo:block>Frei-
gemeldet</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="1">
						<fo:block>mit
Verschluss</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="1">
						<fo:block>ohne
Verschluss</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="1">
						<fo:block>Bezeichnung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="1">
						<fo:block>Länge</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block>m</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block>m</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block>m</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-right="{$wide-border-style}">
						<fo:block>&#8240;</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}">
						<fo:block>m</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block>s</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
				</fo:table-row>

			</fo:table-header>

			<!-- Fill table body -->
			<fo:table-body>
				<xsl:apply-templates select="Rows/Row" />
			</fo:table-body>
		</fo:table>

		<!-- Fill footnotes -->
		<xsl:apply-templates select="Footnotes" />

	</xsl:template>

	<!-- table without rows -->
	<xsl:template match="Table[not(Rows/Row)]">
		<fo:block>Die Tabelle ist leer.</fo:block>
	</xsl:template>

	<xsl:template match="Cell[contains(' 5 10 15 19 ', concat(' ', @column-number, ' '))]" priority="1">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" border-right="{$wide-border-style}" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="Cell[contains(' 5 10 15 19 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="2">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" border-right="{$wide-border-style}" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="Cell[contains(' 2 3 20 ', concat(' ', @column-number, ' '))]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" text-align="left" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="Cell[contains(' 2 3 20 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" text-align="left" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

</xsl:stylesheet>
