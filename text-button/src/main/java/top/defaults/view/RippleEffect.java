package top.defaults.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;

public class RippleEffect implements BackgroundEffect {

    private TextButton textButton;
    private Drawable ownLayer;

    @Override
    public void init(TextButton textButton) {
        restore();
        this.textButton = textButton;
        Drawable background = textButton.getBackgroundProxy().getRoot();
        Drawable mask = background instanceof DrawableContainer ? background.getCurrent() : background;
        if (mask instanceof ShapeDrawable) {
            Drawable.ConstantState state = mask.getConstantState();
            if (state != null) {
                ShapeDrawable drawable = ((ShapeDrawable) state.newDrawable().mutate());
                drawable.getPaint().setColor(0x43ffffff);
                mask = drawable;
            }
        } else if (mask instanceof GradientDrawable) {
            Drawable.ConstantState state = mask.getConstantState();
            if (state != null) {
                GradientDrawable drawable = ((GradientDrawable) state.newDrawable().mutate());
                drawable.setColor(0x43ffffff);
                mask = drawable;
            }
        } else {
            mask = new ColorDrawable(0x43ffffff);
        }

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{} // this should be empty to make default color as we want
                },
                new int[]{
                        textButton.rippleColor
                }
        );

        ownLayer = new RippleDrawableProxy(colorStateList, background, mask).get();
        textButton.setBackgroundWithProxy(textButton.getBackgroundProxy().addLayer(ownLayer));
    }

    public void restore() {
        if (textButton != null) {
            textButton.setBackgroundWithProxy(textButton.getBackgroundProxy().removeLayer(ownLayer));
        }
    }

    @Override
    public void actionDown() {

    }

    @Override
    public void actionUp() {

    }
}
