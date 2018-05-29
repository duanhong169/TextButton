package top.defaults.view;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Map;

public class EffectSettings {
    public static final String KEY_DEFAULT_TEXT_COLOR = "key_default_text_color";
    public static final String KEY_PRESSED_TEXT_COLOR = "key_pressed_text_color";
    public static final String KEY_DISABLED_TEXT_COLOR = "key_disabled_text_color";
    public static final String KEY_IS_UNDERLINED = "key_is_underlined";
    public static final String KEY_EFFECT_DURATION = "key_effect_duration";

    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator();
    private static final int DURATION_SHORT = 100;
    private static final int DURATION_MEDIUM = 200;
    private static final int DURATION_LONG = 300;
    private static final int DEFAULT_DURATION = DURATION_SHORT;

    static void apply(ValueAnimator animator, Map<String, Object> settings) {
        animator.setInterpolator(DEFAULT_INTERPOLATOR);
        animator.setDuration(MapUtil.getIntOrDefault(settings, KEY_EFFECT_DURATION, DEFAULT_DURATION));
    }

    static void reviewSettings(int type, Map<String, Object> origin) {
        if (MapUtil.getIntOrDefault(origin, KEY_EFFECT_DURATION, -1) <= 0) {
            switch (type) {
                case TextButtonEffect.EFFECT_ANIMATE_TEXT_COLOR:
                    origin.put(KEY_EFFECT_DURATION, DURATION_LONG);
                    break;
                case TextButtonEffect.EFFECT_ANIMATE_TEXT_COLOR_AND_SIZE:
                    origin.put(KEY_EFFECT_DURATION, DURATION_MEDIUM);
                    break;
                default:
                    origin.put(KEY_EFFECT_DURATION, DEFAULT_DURATION);
                    break;
            }
        }
    }
}
