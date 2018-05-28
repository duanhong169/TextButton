package top.defaults.view;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Map;

public class EffectSettings {
    private static final String KEY_DURATION = "key_duration";

    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator();
    private static final int DURATION_SHORT = 100;
    private static final int DURATION_MEDIUM = 200;
    private static final int DURATION_LONG = 300;
    private static final int DEFAULT_DURATION = DURATION_SHORT;

    static void apply(ValueAnimator animator, Map<String, Object> settings) {
        animator.setInterpolator(DEFAULT_INTERPOLATOR);
        animator.setDuration(MapUtil.getIntOrDefault(settings, KEY_DURATION, DEFAULT_DURATION));
    }

    static Map<String, Object> defaultSettings(int type) {
        switch (type) {
            case TextButtonEffect.EFFECT_ANIMATE_TEXT_COLOR:
                return MapUtil.singletonMap(EffectSettings.KEY_DURATION, DURATION_LONG);
            case TextButtonEffect.EFFECT_ANIMATE_TEXT_COLOR_AND_SIZE:
                return MapUtil.singletonMap(EffectSettings.KEY_DURATION, DURATION_MEDIUM);
            default:
                break;
        }

        return null;
    }
}
