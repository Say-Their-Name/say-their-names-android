package com.blm.saytheirnames.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendInterface {
    @GET("/api/people")
    Call<JsonObject> getPeople();

    @GET("/api/people/{id}")
    Call<JsonObject> getPeopleById(@Path("id") int id);


    @GET("/api/petitions")
    Call<JsonObject> getPetitions();


    @GET("/api/petitions/{id}")
    Call<JsonObject> getPetitionsById(@Path("id") int id);


    @GET("/api/donations")
    Call<JsonObject> getDonations();


    @GET("/api/donations/{id}")
    Call<JsonObject> getDonationsById(@Path("id") int id);


}