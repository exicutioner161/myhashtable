public class LookupResult<T> implements Comparable<LookupResult<T>> {
    private final T value;
    private final long elapsedNanos;

    public LookupResult(T value, long elapsedNanos) {
        this.value = value;
        this.elapsedNanos = elapsedNanos;
    }

    public T getValue() {
        return value;
    }

    public long getElapsedNanos() {
        return elapsedNanos;
    }

    @Override
    public int compareTo(LookupResult<T> o) {
        Long thisTime = elapsedNanos;
        Long otherTime = o.getElapsedNanos();
        return thisTime.compareTo(otherTime);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        LookupResult<T> other = (LookupResult<T>) obj;
        return elapsedNanos == other.elapsedNanos;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(elapsedNanos);
    }
}
