package com.example.android.testfileupload.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Triton-PC on 02-11-2016.
 */

public class ApiClient {

    //static final String new_BASE_URL = "http://www.callistaindia.com/demo/bounze/api/";
    //static final String new_BASE_URL = "http://protrology.com/androidfileupload/";
    static final String new_BASE_URL = "http://callistaindia.com/demo/HouseOfTutor/api/";

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(new_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static  <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    private static Retrofit.Builder builderTest =
            new Retrofit.Builder()
                    .baseUrl(new_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
    public static  <S> S createServiceTest(Class<S> serviceClass) {
        Retrofit retrofit = builderTest.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(new_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiInterface getApiService() {
        return getRetroClient().create(ApiInterface.class);
    }

}
