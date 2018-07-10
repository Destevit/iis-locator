package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonRequester extends RequesterDecorator<String, JSONObject, Exception> {

    JsonRequester(@NonNull final Requester<String, Exception> nestedRequester) {
        super(nestedRequester);
    }

    @Override
    protected void handleSuccess(
            @NonNull final String response,
            @NonNull final ResponseListener<JSONObject, Exception> listener
    ) {
        final JSONObject rootObject;

        try {
            rootObject = new JSONObject(response);
        } catch (final JSONException exception) {
            listener.onFailure(exception);
            return;
        }

        listener.onSuccess(rootObject);
    }

    @Override
    protected void handleFailure(
            @Nullable final Exception error,
            @NonNull final ResponseListener<JSONObject, Exception> listener
    ) {
        listener.onFailure(error);
    }
}
