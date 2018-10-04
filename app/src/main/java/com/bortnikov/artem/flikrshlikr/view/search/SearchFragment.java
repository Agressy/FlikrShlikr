package com.bortnikov.artem.flikrshlikr.view.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;
import com.bortnikov.artem.flikrshlikr.presenter.search.SearchPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.search.SearchingView;
import com.bortnikov.artem.flikrshlikr.view.Adapter;
import com.bortnikov.artem.flikrshlikr.view.ItemViewAlertDialogFragment;

import java.util.List;

import static android.support.v4.internal.view.SupportMenuItem.SHOW_AS_ACTION_IF_ROOM;

public class SearchFragment extends MvpAppCompatFragment implements SearchingView,
        Adapter.OnFeedClickListener,
        SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener {

    @InjectPresenter
    SearchPresenter searchPresenter;

    private Adapter adapter = new Adapter(this);

    private RecyclerView photoRecyclerView;

    private ProgressBar progressBar;

    private MenuItem searchItem;

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

        photoRecyclerView = view.findViewById(R.id.search_recycler_view);

        photoRecyclerView.setAdapter(adapter);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onDestroy() {
        super.onDestroy();
        searchItem.setVisible(false);
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
    public void setItems(List<DataViewModel> items) {
        adapter.setItems(items);
    }

    @Override
    public void updateList(){
        adapter.notifyDataSetChanged();
        photoRecyclerView.invalidate();
    }

    @Override
    public void onFeedClick(String title, String imageUrl) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ItemViewAlertDialogFragment alertDialog = ItemViewAlertDialogFragment.newInstance(title, imageUrl);
        alertDialog.show(fm, "alert_dialog");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchPresenter.searchNewInfo(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return true;
    }
}

