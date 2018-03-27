package com.example.notes.webservice;

import android.support.annotation.NonNull;

import com.example.notes.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class WebService {

    private static Retrofit retrofit;

    private WebService() {
    }

    /**
     * basic retrofit instance generator
     */
    @NonNull
    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(new OkHttpClient.Builder().build())
                    .build();
        }
        return retrofit;
    }

    public static ApiCallMethods createService() {
        return getRetrofitInstance().create(ApiCallMethods.class);
    }
}
