package com.example.lenovo.emptypro.ApiCallClasses;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lenovo.emptypro.Utils.GlobalData.BASE_URL;

/**
 * Created by Aakash on 06/02/2018.
 */

public class MainServiceGenerator {

    private static MainServices gitHubService;

    private MainServiceGenerator() {
    }



    public static final  OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getAppClient() {
        if (retrofit == null) {


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static MainServices mainServices(String baseUrl) {

        if (gitHubService == null) {


            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                    new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("Retrofit Network", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .connectTimeout(5, TimeUnit.MINUTES).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            gitHubService = retrofit.create(MainServices.class);
        }
        return gitHubService;


    }

}
