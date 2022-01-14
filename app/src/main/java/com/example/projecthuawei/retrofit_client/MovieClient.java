package com.example.projecthuawei.retrofit_client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {

    public static Retrofit MovieClientInstance = null;
    public static String BASE_URL = "https://api.themoviedb.org";


    public static Retrofit getMovieClientInstance(){

        if(MovieClientInstance == null){
            MovieClientInstance = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return MovieClientInstance;
    }

}
