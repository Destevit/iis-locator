package pl.dominikostrowski.isslocator.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class SimpleSubject<T, E> implements Subject<T, E> {

    @NonNull
    private final Observable<T, E> observable;

    public SimpleSubject(@NonNull Observable<T, E> observable) {
        this.observable = observable;
    }

    @Override
    public void onNext(@Nullable final T object) {
        for (Observer<T, E> observer :
                getObservers()) {
            observer.onNext(object);
        }
    }

    @Override
    public void onError(@Nullable final E error) {
        for (Observer<T, E> observer :
                getObservers()) {
            observer.onError(error);
        }
    }

    @Override
    public void addObserver(@NonNull final Observer<T, E> observer) {
        observable.addObserver(observer);
    }

    @Override
    public void removeObserver(@NonNull final Observer<T, E> observer) {
        observable.removeObserver(observer);
    }

    @Override
    public int observersCount() {
        return observable.observersCount();
    }

    @NonNull
    @Override
    public List<Observer<T, E>> getObservers() {
        return observable.getObservers();
    }
}
