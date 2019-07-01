package com.epfd.csandroid;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_vertical_guide) Guideline mGuidelineVertical;
    @BindView(R.id.main_horizontal_guide) Guideline mGuideLineHorizontal;
    @BindView(R.id.main_vertical_left_guide) Guideline mGuidelineLeftVertical;
    @BindView(R.id.main_connexion_btn) Button mMainConnexionBtn;

    private AnimatedVectorDrawable mAnimationDrawable;

    private PropertyValuesHolder mAlphaValues = PropertyValuesHolder.ofInt("ALPHA", 0, 100);
    private PropertyValuesHolder mWaitValues = PropertyValuesHolder.ofInt("WAIT", 3000, 1);
    private PropertyValuesHolder mPurcentGuideLineValues = PropertyValuesHolder.ofInt("DIMENSION", 100, 55);
    private PropertyValuesHolder mPurcentBottomGuideLineValues = PropertyValuesHolder.ofInt("BOTTOM_DIMENSION", 100, 40);
    private PropertyValuesHolder mPurcentLeftGuideLineValues = PropertyValuesHolder.ofInt("LEFT_DIMENSION", 10, 15);

    private int mAnimationAlpha = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ImageView imageView = findViewById(R.id.logo_animation);
        imageView.setBackgroundResource(R.drawable.avd_anim_epfd);
        mAnimationDrawable = (AnimatedVectorDrawable) imageView.getBackground();
        mAnimationDrawable.start();

        this.playAnimation();
    }

    /**
     *  ANIMATION
     */

    private void playAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        animators.add(waitVectorAnimation());
        animators.add(reduceLogoAnimation());
        animators.add(showBtn());
        animatorSet.playSequentially(animators);
        animatorSet.start();
    }

    private Animator waitVectorAnimation(){
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(mWaitValues);
        animator.setDuration(2000);
        animator.addUpdateListener(animation -> {});
        return animator;
    }

    private Animator reduceLogoAnimation(){
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(mPurcentGuideLineValues, mPurcentLeftGuideLineValues, mPurcentBottomGuideLineValues);
        animator.setDuration(700);
        animator.addUpdateListener(animation -> {
            int rightValue = (int) animation.getAnimatedValue("DIMENSION");
            int leftValue = (int) animation.getAnimatedValue("LEFT_DIMENSION");
            int bottomValue = (int) animation.getAnimatedValue("BOTTOM_DIMENSION");
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mGuidelineVertical.getLayoutParams();
            ConstraintLayout.LayoutParams paramsL = (ConstraintLayout.LayoutParams) mGuidelineLeftVertical.getLayoutParams();
            ConstraintLayout.LayoutParams paramsH = (ConstraintLayout.LayoutParams) mGuideLineHorizontal.getLayoutParams();
            paramsL.guidePercent = (float)leftValue /100;
            params.guidePercent = (float)rightValue /100;
            paramsH.guidePercent = (float)bottomValue /100;
            mGuidelineLeftVertical.setLayoutParams(paramsL);
            mGuidelineVertical.setLayoutParams(params);
            mGuideLineHorizontal.setLayoutParams(paramsH);
        });
        return animator;
    }

    private Animator showBtn(){
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(mAlphaValues);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> {
            mAnimationAlpha = (int) animation.getAnimatedValue();
            mMainConnexionBtn.setAlpha((float)mAnimationAlpha /100);
        });
        return animator;
    }





}
