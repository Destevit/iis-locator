package pl.dominikostrowski.isslocator.provider;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.dominikostrowski.isslocator.model.Position;
import pl.dominikostrowski.isslocator.util.Observer;
import pl.dominikostrowski.isslocator.util.Subject;
import pl.dominikostrowski.isslocator.util.SubjectDecorator;

class PositionSubjectCache extends SubjectDecorator<Position, Exception> {

    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String TIMESTAMP_KEY = "timestamp";

    @NonNull
    private final SharedPreferences sharedPreferences;

    PositionSubjectCache(
            @NonNull final Subject<Position, Exception> subject,
            @NonNull final SharedPreferences sharedPreferences
    ) {
        super(subject);
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void addObserver(@NonNull Observer<Position, Exception> observer) {
        Position cachedPosition = loadPosition();
        if (cachedPosition != null) {
            observer.onNext(cachedPosition);
        }

        super.addObserver(observer);
    }

    @Override
    public void onNext(Position object) {
        super.onNext(object);

        savePosition(object);
    }

    private void savePosition(Position position) {
        sharedPreferences.edit()
                .putLong(LATITUDE_KEY, Double.doubleToRawLongBits(position.getLatitude()))
                .putLong(LONGITUDE_KEY, Double.doubleToRawLongBits(position.getLongitude()))
                .putInt(TIMESTAMP_KEY, position.getTimestamp())
                .apply();
    }

    @Nullable
    private Position loadPosition() {
        if (!sharedPreferences.contains(LATITUDE_KEY)
                || !sharedPreferences.contains(LONGITUDE_KEY)
                || !sharedPreferences.contains(TIMESTAMP_KEY)) {
            return null;
        }

        double latitude, longitude;
        int timestamp;

        latitude = Double.longBitsToDouble(sharedPreferences.getLong(LATITUDE_KEY, 0));
        longitude = Double.longBitsToDouble(sharedPreferences.getLong(LONGITUDE_KEY, 0));
        timestamp = sharedPreferences.getInt(TIMESTAMP_KEY, 0);

        return new Position(latitude, longitude, timestamp);
    }
}
