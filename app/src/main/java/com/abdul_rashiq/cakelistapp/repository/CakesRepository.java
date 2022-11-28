package com.abdul_rashiq.cakelistapp.repository;

import com.abdul_rashiq.cakelistapp.model.Cake;
import com.abdul_rashiq.cakelistapp.services.CakesRetrofitService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CakesRepository {

    private final CakesRetrofitService cakesRetrofitService;

    @Inject
    public CakesRepository(CakesRetrofitService cakesRetrofitService) {
        this.cakesRetrofitService = cakesRetrofitService;
    }

    public Single<List<Cake>> getCakesList(){
        return cakesRetrofitService.getCakesListAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
