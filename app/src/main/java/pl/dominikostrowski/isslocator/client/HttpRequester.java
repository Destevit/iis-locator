package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

class HttpRequester extends RequesterDecorator<HttpURLConnection, InputStream, Exception> {

    HttpRequester(@NonNull final Requester<HttpURLConnection, Exception> nestedRequester) {
        super(nestedRequester);
    }

    @Override
    protected void handleSuccess(
            @NonNull final HttpURLConnection response,
            @NonNull final ResponseListener<InputStream, Exception> listener
    ) {
        final int responseCode;
        try {
            response.connect();
            responseCode = response.getResponseCode();
        } catch (final IOException exception) {
            listener.onFailure(exception);
            return;
        }

        if (responseCode < 200 || responseCode >= 300) {
            listener.onFailure(null);
            return;
        }

        try (final InputStream inputStream = response.getInputStream()) {
            listener.onSuccess(inputStream);
        } catch (final IOException exception) {
            listener.onFailure(exception);
        }
    }

    @Override
    protected void handleFailure(
            @Nullable final Exception error,
            @NonNull final ResponseListener<InputStream, Exception> listener
    ) {
        listener.onFailure(error);
    }
}
