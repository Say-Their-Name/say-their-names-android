package io.saytheirnames.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.saytheirnames.models.Petition;
import io.saytheirnames.models.PetitionData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetitionDetailsRepository {

    private BackendInterface mBackEndInterface;
    private MutableLiveData<Petition> mPetitionMutableLiveData;

    public PetitionDetailsRepository() {
        mBackEndInterface = Utils.getBackendService();
        mPetitionMutableLiveData = new MutableLiveData<>();
    }

    public void searchPetitionWithId(String id) {
        mBackEndInterface.getPetitionsById(id).enqueue(new Callback<PetitionData>() {
            @Override
            public void onResponse(Call<PetitionData> call, Response<PetitionData> response) {
                if (response.body() != null)
                    mPetitionMutableLiveData.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<PetitionData> call, Throwable t) {
                //TODO: Handle failure here
            }
        });
    }

    public LiveData<Petition> getPetition() {
        return mPetitionMutableLiveData;
    }

}
