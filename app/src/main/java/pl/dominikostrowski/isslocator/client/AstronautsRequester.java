package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.dominikostrowski.isslocator.model.Astronaut;

class AstronautsRequester extends RequesterDecorator<JSONObject, Astronaut[], Exception> {

    AstronautsRequester(@NonNull final Requester<JSONObject, Exception> nestedRequester) {
        super(nestedRequester);
    }

    @Override
    protected void handleSuccess(
            @NonNull final JSONObject response,
            @NonNull final ResponseListener<Astronaut[], Exception> listener
    ) {
        final Astronaut[] astronauts;

        try {
            JSONArray jsonAstronauts = response.getJSONArray("people");

            astronauts = new Astronaut[jsonAstronauts.length()];

            for (int i = 0; i < astronauts.length; i++) {
                final JSONObject jsonAstronaut = jsonAstronauts.getJSONObject(i);

                final Astronaut parsedAstronaut = parseAstronaut(jsonAstronaut);
                astronauts[i] = parsedAstronaut;
            }
        } catch (final JSONException exception) {
            listener.onFailure(exception);
            return;
        }

        listener.onSuccess(astronauts);
    }

    private Astronaut parseAstronaut(JSONObject jsonAstronaut) throws JSONException {
        String name = jsonAstronaut.getString("name");
        String craft = jsonAstronaut.getString("craft");

        return new Astronaut(name, craft);
    }

    @Override
    protected void handleFailure(
            @Nullable final Exception error,
            @NonNull final ResponseListener<Astronaut[], Exception> listener
    ) {
        listener.onFailure(error);
    }
}
