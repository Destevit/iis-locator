package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;

import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.model.Position;

public interface Client {

    void getPosition(@NonNull ResponseListener<Position, Exception> listener);

    void getISSAstronauts(@NonNull ResponseListener<Astronaut[], Exception> listener);
}
