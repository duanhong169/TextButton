package top.defaults.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

import top.defaults.logger.Logger;
import top.defaults.view.clickabletextview.R;

import static top.defaults.view.EffectSettings.KEY_DEFAULT_TEXT_COLOR;
import static top.defaults.view.EffectSettings.KEY_DISABLED_TEXT_COLOR;
import static top.defaults.view.EffectSettings.KEY_EFFECT_DURATION;
import static top.defaults.view.EffectSettings.KEY_IS_UNDERLINED;
import static top.defaults.view.EffectSettings.KEY_PRESSED_TEXT_COLOR;
import static top.defaults.view.TextButtonEffect.EFFECT_DEFAULT;

public class TextButton extends android.support.v7.widget.AppCompatTextView {

    private static final boolean DEBUG = false;

    @ColorInt int defaultTextColor;
    @ColorInt int pressedTextColor;
    @ColorInt int disabledTextColor;
    @ColorInt int highlightedTextColor;
    boolean isUnderlined;
    int effectType;
    int effectDuration;
    private TextButtonEffect effect;

    public TextButton(Context context) {
        this(context, null);
    }

    public TextButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextButton);
        defaultTextColor = typedArray.getColor(R.styleable.TextButton_defaultTextColor, getCurrentTextColor());
        pressedTextColor = typedArray.getColor(R.styleable.TextButton_pressedTextColor, calculatePressedColor(defaultTextColor));
        disabledTextColor = typedArray.getColor(R.styleable.TextButton_disabledTextColor, calculateDisabledColor(defaultTextColor));
        highlightedTextColor = typedArray.getColor(R.styleable.TextButton_highlightedTextColor, calculateHighlightedColor(defaultTextColor));
        isUnderlined = typedArray.getBoolean(R.styleable.TextButton_underline, false);
        effectType = typedArray.getInt(R.styleable.TextButton_effect, EFFECT_DEFAULT);
        // -1 means use effect's default duration
        effectDuration = typedArray.getInt(R.styleable.TextButton_effectDuration, -1);
        typedArray.recycle();

        apply();
    }

    public Map<String, Object> getSettings() {
        Map<String, Object> settings = new HashMap<>(10);

        settings.put(KEY_DEFAULT_TEXT_COLOR, defaultTextColor);
        settings.put(KEY_PRESSED_TEXT_COLOR, pressedTextColor);
        settings.put(KEY_DISABLED_TEXT_COLOR, disabledTextColor);
        settings.put(KEY_IS_UNDERLINED, isUnderlined);
        settings.put(KEY_EFFECT_DURATION, effectDuration);

        EffectSettings.reviewSettings(effectType, settings);

        return settings;
    }

    /**
     * Set custom {@link TextButtonEffect}, which will override the xml settings
     *
     * @param effect user defined effect
     */
    public void setEffect(TextButtonEffect effect) {
        this.effect = effect;
        apply();
    }

    private void apply() {
        setDefaultColorState();

        if (effect == null) {
            effect = TextButtonEffect.Factory.create(this);
        }

        effect.init(this);

        if (isUnderlined) {
            setPaintFlags(getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        setGravity(Gravity.CENTER);
    }

    private int calculatePressedColor(@ColorInt int defaultColor) {
        int alpha = Color.alpha(defaultColor);
        alpha = Math.max(16, alpha - 96);
        return Color.argb(alpha, Color.red(defaultColor), Color.green(defaultColor), Color.blue(defaultColor));
    }

    private int calculateDisabledColor(@ColorInt int defaultColor) {
        int alpha = Color.alpha(defaultColor);
        float[] hsv = new float[3];
        Color.colorToHSV(defaultColor, hsv);
        if (hsv[1] < 0.2f) return 0xffaaaaaa;
        hsv[1] = Math.max(0, hsv[1] - 0.4f);
        return Color.HSVToColor(alpha, hsv);
    }

    private int calculateHighlightedColor(@ColorInt int defaultColor) {
        int alpha = Color.alpha(defaultColor);
        float[] hsv = new float[3];
        Color.colorToHSV(defaultColor, hsv);
        if (hsv[1] > 0.8f) return 0xffaa0000;
        hsv[1] = Math.min(1, hsv[1] + 0.4f);
        return Color.HSVToColor(alpha, hsv);
    }

    void setDefaultColorState() {
        // Set default color state list first, other effect can override
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{ android.R.attr.state_pressed },
                        new int[]{ -android.R.attr.state_enabled },
                        new int[]{} // this should be empty to make default color as we want
                },
                new int[]{
                        pressedTextColor,
                        disabledTextColor,
                        defaultTextColor
                }
        );
        setTextColor(colorStateList);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (DEBUG) {
            Logger.d("event action: %d", event.getAction());
        }

        if (isEnabled() && isClickable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    effect.actionDown();
                    break;
                case MotionEvent.ACTION_UP:
                    effect.actionUp();
                    break;
            }
        }

        return super.onTouchEvent(event);
    }
}
