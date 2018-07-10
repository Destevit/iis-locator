package pl.dominikostrowski.isslocator.util;

import android.support.annotation.NonNull;

import java.util.List;

public class SubjectDecorator<T, E> implements Subject<T, E> {

    @NonNull
    private final Subject<T, E> subject;

    public SubjectDecorator(@NonNull final Subject<T, E> subject) {
        this.subject = subject;
    }

    @Override
    public void addObserver(@NonNull Observer<T, E> observer) {
        subject.addObserver(observer);
    }

    @Override
    public void removeObserver(@NonNull Observer<T, E> observer) {
        subject.removeObserver(observer);
    }

    @Override
    public int observersCount() {
        return subject.observersCount();
    }

    @NonNull
    @Override
    public List<Observer<T, E>> getObservers() {
        return subject.getObservers();
    }

    @Override
    public void onNext(T object) {
        subject.onNext(object);
    }

    @Override
    public void onError(E error) {
        subject.onError(error);
    }
}
