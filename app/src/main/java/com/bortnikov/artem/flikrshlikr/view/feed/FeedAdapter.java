package com.bortnikov.artem.flikrshlikr.view.feed;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedListItemView;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedListPresenter;
import com.bortnikov.artem.flikrshlikr.view.GlideApp;
import com.bortnikov.artem.flikrshlikr.view.MainApplication;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private FeedListPresenter presenter;

    FeedAdapter(FeedListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pos = position;
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getViewCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements FeedListItemView {
        int pos;
        ImageView imageView;
        TextView titleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rv_item_image);
            titleTextView = itemView.findViewById(R.id.rv_item_title);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setImage(String imageLink) {
            //Picasso.get().load(imageLink).centerCrop().resize(120, 120).into(imageView);
            GlideApp.with(imageView.getContext())
                    .load(imageLink)
                    .placeholder(new ColorDrawable(Color.GRAY))
                    .into(imageView);
        }

        @Override
        public void setText(String text) {
            titleTextView.setText(text);
        }

    }
}
