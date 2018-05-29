package top.defaults.view;

import java.util.Map;

public class MapUtil {

    public static int getIntOrDefault(Map<String, Object> map, String key, int defaultValue) {
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
