import java.util.TreeSet;

public class HashTableBucket<T extends Comparable<? super T>> {
    private final TreeSet<T> set;

    public HashTableBucket() {
        set = new TreeSet<>();
    }

    public boolean put(T item) {
        return set.add(item);
    }

    public boolean remove(T item) {
        return set.remove(item);
    }

    public T get(T item) {
        T match = set.ceiling(item);
        return match != null && match.equals(item) ? match : null;
    }

    public Iterable<T> items() {
        return set;
    }

    @Override
    public String toString() {
        return set.toString();
    }
}
