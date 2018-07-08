package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ResponseListener<ResponseT, ErrorT> {

    void onSuccess(@NonNull ResponseT response);

    void onFailure(@Nullable ErrorT error);

}
