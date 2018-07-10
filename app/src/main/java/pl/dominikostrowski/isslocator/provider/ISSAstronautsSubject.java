package pl.dominikostrowski.isslocator.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.dominikostrowski.isslocator.client.Client;
import pl.dominikostrowski.isslocator.client.ResponseListener;
import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.model.Position;
import pl.dominikostrowski.isslocator.util.Observer;
import pl.dominikostrowski.isslocator.util.Subject;
import pl.dominikostrowski.isslocator.util.SubjectDecorator;

class ISSAstronautsSubject extends SubjectDecorator<Astronaut[], Exception> {

    @NonNull
    private final Client client;

    ISSAstronautsSubject(
            @NonNull final Subject<Astronaut[], Exception> subject,
            @NonNull final Client client
    ) {
        super(subject);
        this.client = client;
    }

    @Override
    public void addObserver(@NonNull Observer<Astronaut[], Exception> observer) {
        super.addObserver(observer);

        requestAstronauts();
    }

    private void requestAstronauts() {
        client.getISSAstronauts(new ResponseListener<Astronaut[], Exception>() {
            @Override
            public void onSuccess(@NonNull Astronaut[] response) {
                onNext(response);
            }

            @Override
            public void onFailure(@Nullable Exception error) {
                onError(error);
            }
        });
    }
}