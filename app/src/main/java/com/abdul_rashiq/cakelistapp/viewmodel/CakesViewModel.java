package com.abdul_rashiq.cakelistapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;

import androidx.lifecycle.ViewModel;

import com.abdul_rashiq.cakelistapp.model.Cake;
import com.abdul_rashiq.cakelistapp.repository.CakesRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class CakesViewModel extends ViewModel {

    private final CakesRepository cakesRepository;

    public MutableLiveData<List<Cake>> getCakesListState = new MutableLiveData<>();

    @Inject
    public CakesViewModel(CakesRepository cakesRepository) {
        this.cakesRepository = cakesRepository;
    }

    public LiveData<List<Cake>> getCakesListAPI(){
        return LiveDataReactiveStreams.fromPublisher(callGetCakesListAPI());
    }

    private Flowable<List<Cake>> callGetCakesListAPI() {
        return this.cakesRepository.getCakesList()
                .toFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cakes -> {
                    getCakesListState.postValue(cakes);
                })
                .doOnError(throwable -> {
                    getCakesListState.postValue(new ArrayList<>());
                });
    }

}
