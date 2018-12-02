package com.bortnikov.artem.flikrshlikr.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.bortnikov.artem.flikrshlikr.data.model.realm.RealmModel;
import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<DataViewModel> data = new ArrayList<>();
    private OnFeedClickListener listener;

    public Adapter(OnFeedClickListener listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItems(List<DataViewModel> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addItems(List<DataViewModel> list) {
        int oldIndex = data.size();
        data.addAll(list);
        notifyItemRangeInserted(oldIndex, list.size());
    }

    public interface OnFeedClickListener {
        void onFeedClick(View view, String title, String imageUrl);
    }

}

