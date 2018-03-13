package com.example.mike.animationlibrarytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.mike.animationlibrarytest.ui.animation.MyAnimation;
import com.example.mike.animationlibrarytest.ui.animation.SequenceAnimationSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView yellowBall = findViewById(R.id.yellowBall);

        ImageView purpleBall = findViewById(R.id.purpleBall);

        MyAnimation animation = new MyAnimation(new TranslateAnimation(0,100,0,0));
        animation.setDuration(1000);
        animation.setAnimationListener(new TestAnimationListener());

        MyAnimation animation2 = new MyAnimation(new TranslateAnimation(0,100,0,0));
        animation2.setDuration(1000);
        animation2.setAnimationListener(new TestAnimationListener());

        SequenceAnimationSet sequenceAnimationSet = new SequenceAnimationSet();

        sequenceAnimationSet.add(yellowBall,animation);

        sequenceAnimationSet.add(purpleBall,animation2);

        sequenceAnimationSet.play();
    }

    private static class TestAnimationListener implements Animation.AnimationListener
    {

        @Override
        public void onAnimationStart(Animation animation)
        {
            System.out.print("TestAnimation Started!!");
        }

        @Override
        public void onAnimationEnd(Animation animation) {

            System.out.print("TestAnimation  Ended!!");
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
