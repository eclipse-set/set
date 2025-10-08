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

	<xsl:variable name="small-border-width" select="0.125" />
	<xsl:variable name="wide-border-width" select="0.3" />

	<xsl:variable name="small-border-style" select="concat($small-border-width, 'mm solid')"/>
	<xsl:variable name="wide-border-style" select="concat($wide-border-width, 'mm solid')"/>
	<!-- SB = small border / 2, WB = wide border / 2 -->
	<xsl:variable name="SB" select="$small-border-width div 2" />
	<xsl:variable name="WB" select="$wide-border-width div 2" />

	<xsl:variable name="folding-mark-region-extend">10mm</xsl:variable>
	<xsl:variable name="title-box-region-extend">90mm</xsl:variable>

	<xsl:attribute-set name="folding-mark-region-style">
		<!-- Width of the region with the folding marks box -->
		<xsl:attribute name="extent">
			<xsl:value-of select="$folding-mark-region-extend"/>
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="title-box-region-style">
		<!-- Width of the region with the title box -->
		<xsl:attribute name="extent">
			<xsl:value-of select="$title-box-region-extend"/>
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="default-font">
		<xsl:attribute name="font-family">Open Sans Condensed</xsl:attribute>
		<xsl:attribute name="font-size">8</xsl:attribute>
		<xsl:attribute name="font-style">normal</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="titlebox-font">
		<xsl:attribute name="font-family">Open Sans Condensed</xsl:attribute>
		<xsl:attribute name="font-style">normal</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="external-graphic-style">
		<xsl:attribute name="content-height">8pt</xsl:attribute>
		<xsl:attribute name="alignment-adjust">-15%</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="significant-information-style">
		<xsl:attribute name="font-size">8</xsl:attribute>
		<xsl:attribute name="text-align">right</xsl:attribute>
		<xsl:attribute name="start-indent">-5mm</xsl:attribute>
		<xsl:attribute name="end-indent">5mm</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="body-row-style">
		<xsl:attribute name="border-right">
			<xsl:value-of select="$wide-border-style"/>
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="body-row-cell-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="border-right">
			<xsl:value-of select="$wide-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="border-bottom">
			<xsl:value-of select="$small-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="body-last-row-first-cell-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="border-right">
			<xsl:value-of select="$wide-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="default-cell-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="padding-left">1pt</xsl:attribute>
		<xsl:attribute name="padding-right">1pt</xsl:attribute>
		<xsl:attribute name="padding-top">1pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="first-column-cell-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="border-left">
			<xsl:value-of select="$wide-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
		<xsl:attribute name="padding-left">1pt</xsl:attribute>
		<xsl:attribute name="padding-right">1pt</xsl:attribute>
		<xsl:attribute name="padding-top">1pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="first-column-last-row-cell-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="border-left">
			<xsl:value-of select="$wide-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="border-bottom">
			<xsl:value-of select="$wide-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
		<xsl:attribute name="padding-left">1pt</xsl:attribute>
		<xsl:attribute name="padding-right">1pt</xsl:attribute>
		<xsl:attribute name="padding-top">1pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="last-row-cell-style">
		<xsl:attribute name="border">
			<xsl:value-of select="$small-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="border-bottom">
			<xsl:value-of select="$wide-border-style"/>
		</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="padding-left">1pt</xsl:attribute>
		<xsl:attribute name="padding-right">1pt</xsl:attribute>
		<xsl:attribute name="padding-top">1pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="region-body-style">
		<xsl:attribute name="margin-top">
			<xsl:value-of select="$folding-mark-region-extend"/>
		</xsl:attribute>
		<xsl:attribute name="margin-bottom">
			<xsl:value-of select="$title-box-region-extend"/>
		</xsl:attribute>
		<xsl:attribute name="overflow">hidden</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="title-box-table-style">
		<xsl:attribute name="width">18.08cm</xsl:attribute>
		<xsl:attribute name="margin">0mm</xsl:attribute>
		<xsl:attribute name="padding">0mm</xsl:attribute>
		<xsl:attribute name="start-indent">0mm</xsl:attribute>
		<xsl:attribute name="end-indent">0mm</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="linefeed-treatment">preserve</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="title-footnotes-style">
		<xsl:attribute name="font-size">16pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
	</xsl:attribute-set>

	<!-- Fill body rows -->
	<xsl:template match="Row">
		<fo:table-row xsl:use-attribute-sets="body-row-style" keep-together.within-page="always">
			<fo:table-cell xsl:use-attribute-sets="body-row-cell-style">
				<fo:block>
					<xsl:value-of select="@group-number"/>
				</fo:block>
			</fo:table-cell>
			<xsl:apply-templates/>
		</fo:table-row>
	</xsl:template>
	<xsl:template match="Row[position()=last()]">
		<fo:table-row xsl:use-attribute-sets="body-row-style" keep-together.within-page="always">
			<fo:table-cell xsl:use-attribute-sets="body-last-row-first-cell-style">
				<fo:block>
					<xsl:value-of select="@group-number"/>
				</fo:block>
			</fo:table-cell>
			<xsl:apply-templates/>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="Row[not(Cell[@column-number = '1'])]">
		<fo:table-row xsl:use-attribute-sets="body-row-style"
									keep-with-previous.within-page="always">
			<fo:table-cell xsl:use-attribute-sets="body-row-cell-style">
				<fo:block>
					<xsl:value-of select="@group-number"/>
				</fo:block>
			</fo:table-cell>
			<xsl:apply-templates/>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="TitleboxRegion">
		<xsl:param name="pagePostfix" select="''" />
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<!-- IMPROVE: Schriftfeld ist etwas breiter als erwartet -->
			<fo:table-column column-width="{180 + $WB + $WB + $WB + $WB}mm"/>
			<fo:table-body start-indent="{$WB + $WB + $WB + $WB}mm"
										 end-indent="{- $WB - $WB - $WB - $WB}mm">
				<fo:table-row>
					<fo:table-cell min-height="70mm">
						<fo:block></fo:block>
					</fo:table-cell>
					<fo:table-cell column-number="2" padding-top="5mm" number-rows-spanned="2">
						<fo:table table-layout="fixed" width="100%">
							<fo:table-column column-width="100%"/>
							<fo:table-body>
								<fo:table-row max-height="75mm">
									<fo:table-cell>
										<xsl:apply-templates select="//TitleBox">
											<xsl:with-param name="pagePostfix" select="$pagePostfix" />
										</xsl:apply-templates>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell display-align="center">
						<fo:block-container height="10mm" overflow="hidden" display-align="after">
							<xsl:choose>
								<xsl:when test="/Table/Freefield/SignificantInformation">
									<xsl:apply-templates
										select="/Table/Freefield/SignificantInformation" />
								</xsl:when>
								<xsl:otherwise>
									<fo:block></fo:block>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="SignificantInformation">
		<fo:block xsl:use-attribute-sets="significant-information-style">
			<xsl:value-of select="."/>
		</fo:block>
	</xsl:template>

	<xsl:template match="Footnotes[Footnote]">
		<fo:block page-break-before="always" xsl:use-attribute-sets="title-footnotes-style">
			<xsl:text>Bemerkungen</xsl:text>
		</fo:block>
		<xsl:apply-templates select="Footnote"/>
	</xsl:template>

	<xsl:template match="Footnote">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="COMMON_FOOTNOTE">
		<fo:block text-align="left">
			<xsl:text>*</xsl:text>
			<xsl:value-of select="@footnote-number"/>
			<xsl:text>: </xsl:text>
			<xsl:value-of select="."/>
		</fo:block>
	</xsl:template>
	<xsl:template match="NEW_FOOTNOTE">
		<fo:block text-align="left">
			<fo:inline color="#cd0000">
				<xsl:text>*</xsl:text>
				<xsl:value-of select="@footnote-number"/>
				<xsl:text>: </xsl:text>
				<xsl:value-of select="."/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OLD_FOOTNOTE">
		<fo:block text-align="left">
			<fo:inline background-color="yellow" text-decoration="line-through">
				<xsl:text>*</xsl:text>
				<xsl:value-of select="@footnote-number"/>
				<xsl:text>: </xsl:text>
				<xsl:value-of select="."/>
			</fo:inline>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>