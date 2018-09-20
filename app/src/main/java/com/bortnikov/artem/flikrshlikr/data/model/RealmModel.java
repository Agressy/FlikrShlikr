package com.bortnikov.artem.flikrshlikr.data.model;

import io.realm.RealmObject;


public class RealmModel extends RealmObject {
    private String title;
    private String imageUrl;

    public RealmModel() {
    }

    public RealmModel(String title, String imageUrl) {
        super();
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
