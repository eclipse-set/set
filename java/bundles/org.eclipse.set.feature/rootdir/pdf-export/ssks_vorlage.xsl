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
	<xsl:include href="content.xsl" />
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
					master-name="table-master-a">
					<fo:region-body xsl:use-attribute-sets="region-body-style"/>
					<fo:region-before region-name="folding-mark-region"
						xsl:use-attribute-sets="folding-mark-region-style"/>
					<fo:region-after region-name="title-box-region"
						xsl:use-attribute-sets="title-box-region-style" />
				</fo:simple-page-master>
				<fo:simple-page-master xsl:use-attribute-sets="table-master-style"
					master-name="table-master-b">
					<fo:region-body xsl:use-attribute-sets="region-body-style"/>
					<fo:region-before region-name="folding-mark-region"
						xsl:use-attribute-sets="folding-mark-region-style"/>
					<fo:region-after region-name="title-box-region"
						xsl:use-attribute-sets="title-box-region-style" />
				</fo:simple-page-master>
				<fo:simple-page-master xsl:use-attribute-sets="table-master-style"
					master-name="table-master-b-last">
					<fo:region-body xsl:use-attribute-sets="region-body-style"/>
					<fo:region-before region-name="folding-mark-region"
						xsl:use-attribute-sets="folding-mark-region-style"/>
					<fo:region-after region-name="title-box-region-last"
						xsl:use-attribute-sets="title-box-region-style" />
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="page-sequence-master-b">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference master-reference="table-master-b-last" page-position="last"/>
						<fo:conditional-page-master-reference master-reference="table-master-b"/>
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="table-master-a">
				<fo:static-content flow-name="folding-mark-region">
					<xsl:call-template name="FoldingMarksTop"/>
				</fo:static-content>
				<fo:static-content flow-name="title-box-region">
					<xsl:call-template name="TitleboxRegion">
						<xsl:with-param name="pagePostfix" select="'a+'"/>
					</xsl:call-template>
					<xsl:call-template name="FoldingMarksBottom"/>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
 					<xsl:apply-templates />
				</fo:flow>
			</fo:page-sequence>
			<fo:page-sequence master-reference="page-sequence-master-b" initial-page-number="1">
				<fo:static-content flow-name="folding-mark-region">
					<xsl:call-template name="FoldingMarksTop"/>
				</fo:static-content>
				<fo:static-content flow-name="title-box-region">
					<xsl:call-template name="TitleboxRegion">
						<xsl:with-param name="pagePostfix" select="'b+'"/>
					</xsl:call-template>
					<xsl:call-template name="FoldingMarksBottom"/>
				</fo:static-content>
				<fo:static-content flow-name="title-box-region-last">
					<xsl:call-template name="TitleboxRegion">
						<xsl:with-param name="pagePostfix" select="'b-'"/>
					</xsl:call-template>
					<xsl:call-template name="FoldingMarksBottom"/>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block start-indent="-39cm">
 						<xsl:apply-templates />
 					</fo:block>
					<!-- Fill footnotes -->
					<xsl:apply-templates select="Table/Footnotes" />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<!-- table with rows -->
	<xsl:template match="Table[Rows/Row]">
		<fo:block>
			<fo:table table-layout="fixed" width="100%">
				<!-- Store fix values for column width -->
				<fo:table-column column-number="1" column-width="0.66cm" /> <!--  -->
				<fo:table-column column-number="2" column-width="1.91cm" /> <!-- A -->
				<fo:table-column column-number="3" column-width="1.11cm" /> <!-- B -->
				<fo:table-column column-number="4" column-width="1.11cm" /> <!-- C -->
				<fo:table-column column-number="5" column-width="1.19cm" /> <!-- D -->
				<fo:table-column column-number="6" column-width="1.32cm" /> <!-- E -->
				<fo:table-column column-number="7" column-width="1.43cm" /> <!-- F -->
				<fo:table-column column-number="8" column-width="1.35cm" /> <!-- G -->
				<fo:table-column column-number="9" column-width="1.16cm" /> <!-- H -->
				<fo:table-column column-number="10" column-width="1.27cm" /> <!-- I -->
				<fo:table-column column-number="11" column-width="1.27cm" /> <!-- J -->
				<fo:table-column column-number="12" column-width="1.16cm" /> <!-- K -->
				<fo:table-column column-number="13" column-width="1.16cm" /> <!-- L -->
				<fo:table-column column-number="14" column-width="1.16cm" /> <!-- M -->
				<fo:table-column column-number="15" column-width="1.43cm" /> <!-- N -->
				<fo:table-column column-number="16" column-width="1.43cm" /> <!-- O -->
				<fo:table-column column-number="17" column-width="2.41cm" /> <!-- P -->
				<fo:table-column column-number="18" column-width="2.30cm" /> <!-- Q -->
				<fo:table-column column-number="19" column-width="1.32cm" /> <!-- R -->
				<fo:table-column column-number="20" column-width="0.71cm" /> <!-- S -->
				<fo:table-column column-number="21" column-width="1.14cm" /> <!-- T -->
				<fo:table-column column-number="22" column-width="2.59cm" /> <!-- U -->
				<fo:table-column column-number="23" column-width="1.16cm" /> <!-- V -->
				<fo:table-column column-number="24" column-width="1.69cm" /> <!-- W -->
				<fo:table-column column-number="25" column-width="1.35cm" /> <!-- X -->
				<fo:table-column column-number="26" column-width="1.69cm" /> <!-- Y -->
				<fo:table-column column-number="27" column-width="{1.32  + 1.18}cm" /> <!-- Z + page filling width-->
				<fo:table-column column-number="28" column-width="0.66cm" /> <!--  -->
				<fo:table-column column-number="29" column-width="1.91cm" /> <!-- A -->
				<fo:table-column column-number="30" column-width="0.85cm" /> <!-- AA -->
				<fo:table-column column-number="31" column-width="0.85cm" /> <!-- AB -->
				<fo:table-column column-number="32" column-width="0.85cm" /> <!-- AC -->
				<fo:table-column column-number="33" column-width="0.93cm" /> <!-- AD -->
				<fo:table-column column-number="34" column-width="0.85cm" /> <!-- AE -->
				<fo:table-column column-number="35" column-width="0.85cm" /> <!-- AF -->
				<fo:table-column column-number="36" column-width="0.85cm" /> <!-- AG -->
				<fo:table-column column-number="37" column-width="0.85cm" /> <!-- AH -->
				<fo:table-column column-number="38" column-width="0.85cm" /> <!-- AI -->
				<fo:table-column column-number="39" column-width="0.85cm" /> <!-- AJ -->
				<fo:table-column column-number="40" column-width="0.85cm" /> <!-- AK -->
				<fo:table-column column-number="41" column-width="1.69cm" /> <!-- AL -->
				<fo:table-column column-number="42" column-width="1.69cm" /> <!-- AM -->
				<fo:table-column column-number="43" column-width="1.46cm" /> <!-- AN -->
				<fo:table-column column-number="44" column-width="2.65cm" /> <!-- AO -->
				<fo:table-column column-number="45" column-width="1.67cm" /> <!-- AP -->
				<fo:table-column column-number="46" column-width="1.43cm" /> <!-- AQ -->
				<fo:table-column column-number="47" column-width="1.43cm" /> <!-- AR -->
				<fo:table-column column-number="48" column-width="1.22cm" /> <!-- AS -->
				<fo:table-column column-number="49" column-width="1.43cm" /> <!-- AT -->
				<fo:table-column column-number="50" column-width="0.93cm" /> <!-- AU -->
				<fo:table-column column-number="51" column-width="1.24cm" /> <!-- AV -->
				<fo:table-column column-number="52" column-width="8.94cm" /> <!-- AW -->
					
				<fo:table-header xsl:use-attribute-sets="table-header-style">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="no-border-style">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">A</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">B</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">C</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">D</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">E</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">F</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
						 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">G</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
						 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">H</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">I</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">J</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">K</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">L</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">M</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">N</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
						 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">O</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">P</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">Q</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">R</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">S</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">T</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">U</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">V</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">W</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">X</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">Y</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">Z</fo:block>
						</fo:table-cell>
						<fo:table-cell border-left="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">A</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AA</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AB</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AC</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AD</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AE</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AF</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AG</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AH</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AI</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AJ</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AK</fo:block>
						</fo:table-cell>
							<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AL</fo:block>
						</fo:table-cell>
							<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AM</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AN</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AO</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AP</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AQ</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AR</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AS</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AT</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AU</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" 
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AV</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" 
							 border-top="{$small-border-style}" border-bottom="none">
							<fo:block start-indent="0mm">AW</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row font-weight="bold" display-align="before">
						<fo:table-cell xsl:use-attribute-sets="no-border-style" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" number-rows-spanned="3"   
							 border-top="{$wide-border-style}" >
							<fo:block start-indent="0mm">Bezeichnung Signal</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}"
							 border-top="{$wide-border-style}" number-columns-spanned="2" >
							<fo:block start-indent="0mm">Signal-Art</fo:block> 
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}"
							 border-top="{$wide-border-style}" number-columns-spanned="12">
							<fo:block start-indent="0mm">Standortmerkmale</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}"
							 border-top="{$wide-border-style}" number-columns-spanned="7">
							<fo:block start-indent="0mm">Konstruktive Merkmale</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" border-right="{$wide-border-style}"
							 border-top="{$wide-border-style}" number-columns-spanned="4" >
							<fo:block start-indent="0mm">Anschluss</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="no-border-style">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" number-rows-spanned="3"
							border-top="{$wide-border-style}" >
							<fo:block start-indent="0mm">Bezeichnung Signal</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}"
							 border-top="{$wide-border-style}" number-columns-spanned="15" >
							<fo:block start-indent="0mm">Signalisierung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}"
							 border-top="{$wide-border-style}" number-columns-spanned="7" >
							<fo:block start-indent="0mm">Sonstiges</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="wide-border-style" number-columns-spanned="1" number-rows-spanned="4">
							<fo:block start-indent="0mm">Bemerkung</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row >
						<fo:table-cell xsl:use-attribute-sets="no-border-style" border-right="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" number-rows-spanned="2" border-right="{$small-border-style}"
							border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Reales Signal</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-right="{$wide-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}"> 
							<fo:block start-indent="0mm">Fiktives Signal</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Standort</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Sonstige zulässige Anordnung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Lichtraum- profil</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Über-
höhung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Abstand
Mastmitte - Gleismitte</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="3">
							<fo:block start-indent="0mm">Sichtbarkeit</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$wide-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Ausrichtung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Anordnung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Obere Lichtpunkt- höhe</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Streuscheibe</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$wide-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Fundament</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Schaltkasten</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Separate Schaltkasten Steuerung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-right="{$wide-border-style}" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Dauerhaft
Nacht-
schaltung</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="no-border-style">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="5">
							<fo:block start-indent="0mm">Signalbegriffe
(Schirm)</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="7">
							<fo:block start-indent="0mm">Signalbegriffe
(Zusatzanzeiger)</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Nach- geordnetes Signal</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Mastschild</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Vorsignaltafel</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Auto- matische Fahrt- stellung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Dunkel- schaltung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Durchfahrt erlaubt</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Besetzte Ausfahrt</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" number-rows-spanned="2" border-right="{$small-border-style}"
							 border-top="{$small-border-style}">
							<fo:block start-indent="0mm">Löschung Zs 1/Zs 7</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-right="{$wide-border-style}"
							 border-top="{$small-border-style}" number-columns-spanned="2">
							<fo:block start-indent="0mm">Überwachung</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row >
						<fo:table-cell xsl:use-attribute-sets="no-border-style"  border-right="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}"
							 border-top="none" >
							<fo:block start-indent="0mm">Strecke</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="none" >
							<fo:block start-indent="0mm">km</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							 border-top="none" >
							<fo:block start-indent="0mm">Links</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}"
							border-top="none" >
							<fo:block start-indent="0mm">Rechts</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none">
							<fo:block start-indent="0mm">Soll</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none">
							<fo:block start-indent="0mm">Mindest</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none">
							<fo:block start-indent="0mm">Ist</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none">
							<fo:block start-indent="0mm">Entfernung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none">
							<fo:block start-indent="0mm">Richtpunkt</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">Befestigung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none">
							<fo:block start-indent="0mm">Regelzeichnung
Nr. (Bild)</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="no-border-style" border-left="{$small-border-style}" border-right="{$small-border-style}" >
							<fo:block start-indent="0mm">Art</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="no-border-style" border-left="{$small-border-style}" border-right="{$small-border-style}" >
							<fo:block start-indent="0mm">Stellung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm">Regelzeichnung
Nr. (Bild)</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm">Höhe
u. SO</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">Bezeichnung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm">Entfernung</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm">Bezeichnung</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="no-border-style">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}">
							<fo:block start-indent="0mm">Hp, (Hl)</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Ks, (Vr)</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zl, Kl</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Ra 12/ Sh 1</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zs</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zs 2</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-right="{$small-border-style}" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zs 2v</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zs 3</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zs 3v</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zs</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zp</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Kombination</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$wide-border-style}">
							<fo:block start-indent="0mm">Regelzeichnung
Nr. (Bild)</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}">
							<fo:block start-indent="0mm">Zs 2</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$wide-border-style}">
							<fo:block start-indent="0mm">Zs 2v</fo:block>
						</fo:table-cell>
						
					</fo:table-row>
					<fo:table-row >
						<fo:table-cell xsl:use-attribute-sets="no-border-style" border-left="{$small-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="wide-border-style" border-top="none">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="wide-border-style" border-right="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="wide-border-style" border-left="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="wide-border-style" border-right="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">mm</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">mm</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">mm</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">m</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">m</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">m</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="thin-border-style" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">m</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="wide-border-style" border-left="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">mm</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" xsl:use-attribute-sets="wide-border-style" border-left="{$small-border-style}" border-top="none" >
							<fo:block start-indent="0mm">mm</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm">m</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}"
							 border-top="none" >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="no-border-style" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$wide-border-style}" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}"
							border-top="none"  >
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}"> 
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell> 
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}"> 
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-right="{$wide-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border-left="{$small-border-style}" border-bottom="{$wide-border-style}" border-right="{$wide-border-style}">
							<fo:block start-indent="0mm"></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-header>

				<!-- Fill table body -->
				<fo:table-body start-indent="0mm">
					<xsl:apply-templates select="Rows/Row" />
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template match="Cell">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
	
	<!-- Cells belonging to the first column and not to the last row -->
	<xsl:template match="Cell[@column-number = '1' and ../@group-number != count(/Table/Rows/Row)]">
		<fo:table-cell xsl:use-attribute-sets="first-column-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
	
	<!-- Cells belonging to the first column and to the last row -->
	<xsl:template match="Cell[@column-number = '1' and ../@group-number = count(/Table/Rows/Row)]">
		<fo:table-cell xsl:use-attribute-sets="first-column-last-row-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
	
	<!-- Cells not belonging to the first column and to the last row -->
	<xsl:template match="Cell[@column-number != '1' and @column-number != '26' and ../@group-number = count(/Table/Rows/Row)]">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<!-- Handle extra styling in column Z -->
	<xsl:template match="Cell[@column-number = '26' and ../@group-number != count(/Table/Rows/Row)]">
		<!-- column Z -->
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
		
		<!-- number column -->
		<fo:table-cell xsl:use-attribute-sets="body-row-cell-style">
			<fo:block>
				<xsl:value-of select="../@group-number" />
			</fo:block>
		</fo:table-cell>
		
		<!-- column A -->
		<xsl:apply-templates select="../*[@column-number = '1']"/>
	</xsl:template>
	
	<xsl:template match="Cell[@column-number = '26' and ../@group-number = count(/Table/Rows/Row)]">
		<!-- column Z -->
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
		
		<!-- number column -->
		<fo:table-cell xsl:use-attribute-sets="body-row-cell-style">
			<fo:block>
				<xsl:value-of select="../@group-number" />
			</fo:block>
		</fo:table-cell>
		
		<!-- column A -->
		<xsl:apply-templates select="../*[@column-number = '1']"/>
	</xsl:template>
	
	<xsl:template match="Cell[contains(' 3 15 22 41 48 ', concat(' ', @column-number, ' '))]" priority="1">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
		
	<xsl:template match="Cell[contains(' 3 15 22 41 48 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="2">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" border-right="{$wide-border-style}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="Cell[contains(' 49 ', concat(' ', @column-number, ' '))]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" text-align="left">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>
		
	<xsl:template match="Cell[contains(' 49 ', concat(' ', @column-number, ' ')) and ../@group-number = count(/Table/Rows/Row)]" priority="3">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" text-align="left">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<!-- Alternative filling for empty tables -->
	<xsl:template match="Table[not(Rows/Row)]">
		<fo:block>Die Tabelle ist leer.</fo:block>
	</xsl:template>
</xsl:stylesheet>
