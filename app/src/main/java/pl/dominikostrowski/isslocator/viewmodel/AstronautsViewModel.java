package pl.dominikostrowski.isslocator.viewmodel;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import pl.dominikostrowski.isslocator.model.Astronaut;

public class AstronautsViewModel {

    @NonNull
    private final String astronauts;

    private final int numberOfAstronauts;

    public AstronautsViewModel(@NonNull final Astronaut[] astronauts) {
        this.numberOfAstronauts = astronauts.length;

        final String[] astronautNames = new String[astronauts.length];
        for (int i = 0; i < astronauts.length; i++) {
            astronautNames[i] = astronauts[i].getName();
        }

        this.astronauts = TextUtils.join("\n", astronautNames);
    }

    @NonNull
    public String getAstronauts() {
        return astronauts;
    }

    public int getNumberOfAstronauts() {
        return numberOfAstronauts;
    }
}
