package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.HttpURLConnection;

import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.model.Position;

class RequesterFactory {

    @NonNull
    private final ConnectionFactory connectionFactory;

    @Nullable
    private PositionRequester positionRequester;

    @Nullable
    private ISSAstronautsRequester issAstronautsRequester;

    RequesterFactory(@NonNull final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @NonNull
    Requester<Position, Exception> createPositionRequester() {
        if (positionRequester != null) {
            return positionRequester;
        }

        final Requester<HttpURLConnection, Exception> connectionRequester =
                createHttpURLConnection(connectionFactory::createIssNowRequest);

        final JsonRequester jsonRequester = createJsonRequester(connectionRequester);

        positionRequester = new PositionRequester(jsonRequester);
        return positionRequester;
    }

    @NonNull
    Requester<Astronaut[], Exception> createISSAstronautsRequester() {
        if (issAstronautsRequester != null) {
            return issAstronautsRequester;
        }

        final Requester<HttpURLConnection, Exception> connectionRequester =
                createHttpURLConnection(connectionFactory::createAstrosRequest);

        final JsonRequester jsonRequester = createJsonRequester(connectionRequester);

        final AstronautsRequester astronautsRequester = new AstronautsRequester(jsonRequester);

        issAstronautsRequester = new ISSAstronautsRequester(astronautsRequester);
        return issAstronautsRequester;
    }

    @NonNull
    private JsonRequester createJsonRequester(
            Requester<HttpURLConnection, Exception> nestedRequester
    ) {
        final HttpRequester httpRequester = new HttpRequester(nestedRequester);
        final StringRequester stringRequester = new StringRequester(httpRequester);
        return new JsonRequester(stringRequester);
    }

    @NonNull
    private Requester<HttpURLConnection, Exception> createHttpURLConnection(
            @NonNull final ConnectionCreate create) {
        return responseListener -> {
            final HttpURLConnection connection;
            try {
                connection = create.create();
            } catch (IOException exception) {
                responseListener.onFailure(exception);
                return;
            }

            responseListener.onSuccess(connection);
        };
    }

    @FunctionalInterface
    interface ConnectionCreate {
        @NonNull
        HttpURLConnection create() throws IOException;
    }
}
