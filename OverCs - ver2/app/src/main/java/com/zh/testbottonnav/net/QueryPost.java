package com.zh.testbottonnav.net;

import java.util.List;

public class QueryPost{
    // private String postType;
    public enum postType { REQUEST, HELP }
    postType type;
    private List<String> categoryIds;
    // private String delivery;
    private String country;
    private String province;
    private String city;
    private String quantity;
    private String lengthMin;
    private String lengthMax;
    private String widthMin;
    private String widthMax;
    private String heightMin;
    private String heightMax;
    private String weightMin;
    private String weightMax;
    private String priceMin;
    private String priceMax;
    private String appendedCid;
    private String delivery;

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLengthMin() {
        return lengthMin;
    }

    public void setLengthMin(String lengthMin) {
        this.lengthMin = lengthMin;
    }

    public String getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(String lengthMax) {
        this.lengthMax = lengthMax;
    }

    public String getWidthMin() {
        return widthMin;
    }

    public void setWidthMin(String widthMin) {
        this.widthMin = widthMin;
    }

    public String getWidthMax() {
        return widthMax;
    }

    public void setWidthMax(String widthMax) {
        this.widthMax = widthMax;
    }

    public String getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(String heightMin) {
        this.heightMin = heightMin;
    }

    public String getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(String heightMax) {
        this.heightMax = heightMax;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getAppendedCid() {
        return appendedCid;
    }

    public void setAppendedCid(String appendedCid) {
        this.appendedCid = appendedCid;
    }

    public String getWeightMin() {
        return weightMin;
    }

    public void setWeightMin(String weightMin) {
        this.weightMin = weightMin;
    }

    public String getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(String weightMax) {
        this.weightMax = weightMax;
    }

    public String getType() {return type.name(); }

    public void setType(String type) {
        this.type = postType.valueOf(type);
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
