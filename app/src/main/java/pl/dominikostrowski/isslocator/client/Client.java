package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;

import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.model.Position;

public class Client {

    @NonNull
    private final RequesterFactory requesterFactory;

    public Client() {
        this(new RequesterFactory(new ConnectionFactory(new URLFactory())));
    }

    Client(@NonNull RequesterFactory requesterFactory) {
        this.requesterFactory = requesterFactory;
    }

    public void getPosition(ResponseListener<Position, Exception> listener) {
        requesterFactory.createPositionRequester().makeRequest(listener);
    }

    public void getISSAstronauts(ResponseListener<Astronaut[], Exception> listener) {
        requesterFactory.createISSAstronautsRequester().makeRequest(listener);
    }
}
