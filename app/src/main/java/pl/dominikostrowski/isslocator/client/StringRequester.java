package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StringRequester extends RequesterDecorator<InputStream, String, Exception> {

    StringRequester(@NonNull Requester<InputStream, Exception> nestedRequester) {
        super(nestedRequester);
    }

    @Override
    protected void handleSuccess(
            @NonNull final InputStream response,
            @NonNull final ResponseListener<String, Exception> listener
    ) {
        final StringBuilder builder = new StringBuilder();

        try (final InputStreamReader streamReader = new InputStreamReader(response);
             final BufferedReader reader = new BufferedReader(streamReader)
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (final IOException exception) {
            listener.onFailure(exception);
            return;
        }

        listener.onSuccess(builder.toString());
    }

    @Override
    protected void handleFailure(
            @Nullable final Exception error,
            @NonNull final ResponseListener<String, Exception> listener
    ) {
        listener.onFailure(error);
    }
}
