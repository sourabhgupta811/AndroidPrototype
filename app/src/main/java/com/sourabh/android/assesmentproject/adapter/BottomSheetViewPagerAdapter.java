package com.sourabh.android.assesmentproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sourabh.android.assesmentproject.R;
import com.sourabh.android.assesmentproject.animations.CustomAnimator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BottomSheetViewPagerAdapter extends PagerAdapter {
    private List<Integer> pagesList = Arrays.asList(R.layout.view_pager_item_one, R.layout.view_pager_item_two, R.layout.view_pager_item_three);
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private CustomAnimator animator;

    public BottomSheetViewPagerAdapter(Context context) {
        animator = new CustomAnimator(context);
    }

    @Override
    public int getCount() {
        return pagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View v = inflater.inflate(pagesList.get(position), container, false);
        if (position == 1) {
            buttonOne = v.findViewById(R.id.button_one);
            buttonTwo = v.findViewById(R.id.button_two);
            buttonThree = v.findViewById(R.id.button_three);
            animator.setButtonAnimationListener(buttonOne);
            animator.setButtonAnimationListener(buttonTwo);
            animator.setButtonAnimationListener(buttonThree);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectClickedButton(v);
                }
            };
            buttonTwo.setOnClickListener(listener);
            buttonOne.setOnClickListener(listener);
            buttonThree.setOnClickListener(listener);
        }
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void selectClickedButton(View v) {
        switch (v.getId()) {
            case R.id.button_one:
                buttonOne.setBackgroundResource(R.drawable.green_circle);
                buttonTwo.setBackgroundResource(R.drawable.placeholder_circle);
                buttonThree.setBackgroundResource(R.drawable.placeholder_circle);
                break;
            case R.id.button_two:
                buttonOne.setBackgroundResource(R.drawable.placeholder_circle);
                buttonTwo.setBackgroundResource(R.drawable.green_circle);
                buttonThree.setBackgroundResource(R.drawable.placeholder_circle);
                break;
            case R.id.button_three:
                buttonOne.setBackgroundResource(R.drawable.placeholder_circle);
                buttonTwo.setBackgroundResource(R.drawable.placeholder_circle);
                buttonThree.setBackgroundResource(R.drawable.green_circle);
                break;
        }
    }
}
