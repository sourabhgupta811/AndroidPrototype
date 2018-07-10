package com.sourabh.android.assesmentproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sourabh.android.assesmentproject.DetailActivity;
import com.sourabh.android.assesmentproject.R;
import com.sourabh.android.assesmentproject.animations.CustomAnimator;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private static final int itemCount = 5;
    private final Context context;
    private CustomAnimator mAnimator;

    public MainActivityAdapter(Context context) {
        mAnimator = new CustomAnimator(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.main_activity_recyclerview_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView titleImage;
        ImageButton imageButton;
        View.OnClickListener mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                mAnimator.playClickSound();
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                        Pair.<View, String>create(titleImage, ViewCompat.getTransitionName(titleImage)),
                        Pair.<View, String>create(imageButton, ViewCompat.getTransitionName(imageButton)));
                context.startActivity(intent, optionsCompat.toBundle());
            }
        };

        ViewHolder(View itemView) {
            super(itemView);
            titleImage = itemView.findViewById(R.id.recyclerview_item_title_image);
            imageButton = itemView.findViewById(R.id.main_activity_recyclerview_image_button);
            imageButton.setOnClickListener(mListener);
            itemView.setOnClickListener(mListener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                titleImage.setClipToOutline(true);
            }
        }
    }
}
