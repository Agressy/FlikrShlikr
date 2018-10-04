package com.bortnikov.artem.flikrshlikr.view.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.model.RealmModel;
import com.bortnikov.artem.flikrshlikr.presenter.search.SearchPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.search.SearchingView;
import com.bortnikov.artem.flikrshlikr.view.Adapter;

import java.util.List;


public class SearchFragment extends MvpAppCompatFragment implements SearchingView, Adapter.OnFeedClickListener {

    @InjectPresenter
    SearchPresenter searchPresenter;

    private Adapter adapter = new Adapter(this);

    private RecyclerView photoRecyclerView;
    private SearchView searchView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.search_progress_bar);
        searchView = view.findViewById(R.id.search_search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPresenter.searchNewInfo(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        photoRecyclerView = view.findViewById(R.id.search_recycler_view);

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
    }

    @Override
    public void setItems(List<RealmModel> items) {
        adapter.setItems(items);
    }

    @Override
    public void updateList(){
        adapter.notifyDataSetChanged();
        photoRecyclerView.invalidate();
    }

    @Override
    public void onFeedClick(String title, String imageUrl) {
        Toast.makeText(getActivity(), "title = " + title + "   url = " + imageUrl, Toast.LENGTH_SHORT).show();
    }
}

