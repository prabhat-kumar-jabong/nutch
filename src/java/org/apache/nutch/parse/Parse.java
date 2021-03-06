/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.apache.nutch.parse;

public class Parse {

  private String text;
  private String title;
  private Outlink[] outlinks;
  private org.apache.nutch.storage.ParseStatus parseStatus;
  private boolean pdp;
  private String company;
  private String productTitle;
  private String sellingPrice;
  private String mrp;
  private String breadcrumb;
  private String images;
  private String brand;
  private String size;
  private String availableSizeCount;
  private String sku;
  private Integer mapped;
  private String breadcrumb1;
  private String breadcrumb2;
  private String breadcrumb3;
  private String breadcrumb4;

  public Parse() {
  }
  
  public Parse(String text, String title, Outlink[] outlinks,
      org.apache.nutch.storage.ParseStatus parseStatus) {
    this.text = text;
    this.title = title;
    this.outlinks = outlinks;
    this.parseStatus = parseStatus;
  }

  public Parse(String text, String title, Outlink[] outlinks,
      org.apache.nutch.storage.ParseStatus parseStatus, boolean isPDP, String company,
      String productTitle, String sellingPrice, String mrp, String breadcrumb,
      String images, String brand, String size, String availableSizeCount,
      String sku, Integer mapped, String breadcrumb1, String breadcrumb2,
      String breadcrumb3, String breadcrumb4) {
    this.text = text;
    this.title = title;
    this.outlinks = outlinks;
    this.parseStatus = parseStatus;
    this.pdp = isPDP;
    this.company = company;
    this.productTitle = productTitle;
    this.sellingPrice = sellingPrice;
    this.mrp = mrp;
    this.breadcrumb = breadcrumb;
    this.images = images;
    this.brand = brand;
    this.size = size;
    this.availableSizeCount = availableSizeCount;
    this.sku = sku;
    this.mapped = mapped;
    this.breadcrumb1 = breadcrumb1;
    this.breadcrumb2 = breadcrumb2;
    this.breadcrumb3 = breadcrumb3;
    this.breadcrumb4 = breadcrumb4;
  }

  public String getText() {
    return text;
  }

  public String getTitle() {
    return title;
  }

  public Outlink[] getOutlinks() {
    return outlinks;
  }

  public org.apache.nutch.storage.ParseStatus getParseStatus() {
    return parseStatus;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setOutlinks(Outlink[] outlinks) {
    this.outlinks = outlinks;
  }

  public void setParseStatus(org.apache.nutch.storage.ParseStatus parseStatus) {
    this.parseStatus = parseStatus;
  }
  
  public boolean isPdp() {
    return pdp;
  }

  public void setPdp(boolean pdp) {
    this.pdp = pdp;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getProductTitle() {
    return productTitle!=null?productTitle:"";
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getSellingPrice() {
    return sellingPrice!=null?sellingPrice:"";
  }

  public void setSellingPrice(String sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public String getMrp() {
    return mrp;
  }

  public void setMrp(String mrp) {
    this.mrp = mrp;
  }

  public String getBreadcrumb() {
    return breadcrumb!=null?breadcrumb:"";
  }

  public void setBreadcrumb(String breadcrumb) {
    this.breadcrumb = breadcrumb;
  }

  public String getImages() {
    return images!=null?images:"";
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getBrand() {
    return brand!=null?brand:"";
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getSize() {
    return size!=null?size:"";
  }

  public void setSize(String size) {
    this.size = size;
  }
  
  public String getAvailableSizeCount() {
    return availableSizeCount;
  }

  public void setAvailableSizeCount(String availableSizeCount) {
    this.availableSizeCount = availableSizeCount;
  }

  public String getSku() {
    return sku!=null?sku:"";
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public Integer getMapped() {
    return mapped;
  }

  public void setMapped(Integer mapped) {
    this.mapped = mapped;
  }

  public String getBreadcrumb1() {
    return breadcrumb1;
  }

  public void setBreadcrumb1(String breadcrumb1) {
    this.breadcrumb1 = breadcrumb1;
  }

  public String getBreadcrumb2() {
    return breadcrumb2;
  }

  public void setBreadcrumb2(String breadcrumb2) {
    this.breadcrumb2 = breadcrumb2;
  }

  public String getBreadcrumb3() {
    return breadcrumb3;
  }

  public void setBreadcrumb3(String breadcrumb3) {
    this.breadcrumb3 = breadcrumb3;
  }

  public String getBreadcrumb4() {
    return breadcrumb4;
  }

  public void setBreadcrumb4(String breadcrumb4) {
    this.breadcrumb4 = breadcrumb4;
  }
}
