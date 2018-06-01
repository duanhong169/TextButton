package top.defaults.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;

public class AnimateBackgroundColorEffect implements BackgroundEffect {

    private ValueAnimator pressColorAnimation;
    private TextButton textButton;
    private Runnable reverseAnimation;

    @Override
    public void init(final TextButton textButton) {
        this.textButton = textButton;
        pressColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                textButton.defaultBackgroundColor, textButton.pressedBackgroundColor);
        EffectSettings.apply(pressColorAnimation, textButton.getSettings());

        pressColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimateBackgroundColorEffect.this.textButton
                        .setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });

        pressColorAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                if (isReverse) {
                    textButton.setColorStateBackground();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                textButton.setColorStateBackground();
            }
        });
    }

    @Override
    public void actionDown() {
        pressColorAnimation.cancel();
        textButton.removeCallbacks(reverseAnimation);
        pressColorAnimation.start();
    }

    @Override
    public void actionUp() {
        reverseAnimation = new Runnable() {
            @Override
            public void run() {
                pressColorAnimation.reverse();
            }
        };
        textButton.postDelayed(reverseAnimation, pressColorAnimation.getDuration() - pressColorAnimation.getCurrentPlayTime());
    }
}
