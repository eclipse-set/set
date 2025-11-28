<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2023 DB Netz AG and others.

All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v2.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v20.html
-->
<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" exclude-result-prefixes="fo">

    <xsl:include href="data/export/pdf/common.xsl" />
    <xsl:include href="data/export/pdf/titlebox.xsl" />
    <xsl:include href="data/export/pdf/cells.xsl" />
    <xsl:include href="data/export/pdf/folding-marks.xsl" />
    <xsl:include href="data/export/pdf/content.xsl" />
    <xsl:variable name="water-mark-content"/>
    <!-- CUSTOMIZE: Adjust the following defaults only if needed -->
    <xsl:attribute-set name="table-master-style">
        <!-- Page layout -->
        <xsl:attribute name="margin-left">20mm</xsl:attribute>
        <xsl:attribute name="margin-right">10mm</xsl:attribute>
        <xsl:attribute name="margin-top">0mm</xsl:attribute>
        <xsl:attribute name="margin-bottom">0mm</xsl:attribute>
        <xsl:attribute name="page-height">297mm</xsl:attribute>
        <xsl:attribute name="page-width">420mm</xsl:attribute>
    </xsl:attribute-set>
    
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
    <xsl:template name="MainPage" match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
            xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" language="de" linefeed-treatment="preserve" xsl:use-attribute-sets="default">
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
            <fo:declarations xsl:use-attribute-sets="default" />
            <fo:page-sequence master-reference="page-sequence-master">
                <fo:static-content flow-name="folding-mark-region">
                    <xsl:call-template name="FoldingMarksTop"/>
                    <xsl:call-template name="WaterMark"/>
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

    <xsl:template name="WaterMark">
        <fo:block-container width="47cm" absolute-position="absolute" top="-3.5cm" fox:transform="rotate(30)">
            <fo:block text-align="center" font-size="200pt" font-weight="bold" color="#f5f5f5">
                <xsl:value-of select="$water-mark-content"/>
            </fo:block>
        </fo:block-container>
    </xsl:template>
</xsl:stylesheet>
