<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2017 DB Netz AG and others.

All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v2.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v20.html
-->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo">

	<xsl:include href="common.xsl" />
	<xsl:include href="titlebox.xsl" />
	<xsl:include href="cells.xsl" />
	<xsl:include href="folding-marks.xsl" />

	<!-- CUSTOMIZE: Adjust the following defaults only if needed -->
	<xsl:attribute-set name="table-header-style">
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="border"><xsl:value-of select="$small-border-style" /></xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="no-border-style">
		<xsl:attribute name="border">none</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="thin-border-style">
		<xsl:attribute name="border"><xsl:value-of select="$small-border-style" /></xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="wide-border-style">
		<xsl:attribute name="border"><xsl:value-of select="$wide-border-style" /></xsl:attribute>
	</xsl:attribute-set>
	
	<!-- Main page layout -->
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" language="de" linefeed-treatment="preserve" xsl:use-attribute-sets="default-font">
			<fo:layout-master-set>
				<fo:simple-page-master xsl:use-attribute-sets="table-master-style"
					master-name="table-master">
					<fo:region-body xsl:use-attribute-sets="region-body-style"/>
					<fo:region-before region-name="folding-mark-region"
						xsl:use-attribute-sets="folding-mark-region-style"/>
					<fo:region-after region-name="title-box-region"
						xsl:use-attribute-sets="title-box-region-style" />
				</fo:simple-page-master>
				<fo:simple-page-master xsl:use-attribute-sets="table-master-style"
					master-name="table-master-last">
					<fo:region-body xsl:use-attribute-sets="region-body-style"/>
					<fo:region-before region-name="folding-mark-region"
						xsl:use-attribute-sets="folding-mark-region-style"/>
					<fo:region-after region-name="title-box-region-last"
						xsl:use-attribute-sets="title-box-region-style" />
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

	<!-- Fill main table -->
	<xsl:template match="Table[Rows/Row]">
		<fo:table table-layout="fixed" width="100%">

			<!-- Store fix values for column width -->
			<fo:table-column column-number="1" column-width="0.49cm" />
			<fo:table-column column-number="2" column-width="2.83cm" /> <!-- A -->
			<fo:table-column column-number="3" column-width="1.14cm" /> <!-- B -->
			<fo:table-column column-number="4" column-width="1.14cm" /> <!-- C -->
			<fo:table-column column-number="5" column-width="0.56cm" /> <!-- D -->
			<fo:table-column column-number="6" column-width="1.93cm" /> <!-- E -->
			<fo:table-column column-number="7" column-width="1.27cm" /> <!-- F -->
			<fo:table-column column-number="8" column-width="0.50cm" /> <!-- G -->
			<fo:table-column column-number="9" column-width="1.06cm" /> <!-- H -->
			<fo:table-column column-number="10" column-width="0.93cm" /> <!-- I -->
			<fo:table-column column-number="11" column-width="1.27cm" /> <!-- J -->
			<fo:table-column column-number="12" column-width="1.56cm" /> <!-- K -->
			<fo:table-column column-number="13" column-width="1.32cm" /> <!-- L -->
			<fo:table-column column-number="14" column-width="1.51cm" /> <!-- M -->
			<fo:table-column column-number="15" column-width="1.32cm" /> <!-- N -->
			<fo:table-column column-number="16" column-width="1.51cm" /> <!-- O -->
			<fo:table-column column-number="17" column-width="0.77cm" /> <!-- P -->
			<fo:table-column column-number="18" column-width="0.82cm" /> <!-- Q -->
			<fo:table-column column-number="19" column-width="0.87cm" /> <!-- R -->
			<fo:table-column column-number="20" column-width="1.01cm" /> <!-- S -->
			<fo:table-column column-number="21" column-width="0.95cm" /> <!-- T -->
			<fo:table-column column-number="22" column-width="1.51cm" /> <!-- U -->
			<fo:table-column column-number="23" column-width="0.56cm" /> <!-- V -->
			<fo:table-column column-number="24" column-width="0.61cm" /> <!-- W -->
			<fo:table-column column-number="25" column-width="0.66cm" /> <!-- X -->
			<fo:table-column column-number="26" column-width="0.64cm" /> <!-- Y -->
			<fo:table-column column-number="27" column-width="0.66cm" /> <!-- Z -->
			<fo:table-column column-number="28" column-width="0.64cm" /> <!-- AA -->
			<fo:table-column column-number="29" column-width="1.61cm" /> <!-- AB -->
			<fo:table-column column-number="30" column-width="1.24cm" /> <!-- AC -->
			<fo:table-column column-number="31" column-width="1.24cm" /> <!-- AD -->
			<fo:table-column column-number="32" column-width="1.24cm" /> <!-- AE -->
			<fo:table-column column-number="33" column-width="2.41cm" /> <!-- AF -->

			<!-- Fill table header -->
			<fo:table-header xsl:use-attribute-sets="table-header-style" display-align="before">
				<fo:table-row>
					<fo:table-cell number-rows-spanned="5">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>A</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>B</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>C</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>D</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>E</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>F</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>G</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>H</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>I</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>J</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>K</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>L</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>M</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>N</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>O</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>P</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>Q</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>R</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>S</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>T</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>U</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>V</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>W</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>X</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>Y</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>Z</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>AA</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>AB</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>AC</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>AD</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>AE</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>AF</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row xsl:use-attribute-sets="thin-border-style" font-weight="bold" display-align="before">
					<fo:table-cell text-align="center"
						number-columns-spanned="7" border-left="{$wide-border-style}" border-right="{$wide-border-style}"
						border-top="{$wide-border-style}">
						<fo:block>Grundsatzangaben</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						number-columns-spanned="2" border-left="{$wide-border-style}" border-right="{$wide-border-style}"
						border-top="{$wide-border-style}">
						<fo:block>Einstellung</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						number-columns-spanned="6" border-left="{$wide-border-style}" border-right="{$wide-border-style}"
						border-top="{$wide-border-style}">
						<fo:block>Abhängigkeiten</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						number-columns-spanned="16" border-left="{$wide-border-style}" border-right="{$wide-border-style}"
						border-top="{$wide-border-style}">
						<fo:block>Signalisierung</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-rows-spanned="3" border-left="{$wide-border-style}" border-right="{$wide-border-style}"
						border-top="{$wide-border-style}">
						<fo:block>Bemerkung</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell  border-left="{$wide-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" border-bottom="none">
						<fo:block>Bezeichnung</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" number-columns-spanned="4" border="none">
						<fo:block>Fahrweg</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>D-Weg</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						 border-right="{$wide-border-style}" >
						<fo:block>Art</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" text-align="center" border-right="{$small-border-style}">
						<fo:block>Autom. Einstel-lung</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" text-align="center" border-right="{$wide-border-style}">
						<fo:block>F-
Bedie-
nung</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>Inselgleis</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>Überwachte
Ssp</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>Abhängiger
BÜ</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>Nichthaltfall- abschnitt</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="{$small-border-style}" >
						<fo:block>2. Haltfall- kriterium</fo:block>
					</fo:table-cell>
					<fo:table-cell number-rows-spanned="2" text-align="center" border-left="{$small-border-style}"
						border-right="{$wide-border-style}"  >
						<fo:block>Anrück-
verschluss</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						number-columns-spanned="6" border-left="{$small-border-style}" border-right="{$small-border-style}"
						border-top="{$small-border-style}" border-bottom="none">
						<fo:block>Geschwindigkeit am Startsignal</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						number-columns-spanned="6" border-left="{$small-border-style}" border-right="{$small-border-style}"
						border-top="{$small-border-style}" border-bottom="none">
						<fo:block>Sonstiges am Startsignal</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						number-columns-spanned="4" border-right="{$wide-border-style}">
						<fo:block>Im Fahrweg</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell text-align="center" border-left="{$wide-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell border="none">
						<fo:block>Start</fo:block>
					</fo:table-cell>
					<fo:table-cell  border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Ziel</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Nr.</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Entscheidungs-
weiche
mit Stellung</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Bezeich- nung (*Vorzug)</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Hg</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Fahr- weg</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>D-Weg</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Beson-
ders</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs 3
(Zs 3
Ziel)</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Aufwertung bei Mwtfstr</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zl</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs
3v</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs 2</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs
2v</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs 6</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs
13</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs 3</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="none">
						<fo:block>Zs 6</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-right="{$small-border-style}">
						<fo:block>Kennlicht
an</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-right="{$wide-border-style}">
						<fo:block>Vorsigna-
lisierung
Ziel</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell text-align="center" border-left="{$wide-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$wide-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$wide-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-left="{$small-border-style}"
						border-right="{$small-border-style}" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-right="{$small-border-style}" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="center" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block />
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
	
	<!-- Alternative filling for empty tables -->
	<xsl:template match="Table[not(Rows/Row)]">
		<fo:block>Die Tabelle ist leer.</fo:block>
	</xsl:template>
	
	<xsl:template match="Cell[contains(' 7 9 15 31 ', concat(' ', @column-number, ' '))]" priority="1">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
		
	<xsl:template match="Cell[contains(' 7 9 15 31 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="2">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="Cell[contains(' 1 2 3 32 ', concat(' ', @column-number, ' '))]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" text-align="left">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
		
	<xsl:template match="Cell[contains(' 1 2 3 32 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" text-align="left">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
</xsl:stylesheet>
