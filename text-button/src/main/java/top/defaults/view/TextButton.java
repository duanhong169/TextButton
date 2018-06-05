package top.defaults.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.StateSet;
import android.view.Gravity;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

import top.defaults.logger.Logger;
import top.defaults.view.textbutton.R;

import static top.defaults.view.EffectSettings.KEY_EFFECT_DURATION;
import static top.defaults.view.TextButtonEffect.BACKGROUND_EFFECT_NONE;
import static top.defaults.view.TextButtonEffect.TEXT_EFFECT_DEFAULT;

public class TextButton extends android.support.v7.widget.AppCompatTextView {

    private static final boolean DEBUG = false;

    @ColorInt int defaultTextColor;
    @ColorInt int pressedTextColor;
    @ColorInt int disabledTextColor;
    @ColorInt int selectedTextColor;
    boolean isUnderlined;
    int effectType;
    int effectDuration;
    @ColorInt int defaultBackgroundColor;
    @ColorInt int pressedBackgroundColor;
    @ColorInt int disabledBackgroundColor;
    @ColorInt int selectedBackgroundColor;
    int backgroundEffectType;
    @ColorInt int defaultRippleColor;
    @ColorInt int pressedRippleColor;
    private EffectSet effects;

    private Drawable instinctBackground;
    private LayerDrawableProxy backgroundProxy = new LayerDrawableProxy();

    public static class Defaults {

        private static Defaults defaults = new Defaults();
        private SparseArray<Object> settings = new SparseArray<>();

        public static Defaults get() {
            return defaults;
        }

        Integer getInt(int key, int defaultValue) {
            Object o = settings.get(key);
            if (o instanceof Integer) {
                return (Integer) o;
            } else {
                return defaultValue;
            }
        }

        Boolean getBoolean(int key, boolean defaultValue) {
            Object o = settings.get(key);
            if (o instanceof Boolean) {
                return (Boolean) o;
            } else {
                return defaultValue;
            }
        }

        public void set(int key, Object o) {
            settings.put(key, o);
        }
    }

    private int getColor(TypedArray typedArray, int key, int defaultValue) {
        return typedArray.getColor(key, Defaults.get().getInt(key, defaultValue));
    }
    
    private int getInt(TypedArray typedArray, int key, int defaultValue) {
        return typedArray.getInt(key, Defaults.get().getInt(key, defaultValue));
    }

    private boolean getBoolean(TypedArray typedArray, int key, @SuppressWarnings("SameParameterValue") boolean defaultValue) {
        return typedArray.getBoolean(key, Defaults.get().getBoolean(key, defaultValue));
    }

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
        defaultTextColor = getColor(typedArray, R.styleable.TextButton_defaultTextColor, getCurrentTextColor());
        pressedTextColor = getColor(typedArray, R.styleable.TextButton_pressedTextColor, calculatePressedColor(defaultTextColor));
        disabledTextColor = getColor(typedArray, R.styleable.TextButton_disabledTextColor, calculateDisabledColor(defaultTextColor));
        selectedTextColor = getColor(typedArray, R.styleable.TextButton_selectedTextColor, calculateSelectedColor(defaultTextColor));
        isUnderlined = getBoolean(typedArray, R.styleable.TextButton_underline, false);
        effectType = getInt(typedArray, R.styleable.TextButton_textEffect, TEXT_EFFECT_DEFAULT);
        // -1 means use effect's default duration
        effectDuration = getInt(typedArray, R.styleable.TextButton_effectDuration, -1);
        defaultBackgroundColor = getColor(typedArray, R.styleable.TextButton_defaultBackgroundColor, 0);
        pressedBackgroundColor = getColor(typedArray, R.styleable.TextButton_pressedBackgroundColor, calculatePressedColor(defaultBackgroundColor));
        disabledBackgroundColor = getColor(typedArray, R.styleable.TextButton_disabledBackgroundColor, calculateDisabledColor(defaultBackgroundColor));
        selectedBackgroundColor = getColor(typedArray, R.styleable.TextButton_selectedBackgroundColor, calculateSelectedColor(defaultBackgroundColor));
        backgroundEffectType = getInt(typedArray, R.styleable.TextButton_backgroundEffect, BACKGROUND_EFFECT_NONE);
        defaultRippleColor = getColor(typedArray, R.styleable.TextButton_defaultRippleColor, defaultTextColor);
        pressedRippleColor = getColor(typedArray, R.styleable.TextButton_pressedRippleColor, pressedTextColor);
        typedArray.recycle();

        instinctBackground = getBackground();
        apply();
    }

    public Map<String, Object> getSettings() {
        Map<String, Object> settings = new HashMap<>(10);
        settings.put(KEY_EFFECT_DURATION, effectDuration);
        EffectSettings.reviewAndAdjustSettings(effectType, settings);
        return settings;
    }

    /**
     * Set custom {@link TextButtonEffect}s, which will override the xml settings
     *
     * @param effects user defined effect
     */
    public void setEffects(TextButtonEffect... effects) {
        this.effects.clear();
        if (effects.length == 0) {
            this.effects = null;
        } else {
            this.effects.add(effects);
        }
        apply();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        apply();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        apply();
    }

    public void addRipple() {
        if (effects == null) {
            effects = new EffectSet();
        }
        effects.addRipple();
        apply();
    }

    public void removeRipple() {
        if (effects != null) {
            effects.removeRipple();
        }
        apply();
    }

    private void apply() {
        setDefaultTextColorState();
        backgroundProxy.clear().addLayer(instinctBackground);
        setBackgroundWithProxy(backgroundProxy);

        if (effects == null) {
            effects = TextButtonEffect.Factory.create(this);
        }

        effects.init(this);

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
        if (hsv[2] < 0.3f) return 0xffaaaaaa;
        hsv[2] = Math.max(0, hsv[2] - 0.3f);
        return Color.HSVToColor(alpha, hsv);
    }

    private int calculateSelectedColor(@ColorInt int defaultColor) {
        int alpha = Color.alpha(defaultColor);
        float[] hsv = new float[3];
        Color.colorToHSV(defaultColor, hsv);
        if (hsv[2] > 0.7f) return 0xfff44336;
        hsv[2] = Math.min(1, hsv[2] + 0.3f);
        return Color.HSVToColor(alpha, hsv);
    }

    ColorStateList getTextColorState() {
        return new ColorStateList(
                new int[][]{
                        new int[]{ android.R.attr.state_pressed },
                        new int[]{ -android.R.attr.state_enabled },
                        new int[]{ android.R.attr.state_selected },
                        new int[]{} // this should be empty to make default color as we want
                },
                new int[]{
                        pressedTextColor,
                        disabledTextColor,
                        selectedTextColor,
                        defaultTextColor
                }
        );
    }

    void setDefaultTextColorState() {
        // Set default color state list first, other effects can override
        setTextColor(getTextColorState());
    }

    private StateListDrawable getBackgroundColorStateDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressedBackgroundColor));
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, new ColorDrawable(disabledBackgroundColor));
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(selectedBackgroundColor));
        stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(defaultBackgroundColor));
        return stateListDrawable;
    }

    void setColorStateBackground() {
        setBackgroundWithProxy(backgroundProxy.addLayer(getBackgroundColorStateDrawable()));
    }

    public LayerDrawableProxy getBackgroundProxy() {
        return backgroundProxy;
    }

    public void setBackgroundWithProxy(LayerDrawableProxy backgroundProxy) {
        this.backgroundProxy = backgroundProxy;
        setBackgroundDrawable(backgroundProxy.get());
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
                    effects.actionDown();
                    break;
                case MotionEvent.ACTION_UP:
                    effects.actionUp();
                    break;
            }
        }

        return super.onTouchEvent(event);
    }
}
