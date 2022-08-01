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
			<fo:table-column column-number="1" column-width="0.66cm" />
			<!--  -->
			<fo:table-column column-number="2" column-width="1.88cm" />
			<!-- A -->
			<fo:table-column column-number="3" column-width="0.95cm" />
			<!-- B -->
			<fo:table-column column-number="4" column-width="1.40cm" />
			<!-- C -->
			<fo:table-column column-number="5" column-width="1.56cm" />
			<!-- D -->
			<fo:table-column column-number="6" column-width="2.30cm" />
			<!-- E -->
			<fo:table-column column-number="7" column-width="4.60cm" />
			<!-- F -->
			<fo:table-column column-number="8" column-width="3.25cm" />
			<!-- G -->
			<fo:table-column column-number="9" column-width="1.72cm" />
			<!-- H -->
			<fo:table-column column-number="10" column-width="3.25cm" />
			<!-- I -->
			<fo:table-column column-number="11" column-width="1.32cm" />
			<!-- J -->
			<fo:table-column column-number="12" column-width="3.02cm" />
			<!-- K -->
			<fo:table-column column-number="13" column-width="2.28cm" />
			<!-- L -->
			<fo:table-column column-number="14" column-width="2.65cm" />
			<!-- M -->
			<fo:table-column column-number="15" column-width="7.83cm" />
			<!-- N -->

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
				</fo:table-row>
				<fo:table-row font-weight="bold" display-align="before">
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" number-columns-spanned="2" border-top="{$wide-border-style}">
						<fo:block>Grundsatzangaben</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-columns-spanned="3">
						<fo:block>Unterstellungsverhältnis</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-columns-spanned="1">
						<fo:block>Grenze</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-columns-spanned="6">
						<fo:block>Element</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" number-rows-spanned="1">
						<fo:block>Nahstellbereich R</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-top="{$wide-border-style}" border-bottom="none" number-rows-spanned="3">
						<fo:block>Bemerkung</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row display-align="before">
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}" number-rows-spanned="1">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none">
						<fo:block>Bereich/Zone</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none">
						<fo:block>Art</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none">
						<fo:block>unterge-
ordnet zu</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none">
						<fo:block>Rang der
Zuschaltung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none">
						<fo:block>Auflösung Grenze zu
übergeordneter
Zone</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none">
						<fo:block>Bezeichnung
(Element innen, außen)</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none" number-columns-spanned="2">
						<fo:block>Weiche/Gleissperre</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none" number-columns-spanned="2">
						<fo:block>Signal</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-bottom="none" number-columns-spanned="1" number-rows-spanned="2">
						<fo:block>Schlüsselsperre</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none" number-columns-spanned="1" number-rows-spanned="2">
						<fo:block>Bedieneinrichtung</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-right="{$wide-border-style}" border-bottom="none" number-columns-spanned="1" number-rows-spanned="2">
						<fo:block>Bedienungshandlung</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row display-align="before">
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-top="none" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-top="none" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-top="none" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" number-rows-spanned="2" border-top="none" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-top="none" border-bottom="none">
						<fo:block>frei stellbar
(Rückgabevoraussetzung)</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-top="none" border-bottom="none">
						<fo:block>verschlossen</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-top="none" border-bottom="none">
						<fo:block>frei stellbar
(Rückgabevoraussetzung)</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="thin-border-style" border-top="none" border-bottom="none">
						<fo:block>Kennlicht</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
						<fo:block color="white">.</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$small-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$small-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$small-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$small-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$small-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
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

	<xsl:template match="Cell[contains(' 2 5 6 12 13 ', concat(' ', @column-number, ' '))]" priority="1">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" border-right="{$wide-border-style}" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="Cell[contains(' 2 5 6 12 13 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="2">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" border-right="{$wide-border-style}" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="Cell[contains(' 14 ', concat(' ', @column-number, ' '))]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" text-align="left" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="Cell[contains(' 14 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" text-align="left" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

</xsl:stylesheet>
