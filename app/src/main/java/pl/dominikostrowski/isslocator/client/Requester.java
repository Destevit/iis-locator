package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;

interface Requester<ResponseT, ErrorT> {

    void makeRequest(@NonNull ResponseListener<ResponseT, ErrorT> responseListener);

}
