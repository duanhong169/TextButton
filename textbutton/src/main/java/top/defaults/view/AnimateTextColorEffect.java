package top.defaults.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;

import java.util.Map;

import top.defaults.logger.Logger;

public class AnimateTextColorEffect implements TextButtonEffect {

    private ValueAnimator pressColorAnimation;
    private TextButton textButton;
    private Runnable reverseAnimation;

    @Override
    public void init(final TextButton textButton, Map<String, Object> params) {
        this.textButton = textButton;
        pressColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                textButton.defaultTextColor, textButton.pressedTextColor);
        EffectSettings.apply(pressColorAnimation, params);

        pressColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimateTextColorEffect.this.textButton
                        .setTextColor((Integer) animation.getAnimatedValue());
            }
        });

        pressColorAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                textButton.setTextColor(textButton.defaultTextColor);
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
