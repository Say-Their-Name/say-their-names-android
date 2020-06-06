package com.blm.saytheirnames.network;

import com.blm.saytheirnames.models.DonationData;
import com.blm.saytheirnames.models.PeopleData;
import com.blm.saytheirnames.models.PersonData;
import com.blm.saytheirnames.models.Petition;
import com.blm.saytheirnames.models.PetitionData;
import com.blm.saytheirnames.models.PetitionsData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendInterface {
    @GET("/api/people")
    Call<PeopleData> getPeople();

    @GET("/api/people/{id}")
    Call<PersonData> getPeopleById(@Path("id") String id);


    @GET("/api/petitions")
    Call<PetitionsData> getPetitions();


    @GET("/api/petitions/{id}")
    Call<PetitionData> getPetitionsById(@Path("id") int id);


    @GET("/api/donations")
    Call<DonationData> getDonations();


    @GET("/api/donations/{id}")
    Call<JsonObject> getDonationsById(@Path("id") int id);


}