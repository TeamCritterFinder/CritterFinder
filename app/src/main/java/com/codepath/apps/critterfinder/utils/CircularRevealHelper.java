package com.codepath.apps.critterfinder.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by smacgregor on 3/16/16.
 */
public class CircularRevealHelper {

    /**
     * Perform an enter animation for a circular reveal on viewToReveal
     * @param viewToReveal
     */
    public static void enterReveal(final View viewToReveal, final Activity activity, final Transition.TransitionListener transitionListener) {
        int cx = viewToReveal.getMeasuredWidth() / 2;
        int cy = viewToReveal.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(viewToReveal.getWidth(), viewToReveal.getHeight()) / 2;

        Animator animator = ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, 0, finalRadius);
        viewToReveal.setVisibility(View.VISIBLE);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                activity.getWindow().getEnterTransition().removeListener(transitionListener);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animator.start();
    }

    /**
     * Perform an exit animation for a circular reveal on viewToReveal
     * @param viewToReveal
     */
    public static void exitReveal(final View viewToReveal, final AppCompatActivity activity) {
        int cx = viewToReveal.getMeasuredWidth() / 2;
        int cy = viewToReveal.getMeasuredHeight() / 2;

        int initialRadius = viewToReveal.getWidth() / 2;

        Animator animator = ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, initialRadius, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewToReveal.setVisibility(View.INVISIBLE);

                activity.supportFinishAfterTransition();
            }
        });
        animator.start();
    }
}
