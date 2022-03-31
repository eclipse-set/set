<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2018 DB Netz AG and others.

All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v2.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v20.html
-->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:fox="http://xmlgraphics.apache.org/fop/extensions"
	exclude-result-prefixes="fo">
	
	<xsl:variable name="folding-mark-height">10mm</xsl:variable>
	<xsl:variable name="folding-mark-border">0.25mm solid black</xsl:variable>
	<xsl:attribute-set name="folding-mark-row-style">
		<xsl:attribute name="height"><xsl:value-of select="$folding-mark-height" /></xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="folding-mark-border-style">
		<xsl:attribute name="border-right"><xsl:value-of select="$folding-mark-border" /></xsl:attribute>
	</xsl:attribute-set>

	<xsl:template name="FoldingMarksTop">
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column column-width="105mm" />
			<fo:table-column column-width="105mm" />
			<fo:table-column column-width="180mm" />
			<fo:table-body>
				<fo:table-row xsl:use-attribute-sets="folding-mark-row-style">
					<fo:table-cell xsl:use-attribute-sets="folding-mark-border-style">
						<fo:block color="white">.</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="folding-mark-border-style">
						<fo:block color="white">.</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block color="white">.</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template name="FoldingMarksBottom">
		<fo:table table-layout="fixed" width="100%">
		<fo:table-column column-width="105mm" />
		<fo:table-column column-width="105mm" />
		<fo:table-column column-width="180mm" />
		<fo:table-body>
			<fo:table-row xsl:use-attribute-sets="folding-mark-row-style">
				<fo:table-cell xsl:use-attribute-sets="folding-mark-border-style">
					<fo:block color="white">.</fo:block>
				</fo:table-cell>
				<fo:table-cell xsl:use-attribute-sets="folding-mark-border-style">
					<fo:block color="white">.</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block color="white">.</fo:block>
				</fo:table-cell>
			</fo:table-row>
		</fo:table-body>
	</fo:table>
	</xsl:template>

</xsl:stylesheet>
