package com.abdul_rashiq.cakelistapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Cake {

    @SerializedName("title")
    private String title;
    @SerializedName("desc")
    private String description;
    @SerializedName("image")
    private String imageURL;

    public Cake() {
    }

    public Cake(String title, String description, String imageURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cake cake = (Cake) o;
        return title.equals(cake.title) && description.equals(cake.description) && imageURL.equals(cake.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, imageURL);
    }
}
