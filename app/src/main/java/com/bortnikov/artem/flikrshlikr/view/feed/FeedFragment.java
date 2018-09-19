package com.bortnikov.artem.flikrshlikr.view.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.data.model.Photo;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedView;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends MvpAppCompatFragment implements FeedView {

    @InjectPresenter
    FeedPresenter feedPresenter;

    FeedAdapter adapter;

    private RecyclerView photoRecyclerView;
    private List<Photo> itemsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        photoRecyclerView = v.findViewById(R.id.photo_recycler_view);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new FeedAdapter(feedPresenter.getListFiles());
        photoRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void startLoad() {

    }

    @Override
    public void finishLoad() {

    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }
}

