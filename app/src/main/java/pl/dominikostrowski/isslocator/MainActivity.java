package pl.dominikostrowski.isslocator;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import pl.dominikostrowski.isslocator.provider.ViewModelProvider;
import pl.dominikostrowski.isslocator.util.Observer;
import pl.dominikostrowski.isslocator.viewmodel.AstronautsViewModel;
import pl.dominikostrowski.isslocator.viewmodel.PositionViewModel;

public class MainActivity extends AppCompatActivity {

    private Marker marker;
    private MapView mapView;
    private TextView lastUpdateDateTime;
    private ViewModelProvider provider;
    private Observer<PositionViewModel, Exception> positionObserver;
    private Observer<AstronautsViewModel, Exception> astronautsObserver;
    protected IconFactory iconFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        lastUpdateDateTime = findViewById(R.id.lastUpdateDateTime);

        iconFactory = IconFactory.getInstance(this);
        provider = new ViewModelProvider(5000, getSharedPreferences("iss", MODE_PRIVATE));

        getMapboxInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
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

        mapView.getMapAsync(this::trackIssPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

        if (positionObserver != null) {
            provider.getPositionObservable().removeObserver(positionObserver);
            positionObserver = null;
        }

        if (astronautsObserver != null) {
            provider.getAstronautsObservable().removeObserver(astronautsObserver);
            astronautsObserver = null;
        }
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

    private void trackIssPosition(MapboxMap map) {
        if (positionObserver != null) {
            return;
        }

        positionObserver = new Observer<PositionViewModel, Exception>() {
            @Override
            public void onNext(PositionViewModel object) {
                lastUpdateDateTime.setText(object.getUpdateDateTime());

                if (marker == null) {
                    final MarkerOptions options = createMarkerOptions();
                    final Icon icon = iconFactory.fromResource(R.drawable.iss_position_marker);
                    options.setIcon(icon);
                    options.setPosition(object.getLatLng());
                    marker = map.addMarker(options);
                    setupMarkerAnnotation(marker);
                    map.easeCamera(mapboxMap ->
                            new CameraPosition
                                    .Builder()
                                    .target(object.getLatLng())
                                    .build()
                    );
                    return;
                }

                marker.setPosition(object.getLatLng());
            }

            @Override
            public void onError(Exception error) {
                Toast.makeText(
                        getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG
                ).show();
            }
        };

        provider.getPositionObservable().addObserver(positionObserver);
    }

    private void setupMarkerAnnotation(@NonNull final Marker marker) {
        if (astronautsObserver != null) {
            return;
        }

        astronautsObserver = new Observer<AstronautsViewModel, Exception>() {
            @Override
            public void onNext(AstronautsViewModel object) {
                int numberOfAstronauts = object.getNumberOfAstronauts();
                String title = getResources()
                        .getQuantityString(
                                R.plurals.astronautsNumber, numberOfAstronauts, numberOfAstronauts
                        );
                marker.setTitle(title);
                marker.setSnippet(object.getAstronauts());
            }

            @Override
            public void onError(Exception error) {
                Toast.makeText(
                        getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG
                ).show();
            }
        };

        provider.getAstronautsObservable().addObserver(astronautsObserver);
    }

    protected Mapbox getMapboxInstance(@NonNull Context context, @Nullable String accessToken) {
        return Mapbox.getInstance(context, accessToken);
    }

    protected MarkerOptions createMarkerOptions() {
        return new MarkerOptions();
    }
}
