package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import pl.dominikostrowski.isslocator.model.Position;

class PositionRequester extends RequesterDecorator<JSONObject, Position, Exception> {

    PositionRequester(@NonNull final Requester<JSONObject, Exception> nestedRequester) {
        super(nestedRequester);
    }

    @Override
    protected void handleSuccess(
            @NonNull final JSONObject response,
            @NonNull final ResponseListener<Position, Exception> listener
    ) {
        final double latitude, longitude, timestamp;

        try {
            timestamp = response.getInt("timestamp");

            JSONObject position = response.getJSONObject("iss_position");
            latitude = position.getDouble("latitude");
            longitude = position.getDouble("longitude");
        } catch (JSONException exception) {
            listener.onFailure(exception);
            return;
        }

        Position parsedPosition = new Position(latitude, longitude, timestamp);
        listener.onSuccess(parsedPosition);
    }

    @Override
    protected void handleFailure(
            @Nullable final Exception error,
            @NonNull final ResponseListener<Position, Exception> listener
    ) {
        listener.onFailure(error);
    }
}
