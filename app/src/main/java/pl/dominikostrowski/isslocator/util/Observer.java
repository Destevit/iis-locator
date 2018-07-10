package pl.dominikostrowski.isslocator.util;

public interface Observer<T, E> {
    void onNext(T object);
    void onError(E error);
}
