import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path<X> implements Admin<X, Path<X>>, Iterable<X> {
    private final List<X> elements = new ArrayList<>();

    @Override
    public Path<X> add(X x) {
        if (!elements.contains(x)) {
            elements.add(x);
        }
        return this;
    }

    @Override
    public Path<X> remove(X x) {
        elements.remove(x);
        return this;
    }

    @Override
    public Iterator<X> iterator() {
        return elements.iterator();
    }

    @Override
    public String toString() {
        return "Path with elements: " + elements.toString();
    }
}