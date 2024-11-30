import java.util.Iterator;

public class AdminSet<X extends Approvable<P, T>, P, T extends Admin<? super X, T>> implements ApprovableSet<X, P, T> {
    private final CustomSet<X> entries = new CustomSet<>();
    private final CustomSet<P> criteria = new CustomSet<>();

    @Override
    public void add(X x) {
        entries.add(x);
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

    public void remove(P rc1) {
        criteria.remove(rc1);
    }
}