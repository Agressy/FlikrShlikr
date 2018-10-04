package com.bortnikov.artem.flikrshlikr.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.model.RealmModel;

public class ViewHolder extends RecyclerView.ViewHolder {

    private View root;
    private ImageView image;
    private TextView title;

    private RealmModel model;
    Adapter.OnFeedClickListener listener;

    private ViewHolder(View view) {
        super(view);
        root = view;
        root.setOnClickListener(view1 -> {
                    if (listener != null) {
                        listener.onFeedClick(model.getTitle(), model.getImageUrl());
                    }
                }
        );
        image = root.findViewById(R.id.rv_item_image);
        title = root.findViewById(R.id.rv_item_title);
    }

    public void bind(RealmModel model, Adapter.OnFeedClickListener listener) {
        this.listener = listener;
        this.model = model;
        setImage(model.getImageUrl());
        title.setText(model.getTitle());
    }

    public void setImage(String imageLink) {
        GlideApp.with(root.getContext())
                .load(imageLink)
                .placeholder(new ColorDrawable(Color.GRAY))
                .centerCrop()
                .into(image);
    }

    static ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item, parent, false));
    }
}
