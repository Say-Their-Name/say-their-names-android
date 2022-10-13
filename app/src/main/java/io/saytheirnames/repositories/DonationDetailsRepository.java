package io.saytheirnames.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.saytheirnames.models.Donation;
import io.saytheirnames.models.DonationData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationDetailsRepository {

    private BackendInterface mBackEndInterface;
    private MutableLiveData<Donation> donation;

    public DonationDetailsRepository() {
        mBackEndInterface = Utils.getBackendService();
        donation = new MutableLiveData<>();
    }

    public void searchDonationWithId(String identifier) {
        mBackEndInterface.getDonationsById(identifier).enqueue(new Callback<DonationData>() {
            @Override
            public void onResponse(Call<DonationData> call, Response<DonationData> response) {
                if (response.body() != null) {
                    donation.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DonationData> call, Throwable t) {
                //TODO: Handle failure here
            }
        });
    }

    public LiveData<Donation> getDonation() {
        return donation;
    }

}
