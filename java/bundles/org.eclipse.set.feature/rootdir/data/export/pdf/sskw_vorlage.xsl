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

	<!-- table with rows -->
	<xsl:template match="Table[Rows/Row]">
		<fo:table table-layout="fixed" width="100%">

			<!-- CUSTOMIZE: Adjust number of used columns and insert column width 
				from excel template. -->
			<fo:table-column column-number="1" column-width="0.71cm" /> <!--  -->
			<fo:table-column column-number="2" column-width="1.93cm" /> <!-- A -->
			<fo:table-column column-number="3" column-width="3.68cm" /> <!-- B -->
			<fo:table-column column-number="4" column-width="1.43cm" /> <!-- C -->
			<fo:table-column column-number="5" column-width="1.43cm" /> <!-- D -->
			<fo:table-column column-number="6" column-width="1.43cm" /> <!-- E -->
			<fo:table-column column-number="7" column-width="1.43cm" /> <!-- F -->
			<fo:table-column column-number="8" column-width="1.11cm" /> <!-- G -->
			<fo:table-column column-number="9" column-width="1.11cm" /> <!-- H -->
			<fo:table-column column-number="10" column-width="1.19cm" /> <!-- I -->
			<fo:table-column column-number="11" column-width="1.85cm" /> <!-- J -->
			<fo:table-column column-number="12" column-width="1.43cm" /> <!-- K -->
			<fo:table-column column-number="13" column-width="1.19cm" /> <!-- L -->
			<fo:table-column column-number="14" column-width="1.14cm" /> <!-- M -->
			<fo:table-column column-number="15" column-width="1.14cm" /> <!-- N -->
			<fo:table-column column-number="16" column-width="1.14cm" /> <!-- O -->
			<fo:table-column column-number="17" column-width="1.14cm" /> <!-- P -->
			<fo:table-column column-number="18" column-width="1.85cm" /> <!-- Q -->
			<fo:table-column column-number="19" column-width="1.11cm" /> <!-- R -->
			<fo:table-column column-number="20" column-width="1.11cm" /> <!-- S -->
			<fo:table-column column-number="21" column-width="1.11cm" /> <!-- T -->
			<fo:table-column column-number="22" column-width="1.11cm" /> <!-- U -->
			<fo:table-column column-number="23" column-width="2.38cm" /> <!-- V -->
			<fo:table-column column-number="24" column-width="0.82cm" /> <!-- W -->
			<fo:table-column column-number="25" column-width="2.28cm" /> <!-- X -->
			<fo:table-column column-number="26" column-width="2.28cm" /> <!-- Y -->

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
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>U</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>V</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>W</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>X</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style">
						<fo:block>Y</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row font-weight="bold" display-align="before"> 
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="2" number-rows-spanned="1" xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Weiche/Kreuzung/Gleissperren/Sonderanlage</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="4" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Freimeldung</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="2" number-rows-spanned="1" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Vorzugslage/Grundstellung</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="6" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Weiche</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="2" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Kreuzung</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="1" number-rows-spanned="1" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Herzstück</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="4" number-rows-spanned="1" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Gleissperre</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="2" number-rows-spanned="1" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Sonstiges</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="1" number-rows-spanned="1" border-right="{$wide-border-style}" border-top="{$wide-border-style}">
						<fo:block>Sonderanlage</fo:block>
					</fo:table-cell>
					<fo:table-cell 
						number-columns-spanned="1" number-rows-spanned="4" border-right="{$wide-border-style}" border-top="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block>Bemerkung</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>Bezeichnung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-right="{$wide-border-style}" border-bottom="none">
						<fo:block>Form</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>FMA</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" number-columns-spanned="2" border-top="{$small-border-style}">
						<fo:block>nicht grenzzeichenfrei</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none" number-rows-spanned="2">
						<fo:block>Isolierfall</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>Lage/
Stellung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none" number-rows-spanned="2">
						<fo:block>Auto-
matik</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>Auffahr-
ortung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>Anzahl (Lage)
Antriebe</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>Weichen-
signal</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>Prüf-
kontakte</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" number-columns-spanned="2" border-right="{$wide-border-style}" border-top="{$small-border-style}" border-bottom="none">
						<fo:block>zul. Geschwindigkeit</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" number-columns-spanned="2" border-right="{$wide-border-style}" border-top="{$small-border-style}" border-bottom="none">
						<fo:block>zul. Geschwindigkeit</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none" number-rows-spanned="2">
						<fo:block>Anzahl (Lage)
Antriebe</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none" number-rows-spanned="2">
						<fo:block>Anzahl
Antriebe</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none" number-rows-spanned="2">
						<fo:block>Gsp-
Signal</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none" number-rows-spanned="2">
						<fo:block>Auswurf-
richtung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none" border-right="{$wide-border-style}" number-rows-spanned="2">
						<fo:block>Schutz-schiene</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-bottom="none">
						<fo:block>Regelzeichnung
Nr. (Bild)</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none" number-rows-spanned="2">
						<fo:block>DWS</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none" number-rows-spanned="2">
						<fo:block>Art</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-left="{$small-border-style}" border-right="{$small-border-style}" >
						<fo:block>Links mit</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-left="{$small-border-style}" border-right="{$small-border-style}" >
						<fo:block>Rechts mit</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$small-border-style}">
						<fo:block>Links</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block>Rechts</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$small-border-style}">
						<fo:block>Links</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block>Rechts</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}" >
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>St</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-columns-spanned="1" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>St</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>km/h</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>St</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block>St</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none">
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
	
	<xsl:template match="Cell[contains(' 2 6 8 14 16 17 21 23 24 ', concat(' ', @column-number, ' '))]" priority="1">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
		
	<xsl:template match="Cell[contains(' 2 6 8 14 16 17 21 23 24 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="2">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="Cell[contains(' 25 ', concat(' ', @column-number, ' '))]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" text-align="left">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
		
	<xsl:template match="Cell[contains(' 25 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" text-align="left">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
</xsl:stylesheet>
