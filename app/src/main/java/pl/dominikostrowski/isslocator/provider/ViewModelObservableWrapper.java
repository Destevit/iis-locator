package pl.dominikostrowski.isslocator.provider;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.dominikostrowski.isslocator.util.Observable;
import pl.dominikostrowski.isslocator.util.Observer;
import pl.dominikostrowski.isslocator.util.Subject;

abstract class ViewModelObservableWrapper<ModelT, ViewModelT, ErrorT>
        implements Observable<ViewModelT, ErrorT> {

    @NonNull
    private final Subject<ModelT, ErrorT> subject;

    ViewModelObservableWrapper(@NonNull final Subject<ModelT, ErrorT> subject) {
        this.subject = subject;
    }

    @Override
    public void addObserver(@NonNull Observer<ViewModelT, ErrorT> observer) {
        subject.addObserver(new ObserverWrapper(observer));
    }

    @Override
    public void removeObserver(@NonNull Observer<ViewModelT, ErrorT> observer) {
        subject.removeObserver(new ObserverWrapper(observer));
    }

    @Override
    public int observersCount() {
        return subject.observersCount();
    }

    @NonNull
    @Override
    public List<Observer<ViewModelT, ErrorT>> getObservers() {
        List<Observer<ModelT, ErrorT>> observers = subject.getObservers();
        List<Observer<ViewModelT, ErrorT>> originalObservers =
                new ArrayList<>(observersCount());

        for (Observer<ModelT, ErrorT> observer :
                observers) {
            originalObservers.add(((ObserverWrapper) observer).getObserver());
        }

        return originalObservers;
    }

    @NonNull
    abstract ViewModelT createViewModel(ModelT model);

    private class ObserverWrapper implements Observer<ModelT, ErrorT> {

        @NonNull
        private final Observer<ViewModelT, ErrorT> observer;

        private ObserverWrapper(@NonNull Observer<ViewModelT, ErrorT> observer) {
            this.observer = observer;
        }

        @NonNull
        public Observer<ViewModelT, ErrorT> getObserver() {
            return observer;
        }

        @Override
        public void onNext(ModelT object) {
            if (object == null) {
                return;
            }

            observer.onNext(createViewModel(object));
        }

        @Override
        public void onError(ErrorT error) {
            observer.onError(error);
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ObserverWrapper that = (ObserverWrapper) o;
            return Objects.equals(observer, that.observer);
        }

        @Override
        public int hashCode() {

            return Objects.hash(observer);
        }
    }
}
