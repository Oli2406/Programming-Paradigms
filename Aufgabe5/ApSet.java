import java.util.Iterator;

public class ApSet<X extends Approvable<P, T>, P, T> implements ApprovableSet<X, P, T> {
    private CustomSet<X> entries = new CustomSet<>();
    private CustomSet<P> criteria = new CustomSet<>();

    @Override
    public void add(X x) {
        entries.add(x);
    }

    public void remove(X x) {
        entries.remove(x);
    }

    @Override
    public void addCriterion(P p) {
        criteria.add(p);
    }

    @Override
    public Iterator<X> iteratorAll() {
        return entries.iterator();
    }

    @Override
    public Iterator<X> iterator(P p) {
        return entries.stream()
            .filter(x -> x.approved(p) != null)
            .iterator();
    }

    @Override
    public Iterator<X> iteratorNot(P p) {
        return entries.stream()
            .filter(x -> x.approved(p) == null)
            .iterator();
    }

    @Override
    public Iterator<P> criterions() {
        return criteria.iterator();
    }

    @Override
    public Iterator<X> iterator() {
        return entries.stream()
            .filter(x -> criteria.stream().allMatch(p -> x.approved(p) != null))
            .iterator();
    }

    @Override
    public String toString() {
        return "ApSet with entries: " + entries.toString() + " and criteria: " + criteria.toString();
    }
}