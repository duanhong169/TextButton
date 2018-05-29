package top.defaults.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    static Map<String, Object> singletonMap(String key, Object value) {
        return new HashMap<>(Collections.singletonMap(key, value));
    }

    static int getIntOrDefault(Map<String, Object> map, String key, int defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return defaultValue;
        }
    }
}
