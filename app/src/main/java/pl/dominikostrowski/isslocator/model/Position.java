package pl.dominikostrowski.isslocator.model;

import java.util.Objects;

public class Position {

    private final double latitude;
    private final double longitude;
    private final double timestamp;

    public Position(double latitude, double longitude, double timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(position.latitude, latitude) == 0 &&
                Double.compare(position.longitude, longitude) == 0 &&
                Double.compare(position.timestamp, timestamp) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(latitude, longitude, timestamp);
    }
}
