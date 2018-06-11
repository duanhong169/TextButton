package top.defaults.textbuttonapp;

import android.app.Application;

import top.defaults.view.TextButton;
import top.defaults.view.TextButtonEffect;

public class TextButtonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TextButton.Defaults defaults = TextButton.Defaults.get();
        defaults.set(top.defaults.view.textbutton.R.styleable.TextButton_selectedTextColor, 0xff0000ff); // Blue
    }
}
