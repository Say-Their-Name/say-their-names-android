package com.blm.saytheirnames.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public  class Utils {


    static final OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(10, TimeUnit.MINUTES).connectTimeout(10, TimeUnit.MINUTES).build();

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://saytheirnames.dev/api/").addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
    public static BackendInterface getBackendService() {


        BackendInterface service = retrofit.create(BackendInterface.class);
        return service;
    }

}