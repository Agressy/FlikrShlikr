package com.bortnikov.artem.flikrshlikr.data.model.realm;

import javax.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmModel extends RealmObject {

    @PrimaryKey
    public int id;

    @Nullable
    public String imageUrl;
    @Nullable
    public String title;
}
