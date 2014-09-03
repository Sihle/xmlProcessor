<?xml version="1.0"?>

  <!--
   * Licensed to the Apache Software Foundation (ASF) under one
   * or more contributor license agreements. See the NOTICE file
   * distributed with this work for additional information
   * regarding copyright ownership. The ASF licenses this file
   * to you under the Apache License, Version 2.0 (the  "License");
   * you may not use this file except in compliance with the License.
   * You may obtain a copy of the License at
   *
   *     http://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   * See the License for the specific language governing permissions and
   * limitations under the License.
  -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
<xsl:param name="pBirthDate" select="'default value'"/>
<xsl:param name="pSIMCardNumber" select="'default value'"/>
<!-- <xsl:param name="pidNumber" select="'default value'"/> -->

<xsl:strip-space elements="*"/>

<xsl:template match="node()|@*">
    <xsl:copy>
        <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
</xsl:template>

<!-- <xsl:template match="Mobile-MSISDN/@BirthDate[.='1969-06-24T00:00:00']"> -->
<xsl:template match="@BirthDate">
  <xsl:attribute name="BirthDate">
    <xsl:value-of select="concat('19',substring($pBirthDate,1,2),'-',substring($pBirthDate,3,2),'-',substring($pBirthDate,5,2),'T00:00:00')"/>
  </xsl:attribute>
 </xsl:template>

<xsl:template match="@SIMCardNumber">
  <xsl:attribute name="SIMCardNumber">
    <xsl:value-of select="$pSIMCardNumber"/>
  </xsl:attribute>
 </xsl:template>
 
 <xsl:template match="@idNumber">
  <xsl:attribute name="idNumber">
    <xsl:value-of select="$pBirthDate"/>
  </xsl:attribute>
 </xsl:template>

</xsl:stylesheet>
