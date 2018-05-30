package top.defaults.view;

import android.content.res.ColorStateList;

public interface TextButtonEffect {

    int EFFECT_DEFAULT = 0;
    int EFFECT_ANIMATE_TEXT_COLOR = 1;
    int EFFECT_ANIMATE_TEXT_SIZE = 2;
    int EFFECT_ANIMATE_TEXT_COLOR_AND_SIZE = 3;

    void init(TextButton textButton);

    void actionDown();

    void actionUp();

    class Factory {

        static TextButtonEffect create(TextButton textButton) {
            // Set default color state list first, other effect can override
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{ android.R.attr.state_pressed },
                            new int[]{ -android.R.attr.state_enabled },
                            new int[]{} // this should be empty to make default color as we want
                    },
                    new int[]{
                            textButton.pressedTextColor,
                            textButton.disabledTextColor,
                            textButton.defaultTextColor
                    }
            );
            textButton.setTextColor(colorStateList);

            TextButtonEffect effect;
            switch (textButton.effectType) {
                case EFFECT_ANIMATE_TEXT_COLOR:
                    effect = new AnimateTextColorEffect();
                    break;
                case EFFECT_ANIMATE_TEXT_SIZE:
                    effect = new AnimateTextSizeEffect();
                    break;
                case EFFECT_ANIMATE_TEXT_COLOR_AND_SIZE:
                    effect = new TextButtonEffect() {
                        AnimateTextColorEffect colorEffect = new AnimateTextColorEffect();
                        AnimateTextSizeEffect sizeEffect = new AnimateTextSizeEffect();

                        @Override
                        public void init(TextButton textButton) {
                            colorEffect.init(textButton);
                            sizeEffect.init(textButton);
                        }

                        @Override
                        public void actionDown() {
                            colorEffect.actionDown();
                            sizeEffect.actionDown();
                        }

                        @Override
                        public void actionUp() {
                            colorEffect.actionUp();
                            sizeEffect.actionUp();
                        }
                    };
                    break;
                default:
                    effect = new DefaultEffect();
                    break;
            }

            effect.init(textButton);
            return effect;
        }
    }

    class DefaultEffect implements TextButtonEffect {

        @Override
        public void init(TextButton textButton) {

        }

        @Override
        public void actionDown() {

        }

        @Override
        public void actionUp() {

        }
    }
}
