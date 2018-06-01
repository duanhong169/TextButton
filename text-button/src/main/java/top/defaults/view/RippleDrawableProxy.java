package top.defaults.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class RippleDrawableProxy {

    private Drawable drawable;
    private Drawable content;
    private Drawable mask;

    /**
     * Creates a new ripple drawable with the specified ripple color and
     * optional content and mask drawables.
     *
     * @param color   The ripple color
     * @param content The content drawable, may be {@code null}
     * @param mask    The mask drawable, may be {@code null}
     */
    RippleDrawableProxy(@NonNull ColorStateList color, @Nullable Drawable content, @Nullable Drawable mask) {
        this.content = content;
        this.mask = mask;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = new RippleDrawable(color, content, mask);
        } else {
            drawable = content;
        }
    }

    Drawable get() {
        return drawable;
    }

    public Drawable getContent() {
        return content;
    }

    public Drawable getMask() {
        return mask;
    }
}
