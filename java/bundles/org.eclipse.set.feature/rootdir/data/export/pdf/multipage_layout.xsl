<?xml version="1.0" encoding="UTF-8"?>
<xsl:template match="/">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" language="de" linefeed-treatment="preserve" xsl:use-attribute-sets="default">
        <fo:layout-master-set>
            <fo:simple-page-master xsl:use-attribute-sets="table-master-style" master-name="table-master-a">
                <fo:region-body xsl:use-attribute-sets="region-body-style"/>
                <fo:region-before region-name="folding-mark-region" xsl:use-attribute-sets="folding-mark-region-style"/>
                <fo:region-after region-name="title-box-region" xsl:use-attribute-sets="title-box-region-style" />
            </fo:simple-page-master>
            <fo:simple-page-master xsl:use-attribute-sets="table-master-style" master-name="table-master-b">
                <fo:region-body xsl:use-attribute-sets="region-body-style"/>
                <fo:region-before region-name="folding-mark-region" xsl:use-attribute-sets="folding-mark-region-style"/>
                <fo:region-after region-name="title-box-region" xsl:use-attribute-sets="title-box-region-style" />
            </fo:simple-page-master>
            <fo:simple-page-master xsl:use-attribute-sets="table-master-style" master-name="table-master-b-last">
                <fo:region-body xsl:use-attribute-sets="region-body-style"/>
                <fo:region-before region-name="folding-mark-region" xsl:use-attribute-sets="folding-mark-region-style"/>
                <fo:region-after region-name="title-box-region-last" xsl:use-attribute-sets="title-box-region-style" />
            </fo:simple-page-master>
            <fo:page-sequence-master master-name="page-sequence-master-b">
                <fo:repeatable-page-master-alternatives>
                    <fo:conditional-page-master-reference master-reference="table-master-b-last" page-position="last"/>
                    <fo:conditional-page-master-reference master-reference="table-master-b"/>
                </fo:repeatable-page-master-alternatives>
            </fo:page-sequence-master>
        </fo:layout-master-set>
        <fo:declarations xsl:use-attribute-sets="default" />
        <fo:page-sequence force-page-count="no-force" master-reference="table-master-a">
            <fo:static-content flow-name="folding-mark-region">
                <xsl:call-template name="FoldingMarksTop"/>
                <xsl:call-template name="WaterMark"/>
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
        <fo:page-sequence force-page-count="no-force" master-reference="page-sequence-master-b" initial-page-number="1">
            <fo:static-content flow-name="folding-mark-region">
                <xsl:call-template name="FoldingMarksTop"/>
                <xsl:call-template name="WaterMark"/>
            </fo:static-content>
            <fo:static-content flow-name="title-box-region">
                <xsl:call-template name="TitleboxRegion">
                    <xsl:with-param name="pagePostfix" select="'b+'"/>
                </xsl:call-template>
                <xsl:call-template name="FoldingMarksBottom"/>
            </fo:static-content>
            <fo:static-content flow-name="title-box-region-last">
                <xsl:variable name="tablePagePostfix">
                    <xsl:choose>
                        <xsl:when test="Table/Footnotes and normalize-space(Table/Footnotes) != ''">
                            <xsl:value-of select="'b+'" />
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'b-'" />
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:call-template name="TitleboxRegion">
                    <xsl:with-param name="pagePostfix" select="$tablePagePostfix" />
                </xsl:call-template>
                <xsl:call-template name="FoldingMarksBottom"/>
            </fo:static-content>
            <fo:flow flow-name="xsl-region-body">
                <fo:block id="start-indent">
                    <xsl:apply-templates />
                </fo:block>
            </fo:flow>
        </fo:page-sequence>
        <xsl:if test="Table/Footnotes and normalize-space(Table/Footnotes) != ''">
            <fo:page-sequence force-page-count="no-force" master-reference="page-sequence-master-b">
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
                    <!-- Fill footnotes -->
                    <xsl:apply-templates select="Table/Footnotes" />
                </fo:flow>
            </fo:page-sequence>
        </xsl:if>
    </fo:root>
</xsl:template>