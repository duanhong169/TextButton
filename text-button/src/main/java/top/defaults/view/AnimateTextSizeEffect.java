package top.defaults.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.ViewGroup;

public class AnimateTextSizeEffect implements TextButtonEffect {

    private ValueAnimator pressSizeAnimation;
    private TextButton textButton;
    private ViewGroup.LayoutParams layoutParams;
    private int originWidth;
    private int originHeight;
    private float originTextSize;
    private Runnable reverseAnimation;

    @Override
    public void init(final TextButton textButton) {
        this.textButton = textButton;
        originTextSize = textButton.getTextSize();
        pressSizeAnimation = ValueAnimator.ofObject(new FloatEvaluator(),
                originTextSize, Math.max(originTextSize - 4, textButton.getTextSize() * 0.9));
        EffectSettings.apply(pressSizeAnimation, textButton.getSettings());

        pressSizeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimateTextSizeEffect.this.textButton
                        .setTextSize(TypedValue.COMPLEX_UNIT_PX, (Float) animation.getAnimatedValue());
            }
        });
        pressSizeAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                if (!isReverse) {
                    TextButton textButton = AnimateTextSizeEffect.this.textButton;

                    layoutParams = textButton.getLayoutParams();
                    originWidth = layoutParams.width;
                    originHeight = layoutParams.height;

                    layoutParams.width = textButton.getWidth();
                    layoutParams.height = textButton.getHeight();
                    textButton.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                if (isReverse) {
                    if (layoutParams != null) {
                        // restore
                        layoutParams.width = originWidth;
                        layoutParams.height = originHeight;
                        AnimateTextSizeEffect.this.textButton.setLayoutParams(layoutParams);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                textButton.setTextSize(originTextSize);
            }
        });
    }

    @Override
    public void actionDown() {
        pressSizeAnimation.cancel();
        textButton.removeCallbacks(reverseAnimation);
        pressSizeAnimation.start();
    }

    @Override
    public void actionUp() {
        reverseAnimation = new Runnable() {
            @Override
            public void run() {
                pressSizeAnimation.reverse();
            }
        };
        textButton.postDelayed(reverseAnimation, pressSizeAnimation.getDuration() - pressSizeAnimation.getCurrentPlayTime());
    }
}
