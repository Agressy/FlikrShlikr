package com.bortnikov.artem.flikrshlikr.view.feed;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.data.model.view.DataViewModel;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedPresenter;
import com.bortnikov.artem.flikrshlikr.presenter.feed.FeedView;
import com.bortnikov.artem.flikrshlikr.view.Adapter;
import com.bortnikov.artem.flikrshlikr.view.ItemViewAlertDialogFragment;

import java.util.List;
import java.util.Objects;

public class FeedFragment extends MvpAppCompatFragment implements FeedView,
        Adapter.OnFeedClickListener {

    @InjectPresenter
    FeedPresenter feedPresenter;

    private Adapter adapter = new Adapter(this);

    private RecyclerView photoRecyclerView;
    private ProgressBar progressBar;

    private int firstCoord;
    private int secondCoord;

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
        photoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

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
    public void setItems(List<DataViewModel> items) {
        adapter.setItems(items);
    }

    @Override
    public void onFeedClick(View clickSource, String title, String imageUrl) {

        int[] clickCoords = new int[2];
        clickSource.getLocationOnScreen(clickCoords);
        clickCoords[0] += clickSource.getWidth() / 2;
        clickCoords[1] += clickSource.getHeight() / 2;
        performRevealAnimation(Objects.requireNonNull(this.getView()),
                clickCoords[0],
                clickCoords[1]);

        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        ItemViewAlertDialogFragment alertDialog = ItemViewAlertDialogFragment
                .newInstance(title, imageUrl);
        alertDialog.show(fm, "alert_dialog");
    }

    private void performRevealAnimation(View view, int screenCenterX,
                                        int screenCenterY) {
        int[] animatingViewCoords = new int[2];
        view.getLocationOnScreen(animatingViewCoords);
        int centerX = screenCenterX - animatingViewCoords[0];
        int centerY = screenCenterY - animatingViewCoords[1];
        Point size = new Point();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getSize(size);
        int maxRadius = size.y;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(view, centerX, centerY,0, maxRadius)
                    .start();
        }

    }
}

