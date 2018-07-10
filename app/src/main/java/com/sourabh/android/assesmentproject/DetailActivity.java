package com.sourabh.android.assesmentproject;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.TransitionManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourabh.android.assesmentproject.adapter.BottomSheetViewPagerAdapter;
import com.sourabh.android.assesmentproject.adapter.DetailActivityAdapter;
import com.sourabh.android.assesmentproject.animations.CustomAnimator;

import java.util.Timer;
import java.util.TimerTask;

public class DetailActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    DetailActivityAdapter mAdapter;
    FrameLayout progressBarLayout;
    ImageView topProgressImage;
    Runnable progressRunnable;
    ViewPager viewPager;
    TextView progressBarTextView;
    ConstraintLayout rootLayout;
    ImageButton backToPreviousScreen;
    int imageWidth;
    ImageView circularProgressOnLeft;
    BottomSheetViewPagerAdapter pagerAdapter;
    ImageView progressCancelIndicator;
    Handler handler = new Handler();
    BottomSheetBehavior mBottomSheetBehavior;
    CustomAnimator mAnimator;
    boolean downloaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_with_bottom_sheet);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        viewPager = findViewById(R.id.view_pager);
        rootLayout = findViewById(R.id.detail_root);
        backToPreviousScreen = findViewById(R.id.back_to_previous_screen);
        mAnimator = new CustomAnimator(this);
        mAnimator.setButtonAnimationListener(backToPreviousScreen);
        backToPreviousScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPreviousScreen(v);
            }
        });
        circularProgressOnLeft = findViewById(R.id.circular_progress_on_left);
        circularProgressOnLeft.setVisibility(View.GONE);
        progressBarTextView = findViewById(R.id.progress_bar_textview);
        pagerAdapter = new BottomSheetViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        mRecyclerView = findViewById(R.id.detail_activity_recycler_view);
        progressBarLayout = findViewById(R.id.progressBarLayout);
        topProgressImage = findViewById(R.id.top_progress);
        progressCancelIndicator = findViewById(R.id.progress_cancel_indicator);
        progressCancelIndicator.setVisibility(View.GONE);
        imageWidth = topProgressImage.getWidth();
        topProgressImage.setVisibility(View.GONE);
        mAdapter = new DetailActivityAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressCancelIndicator.setVisibility(View.GONE);
                topProgressImage.setImageResource(R.drawable.blue_play_button);
                progressBarLayout.setVisibility(View.GONE);
                circularProgressOnLeft.setVisibility(View.GONE);
                progressBarLayout.setTranslationY(100);
                progressBarTextView.setText(R.string.play);
                progressBarLayout.setVisibility(View.VISIBLE);
                progressBarLayout.animate().setDuration(300).translationYBy(-100).start();
                final Animation blinkAnimation = new AlphaAnimation(1, 0.7f); // Change alpha from fully visible to invisible
                blinkAnimation.setDuration(500);
                blinkAnimation.setStartOffset(300);// duration - half a second
                blinkAnimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
                blinkAnimation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
                blinkAnimation.setRepeatMode(Animation.REVERSE);
                progressBarLayout.startAnimation(blinkAnimation);
                progressBarLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            final LinearLayout group = findViewById(R.id.group);
                            final Button confirmButton = findViewById(R.id.confirm_button);
                            confirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (viewPager.getCurrentItem() != 6)
                                        mAnimator.playClickSound();
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                                }
                            });
                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    if (position == 1) {
                                        group.setVisibility(View.VISIBLE);
                                        group.setTranslationY(100);
                                        group.animate().setDuration(100).translationYBy(-100).start();
                                    } else if (position == 2) {
                                        confirmButton.setVisibility(View.GONE);
                                        confirmButton.setTranslationY(200);
                                        confirmButton.setBackgroundResource(R.drawable.black_confirm_button);
                                        confirmButton.setText(R.string.confirm);
                                        confirmButton.setVisibility(View.VISIBLE);
                                        confirmButton.animate().setDuration(100).translationYBy(-200).start();
                                    } else {
                                        group.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        } else {
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                        downloaded = true;
                        progressBarLayout.clearAnimation();
                    }
                });
            }
        };
        progressBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimator.playClickSound();
                topProgressImage.setPivotX(0);
                circularProgressOnLeft.setVisibility(View.VISIBLE);
                rootLayout.requestLayout();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(rootLayout);
                }
                progressCancelIndicator.setScaleX(0);
                progressCancelIndicator.setScaleY(0);
                progressCancelIndicator.setVisibility(View.VISIBLE);
                progressCancelIndicator.animate().scaleXBy(1).scaleYBy(1).setDuration(200).start();
                Animator animator = AnimatorInflater.loadAnimator(DetailActivity.this, R.animator.scale_animator);
                animator.setTarget(topProgressImage);
                topProgressImage.setVisibility(View.VISIBLE);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        progressBarTextView.setText("");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBarTextView.setText(R.string.progress_status);
                            }
                        }, 500);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBarTextView.setText("");
                            }
                        }, 3200);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressCancelIndicator.animate().scaleXBy(0).scaleYBy(0).setDuration(100).start();
                        progressCancelIndicator.setImageResource(R.drawable.ic_action_done);
                        progressCancelIndicator.setBackgroundResource(R.drawable.yellow_circle);
                        progressCancelIndicator.animate().scaleYBy(1).scaleXBy(1).setDuration(100).withEndAction(progressRunnable).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                animator.start();
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    public void backToPreviousScreen(View v) {
        super.onBackPressed();
    }
}
