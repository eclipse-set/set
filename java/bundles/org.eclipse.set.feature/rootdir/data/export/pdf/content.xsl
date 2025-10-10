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
	xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" exclude-result-prefixes="fo">

	<xsl:template match="StringContent">
		<fo:block hyphenate="true" language="de">
			<fo:inline>
				<xsl-if test="../../@compareType='CHANGED_GUID_ROW'">
					<xsl:attribute name="text-decoration">line-through</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="StringContent[starts-with(.,'&#x25a1;')]">
		<fo:block hyphenate="true" language="de" font-family="Arial">
			<xsl-if test="../../@compareType='CHANGED_GUID_ROW'">
				<fo:inline text-decoration="line-through">
					<xsl:value-of select="." />
				</fo:inline>
			</xsl:if>
			<xsl:otherwise>
				<xsl:value-of select="." />
			</xsl:otherwise>
		</fo:block>
	</xsl:template>

	<xsl:template match="StringContent[starts-with(.,'Error:') or starts-with(.,'&#9203;')]" priority="1">
		<fo:block hyphenate="true" language="de">
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>

	<xsl:template match="StringContent[starts-with(.,'Error:') or starts-with(.,'&#9203;')]" priority="2">
		<fo:block hyphenate="true" language="de">
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>

	<xsl:template match="DiffContent">
		<fo:block keep-together.within-page="always">
			<xsl:apply-templates />
		</fo:block>
	</xsl:template>

	<xsl:template match="UnchangedValue">
		<fo:block hyphenate="true" language="de">
			<fo:inline>
				<xsl-if test="../../@compareType='CHANGED_GUID_ROW'">
					<xsl:attribute name="text-decoration">line-through</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="UnchangedValue[starts-with(.,'Error:') or starts-with(.,'&#9203;')]" priority="1">
		<fo:block hyphenate="true" language="de">
			<fo:inline>
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_yellow.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue">
		<fo:block hyphenate="true" language="de">
			<fo:inline background-color="yellow" text-decoration="line-through">
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue[starts-with(.,'&#x25a1;')]">
		<fo:block hyphenate="true" language="de">
			<fo:inline font-family="Arial" background-color="yellow" text-decoration="line-through">
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue[starts-with(.,'Error:') or starts-with(.,'&#9203;')]" priority="1">
		<fo:block hyphenate="true" language="de">
			<fo:inline>
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_yellow.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue[starts-with(.,'Error:') or starts-with(.,'&#9203;')]" priority="2">
		<fo:block hyphenate="true" language="de">
			<fo:inline>
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_yellow.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue">
		<fo:block hyphenate="true" language="de">
			<fo:inline color="#cd0000">
				<xsl-if test="../../@compareType='CHANGED_GUID_ROW'">
					<xsl:attribute name="text-decoration">line-through</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue[starts-with(.,'&#x25a1;')]">
		<fo:block hyphenate="true" language="de">
			<fo:inline font-family="Arial" color="#cd0000">
				<xsl-if test="../../@compareType='CHANGED_GUID_ROW'">
					<xsl:attribute name="text-decoration">line-through</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue[starts-with(.,'Error:') or starts-with(.,'&#9203;')]" priority="1">
		<fo:block hyphenate="true" language="de">
			<fo:inline color="#cd0000">
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_red.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue[starts-with(.,'Error:') or starts-with(.,'&#9203;')]" priority="2">
		<fo:block hyphenate="true" language="de">
			<fo:inline color="#cd0000">
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_red.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="MultiColorContent">
		<fo:block hyphenate="true" language="de">
			<xsl:apply-templates />
		</fo:block>
	</xsl:template>

	<xsl:template match="SimpleValue">
		<fo:inline>
			<xsl-if test="../../@compareType='CHANGED_GUID_ROW'">
					<xsl:attribute name="text-decoration">line-through</xsl:attribute>
			</xsl:if>
			<xsl:value-of select="."/>
		</fo:inline>
	</xsl:template>

	<xsl:template match="SimpleValue[starts-with(.,'Error:') or starts-with(.,'&#9203;')]">
		<fo:block hyphenate="true" language="de">
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>

	<xsl:template match="MultiColorValue">
		<xsl:variable name="format" select="." />
		<xsl:variable name="value" select="@multicolorValue" />
		<!--IMPROVE: The order of multi color content shouldn't static-->
		<fo:block hyphenate="true" language="de">

			<fo:inline background-color="yellow">
				<xsl:value-of select="@multicolorValue" />
			</fo:inline>
			<fo:inline color="#cd0000">
				<xsl:value-of select="@multicolorValue" />
			</fo:inline>
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="MultiColorValue[starts-with(.,'Error:') or starts-with(.,'&#9203;')]">
		<fo:block hyphenate="true" language="de">
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="data/pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>

	<xsl:template match="CompareProjectContent">
		<fo:block>
			<xsl:apply-templates />
		</fo:block>
	</xsl:template>
</xsl:stylesheet>
