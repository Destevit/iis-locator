package pl.dominikostrowski.isslocator.provider;

import android.support.annotation.NonNull;

import pl.dominikostrowski.isslocator.model.Astronaut;
import pl.dominikostrowski.isslocator.util.Subject;
import pl.dominikostrowski.isslocator.viewmodel.AstronautsViewModel;

class ISSAstronautsObservableWrapper
        extends ViewModelObservableWrapper<Astronaut[], AstronautsViewModel, Exception> {

    ISSAstronautsObservableWrapper(@NonNull Subject<Astronaut[], Exception> subject) {
        super(subject);
    }

    @NonNull
    @Override
    AstronautsViewModel createViewModel(Astronaut[] model) {
        return new AstronautsViewModel(model);
    }
}