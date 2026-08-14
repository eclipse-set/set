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

	<xsl:include href="titlebox.xsl" />

	<!-- CUSTOMIZE: Adjust the following defaults only if needed -->
	<xsl:variable name="small-border-style">0.25mm solid black</xsl:variable>
	<xsl:variable name="wide-border-style">0.5mm solid black</xsl:variable>
	<xsl:variable name="SB">0.125</xsl:variable>
 	<xsl:variable name="WB">0.25</xsl:variable>
	<xsl:attribute-set name="default">
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
	<xsl:attribute-set name="title-box-table-style">
		<xsl:attribute name="width">18.08cm</xsl:attribute>
		<xsl:attribute name="margin">0mm</xsl:attribute>
		<xsl:attribute name="padding">0mm</xsl:attribute>
		<xsl:attribute name="start-indent">0mm</xsl:attribute>
		<xsl:attribute name="end-indent">0mm</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="linefeed-treatment">preserve</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="table-master-style">
		<!-- Page layout -->
		<xsl:attribute name="margin-left">5mm</xsl:attribute>
		<xsl:attribute name="margin-right">5mm</xsl:attribute>
		<xsl:attribute name="margin-top">5mm</xsl:attribute>
		<xsl:attribute name="margin-bottom">5mm</xsl:attribute>
		<xsl:attribute name="page-height">85mm</xsl:attribute>
		<xsl:attribute name="page-width">191mm</xsl:attribute>
	</xsl:attribute-set>
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
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" language="de" linefeed-treatment="preserve" xsl:use-attribute-sets="default">
			<fo:layout-master-set>
				<fo:simple-page-master xsl:use-attribute-sets="table-master-style"
					master-name="table-master">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="table-master">
				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates select="//TitleBox"/>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
		<fo:declarations xsl:use-attribute-sets="default" />
	</xsl:template>
	
</xsl:stylesheet>
