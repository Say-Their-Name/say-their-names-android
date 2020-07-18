package io.saytheirnames.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.saytheirnames.models.Person;
import io.saytheirnames.repositories.PersonDetailsRepository;

public class PersonDetailsViewModel extends AndroidViewModel {
    private PersonDetailsRepository mPersonDetailsRepository;
    private LiveData<Person> mPersonLiveData;

    public PersonDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        mPersonDetailsRepository = new PersonDetailsRepository();
        mPersonLiveData = mPersonDetailsRepository.getPerson();
    }

    public void searchPersonWithId(String id) {
        mPersonDetailsRepository.searchPersonWithId(id);
    }

    public LiveData<Person> getPerson() {
        return mPersonLiveData;
    }
}
