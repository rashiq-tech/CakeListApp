package com.abdul_rashiq.cakelistapp.services;

import com.abdul_rashiq.cakelistapp.model.Cake;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CakesRetrofitService {

    @GET("/t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client")
    Single<List<Cake>> getCakesListAPI();

}
