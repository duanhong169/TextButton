package top.defaults.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;

public class RippleEffect implements BackgroundEffect {

    private TextButton textButton;
    private RippleDrawableProxy proxy;

    @Override
    public void init(TextButton textButton) {
        restore();
        this.textButton = textButton;
        Drawable background = textButton.getBackground();
        Drawable mask = background instanceof DrawableContainer ? background.getCurrent() : background;
        if (mask instanceof ShapeDrawable) {
            ShapeDrawable drawable = ((ShapeDrawable)mask.mutate());
            drawable.getPaint().setColor(0x43ffffff);
            mask = drawable;
        } else if (mask instanceof GradientDrawable) {
            GradientDrawable drawable = ((GradientDrawable)mask.mutate());
            drawable.setColor(0x43ffffff);
            mask = drawable;
        } else {
            mask = new ColorDrawable(0x43ffffff);
        }

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{ android.R.attr.state_pressed },
                        new int[]{} // this should be empty to make default color as we want
                },
                new int[]{
                        textButton.pressedRippleColor,
                        textButton.defaultRippleColor
                }
        );
        proxy = new RippleDrawableProxy(colorStateList, background, mask);
        textButton.setBackgroundDrawable(proxy.get());
    }

    public void restore() {
        if (textButton != null && proxy != null) {
            textButton.setBackgroundDrawable(proxy.getContent());
        }
    }

    @Override
    public void actionDown() {

    }

    @Override
    public void actionUp() {

    }
}
