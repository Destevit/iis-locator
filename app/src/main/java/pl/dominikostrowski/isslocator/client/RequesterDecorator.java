package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

abstract class RequesterDecorator<InputT, ResponseT, ErrorT>
        implements Requester<ResponseT, ErrorT> {

    @NonNull
    private final Requester<InputT, ErrorT> nestedRequester;

    RequesterDecorator(@NonNull final Requester<InputT, ErrorT> nestedRequester) {
        this.nestedRequester = nestedRequester;
    }

    @Override
    public void makeRequest(@NonNull final ResponseListener<ResponseT, ErrorT> responseListener) {
        nestedRequester.makeRequest(new ResponseListener<InputT, ErrorT>() {
            @Override
            public void onSuccess(@NonNull final InputT response) {
                handleSuccess(response, responseListener);
            }

            @Override
            public void onFailure(@Nullable final ErrorT error) {
                handleFailure(error, responseListener);
            }
        });
    }

    protected abstract void handleSuccess(
            @NonNull InputT response, @NonNull ResponseListener<ResponseT, ErrorT> listener
    );

    protected abstract void handleFailure(
            @Nullable ErrorT error, @NonNull ResponseListener<ResponseT, ErrorT> listener
    );
}
