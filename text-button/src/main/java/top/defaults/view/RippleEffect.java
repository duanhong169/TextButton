package top.defaults.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;

public class RippleEffect implements TextButtonEffect {

    @Override
    public void init(TextButton textButton) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{} // this should be empty to make default color as we want
                    },
                    new int[]{
                            textButton.disabledTextColor
                    }
            );

            RippleDrawable ripple = new RippleDrawable(colorStateList, null, new ColorDrawable(0xffffffff));
            textButton.setBackground(ripple);
        }
    }

    @Override
    public void actionDown() {

    }

    @Override
    public void actionUp() {

    }
}
