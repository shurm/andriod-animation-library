package com.example.mike.animationlibrarytest.ui.animation;

import android.view.View;
import android.view.animation.Animation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mike on 3/12/2018.
 */

public class SequenceAnimationSet
{

    private Animation.AnimationListener listenerForEveryAnimation;
    private List<ParallelAnimationSet> animationPackageList = new LinkedList<>();

    private Iterator<ParallelAnimationSet> iterator;

    public void add(View view,  ParallelAnimationSet parallelAnimationSet)
    {
        parallelAnimationSet.setAnimationListener(new SequenceAnimationListener(parallelAnimationSet));
        animationPackageList.add(parallelAnimationSet);
    }

    public void add(View view, MyAnimation myAnimation)
    {
        add(view, ParallelAnimationSet.create(view, myAnimation));
    }

    public void play()
    {
        iterator = animationPackageList.iterator();
        ParallelAnimationSet parallelAnimationSet = iterator.next();
        parallelAnimationSet.play();
    }


    public void setAnimationListener(Animation.AnimationListener listenerForEveryAnimation)
    {
        this.listenerForEveryAnimation=listenerForEveryAnimation;
    }

    public static SequenceAnimationSet create(View view, MyAnimation ... myAnimations)
    {

        SequenceAnimationSet sequenceAnimationSet = new SequenceAnimationSet();

        for(MyAnimation myAnimation : myAnimations)
            sequenceAnimationSet.add(view, myAnimation);

        return sequenceAnimationSet;
    }




    public Animation.AnimationListener getAnimationListener()
    {
        return listenerForEveryAnimation;
    }



    private class SequenceAnimationListener implements Animation.AnimationListener
    {

        private Animation.AnimationListener myAnimation;

        SequenceAnimationListener(ParallelAnimationSet myAnimation)
        {
            this.myAnimation=myAnimation.getAnimationListener();
        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            myAnimation.onAnimationEnd(animation);

            if(iterator==null)
                throw new RuntimeException("Error animation iterator not intitalized");
            if(iterator.hasNext())
            {
                ParallelAnimationSet nextAnimation = iterator.next();
                nextAnimation.play();
            }
            else if(listenerForEveryAnimation!=null)
            {
                listenerForEveryAnimation.onAnimationEnd(animation);
                System.out.println("Sequence Ended");
            }
        }

        @Override
        public void onAnimationStart(Animation animation)
        {
            if(myAnimation!=null)
                myAnimation.onAnimationStart(animation);
            System.out.println("Sequence Started");

        }
        @Override
        public void onAnimationRepeat(Animation animation)
        {
            myAnimation.onAnimationRepeat(animation);
            System.out.println("Sequence Repeated");
        }
    }

}

