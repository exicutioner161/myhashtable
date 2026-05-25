import java.util.ArrayList;
import java.util.List;

public class MyHashTable<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int MAX_LOAD_FACTOR = 2;
    private List<HashTableBucket<T>> list;
    private int size;

    public MyHashTable() {
        initializeNewContainer(INITIAL_CAPACITY);
    }

    public boolean put(T item) {
        if (getBucket(item).get(item) != null) {
            return false;
        }
        if ((size + 1.0) / list.size() > MAX_LOAD_FACTOR) {
            rehash(list.size() * MAX_LOAD_FACTOR);
        }
        getBucket(item).put(item);
        size++;
        return true;
    }

    public boolean remove(T item) {
        if (getBucket(item).remove(item)) {
            size--;
            return true;
        }
        return false;
    }

    public T get(T item) {
        return getBucket(item).get(item);
    }

    private int getIndex(T item) {
        return Math.floorMod(item.hashCode(), list.size());
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        initializeNewContainer(INITIAL_CAPACITY);
    }

    private void initializeNewContainer(int capacity) {
        list = new ArrayList<>(capacity);
        size = 0;
        fillArray(capacity);
    }

    private void fillArray(int capacity) {
        for (int i = 0; i < capacity; i++) {
            list.add(new HashTableBucket<>());
        }
    }

    private void rehash(int newCapacity) {
        List<HashTableBucket<T>> oldList = list;
        initializeNewContainer(newCapacity);
        for (HashTableBucket<T> bucket : oldList) {
            for (T item : bucket.items()) {
                getBucket(item).put(item);
                size++;
            }
        }
    }

    private HashTableBucket<T> getBucket(T item) {
        return list.get(getIndex(item));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (HashTableBucket<T> bucket : list) {
            sb.append(bucket.toString()).append("\n");
        }
        return sb.toString();
    }
}