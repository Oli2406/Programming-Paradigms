public interface Admin<X, T> {
    T add(X x);
    T remove(X x);
}