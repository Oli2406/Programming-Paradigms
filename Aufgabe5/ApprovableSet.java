import java.util.Iterator;

public interface ApprovableSet<X extends Approvable<P, T>, P, T> extends Iterable<X> {
    void add(X x);
    void addCriterion(P p);
    Iterator<X> iteratorAll();
    Iterator<X> iterator(P p);
    Iterator<X> iteratorNot(P p);
    Iterator<P> criterions();
}