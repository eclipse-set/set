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

	<xsl:include href="content.xsl" />

	<xsl:template match="Cell">
		<fo:table-cell xsl:use-attribute-sets="default-cell-style" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<!-- Cells belonging to the first column and not to the last row -->
	<xsl:template match="Cell[@column-number = '1' and ../@group-number != count(/Table/Rows/Row)]">
		<fo:table-cell xsl:use-attribute-sets="first-column-cell-style" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<!-- Cells belonging to the first column and to the last row -->
	<xsl:template match="Cell[@column-number = '1' and ../@group-number = count(/Table/Rows/Row)]">
		<fo:table-cell xsl:use-attribute-sets="first-column-last-row-cell-style" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

	<!-- Cells not belonging to the first column and to the last row -->
	<xsl:template match="Cell[@column-number != '1' and ../@group-number = count(/Table/Rows/Row)]">
		<fo:table-cell xsl:use-attribute-sets="last-row-cell-style" number-rows-spanned="{@number-rows-spanned}">
			<xsl:apply-templates />
		</fo:table-cell>
	</xsl:template>

</xsl:stylesheet>
