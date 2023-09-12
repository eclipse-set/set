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
		<fo:block>
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="StringContent[starts-with(.,'&#x25a1;')]">
		<fo:block font-family="Arial">
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="StringContent[starts-with(.,'Error:')]" priority="1">
		<fo:block>
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>

	<xsl:template match="StringContent[starts-with(.,'E​r​r​o​r​:')]" priority="2">
		<fo:block>
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>

	<xsl:template match="DiffContent">
		<fo:block>
			<xsl:apply-templates />
		</fo:block>
	</xsl:template>

	<xsl:template match="UnchangedValue">
		<fo:block>
			<fo:inline>
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="UnchangedValue[starts-with(.,'Error:')]" priority="1">
		<fo:block>
			<fo:inline>
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_yellow.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue">
		<fo:block>
			<fo:inline background-color="yellow" text-decoration="line-through">
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue[starts-with(.,'&#x25a1;')]">
		<fo:block>
			<fo:inline font-family="Arial" background-color="yellow" text-decoration="line-through">
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue[starts-with(.,'Error:')]" priority="1">
		<fo:block>
			<fo:inline>
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_yellow.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="OldValue[starts-with(.,'E​r​r​o​r​:')]" priority="2">
		<fo:block>
			<fo:inline>
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_yellow.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue">
		<fo:block>
			<fo:inline color="#cd0000">
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue[starts-with(.,'&#x25a1;')]">
		<fo:block>
			<fo:inline font-family="Arial" color="#cd0000">
				<xsl:value-of select="." />
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue[starts-with(.,'Error:')]" priority="1">
		<fo:block>
			<fo:inline color="#cd0000">
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_red.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="NewValue[starts-with(.,'E​r​r​o​r​:')]" priority="2">
		<fo:block>
			<fo:inline color="#cd0000">
				<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_red.svg" fox:alt-text="Error"/>
			</fo:inline>
		</fo:block>
	</xsl:template>

	<xsl:template match="MultiColorContent">
		<fo:block>
			<xsl:apply-templates />
		</fo:block>
	</xsl:template>

	<xsl:template match="SimpleValue">
		<fo:inline>
			<xsl:value-of select="."/>
		</fo:inline>
	</xsl:template>

	<xsl:template match="SimpleValue[starts-with(.,'Error:')]">
		<fo:block>
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>

	<xsl:template match="MultiColorValue">
		<xsl:variable name="format" select="." />
		<xsl:variable name="value" select="@multicolorValue" />
		<!--IMPROVE: The order of multi color content shouldn't static-->
		<fo:block>

			<fo:inline background-color="yellow">
				<xsl:value-of select="@multicolorValue" />
			</fo:inline>
			<fo:inline color="#cd0000">
				<xsl:value-of select="@multicolorValue" />
			</fo:inline>
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="MultiColorValue[starts-with(.,'Error:')]">
		<fo:block>
			<fo:external-graphic xsl:use-attribute-sets="external-graphic-style" src="pictures/warning_black.svg" fox:alt-text="Error"/>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>
