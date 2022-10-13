package io.saytheirnames.network;

import io.saytheirnames.models.DonationData;
import io.saytheirnames.models.DonationTypesData;
import io.saytheirnames.models.DonationsData;
import io.saytheirnames.models.PeopleData;
import io.saytheirnames.models.PersonData;
import io.saytheirnames.models.PetitionData;
import io.saytheirnames.models.PetitionsData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackendInterface {
    @GET("/api/people")
    Call<PeopleData> getPeople(@Query("page") Integer page);

    @GET("/api/people")
    Call<PeopleData> searchPeople(@Query("name") String name);

    @GET("/api/people/{id}")
    Call<PersonData> getPeopleById(@Path("id") String id);


    @GET("/api/petitions")
    Call<PetitionsData> getPetitions(@Query("page") Integer page);


    @GET("/api/petitions/{id}")
    Call<PetitionData> getPetitionsById(@Path("id") String identifier);


    @GET("/api/donations")
    Call<DonationsData> getDonations(@Query("page") Integer page);


    @GET("/api/donations/{identifier}")
    Call<DonationData> getDonationsById(@Path("identifier") String identifier);

    @GET("/api/donation-types")
    Call<DonationTypesData> getDonationsTypes();

    @GET("/api/donations")
    Call<DonationsData> getFilteredDonations(@Query("type") String type);
}