import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AdminSet<X extends Approvable<P, T>, P, T extends Admin<X, T>> extends ApSet<X, P, T> {
    private Set<X> entries = new HashSet<>();
    private Set<P> criteria = new HashSet<>();

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

    public void extend() {
        for (P p : criteria) {
            for (Iterator<X> it = iterator(p); it.hasNext(); ) {
                X x = it.next();
                T approved = x.approved(p);
                if (approved != null) {
                    x.approve(p, approved.add(x));
                }
            }
        }
    }

    public void shorten() {
        for (P p : criteria) {
            for (Iterator<X> it = iterator(p); it.hasNext(); ) {
                X x = it.next();
                T approved = x.approved(p);
                if (approved != null) {
                    x.approve(p, approved.remove(x));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "AdminSet with entries: " + entries.toString() + " and criteria: " + criteria.toString();
    }
}