package com.example.mike.animationlibrarytest.ui.animation;

import android.view.View;
import android.view.animation.Animation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mike on 3/12/2018.
 */

public class ParallelAnimationSet
{

    private Animation.AnimationListener listenerForEveryAnimation;
    private AtomicInteger animationsThatAreCurrentlyPlaying = new AtomicInteger(0);
    private AtomicInteger animationsThatHaveBeenStarted = new AtomicInteger(0);

    private List<AnimationPackage> animationPackageList = new LinkedList<>();


    public void add(View view, MyAnimation myAnimation)
    {
        myAnimation.setAnimationListener(new ParallelAnimationListener(myAnimation));
        animationPackageList.add(new AnimationPackage(view, myAnimation));
    }


    public void play()
    {
        animationsThatAreCurrentlyPlaying.set(animationPackageList.size());

        for(AnimationPackage animationPackage : animationPackageList)
        {
            View viewToBeAnimated = animationPackage.view;
            MyAnimation animationToBePlayed = animationPackage.animation;

            viewToBeAnimated.startAnimation(animationToBePlayed.getAnimation());
        }
    }



    public void setAnimationListener(Animation.AnimationListener listenerForEveryAnimation)
    {
        this.listenerForEveryAnimation=listenerForEveryAnimation;
    }

    public static ParallelAnimationSet create(View view, MyAnimation ... myAnimations)
    {

        ParallelAnimationSet parallelAnimationSet = new ParallelAnimationSet();

        for(MyAnimation myAnimation : myAnimations)
        {
            parallelAnimationSet.add(view, myAnimation);
        }
        return parallelAnimationSet;
    }



    public Animation.AnimationListener getAnimationListener()
    {
        return listenerForEveryAnimation;
    }

    private static class AnimationPackage
    {
        MyAnimation animation;
        View view;

        AnimationPackage(View view, MyAnimation animation)
        {
            this.animation = animation;
            this.view = view;
        }
        public String toString()
        {
            return animation.getAnimation().getClass().getName()+" on "+view.toString();
        }
    }

    private class ParallelAnimationListener implements Animation.AnimationListener
    {

        private Animation.AnimationListener singleAnimationListener;

        ParallelAnimationListener(MyAnimation myAnimation)
        {
            this.singleAnimationListener=myAnimation.getListener();
        }
        @Override
        public void onAnimationEnd(Animation animation)
        {
            singleAnimationListener.onAnimationEnd(animation);
            if (animationsThatAreCurrentlyPlaying.decrementAndGet() == 0)
            {
                System.out.println("Parallel Ended");
                if(listenerForEveryAnimation!=null)
                    listenerForEveryAnimation.onAnimationEnd(animation);
            }
        }

        @Override
        public void onAnimationStart(Animation animation)
        {
            singleAnimationListener.onAnimationStart(animation);
            if (animationsThatHaveBeenStarted.incrementAndGet() == animationPackageList.size())
            {
                System.out.println("Parallel Started");
                if(listenerForEveryAnimation!=null)
                    listenerForEveryAnimation.onAnimationStart(animation);
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation)
        {}
    }

}
