package top.defaults.view;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LayerDrawableProxy {

    private LayerDrawable layerDrawable;
    private List<Drawable> layers = new ArrayList<>();

    LayerDrawableProxy(Drawable... layers) {
        this(new ArrayList<>(Arrays.asList(layers)));
    }

    private LayerDrawableProxy(List<Drawable> layers) {
        for (int i = layers.size() - 1; i >= 0; i--) {
            if (layers.get(i) == null) {
                layers.remove(i);
            }
        }
        layerDrawable = new LayerDrawable(layers.toArray(new Drawable[layers.size()]));
        this.layers.addAll(layers);
    }

    public LayerDrawableProxy clear() {
        layers.clear();
        layerDrawable = new LayerDrawable(layers.toArray(new Drawable[layers.size()]));
        return this;
    }

    public LayerDrawableProxy addLayer(Drawable layer) {
        if (layer == null) return this;
        this.layers.add(layer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layerDrawable.addLayer(layer);
        } else {
            layerDrawable = new LayerDrawable(layers.toArray(new Drawable[layers.size()]));
        }
        return this;
    }

    public LayerDrawableProxy removeLayer(Drawable layer) {
        if (layer != null) {
            layers.remove(layer);
            layerDrawable = new LayerDrawable(layers.toArray(new Drawable[layers.size()]));
        }
        return this;
    }

    public LayerDrawable get() {
        return layers.size() > 0 ? layerDrawable : null;
    }

    public Drawable getRoot() {
        return layers.size() > 0 ? layers.get(0) : null;
    }
}
