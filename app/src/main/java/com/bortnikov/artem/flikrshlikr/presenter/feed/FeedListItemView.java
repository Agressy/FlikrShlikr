package com.bortnikov.artem.flikrshlikr.presenter.feed;

public interface FeedListItemView {
    int getPos();

    void setImage(String imageLink);

    void setText(String text);
}