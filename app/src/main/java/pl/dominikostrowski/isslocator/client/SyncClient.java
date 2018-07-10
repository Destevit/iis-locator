package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;

import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.model.Position;

public class SyncClient implements Client {

    @NonNull
    private final RequesterFactory requesterFactory;

    public SyncClient() {
        this(new RequesterFactory(new ConnectionFactory(new URLFactory())));
    }

    SyncClient(@NonNull RequesterFactory requesterFactory) {
        this.requesterFactory = requesterFactory;
    }

    public void getPosition(@NonNull final ResponseListener<Position, Exception> listener) {
        requesterFactory.createPositionRequester().makeRequest(listener);
    }

    public void getISSAstronauts(@NonNull final ResponseListener<Astronaut[], Exception> listener) {
        requesterFactory.createISSAstronautsRequester().makeRequest(listener);
    }
}
