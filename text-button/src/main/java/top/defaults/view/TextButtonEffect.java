package top.defaults.view;

public interface TextButtonEffect {

    int EFFECT_DEFAULT = 0;
    int EFFECT_ANIMATE_TEXT_COLOR = 1;
    int EFFECT_ANIMATE_TEXT_SIZE = 2;
    int EFFECT_ANIMATE_TEXT_COLOR_AND_SIZE = 3;
    int EFFECT_RIPPLE = 4;

    void init(TextButton textButton);

    void actionDown();

    void actionUp();

    class Factory {

        static TextButtonEffect create(TextButton textButton) {
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
                case EFFECT_RIPPLE:
                    effect = new RippleEffect();
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
