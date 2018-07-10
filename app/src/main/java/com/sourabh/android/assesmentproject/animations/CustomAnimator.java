package com.sourabh.android.assesmentproject.animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import com.sourabh.android.assesmentproject.R;

public class CustomAnimator implements View.OnTouchListener {
    private Animator mAnimatorStart;
    private Animator mAnimatorFinish;
    private MediaPlayer mMediaPlayer;

    public CustomAnimator(Context context) {
        mAnimatorStart = AnimatorInflater.loadAnimator(context, R.animator.scale_animator_start);
        mAnimatorFinish = AnimatorInflater.loadAnimator(context, R.animator.scale_animator_finish);
        mMediaPlayer = MediaPlayer.create(context, R.raw.button_click);
    }

    public void playClickSound() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.seekTo(0);
        mMediaPlayer.start();
    }

    public void setButtonAnimationListener(View v) {
        v.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mAnimatorStart.setTarget(v);
            mAnimatorStart.start();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            v.performClick();
            mAnimatorFinish.setTarget(v);
            playClickSound();
            mAnimatorFinish.start();
            return true;
        }
        return false;
    }
}


