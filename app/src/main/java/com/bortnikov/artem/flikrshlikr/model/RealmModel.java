package com.bortnikov.artem.flikrshlikr.model;

import io.realm.RealmObject;


public class RealmModel extends RealmObject {
    public int id;
    private String title;
    private String imageUrl;

    public RealmModel() {
    }

    public void setId(int id) {this.id = id;}

    public int getId() {return id;}

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
