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
	xmlns:fox="http://xmlgraphics.apache.org/fop/extensions"
	exclude-result-prefixes="fo">

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

		<fo:table xsl:use-attribute-sets="title-box-table-style"
			table-layout="fixed">

			<fo:table-column column-number="1" column-width="56mm" /> <!-- 8 + 48 -->
			<fo:table-column column-number="2" column-width="124mm" /> <!-- 42 + 82 -->
			<fo:table-body>
				<fo:table-row border="{$small-border-style}">
					<fo:table-cell column-number="1" number-rows-spanned="2"
						border="{$wide-border-style}">
						<fo:block xsl:use-attribute-sets="titlebox-font">
							<!-- West -->
							<fo:table table-layout="fixed" width="100%"
								display-align="center">
								<fo:table-column column-number="1" column-width="{8 - $WB}mm" />
								<fo:table-column column-number="2" column-width="11mm" />
								<fo:table-column column-number="3" column-width="9.25mm" />
								<fo:table-column column-number="4" column-width="9.25mm" />
								<fo:table-column column-number="5" column-width="9.25mm" />
								<fo:table-column column-number="6" column-width="{9.25 - $WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{6 - $WB - $WB}mm">
										<fo:table-cell height="inherit" border-style="solid"
											column-number="1" number-columns-spanned="6" border-width="0px" xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=4]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4 - $WB - $SB}mm">
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="1" number-rows-spanned="2" border-left-width="0px"
											border-top="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=11]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="2" number-rows-spanned="2" border-top="{$wide-border-style}"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=12]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3" border-top="{$wide-border-style}"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=13]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4" border-top="{$wide-border-style}"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=14]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5" border-top="{$wide-border-style}"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=15]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-top="{$wide-border-style}"
											border-right-width="0px"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=16]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{4 - $SB - $SB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=19]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=20]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=21]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=22]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 4 -->
									<fo:table-row height="{4 - $SB - $SB}mm">
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="1" number-rows-spanned="2" border-left-width="0px">
											<xsl:apply-templates select="Field[@address=24]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="2" number-rows-spanned="2"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=25]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=26]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=27]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=28]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-right-width="0px"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=29]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 5 -->
									<fo:table-row height="{4 - $SB - $SB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=30]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=31]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=32]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=33]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 6 -->
									<fo:table-row height="{4 - $SB - $SB}mm">
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="1" number-rows-spanned="2" border-left-width="0px">
											<xsl:apply-templates select="Field[@address=34]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="2" number-rows-spanned="2"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=35]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=36]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=37]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=38]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-right-width="0px"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=39]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 7 -->
									<fo:table-row height="{4 - $SB - $SB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=43]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=44]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=45]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=46]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 8 -->
									<fo:table-row height="{4 - $SB - $SB}mm">
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="1" number-rows-spanned="2" border-left-width="0px">
											<xsl:apply-templates select="Field[@address=56]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{2*4-2*$WB}mm" border="{$small-border-style}"
											column-number="2" number-rows-spanned="2"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=57]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=58]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=59]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=60]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-right-width="0px"
											xsl:use-attribute-sets="font-size-west-col2">
											<xsl:apply-templates select="Field[@address=61]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 9 -->
									<fo:table-row height="{4 - $SB - $WB}mm" xsl:use-attribute-sets="font-size-west-col2">
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="3" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=70]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="4" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=71]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="5" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=72]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border="{$small-border-style}"
											column-number="6" border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=73]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 10 -->
									<fo:table-row height="{4 - $WB - $WB}mm"
										font-size="55%">
										<fo:table-cell height="inherit" border-style="solid"
											column-number="1" border-width="0px" border-top="{$wide-border-style}"
											border-right="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=75]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid"
											column-number="2" border-width="0px" border-top="{$wide-border-style}"
											border-right="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=76]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid"
											column-number="3" border-width="0px" border-top="{$wide-border-style}"
											border-right="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=77]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid"
											column-number="4" border-width="0px" border-top="{$wide-border-style}"
											border-right="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=78]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid"
											column-number="5" border-width="0px" border-top="{$wide-border-style}"
											border-right="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=79]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" border-style="solid"
											column-number="6" border-width="0px" border-top="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=80]">
												<xsl:with-param name="fontSize" select="'2mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell column-number="2" border="{$wide-border-style}"
						border-bottom-width="0px">
						<fo:block>
							<!-- East-northeast -->
							<fo:table table-layout="fixed" width="100%"
								display-align="center">
								<fo:table-column column-number="1" column-width="{42 - $WB}mm" />
								<fo:table-column column-number="2" column-width="17mm" />
								<fo:table-column column-number="3" column-width="11mm" />
								<fo:table-column column-number="4"
									column-width="{46 - 17 - 11}mm" />
								<fo:table-column column-number="5" column-width="{36 - 21 - 3}mm" />
								<fo:table-column column-number="6" column-width="21mm" />
								<fo:table-column column-number="7" column-width="{3 - $WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{4 - $WB - $WB}mm">
										<fo:table-cell height="{4+4+12-3*$WB}mm"
											column-number="1" number-rows-spanned="3" border-style="solid"
											border-width="0px" border-bottom="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=5]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=6]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=7]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											number-columns-spanned="2" border-style="solid" border-width="0px"
											border-left="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=8]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=9]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" font-size="50%"
											column-number="7" border-style="solid" border-width="0px"
											border-left="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=10]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4 - $WB - $SB}mm">
										<fo:table-cell height="{4+12-2*$WB}mm"
											column-number="2" number-columns-spanned="3"
											number-rows-spanned="2" border-style="solid" border-width="0px"
											border-left="{$wide-border-style}" border-top="{$wide-border-style}"
											border-bottom="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=17]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="5"
											number-columns-spanned="3" border-style="solid" border-width="0px"
											border-left="{$wide-border-style}" border-top="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=18]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{12 - $SB - $WB}mm">
										<fo:table-cell height="inherit" column-number="5"
											number-columns-spanned="3" border-style="solid" border-width="0px"
											border-left="{$wide-border-style}" border-top="{$small-border-style}"
											border-bottom="{$wide-border-style}">
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
				<fo:table-row border="{$small-border-style}">
					<fo:table-cell column-number="2" border="{$wide-border-style}"
						border-top-width="0px">
						<fo:block>
							<!-- East-southeast -->
							<fo:table table-layout="fixed" width="100%"
								display-align="center">
								<fo:table-column column-number="1" column-width="{42 - $WB}mm" />
								<fo:table-column column-number="2" column-width="10.25mm" />
								<fo:table-column column-number="3" column-width="10.25mm" />
								<fo:table-column column-number="4" column-width="10.25mm" />
								<fo:table-column column-number="5" column-width="10.25mm" />
								<fo:table-column column-number="6" column-width="10.25mm" />
								<fo:table-column column-number="7" column-width="10.25mm" />
								<fo:table-column column-number="8" column-width="10.25mm" />
								<fo:table-column column-number="9" column-width="{10.25 - $WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{4 - $WB - $WB}mm"
										text-align="left">
										<fo:table-cell height="inherit" column-number="1"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=40]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2"
											number-columns-spanned="4" border="{$wide-border-style}"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=41]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6"
											number-columns-spanned="4" border="{$wide-border-style}"
											border-top-width="0px" border-right-width="0px">
											<xsl:apply-templates select="Field[@address=42]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4 - $WB - $WB}mm">
										<fo:table-cell height="{4+4+10-3*$WB}mm"
											column-number="1" number-rows-spanned="3" border-style="solid"
											border-width="0px">
											<fo:block>
												<fo:external-graphic src="pictures/DB-NETZE_eM_rgb_100px.svg"
													fox:alt-text="DB Netze" content-width="3cm" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2"
											border="{$wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=48]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=49]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=50]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="5"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=51]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=52]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="7"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=53]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="8"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=54]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="9"
											border-style="solid" border-width="0px" border-bottom="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=55]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{4 - $WB - $WB}mm">
										<fo:table-cell height="inherit" column-number="2"
											border="{$wide-border-style}" border-top-width="0px">
											<xsl:apply-templates select="Field[@address=62]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=63]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=64]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="5"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=65]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=66]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="7"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=67]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="8"
											border="{$wide-border-style}" border-left-width="0px"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=68]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="9"
											border-style="solid" border-width="0px" border-bottom="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=69]">
												<xsl:with-param name="fontSize" select="'1.6mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 4 -->
									<fo:table-row height="{10 - $WB - $WB}mm">
										<fo:table-cell height="inherit" column-number="2"
											number-columns-spanned="8" border-style="solid" border-width="0px"
											border-left="{$wide-border-style}">
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
					<fo:table-cell column-number="1"
						number-columns-spanned="2" border="{$wide-border-style}">
						<fo:block>
							<!-- South -->
							<fo:table table-layout="fixed" width="100%"
								display-align="center">
								<fo:table-column column-number="1" column-width="{21 - $WB}mm" />
								<fo:table-column column-number="2" column-width="14mm" />
								<fo:table-column column-number="3" column-width="21mm" />
								<fo:table-column column-number="4" column-width="21mm" />
								<fo:table-column column-number="5" column-width="61mm" />
								<fo:table-column column-number="6" column-width="15mm" />
								<fo:table-column column-number="7" column-width="{27 - $WB}mm" />
								<fo:table-body>
									<!-- Zeile 1 -->
									<fo:table-row height="{4.33 - $WB - $SB}mm">
										<fo:table-cell column-number="1"
											number-rows-spanned="3" border="{$wide-border-style}"
											border-left-width="0px" border-top-width="0px" display-align="before">
											<fo:block>
												<!-- West-southwest -->
												<fo:table table-layout="fixed" width="100%"
													display-align="center">
													<fo:table-body>
														<fo:table-row height="{5 - $WB - $WB}mm">
															<fo:table-cell height="inherit"
																border-style="solid" border-width="0px" border-bottom="{$wide-border-style}">
																<xsl:apply-templates select="Field[@address=81]">
																	<xsl:with-param name="fontSize" select="'2.5mm'"/>
																</xsl:apply-templates>
															</fo:table-cell>
														</fo:table-row>
														<fo:table-row height="{8 - $WB - $WB}mm">
															<fo:table-cell height="inherit"
																border-style="solid" border-width="0px">
																<xsl:apply-templates select="Field[@address=86]">
																	<xsl:with-param name="fontSize" select="'3.5mm'"/>
																</xsl:apply-templates>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="2"
											border-style="solid" border-width="0px" border-bottom="{$small-border-style}"
											text-align="left" font-size="60%">
											<xsl:apply-templates select="Field[@address=82]">
												<xsl:with-param name="fontSize" select="'2.8mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}"
											border-bottom="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=83]">
												<xsl:with-param name="fontSize" select="'3.4mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											border="{$small-border-style}" border-right="{$wide-border-style}"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=84]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="{4.33*3+5*2-5*$WB}mm"
											column-number="5" number-columns-spanned="3"
											number-rows-spanned="5" border-style="solid" border-width="0px"
											border-bottom="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=85]">
												<xsl:with-param name="fontSize" select="'4mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 2 -->
									<fo:table-row height="{4.33 - $SB - $SB}mm">
										<fo:table-cell height="inherit" column-number="2"
											border-style="solid" border-width="0px" border-bottom="{$small-border-style}"
											text-align="left" font-size="60%">
											<xsl:apply-templates select="Field[@address=87]">
												<xsl:with-param name="fontSize" select="'2.8mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}"
											border-bottom="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=88]">
												<xsl:with-param name="fontSize" select="'3.4mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											border="{$small-border-style}" border-right="{$wide-border-style}"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=89]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 3 -->
									<fo:table-row height="{4.33 - $SB - $WB}mm">
										<fo:table-cell height="inherit" column-number="2"
											border-style="solid" border-width="0px" border-bottom="{$wide-border-style}"
											text-align="left" font-size="60%">
											<xsl:apply-templates select="Field[@address=90]">
												<xsl:with-param name="fontSize" select="'2.8mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}"
											border-bottom="{$small-border-style}">
											<xsl:apply-templates select="Field[@address=91]">
												<xsl:with-param name="fontSize" select="'3.4mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											border="{$small-border-style}" border-right="{$wide-border-style}"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=92]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 4 -->
									<fo:table-row height="{5 - $SB - $WB}mm">
										<fo:table-cell height="{5+5+10-3*$WB}mm"
											column-number="1" number-columns-spanned="2"
											number-rows-spanned="3" border-style="solid" border-width="0px">
											<xsl:apply-templates select="Field[@address=93]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="3"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}"
											border-bottom="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=94]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											border="{$wide-border-style}" border-left="{$small-border-style}"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=95]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 5 -->
									<fo:table-row height="{5 - $WB - $WB}mm">
										<fo:table-cell height="inherit" column-number="3"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}"
											border-bottom="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=96]">
												<xsl:with-param name="fontSize" select="'2.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="4"
											border="{$wide-border-style}" border-left="{$small-border-style}"
											border-top-width="0px">
											<xsl:apply-templates select="Field[@address=97]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
									</fo:table-row>
									<!-- Zeile 6 -->
									<fo:table-row height="{10 - $WB - $WB}mm">
										<fo:table-cell height="inherit" column-number="3"
											number-columns-spanned="3" border-style="solid" border-width="0px"
											border-left="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=98]">
												<xsl:with-param name="fontSize" select="'5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="6"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}">
											<xsl:apply-templates select="Field[@address=99]">
												<xsl:with-param name="fontSize" select="'3.5mm'"/>
											</xsl:apply-templates>
										</fo:table-cell>
										<fo:table-cell height="inherit" column-number="7"
											border-style="solid" border-width="0px" border-left="{$wide-border-style}">
											<fo:block font-size="3.5mm">
												<fo:page-number />
												<xsl:value-of select="$pagePostfix" />
											</fo:block>
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
		<fo:block-container xsl:use-attribute-sets="cell-block-container-style">
			<fo:block font-size="{$fontSize}">
				<xsl:value-of select="." />
			</fo:block>
		</fo:block-container>
	</xsl:template>

</xsl:stylesheet>
