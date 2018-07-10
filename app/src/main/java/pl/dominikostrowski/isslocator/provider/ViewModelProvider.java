package pl.dominikostrowski.isslocator.provider;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import pl.dominikostrowski.isslocator.client.AsyncClient;
import pl.dominikostrowski.isslocator.client.Client;
import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.model.Position;
import pl.dominikostrowski.isslocator.util.ListObservable;
import pl.dominikostrowski.isslocator.util.Observable;
import pl.dominikostrowski.isslocator.util.SimpleSubject;
import pl.dominikostrowski.isslocator.util.Subject;
import pl.dominikostrowski.isslocator.viewmodel.AstronautsViewModel;
import pl.dominikostrowski.isslocator.viewmodel.PositionViewModel;

public class ViewModelProvider {

    @NonNull
    private final Client client;

    private final long interval;

    private PositionObservableWrapper positionObservable;
    private ISSAstronautsObservableWrapper astronautsObservable;

    @NonNull
    private final SharedPreferences sharedPreferences;

    public ViewModelProvider(
            final long interval,
            @NonNull final SharedPreferences sharedPreferences
    ) {
        this(new AsyncClient(), interval, sharedPreferences);
    }

    public ViewModelProvider(
            @NonNull final Client client,
            final long interval,
            @NonNull final SharedPreferences sharedPreferences
    ) {
        this.client = client;
        this.interval = interval;
        this.sharedPreferences = sharedPreferences;
    }

    public Observable<PositionViewModel, Exception> getPositionObservable() {
        if (positionObservable == null) {
            final Subject<Position, Exception> positionSubject = createPositionSubject();
            positionObservable = new PositionObservableWrapper(positionSubject);
        }

        return positionObservable;
    }

    public Observable<AstronautsViewModel, Exception> getAstronautsObservable() {
        if (astronautsObservable == null) {
            final Subject<Astronaut[], Exception> astronautsSubject = createAstronautsSubject();
            astronautsObservable = new ISSAstronautsObservableWrapper(astronautsSubject);
        }

        return astronautsObservable;
    }

    private Subject<Astronaut[], Exception> createAstronautsSubject() {
        final Observable<Astronaut[], Exception> observable = new ListObservable<>();
        Subject<Astronaut[], Exception> subject = new SimpleSubject<>(observable);
        subject = new ISSAstronautsSubject(subject, client);
        return subject;
    }

    private Subject<Position, Exception> createPositionSubject() {
        final Observable<Position, Exception> observable = new ListObservable<>();
        Subject<Position, Exception> subject = new SimpleSubject<>(observable);
        subject = new PositionSubjectCache(subject, sharedPreferences);
        subject = new PositionSubject(subject, client, interval);
        return subject;
    }
}
