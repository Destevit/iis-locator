package pl.dominikostrowski.isslocator.client;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.model.Position;

public class AsyncClient implements Client {

    @NonNull
    private final SyncClient client;

    @NonNull
    private final Executor executor;

    public AsyncClient() {
        this(new SyncClient(), Executors.newSingleThreadExecutor());
    }

    AsyncClient(@NonNull final SyncClient client, @NonNull final Executor executor) {
        this.client = client;
        this.executor = executor;
    }

    @Override
    public void getPosition(@NonNull final ResponseListener<Position, Exception> listener) {
        final Handler currentHandler = new Handler();
        final ResponseListener<Position, Exception> wrapper =
                new ListenerWrapper<>(currentHandler, listener);

        executor.execute(() -> client.getPosition(wrapper));
    }

    @Override
    public void getISSAstronauts(@NonNull final ResponseListener<Astronaut[], Exception> listener) {
        final Handler currentHandler = new Handler();
        final ResponseListener<Astronaut[], Exception> wrapper =
                new ListenerWrapper<>(currentHandler, listener);

        executor.execute(() -> client.getISSAstronauts(wrapper));
    }

    private class ListenerWrapper<ResponseT, ErrorT> implements ResponseListener<ResponseT, ErrorT> {

        @NonNull
        private final Handler currentHandler;

        @NonNull
        private final ResponseListener<ResponseT, ErrorT> listener;

        private ListenerWrapper(
                @NonNull final Handler currentHandler,
                @NonNull final ResponseListener<ResponseT, ErrorT> listener
        ) {
            this.currentHandler = currentHandler;
            this.listener = listener;
        }

        @Override
        public void onSuccess(@NonNull final ResponseT response) {
            currentHandler.post(() -> listener.onSuccess(response));
        }

        @Override
        public void onFailure(@Nullable final ErrorT error) {
            currentHandler.post(() -> listener.onFailure(error));
        }
    }
}
