<?xml version="1.0" encoding="UTF-8" standalone="no"?><!--
Copyright (c) 2025 DB Netz AG and others.

All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v2.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v20.html
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
  xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" exclude-result-prefixes="fo" version="2.0">

  <xsl:include href="data/export/pdf/common.xsl"/>
  <xsl:include href="data/export/pdf/titlebox.xsl"/>
  <xsl:include href="data/export/pdf/folding-marks.xsl"/>


  <!-- The Variables will be set value by Java-->
  <xsl:variable name="siteplan-freefeld-height"/>
  <xsl:variable name="significant-width"/>
  <xsl:variable name="significant-height"/>
  <xsl:variable name="siteplan-folding-mark-right-width"/>
  <xsl:variable name="region-body-height"/>
  <xsl:variable name="region-body-width"/>
  <xsl:variable name="pagePosition"/>
  <xsl:variable name="pagePostFix"/>

  <xsl:attribute-set name="page-master-style">
    <!-- Page layout -->
    <xsl:attribute name="page-height"/>
    <xsl:attribute name="page-width"/>
  </xsl:attribute-set>

  <xsl:attribute-set name="siteplan-folding-mark-region-attribute">
    <xsl:attribute name="extent">10mm</xsl:attribute>
    <xsl:attribute name="precedence">true</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="siteplan-region-style">
    <xsl:attribute name="margin-top">10mm</xsl:attribute>
    <xsl:attribute name="margin-left">20mm</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="siteplan-title-box-region-style">
    <!-- Width of the region with the title box -->
    <xsl:attribute name="extent">190mm</xsl:attribute>
  </xsl:attribute-set>

  <!-- Main page layout -->
  <xsl:template match="/" name="MainPage">
    <fo:root language="de" linefeed-treatment="preserve" xsl:use-attribute-sets="default-font">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="table-master" xsl:use-attribute-sets="page-master-style">
          <fo:region-body xsl:use-attribute-sets="siteplan-region-style"/>
          <fo:region-before region-name="folding-mark-region-top" xsl:use-attribute-sets="siteplan-folding-mark-region-attribute"/>
          <fo:region-after region-name="folding-mark-region-bottom" xsl:use-attribute-sets="siteplan-folding-mark-region-attribute"/>
          <fo:region-start region-name="folding-mark-region-left" xsl:use-attribute-sets="siteplan-folding-mark-region-attribute"/>
          <fo:region-end region-name="title-box-region" xsl:use-attribute-sets="siteplan-title-box-region-style"/>
        </fo:simple-page-master>

        <fo:page-sequence-master master-name="page-sequence-master">
          <fo:repeatable-page-master-alternatives>
            <fo:conditional-page-master-reference master-reference="table-master"/>
          </fo:repeatable-page-master-alternatives>
        </fo:page-sequence-master>
      </fo:layout-master-set>

      <fo:page-sequence master-reference="page-sequence-master" initial-page-number="{$pagePosition}">
        <fo:static-content flow-name="folding-mark-region-top">
          <xsl:call-template name="CutMark"/>
          <fo:block-container height="100%" width="100%">
            <xsl:call-template name="siteplan-folding-mark-top-bottom"/>
          </fo:block-container>
        </fo:static-content>


        <fo:static-content flow-name="title-box-region">
          <fo:block-container height="100%" width="100%">
            <fo:block>
              <xsl:call-template name="SiteplanTitleboxRegion">
                <xsl:with-param name="pagePostfix" select="$pagePostFix"/>
              </xsl:call-template>
            </fo:block>
          </fo:block-container>
        </fo:static-content>

        <fo:static-content flow-name="folding-mark-region-bottom">
          <fo:block-container height="100%" width="100%" padding-top="5mm">
            <xsl:call-template name="siteplan-folding-mark-top-bottom"/>
          </fo:block-container>
        </fo:static-content>

        <fo:static-content flow-name="folding-mark-region-left">
          <xsl:call-template name="siteplan-folding-mark-side"/>
        </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
          <xsl:for-each select="/Siteplan/Image">
            <fo:block-container display-align="center" height="{$region-body-height}" text-align="center" width="{$region-body-width}" overflow="hidden">
              <fo:block>
                <xsl:variable name="image-width">
                  <xsl:value-of select="current()/Width"/>
                </xsl:variable>
                <xsl:variable name="image-height">
                  <xsl:value-of select="current()/Height"/>
                </xsl:variable>
                <fo:external-graphic content-width="{$image-width}" content-height="{$image-height}">
                  <xsl:attribute name="src">
                    <xsl:value-of select="current()/Byte"/>
                  </xsl:attribute>
                </fo:external-graphic>
              </fo:block>
            </fo:block-container>
          </xsl:for-each>
        </fo:flow>
      </fo:page-sequence>

    </fo:root>
  </xsl:template>

  <xsl:template name="SiteplanTitleboxRegion">
    <xsl:param name="pagePostfix" select="''"/>
    <fo:table table-layout="fixed" width="100%">
      <!-- IMPROVE: Schriftfeld ist etwas breiter als erwartet -->
      <fo:table-column column-width="{180 + $WB + $WB + $WB + $WB}mm"/>
      <!-- Folding markcolumn-->
      <fo:table-column column-width="{$siteplan-folding-mark-right-width}"/>
      <fo:table-body>
        <fo:table-row height="{$siteplan-freefeld-height}">
          <fo:table-cell>
            <fo:block color="white">.</fo:block>
          </fo:table-cell>
          <fo:table-cell number-rows-spanned="2" padding-left="5mm">
            <xsl:call-template name="siteplan-folding-mark-side"/>
          </fo:table-cell>
        </fo:table-row>
        <fo:table-row>
          <fo:table-cell padding-top="5mm">
            <fo:table table-layout="fixed" width="100%">
              <fo:table-column column-width="100%"/>
              <fo:table-body>
                <fo:table-row max-height="75mm">
                  <fo:table-cell>
                    <xsl:apply-templates select="//TitleBox">
                      <xsl:with-param name="pagePostfix" select="$pagePostfix"/>
                    </xsl:apply-templates>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>
    <fo:block-container absolute-position="absolute" bottom="0mm" display-align="after" height="{$significant-height}" overflow="hidden" right="195mm" width="{$significant-width}">
      <fo:block>
        <xsl:choose>
          <xsl:when test="/Siteplan/Freefield/SignificantInformation">
            <xsl:apply-templates select="/Siteplan/Freefield/SignificantInformation"/>
          </xsl:when>
          <xsl:otherwise>
            <fo:block/>
          </xsl:otherwise>
        </xsl:choose>
      </fo:block>
    </fo:block-container>
  </xsl:template>


  <xsl:template name="siteplan-folding-mark-top-bottom">
    <!-- Generate by Siteplan Export Process -->
    <fo:block color="white">.</fo:block>
  </xsl:template>

  <xsl:template name="siteplan-folding-mark-side">
    <!-- Generate by Siteplan Export Process -->
    <fo:block color="white">.</fo:block>
  </xsl:template>
  
	<xsl:template name="CutMark">
    <fo:block-container top="0mm" height="5mm" width="10mm" background-color="black" absolute-position="absolute" z-index="99">
			<fo:block color="black">.</fo:block>
		</fo:block-container>
    <fo:block-container top="5mm" height="5mm" width="5mm" background-color="black" absolute-position="absolute" z-index="99">
			<fo:block color="black">.</fo:block>
		</fo:block-container>
	</xsl:template>
</xsl:stylesheet>
