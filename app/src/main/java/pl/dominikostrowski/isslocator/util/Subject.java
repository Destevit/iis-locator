package pl.dominikostrowski.isslocator.util;

public interface Subject<T, E> extends Observable<T, E>, Observer<T, E> {
}
