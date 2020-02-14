package com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses;

import com.example.lenovo.emptypro.ApiCallClasses.MainServices;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lenovo.emptypro.ApiCallClasses.MainServiceGenerator.okHttpClient;
import static com.example.lenovo.emptypro.Utils.GlobalData.BASE_URL;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
    @Provides
    @Singleton
    OkHttpClient provideHttpLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(280, TimeUnit.SECONDS)
                .writeTimeout(280, TimeUnit.SECONDS)
                .readTimeout(280, TimeUnit.SECONDS)
                .build();
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
    @Provides
    @Singleton
    MainServices provideApiService(Retrofit retrofit) {
        return retrofit.create(MainServices.class);
    }
}
