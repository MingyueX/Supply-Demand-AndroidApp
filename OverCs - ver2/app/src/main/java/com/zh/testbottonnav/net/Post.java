package com.zh.testbottonnav.net;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Post implements Serializable {
    Integer id;
    public enum postType {
        REQUEST,
        HELP
    };
    postType type;
    String title;
    String description;
    String image1;
    String image2;
    String image3;
    String image4;
    String image5;
    String image6;
    String image7;
    String image8;
    String image9;
    String country;
    String province;
    String city;
    Integer lengthMin;
    Integer lengthMax;
    Integer widthMin;
    Integer widthMax;
    Integer heightMin;
    Integer heightMax;
    Double weightMin;
    Double weightMax;
    BigDecimal price;
    Integer stars;
    Integer uid;
    Date createTime;
    Integer category_id;
    Integer quantity;
    public enum wayOfDelivery{
        EXPRESS,
        FACETOFACE,
        BOTH
    };
    wayOfDelivery delivery;
    public enum postStatus{
        ONGOING,
        FINISHED
    };
    postStatus status;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    public String getImage8() {
        return image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public String getImage9() {
        return image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
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

    public Integer getLengthMin() {
        return lengthMin;
    }

    public void setLengthMin(Integer lengthMin) {
        this.lengthMin = lengthMin;
    }

    public Integer getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(Integer lengthMax) {
        this.lengthMax = lengthMax;
    }

    public Integer getWidthMin() {
        return widthMin;
    }

    public void setWidthMin(Integer widthMin) {
        this.widthMin = widthMin;
    }

    public Integer getWidthMax() {
        return widthMax;
    }

    public void setWidthMax(Integer widthMax) {
        this.widthMax = widthMax;
    }

    public Integer getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Integer heightMin) {
        this.heightMin = heightMin;
    }

    public Integer getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Integer heightMax) {
        this.heightMax = heightMax;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = new BigDecimal(price);
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getType() {return type.name(); }

    public void setType(String type) {
        this.type = postType.valueOf(type);
    }

    public String getDelivery() {
        return delivery.name();
    }

    public void setDelivery(String delivery) {
        this.delivery = wayOfDelivery.valueOf(delivery);
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(String status) {
        this.status = postStatus.valueOf(status);
    }

    public Double getWeightMin() {
        return weightMin;
    }

    public void setWeightMin(Double weightMin) {
        this.weightMin = weightMin;
    }

    public Double getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(Double weightMax) {
        this.weightMax = weightMax;
    }
}
