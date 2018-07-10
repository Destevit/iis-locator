package pl.dominikostrowski.isslocator.util;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListObservable<T, E> implements Observable<T,E> {

    @NonNull
    private final List<Observer<T, E>> observers = Collections.synchronizedList(
            new ArrayList<>()
    );

    public void addObserver(@NonNull final Observer<T, E> observer) {
        observers.add(observer);
    }

    public void removeObserver(@NonNull final Observer<T, E> observer) {
        observers.remove(observer);
    }

    @Override
    public int observersCount() {
        return observers.size();
    }

    @NonNull
    public List<Observer<T, E>> getObservers() {
        return new ArrayList<>(observers);
    }
}
