package top.defaults.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;

public class AnimateBackgroundColorEffect implements BackgroundEffect {

    private ValueAnimator pressColorAnimation;
    private TextButton textButton;
    private ColorDrawable ownLayer;
    private Runnable reverseAnimation;

    @Override
    public void init(final TextButton textButton) {
        restore();
        this.textButton = textButton;
        pressColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                textButton.defaultBackgroundColor, textButton.pressedBackgroundColor);
        EffectSettings.apply(pressColorAnimation, textButton.getSettings());
        ownLayer = new ColorDrawable(textButton.defaultBackgroundColor);
        textButton.setBackgroundWithProxy(textButton.getBackgroundProxy().addLayer(ownLayer));

        pressColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ownLayer.setColor((Integer) animation.getAnimatedValue());
                textButton.invalidate();
            }
        });

        pressColorAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                if (isReverse) {
                    ownLayer.setColor(textButton.defaultBackgroundColor);
                    textButton.invalidate();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                ownLayer.setColor(textButton.defaultBackgroundColor);
                textButton.invalidate();
            }
        });
    }

    private void restore() {
        if (textButton != null) {
            textButton.setBackgroundWithProxy(textButton.getBackgroundProxy().removeLayer(ownLayer));
        }
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
