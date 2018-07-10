package pl.dominikostrowski.isslocator.viewmodel;

import android.support.annotation.NonNull;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.dominikostrowski.isslocator.model.Position;

public class PositionViewModel {

    @NonNull
    private final LatLng latLng;

    @NonNull
    private final String updateDateTime;

    public PositionViewModel(@NonNull final Position position) {
        latLng = new LatLng(position.getLatitude(), position.getLongitude());

        final DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        final Date date = new Date(position.getTimestamp() * 1000L);
        updateDateTime = dateFormat.format(date);
    }

    @NonNull
    public LatLng getLatLng() {
        return latLng;
    }

    @NonNull
    public String getUpdateDateTime() {
        return updateDateTime;
    }
}
