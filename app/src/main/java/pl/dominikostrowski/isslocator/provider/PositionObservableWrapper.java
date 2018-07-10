package pl.dominikostrowski.isslocator.provider;

import android.support.annotation.NonNull;

import pl.dominikostrowski.isslocator.model.Position;
import pl.dominikostrowski.isslocator.util.Subject;
import pl.dominikostrowski.isslocator.viewmodel.PositionViewModel;

class PositionObservableWrapper
        extends ViewModelObservableWrapper<Position, PositionViewModel, Exception> {

    PositionObservableWrapper(@NonNull Subject<Position, Exception> subject) {
        super(subject);
    }

    @NonNull
    @Override
    PositionViewModel createViewModel(Position model) {
        return new PositionViewModel(model);
    }
}
