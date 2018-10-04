package com.bortnikov.artem.flikrshlikr.data.model.view;

import com.bortnikov.artem.flikrshlikr.data.model.realm.RealmModel;

public class DataViewModel {
    private int id;
    private String title;
    private String imageUrl;

    public DataViewModel(int id, String title, String imageUrl) {
        this.id = id;
        this.title= title;
        this.imageUrl = imageUrl;
    }

    public DataViewModel(RealmModel model) {
        this(model.id, model.title, model.imageUrl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
