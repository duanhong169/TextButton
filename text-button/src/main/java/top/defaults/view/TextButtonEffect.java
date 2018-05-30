package top.defaults.view;

public interface TextButtonEffect {

    int EFFECT_DEFAULT = 0;
    int EFFECT_ANIMATE_TEXT_COLOR = 1;
    int EFFECT_ANIMATE_TEXT_SIZE = 2;
    int EFFECT_ANIMATE_TEXT_COLOR_AND_SIZE = 3;
    int BACKGROUND_EFFECT_DEFAULT = 0;
    int BACKGROUND_EFFECT_RIPPLE = 1;

    void init(TextButton textButton);

    void actionDown();

    void actionUp();

    class Factory {

        static EffectSet create(TextButton textButton) {
            EffectSet effectSet = new EffectSet();
            switch (textButton.effectType) {
                case EFFECT_ANIMATE_TEXT_COLOR:
                    effectSet.add(new AnimateTextColorEffect());
                    break;
                case EFFECT_ANIMATE_TEXT_SIZE:
                    effectSet.add(new AnimateTextSizeEffect());
                    break;
                case EFFECT_ANIMATE_TEXT_COLOR_AND_SIZE:
                    effectSet.add(new AnimateTextColorEffect());
                    effectSet.add(new AnimateTextSizeEffect());
                    break;
                default:
                    effectSet.add(new DefaultTextEffect());
                    break;
            }

            switch (textButton.backgroundEffectType) {
                case BACKGROUND_EFFECT_RIPPLE:
                    effectSet.add(new RippleEffect());
                    break;
                default:
                    break;
            }

            effectSet.init(textButton);
            return effectSet;
        }
    }

    class DefaultTextEffect implements TextButtonEffect {

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
