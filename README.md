# Text Button [![gitHub release](https://img.shields.io/github/release/duanhong169/TextButton.svg?style=social)](https://github.com/duanhong169/TextButton/releases) [![platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html) [![license](https://img.shields.io/badge/license-Apache%202-green.svg)](https://github.com/duanhong169/TextButton/blob/master/LICENSE)

An easy to use `TextButton` when you need a TextView-like button, with handy touch feedback effects.

![screen-record](art/screen-record.gif)

## Gradle

```
dependencies {
    implementation 'com.github.duanhong169:text-button:${latestVersion}'
    ...
}
```

> Replace `${latestVersion}` with the latest version code. See [releases](https://github.com/duanhong169/TextButton/releases).

## Usage

### Use the built-in effects

Config `TextButton` in xml:

```xml
<top.defaults.view.TextButton
    android:id="@+id/button3"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="animateTextColor"
    android:padding="8dp"
    android:textSize="24sp"
    app:defaultTextColor="@android:color/holo_blue_dark"
    app:pressedTextColor="@android:color/holo_orange_dark"
    app:disabledTextColor="@android:color/darker_gray"
    app:underline="true"
    app:effectDuration="200"
    app:effect="animateTextColor"/>
```

We support four effects for now: `default`, `animateTextColor`, `animateTextSize` and `animateTextColorAndSize`.

### Implement your own effect

```java
button5.setEffect(new TextButtonEffect() {
    private TextButton textButton;

    @Override
    public void init(TextButton textButton) {
        this.textButton = textButton;
    }

    @Override
    public void actionDown() {
        textButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void actionUp() {
        textButton.setVisibility(View.VISIBLE);
    }
});
```

For more details, see the sample app.

### License

See the [LICENSE](./LICENSE) file.