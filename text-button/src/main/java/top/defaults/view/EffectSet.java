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
        if (!hasRipple()) effects.add(new RippleEffect());
    }

    public void removeRipple() {
        for (int i = effects.size() - 1; i >=0; i--) {
            TextButtonEffect effect = effects.get(i);
            if (effect instanceof RippleEffect) {
                ((RippleEffect) effect).restore();
                effects.remove(effect);
            }
        }
    }

    private boolean hasRipple() {
        boolean hasRipple = false;
        for (TextButtonEffect effect: effects) {
            if (effect instanceof RippleEffect) {
                hasRipple = true;
                break;
            }
        }
        return hasRipple;
    }

    @Override
    public void init(TextButton textButton) {
        boolean hasRipple = hasRipple();
        for (TextButtonEffect effect: effects) {
            // If we have RippleEffect, then ignore other BackgroundEffect
            if (hasRipple && effect instanceof BackgroundEffect && !(effect instanceof RippleEffect)) {
                continue;
            }
            effect.init(textButton);
        }
    }

    @Override
    public void actionDown() {
        boolean hasRipple = hasRipple();
        for (TextButtonEffect effect: effects) {
            // If we have RippleEffect, then ignore other BackgroundEffect
            if (hasRipple && effect instanceof BackgroundEffect && !(effect instanceof RippleEffect)) {
                continue;
            }
            effect.actionDown();
        }
    }

    @Override
    public void actionUp() {
        boolean hasRipple = hasRipple();
        for (TextButtonEffect effect: effects) {
            // If we have RippleEffect, then ignore other BackgroundEffect
            if (hasRipple && effect instanceof BackgroundEffect && !(effect instanceof RippleEffect)) {
                continue;
            }
            effect.actionUp();
        }
    }
}
