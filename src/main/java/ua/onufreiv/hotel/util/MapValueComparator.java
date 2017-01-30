package ua.onufreiv.hotel.util;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by yurii on 1/30/17.
 */
public class MapValueComparator<K, V extends Comparable<? super V>> implements Comparator<Map.Entry<K, V>> {

    @Override
    public int compare(Map.Entry<K, V> entry1, Map.Entry<K, V> entry2) {
        return entry2.getValue().compareTo(entry1.getValue());
    }
}
