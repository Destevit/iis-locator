package pl.dominikostrowski.isslocator.util;

import android.support.annotation.NonNull;

import java.util.List;

public interface Observable<T, E> {

    void addObserver(@NonNull final Observer<T, E> observer);
    void removeObserver(@NonNull Observer<T, E> observer);
    int observersCount();
    @NonNull
    public List<Observer<T, E>> getObservers();

}