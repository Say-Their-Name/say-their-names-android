package io.saytheirnames.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.saytheirnames.models.Petition;
import io.saytheirnames.repositories.PetitionDetailsRepository;

public class PetitionDetailsViewModel extends AndroidViewModel {
    private PetitionDetailsRepository mPersonDetailsRepository;
    private LiveData<Petition> mPetitionLiveData;

    public PetitionDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        mPersonDetailsRepository = new PetitionDetailsRepository();
        mPetitionLiveData = mPersonDetailsRepository.getPetition();
    }

    public void searchPetitionWithId(String id) {
        mPersonDetailsRepository.searchPetitionWithId(id);
    }

    public LiveData<Petition> getPerson() {
        return mPetitionLiveData;
    }
}
