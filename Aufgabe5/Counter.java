import java.util.HashMap;
import java.util.Map;

public class Counter<T> implements Approvable<Counter<T>, T> {
    private CustomMap<Counter<T>, T> approvals = new CustomMap<>();
    private int count = 0;
    private T value;

    public Counter(T value) {
        this.value = value;
    }

    @Override
    public T approved(Counter<T> y) {
        this.count += 1000;
        y.count += 1;
        return approvals.get(y);
    }

    @Override
    public void approve(Counter<T> y, T t) {
        approvals.put(y, t);
    }

    @Override
    public String toString() {
        return "Counter: " + count;
    }
}