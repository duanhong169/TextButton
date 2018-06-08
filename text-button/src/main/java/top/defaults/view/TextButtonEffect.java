package top.defaults.view;

public interface TextButtonEffect {

    int TEXT_EFFECT_DEFAULT = 0;
    int TEXT_EFFECT_ANIMATE_COLOR = 1;
    int TEXT_EFFECT_ANIMATE_SIZE = 2;
    int TEXT_EFFECT_ANIMATE_COLOR_AND_SIZE = 3;

    int BACKGROUND_EFFECT_NONE = -1;
    int BACKGROUND_EFFECT_COLOR_STATE = 0;
    int BACKGROUND_EFFECT_RIPPLE = 1;
    int BACKGROUND_EFFECT_ANIMATE_COLOR = 2;

    void init(TextButton textButton);

    void actionDown();

    void actionUp();

    class Factory {

        static EffectSet create(TextButton textButton) {
            EffectSet effectSet = new EffectSet();
            switch (textButton.effectType) {
                case TEXT_EFFECT_ANIMATE_COLOR:
                    effectSet.add(new AnimateTextColorEffect());
                    break;
                case TEXT_EFFECT_ANIMATE_SIZE:
                    effectSet.add(new AnimateTextSizeEffect());
                    break;
                case TEXT_EFFECT_ANIMATE_COLOR_AND_SIZE:
                    effectSet.add(new AnimateTextColorEffect());
                    effectSet.add(new AnimateTextSizeEffect());
                    break;
                default:
                    break;
            }

            switch (textButton.backgroundEffectType) {
                case BACKGROUND_EFFECT_COLOR_STATE:
                    effectSet.add(new ColorStateBackgroundEffect());
                    break;
                case BACKGROUND_EFFECT_RIPPLE:
                    effectSet.add(new RippleEffect());
                    break;
                case BACKGROUND_EFFECT_ANIMATE_COLOR:
                    effectSet.add(new AnimateBackgroundColorEffect());
                    break;
                default:
                    break;
            }

            return effectSet;
        }
    }
}
