public interface Approvable<P, T> {
    T approved(P p);
    void approve(P p, T t);
}