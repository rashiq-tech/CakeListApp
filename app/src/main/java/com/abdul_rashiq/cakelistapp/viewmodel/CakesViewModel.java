package com.abdul_rashiq.cakelistapp.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdul_rashiq.cakelistapp.model.Cake;
import com.abdul_rashiq.cakelistapp.repository.CakesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

@HiltViewModel
public class CakesViewModel extends ViewModel {

    private final CakesRepository cakesRepository;

    public MutableLiveData<List<Cake>> getCakesListState = new MutableLiveData<>();
    public MutableLiveData<String> isErrorState = new MutableLiveData<>();

    @Inject
    public CakesViewModel(CakesRepository cakesRepository) {
        this.cakesRepository = cakesRepository;
    }

    //function to call cakes list api using retrofit
    //which is written in CakesRepository class
    //we use rx to make this event work in separate thread
    public void callGetCakesListAPI() {
        this.cakesRepository.getCakesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Cake>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Cake> cakes) {
                        //on success result from api call, its posted to getCakesListState
                        //which is observed in parent class
                        getCakesListState.postValue(cakes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //on error result from api call, its posted to isErrorState
                        //which is observed in parent class
                        isErrorState.postValue(e.getMessage());
                    }
                });
    }

    //function to get distinct element in list
    public List<Cake> getDistinctList(List<Cake> cakesList) {
        //HashSet is used here to add only distinct object to list
        //Each distinct item is validated my equals() method defined in model class
        Set<Cake> hashSet = new HashSet<>(cakesList);
        return new ArrayList<>(hashSet);
    }

    //function to sort the list alphabetically
    public List<Cake> getSortedList(List<Cake> cakesList) {
        //using Collection api and Comparator the list is sorted against title
        Collections.sort(cakesList, (p1, p2) -> p1.getTitle().compareToIgnoreCase(p2.getTitle()));
        return cakesList;
    }
}
