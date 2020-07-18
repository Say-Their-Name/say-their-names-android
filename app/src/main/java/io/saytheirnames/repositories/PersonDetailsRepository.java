package io.saytheirnames.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.saytheirnames.models.Person;
import io.saytheirnames.models.PersonData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailsRepository {

    private BackendInterface mBackEndInterface;
    private MutableLiveData<Person> mPersonDataMutableLiveData;

    public PersonDetailsRepository() {
        mBackEndInterface = Utils.getBackendService();
        mPersonDataMutableLiveData = new MutableLiveData<>();
    }

    public void searchPersonWithId(String id) {
        mBackEndInterface.getPeopleById(id).enqueue(new Callback<PersonData>() {
            @Override
            public void onResponse(Call<PersonData> call, Response<PersonData> response) {
                if (response.body() != null)
                    mPersonDataMutableLiveData.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<PersonData> call, Throwable t) {
                //TODO: Handle failure here
            }
        });
    }

    public LiveData<Person> getPerson() {
        return mPersonDataMutableLiveData;
    }

}
