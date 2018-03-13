package com.example.mike.animationlibrarytest.ui.animation;

import android.view.animation.Animation;

/**
 * Created by Mike on 3/12/2018.
 */

public class MyAnimation
{
    private Animation animation;

    private Animation.AnimationListener listener;

    public MyAnimation(Animation animation)
    {
        this.animation=animation;
    }

    public Animation getAnimation()
    {
        return animation;
    }

    public void setAnimationListener(Animation.AnimationListener listener) {
        this.listener = listener;
        animation.setAnimationListener(listener);
    }

    public Animation.AnimationListener getListener()
    {
        return listener;
    }

    public void setDuration(long duration)
    {
        animation.setDuration(duration);
    }
}
