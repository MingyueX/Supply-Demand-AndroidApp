package com.zh.testbottonnav;

import android.widget.ImageView;

import com.zh.testbottonnav.net.Post;

import java.io.Serializable;

public class HomePost implements Serializable {
    private Integer id;
    public enum postType {
        REQUEST,
        HELP
    };
    private String name;
    private String description;
    private String imageurl;
    private String imageurl2;
    private String imageurl3;
    private String imageurl4;
    private String imageurl5;
    private String imageurl6;
    private String imageurl7;
    private postType type;

    public HomePost(Integer id, String name, String description, String imageurl, String type){
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
        this.description = description;
        if (type == null) {
            this.type = null;
        } else {
            this.setType(type);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getDescription() { return description; }

    public String getType() { return type == null ? null : type.name(); }

    public void setType(String type) {
        this.type = postType.valueOf(type);
    }


}
