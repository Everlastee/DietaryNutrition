package com.example.dietaryNutrition.Model;

import android.graphics.Bitmap;

public class Item {
    private String name;
    private String description;
    private String imageUri;

    public Item(String name, String description, String imageUri) {
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
