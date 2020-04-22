package com.example.photopostiongyang.Model;

import android.net.Uri;

public class SliderItem {

    private String description;
    private String imageUrl;
    public SliderItem(String imageUrl){this.imageUrl=imageUrl;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String  imageUrl) {
        this.imageUrl = imageUrl;
    }
}
