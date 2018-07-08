package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import pl.dominikostrowski.isslocator.model.Astronaut;

class ISSAstronautsRequester extends RequesterDecorator<Astronaut[], Astronaut[], Exception> {

    ISSAstronautsRequester(@NonNull final Requester<Astronaut[], Exception> nestedRequester) {
        super(nestedRequester);
    }

    @Override
    protected void handleSuccess(
            @NonNull final Astronaut[] response,
            @NonNull final ResponseListener<Astronaut[], Exception> listener
    ) {
        final List<Astronaut> astronauts = new ArrayList<>(Arrays.asList(response));

        for (final Iterator<Astronaut> iterator = astronauts.iterator(); iterator.hasNext();) {
            final Astronaut astronaut = iterator.next();

            if (astronaut == null) {
                iterator.remove();
                continue;
            }

            final String craft = astronaut.getCraft();

            if (craft == null || !craft.equals("ISS")) {
                iterator.remove();
            }
        }

        Astronaut[] astronautsArray = astronauts.toArray(new Astronaut[0]);

        listener.onSuccess(astronautsArray);
    }

    @Override
    protected void handleFailure(
            @Nullable final Exception error,
            @NonNull final ResponseListener<Astronaut[], Exception> listener
    ) {
        listener.onFailure(error);
    }
}
