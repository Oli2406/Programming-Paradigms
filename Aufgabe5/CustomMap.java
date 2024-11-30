import java.util.Arrays;

public class CustomMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[][] table;
    private int size = 0;

    public CustomMap() {
        table = new Object[DEFAULT_CAPACITY][2];
    }

    private int hash(Object key) {
        return key == null ? 0 : Math.abs(key.hashCode() % table.length);
    }

    private void ensureCapacity() {
        if (size == table.length) {
            table = Arrays.copyOf(table, table.length * 2);
        }
    }

    public V get(K key) {
        int index = hash(key);
        for (int i = 0; i < size; i++) {
            if (table[i][0].equals(key)) {
                return (V) table[i][1];
            }
        }
        return null;
    }

    public V put(K key, V value) {
        ensureCapacity();
        int index = hash(key);
        for (int i = 0; i < size; i++) {
            if (table[i][0].equals(key)) {
                V oldValue = (V) table[i][1];
                table[i][1] = value;
                return oldValue;
            }
        }
        table[size][0] = key;
        table[size][1] = value;
        size++;
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        for (int i = 0; i < size; i++) {
            if (table[i][0].equals(key)) {
                V oldValue = (V) table[i][1];
                table[i][0] = table[size - 1][0];
                table[i][1] = table[size - 1][1];
                table[size - 1] = null;
                size--;
                return oldValue;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < size; i++) {
            if (table[i][1].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        table = new Object[DEFAULT_CAPACITY][2];
        size = 0;
    }
}