package pl.dominikostrowski.isslocator.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.dominikostrowski.isslocator.client.Client;
import pl.dominikostrowski.isslocator.client.ResponseListener;
import pl.dominikostrowski.isslocator.model.Position;
import pl.dominikostrowski.isslocator.util.Observer;
import pl.dominikostrowski.isslocator.util.Subject;
import pl.dominikostrowski.isslocator.util.SubjectDecorator;

class PositionSubject extends SubjectDecorator<Position, Exception> {

    @NonNull
    private final Client client;

    private final long interval;

    PositionSubject(
            @NonNull final Subject<Position, Exception> subject,
            @NonNull final Client client,
            final long interval
    ) {
        super(subject);
        this.client = client;
        this.interval = interval;
    }

    @Override
    public void addObserver(@NonNull Observer<Position, Exception> observer) {
        super.addObserver(observer);

        if (getObservers().size() < 2) {
            requestPosition();
        }
    }

    private void requestPosition() {
        client.getPosition(new ResponseListener<Position, Exception>() {
            @Override
            public void onSuccess(@NonNull Position response) {
                onNext(response);
            }

            @Override
            public void onFailure(@Nullable Exception error) {
                onError(error);
            }
        });
    }

    @Override
    public void onNext(Position object) {
        super.onNext(object);

        if (getObservers().size() > 0) {
            new Handler().postDelayed(this::requestPosition, interval);
        }
    }

    @Override
    public void onError(Exception error) {
        super.onError(error);

        if (getObservers().size() > 0) {
            new Handler().postDelayed(this::requestPosition, interval);
        }
    }
}
