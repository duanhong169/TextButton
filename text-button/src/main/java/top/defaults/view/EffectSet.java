package top.defaults.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EffectSet implements TextButtonEffect {

    private List<TextButtonEffect> effects = new ArrayList<>();

    public void add(TextButtonEffect... effects) {
        this.effects.addAll(Arrays.asList(effects));
    }

    public void clear() {
        effects.clear();
    }

    public void addRipple() {
        boolean hasRipple = false;
        for (TextButtonEffect effect: effects) {
            if (effect instanceof RippleEffect) {
                hasRipple = true;
            }
        }

        if (!hasRipple) effects.add(new RippleEffect());
    }

    public void removeRipple() {
        for (int i = effects.size() - 1; i >=0; i--) {
            TextButtonEffect effect = effects.get(i);
            if (effect instanceof RippleEffect) {
                effects.remove(effect);
            }
        }
    }

    @Override
    public void init(TextButton textButton) {
        for (TextButtonEffect effect: effects) {
            effect.init(textButton);
        }
    }

    @Override
    public void actionDown() {
        for (TextButtonEffect effect: effects) {
            effect.actionDown();
        }
    }

    @Override
    public void actionUp() {
        for (TextButtonEffect effect: effects) {
            effect.actionUp();
        }
    }
}
