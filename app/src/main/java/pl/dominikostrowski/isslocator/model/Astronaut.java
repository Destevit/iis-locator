package pl.dominikostrowski.isslocator.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

public class Astronaut {

    @NonNull
    private final String name;

    @Nullable
    private final String craft;

    public Astronaut(@NonNull String name, @Nullable String craft) {
        this.name = name;
        this.craft = craft;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getCraft() {
        return craft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Astronaut astronaut = (Astronaut) o;
        return Objects.equals(name, astronaut.name) &&
                Objects.equals(craft, astronaut.craft);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, craft);
    }
}
