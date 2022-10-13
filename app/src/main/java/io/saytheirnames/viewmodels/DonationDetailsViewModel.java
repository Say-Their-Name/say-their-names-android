package io.saytheirnames.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.saytheirnames.models.Donation;
import io.saytheirnames.repositories.DonationDetailsRepository;

public class DonationDetailsViewModel extends AndroidViewModel {
    private DonationDetailsRepository mDonationDetailsRepository;
    private LiveData<Donation> mDonationLiveData;

    public DonationDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        mDonationDetailsRepository = new DonationDetailsRepository();
        mDonationLiveData = mDonationDetailsRepository.getDonation();
    }

    public void searchDonationWithId(String identifier) {
        mDonationDetailsRepository.searchDonationWithId(identifier);
    }

    public LiveData<Donation> getDonationDetails() {
        return mDonationLiveData;
    }
}
