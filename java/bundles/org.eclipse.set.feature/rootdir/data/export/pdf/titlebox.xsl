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
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:fox="http://xmlgraphics.apache.org/fop/extensions"
	xmlns:str="http://exslt.org/strings" exclude-result-prefixes="fo">

	<xsl:variable name="titlebox-small-border-width" select="0.25"/>
	<xsl:variable name="titlebox-wide-border-width" select="0.5"/>

	<xsl:variable name="titlebox-small-border-style" select="concat($titlebox-small-border-width, 'mm solid black')"/>
	<xsl:variable name="titlebox-wide-border-style" select="concat($titlebox-wide-border-width, 'mm solid black')"/>
	<!-- titlebox-SB = small border / 2, titlebox-WB = wide border / 2 -->
	<xsl:variable name="titlebox-SB" select="$titlebox-small-border-width div 2"/>
	<xsl:variable name="titlebox-WB" select="$titlebox-wide-border-width div 2"/>


	<xsl:attribute-set name="cell-block-container-style">
		<xsl:attribute name="overflow">hidden</xsl:attribute>
		<xsl:attribute name="height">inherit</xsl:attribute>
	</xsl:attribute-set>

	<!-- Font size applied to fields 12, 25, 35, 57 -->
	<xsl:attribute-set name="font-size-west-col2">
		<xsl:attribute name="font-size">70%</xsl:attribute>
	</xsl:attribute-set>

	<xsl:template match="TitleBox">
		<xsl:param name="pagePostfix" select="''" />

		<fo:table xsl:use-attribute-sets="title-box-table-style" table-layout="fixed">

			<fo:table-column column-number="1" column-width="56mm" />
			<!-- 8 + 48 -->
			<fo:table-column column-number="2" column-width="124mm" />
			<!-- 42 + 82 -->
			<fo:table-body>
				<fo:table-row border="{$titlebox-small-border-style}">
					<fo:table-cell column-number="1" number-rows-spanned="2" border="{$titlebox-wide-border-style}">
						<fo:block xsl:use-attribute-sets="titlebox-font">
							<!-- West -->
							<fo:table table-layout="fixed" width="100%" display-align="center">
								<fo:table-column column-number="1" column-width="{8 - $titlebox-WB}mm" />
								<fo:table-column column-number="2" column-width="11mm" />
								<fo:table-column column-number="3" column-width="9.25mm" />
								<fo:table-column column-number="4" column-width="9.25mm" />
								<fo:table-column column-number="5" column-width="9.25mm" />
								<fo:table-column column-number="6" column-width="{9.25 - $titlebox-WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{6 - $titlebox-WB - $titlebox-WB}mm">
										<fo:table-cell height="inherit" border-style="solid" column-number="1" number-columns-spanned="6" border-width="0px" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=4]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4 - $titlebox-WB - $titlebox-SB}mm">
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="1" number-rows-spanned="2" border-left-width="0px" border-top="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=11]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="2" number-rows-spanned="2" border-top="{$titlebox-wide-border-style}" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=12]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" border-top="{$titlebox-wide-border-style}" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=13]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" border-top="{$titlebox-wide-border-style}" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=14]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" border-top="{$titlebox-wide-border-style}" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=15]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-top="{$titlebox-wide-border-style}" border-right-width="0px" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=16]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{4 - $titlebox-SB - $titlebox-SB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=19]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=20]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=21]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=22]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 4 -->
									<fo:table-row height="{4 - $titlebox-SB - $titlebox-SB}mm">
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="1" number-rows-spanned="2" border-left-width="0px">
											<xsl:apply-templates select="Field[@address=24]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="2" number-rows-spanned="2" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=25]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=26]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=27]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=28]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-right-width="0px" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=29]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 5 -->
									<fo:table-row height="{4 - $titlebox-SB - $titlebox-SB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=30]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=31]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=32]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=33]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 6 -->
									<fo:table-row height="{4 - $titlebox-SB - $titlebox-SB}mm">
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="1" number-rows-spanned="2" border-left-width="0px">
											<xsl:apply-templates select="Field[@address=34]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="2" number-rows-spanned="2" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=35]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=36]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=37]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=38]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-right-width="0px" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=39]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 7 -->
									<fo:table-row height="{4 - $titlebox-SB - $titlebox-SB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=43]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=44]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=45]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=46]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 8 -->
									<fo:table-row height="{4 - $titlebox-SB - $titlebox-SB}mm">
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="1" number-rows-spanned="2" border-left-width="0px">
											<xsl:apply-templates select="Field[@address=56]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$titlebox-WB}mm" border="{$titlebox-small-border-style}" column-number="2" number-rows-spanned="2" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=57]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=58]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=59]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=60]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-right-width="0px" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=61]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 9 -->
									<fo:table-row height="{4 - $titlebox-SB - $titlebox-WB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=70]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=71]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=72]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$titlebox-small-border-style}" column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=73]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 10 -->
									<fo:table-row height="{4 - $titlebox-WB - $titlebox-WB}mm" font-size="55%">
										<fo:table-cell height="inherit" border-style="solid" column-number="1" border-width="0px" border-top="{$titlebox-wide-border-style}" border-right="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=75]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid" column-number="2" border-width="0px" border-top="{$titlebox-wide-border-style}" border-right="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=76]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid" column-number="3" border-width="0px" border-top="{$titlebox-wide-border-style}" border-right="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=77]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid" column-number="4" border-width="0px" border-top="{$titlebox-wide-border-style}" border-right="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=78]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid" column-number="5" border-width="0px" border-top="{$titlebox-wide-border-style}" border-right="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=79]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid" column-number="6" border-width="0px" border-top="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=80]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell column-number="2" border="{$titlebox-wide-border-style}" border-bottom-width="0px">
						<fo:block>
							<!-- East-northeast -->
							<fo:table table-layout="fixed" width="100%" display-align="center">
								<fo:table-column column-number="1" column-width="{42 - $titlebox-WB}mm" />
								<fo:table-column column-number="2" column-width="17mm" />
								<fo:table-column column-number="3" column-width="11mm" />
								<fo:table-column column-number="4" column-width="{46 - 17 - 11}mm" />
								<fo:table-column column-number="5" column-width="{36 - 21 - 3}mm" />
								<fo:table-column column-number="6" column-width="21mm" />
								<fo:table-column column-number="7" column-width="{3 - $titlebox-WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{4 - $titlebox-WB - $titlebox-WB}mm">
										<fo:table-cell height="{4+4+12-3*$titlebox-WB}mm" column-number="1" number-rows-spanned="3" border-style="solid" border-width="0px" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=5]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=6]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=7]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" number-columns-spanned="2" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=8]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=9]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" font-size="50%" column-number="7" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=10]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4 - $titlebox-WB - $titlebox-SB}mm">
										<fo:table-cell height="{4+12-2*$titlebox-WB}mm" column-number="2" number-columns-spanned="3" number-rows-spanned="2" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-top="{$titlebox-wide-border-style}" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=17]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="5" number-columns-spanned="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-top="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=18]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{12 - $titlebox-SB - $titlebox-WB}mm">
										<fo:table-cell height="inherit" column-number="5" number-columns-spanned="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-top="{$titlebox-small-border-style}" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=23]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row border="{$titlebox-small-border-style}">
					<fo:table-cell column-number="2" border="{$titlebox-wide-border-style}" border-top-width="0px">
						<fo:block>
							<!-- East-southeast -->
							<fo:table table-layout="fixed" width="100%" display-align="center">
								<fo:table-column column-number="1" column-width="{42 - $titlebox-WB}mm" />
								<fo:table-column column-number="2" column-width="10.25mm" />
								<fo:table-column column-number="3" column-width="10.25mm" />
								<fo:table-column column-number="4" column-width="10.25mm" />
								<fo:table-column column-number="5" column-width="10.25mm" />
								<fo:table-column column-number="6" column-width="10.25mm" />
								<fo:table-column column-number="7" column-width="10.25mm" />
								<fo:table-column column-number="8" column-width="10.25mm" />
								<fo:table-column column-number="9" column-width="{10.25 - $titlebox-WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{4 - $titlebox-WB - $titlebox-WB}mm" text-align="left">
										<fo:table-cell height="inherit" column-number="1" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=40]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2" number-columns-spanned="4" border="{$titlebox-wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=41]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6" number-columns-spanned="4" border="{$titlebox-wide-border-style}" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=42]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4 - $titlebox-WB - $titlebox-WB}mm">
										<fo:table-cell height="{4+4+10-3*$titlebox-WB}mm" column-number="1" number-rows-spanned="3" border-style="solid" border-width="0px">
											<fo:block>
												<fo:external-graphic src="data/pictures/DB_InfraGo_logo_red_black_100px_rgb.svg" fox:alt-text="DB InfraGO" content-width="3cm" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2" border="{$titlebox-wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=48]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=49]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=50]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="5" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=51]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=52]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="7" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=53]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="8" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=54]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="9" border-style="solid" border-width="0px" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=55]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{4 - $titlebox-WB - $titlebox-WB}mm">
										<fo:table-cell height="inherit" column-number="2" border="{$titlebox-wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=62]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=63]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=64]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="5" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=65]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=66]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="7" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=67]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="8" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=68]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="9" border-style="solid" border-width="0px" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=69]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 4 -->
									<fo:table-row height="{10 - $titlebox-WB - $titlebox-WB}mm">
										<fo:table-cell height="inherit" column-number="2" number-columns-spanned="8" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=74]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell column-number="1" number-columns-spanned="2" border="{$titlebox-wide-border-style}">
						<fo:block>
							<!-- South -->
							<fo:table table-layout="fixed" width="100%" display-align="center">
								<fo:table-column column-number="1" column-width="{21 - $titlebox-WB}mm" />
								<fo:table-column column-number="2" column-width="14mm" />
								<fo:table-column column-number="3" column-width="21mm" />
								<fo:table-column column-number="4" column-width="21mm" />
								<fo:table-column column-number="5" column-width="61mm" />
								<fo:table-column column-number="6" column-width="15mm" />
								<fo:table-column column-number="7" column-width="{27 - $titlebox-WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{4.33 - $titlebox-WB - $titlebox-SB}mm">
										<fo:table-cell column-number="1" number-rows-spanned="3" border="{$titlebox-wide-border-style}" border-left-width="0px" border-top-width="0px" display-align="before">
											<fo:block>
												<!-- West-southwest -->
												<fo:table table-layout="fixed" width="100%" display-align="center">
													<fo:table-body>
														<fo:table-row height="{5 - $titlebox-WB - $titlebox-WB}mm">
															<fo:table-cell height="inherit" border-style="solid" border-width="0px" border-bottom="{$titlebox-wide-border-style}">
																<xsl:apply-templates select="Field[@address=81]">
																	<xsl:with-param name="fontSize" select="'3.5mm'"/>
																</xsl:apply-templates>
															</fo:table-cell>
														</fo:table-row>
														<fo:table-row height="{8 - $titlebox-WB - $titlebox-WB}mm">
															<fo:table-cell height="inherit" border-style="solid" border-width="0px">
																<xsl:apply-templates select="Field[@address=86]">
																	<xsl:with-param name="fontSize" select="'3.5mm'"/>
																</xsl:apply-templates>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2" border-style="solid" border-width="0px" border-bottom="{$titlebox-small-border-style}" text-align="center" font-size="60%">
											<xsl:apply-templates select="Field[@address=82]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-bottom="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=83]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" border="{$titlebox-small-border-style}" border-right="{$titlebox-wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=84]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{4.33*3+5*2-5*$titlebox-WB}mm" column-number="5" number-columns-spanned="3" number-rows-spanned="5" border-style="solid" border-width="0px" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=85]">
												<xsl:with-param name="firstLineFontSize" select="'5mm'"/>
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4.33 - $titlebox-SB - $titlebox-SB}mm">
										<fo:table-cell height="inherit" column-number="2" border-style="solid" border-width="0px" border-bottom="{$titlebox-small-border-style}" text-align="center" font-size="60%">
											<xsl:apply-templates select="Field[@address=87]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-bottom="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=88]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" border="{$titlebox-small-border-style}" border-right="{$titlebox-wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=89]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{4.33 - $titlebox-SB - $titlebox-WB}mm">
										<fo:table-cell height="inherit" column-number="2" border-style="solid" border-width="0px" border-bottom="{$titlebox-wide-border-style}" text-align="center" font-size="60%">
											<xsl:apply-templates select="Field[@address=90]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-bottom="{$titlebox-small-border-style}">
											<xsl:apply-templates select="Field[@address=91]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" border="{$titlebox-small-border-style}" border-right="{$titlebox-wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=92]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 4 -->
									<fo:table-row height="{5 - $titlebox-SB - $titlebox-WB}mm">
										<xsl:apply-templates select="PlanningOffice"/>
										<fo:table-cell height="inherit" column-number="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=94]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" border="{$titlebox-wide-border-style}" border-left="{$titlebox-small-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=95]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 5 -->
									<fo:table-row height="{5 - $titlebox-WB - $titlebox-WB}mm">
										<fo:table-cell height="inherit" column-number="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}" border-bottom="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=96]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4" border="{$titlebox-wide-border-style}" border-left="{$titlebox-small-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=97]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 6 -->
									<fo:table-row height="{10 - $titlebox-WB - $titlebox-WB}mm">
										<fo:table-cell height="inherit" column-number="3" number-columns-spanned="3" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=98]">
												<xsl:with-param name="fontSize" select="'5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<xsl:apply-templates select="Field[@address=99]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="7" border-style="solid" border-width="0px" border-left="{$titlebox-wide-border-style}">
											<fo:block font-size="3.5mm">
												<fo:page-number />
												<xsl:value-of select="$pagePostfix" />
											</fo:block>
											<!-- Invisible page number to find out, which page is footnote page in multilayout page -->
											<fo:block-container position="absolute" left="0mm" top="0.5mm">
												<fo:block font-size="5pt" color="white">
													<fo:inline>
														<xsl:text>PageNumber_</xsl:text>
													</fo:inline>
													<fo:page-number />
													<xsl:value-of select="$pagePostfix" />
												</fo:block>
											</fo:block-container>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="Field">
		<xsl:param name="fontSize" select="'0.5mm'" />
		<xsl:param name="firstLineFontSize" select="$fontSize" />
		<fo:block-container xsl:use-attribute-sets="cell-block-container-style">
			<fo:block>
				<xsl:for-each select="str:tokenize(., '&#xA;')">
					<xsl:choose>
						<xsl:when test="position()=1">
							<fo:inline font-size="{$firstLineFontSize}">
								<xsl:value-of select="." />
								<xsl:text>&#xA;</xsl:text>
							</fo:inline>
						</xsl:when>
						<xsl:when test="position()=last()">
							<fo:inline font-size="{$fontSize}">
								<xsl:value-of select="." />
							</fo:inline>
						</xsl:when>
						<xsl:otherwise>
							<fo:inline font-size="{$fontSize}">
								<xsl:value-of select="." />
							</fo:inline>
							<xsl:text>&#xA;</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</fo:block>
		</fo:block-container>
	</xsl:template>

	<xsl:template match="PlanningOffice[@variant='logo-side']">
		<fo:table-cell margin="0" padding="0" height="{5+5+10-3*$titlebox-WB}mm" column-number="1" number-columns-spanned="2" number-rows-spanned="3">
			<fo:block>
				<fo:table table-layout="fixed">
					<fo:table-column column-number="1" column-width="30%" />
					<fo:table-column column-number="2" column-width="70%" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell column-number="1">
								<fo:block>
									<fo:external-graphic src="{@logo}" width="10mm" height="{5+5+10-7*$titlebox-WB}mm" content-height="scale-down-to-fit" content-width="scale-down-to-fit" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell column-number="2">
								<fo:block margin-left="1pt" text-align="left">
									<fo:block font-size="{./Name/@fontsize}">
										<xsl:value-of select="./Name" />
									</fo:block>
									<fo:block font-size="{./Group/@fontsize}">
										<xsl:value-of select="./Group" />
									</fo:block>
									<fo:block font-size="{./Location/@fontsize}">
										<xsl:value-of select="./Location"/>
									</fo:block>
									<fo:block font-size="{./Phone/@fontsize}">
										<xsl:value-of select="./Phone" />
									</fo:block>
									<fo:block font-size="{./Email/@fontsize}">
										<xsl:value-of select="./Email" />
									</fo:block>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
	<xsl:template match="PlanningOffice[@variant='logo-top']">
		<fo:table-cell margin="0" padding="0" height="{5+5+10-3*$titlebox-WB}mm" column-number="1" number-columns-spanned="2" number-rows-spanned="3" border-style="solid" border-width="0px">
			<fo:block-container height="7mm">
				<fo:block margin-top="-0.5mm">
					<fo:external-graphic src="{@logo}" height="7mm" width="33mm" content-height="scale-down-to-fit" content-width="scale-down-to-fit" />
				</fo:block>
			</fo:block-container>
			<fo:block-container margin-left="2pt" text-align="left" height="12mm">
				<fo:block font-size="{./Name/@fontsize}">
					<xsl:value-of select="./Name" />
				</fo:block>
				<fo:block font-size="{./Group/@fontsize}">
					<xsl:value-of select="./Group" />
				</fo:block>
				<fo:block font-size="{./Location/@fontsize}">
					<xsl:value-of select="./Location"/>
				</fo:block>
				<fo:block font-size="{./Phone/@fontsize}">
					<xsl:value-of select="./Phone" />
				</fo:block>
				<fo:block font-size="{./Email/@fontsize}">
					<xsl:value-of select="./Email" />
				</fo:block>
			</fo:block-container>
		</fo:table-cell>
	</xsl:template>
	<xsl:template match="PlanningOffice[@variant='no-logo']">
		<fo:table-cell margin="0" padding="0" height="{5+5+10-3*$titlebox-WB}mm" column-number="1" number-columns-spanned="2" number-rows-spanned="3">
			<fo:block>
				<fo:block margin-left="1pt" text-align="center">
					<fo:block font-size="{./Name/@fontsize}">
						<xsl:value-of select="./Name" />
					</fo:block>
					<fo:block font-size="{./Group/@fontsize}">
						<xsl:value-of select="./Group" />
					</fo:block>
					<fo:block font-size="{./Location/@fontsize}">
						<xsl:value-of select="./Location"/>
					</fo:block>
					<fo:block font-size="{./Phone/@fontsize}">
						<xsl:value-of select="./Phone" />
					</fo:block>
					<fo:block font-size="{./Email/@fontsize}">
						<xsl:value-of select="./Email" />
					</fo:block>
				</fo:block>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
</xsl:stylesheet>
