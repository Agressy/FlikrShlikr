package com.bortnikov.artem.flikrshlikr.view.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.model.RealmModel;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedView;
import com.bortnikov.artem.flikrshlikr.view.Adapter;

import java.util.List;

public class FeedFragment extends MvpAppCompatFragment implements FeedView, Adapter.OnFeedClickListener {

    @InjectPresenter
    FeedPresenter feedPresenter;

    private Adapter adapter = new Adapter(this);

    private RecyclerView photoRecyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.feed_progress_bar);
        photoRecyclerView = view.findViewById(R.id.feed_recycler_view);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        photoRecyclerView.setAdapter(adapter);
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        Log.d("lololo", e.toString());
    }

    @Override
    public void setItems(List<RealmModel> items) {
        adapter.setItems(items);
    }

    @Override
    public void onFeedClick(String title, String imageUrl) {
        Toast.makeText(getActivity(), "title = " + title + "   url = " + imageUrl, Toast.LENGTH_SHORT).show();
    }

}

