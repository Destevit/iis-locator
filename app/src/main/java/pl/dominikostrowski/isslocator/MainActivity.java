package pl.dominikostrowski.isslocator;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.maps.MapView;

public class MainActivity extends AppCompatActivity {

    private Marker marker;
    private MapView mapView;
    private TextView lastUpdateDateTime;
    protected IconFactory iconFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastUpdateDateTime = findViewById(R.id.lastUpdateDateTime);

        iconFactory = IconFactory.getInstance(this);

        getMapboxInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(map -> {
            final MarkerOptions options = createMarkerOptions();
            final Icon icon = iconFactory.fromResource(R.drawable.iss_position_marker);
            options.setIcon(icon);

            marker = map.addMarker(options);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    protected Mapbox getMapboxInstance(@NonNull Context context, @Nullable String accessToken) {
        return Mapbox.getInstance(context, accessToken);
    }

    protected MarkerOptions createMarkerOptions() {
        return new MarkerOptions();
    }
}
