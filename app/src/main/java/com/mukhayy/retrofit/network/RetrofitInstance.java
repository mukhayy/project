package com.mukhayy.retrofit.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static Retrofit retrofit;
    public static String BaseURL = "http://navjacinth9.000webhostapp.com/json/";

    public static Retrofit getRetrofit(){

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
