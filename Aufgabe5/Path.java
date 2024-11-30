import java.util.Iterator;

public class Path<X> implements Admin<X, Path<X>>, Iterable<X> {
    private final CustomList<X> elements = new CustomList<>();

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