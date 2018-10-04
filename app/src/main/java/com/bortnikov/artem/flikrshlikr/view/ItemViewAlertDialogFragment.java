package com.bortnikov.artem.flikrshlikr.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bortnikov.artem.flikrshlikr.R;

public class ItemViewAlertDialogFragment extends DialogFragment {

    private TextView titleTextView;
    private ImageView photoImageView;

    public ItemViewAlertDialogFragment() {
    }

    public static ItemViewAlertDialogFragment newInstance(String title, String imageUrl) {
        ItemViewAlertDialogFragment frag = new ItemViewAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("imageUrl", imageUrl);
        frag.setArguments(args);
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_view_dialog, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView = view.findViewById(R.id.item_view_dialog_text_view);
        photoImageView = view.findViewById(R.id.item_view_dialog_image_view);
        String title = getArguments().getString("title", "Title");
        String imageUrl = getArguments().getString("imageUrl", getString(R.string.std_picture_url));
        titleTextView.setText(title);
        GlideApp.with(view.getContext())
                .load(imageUrl)
                .placeholder(new ColorDrawable(Color.GRAY))
                .centerCrop()
                .into(photoImageView);
    }
}

