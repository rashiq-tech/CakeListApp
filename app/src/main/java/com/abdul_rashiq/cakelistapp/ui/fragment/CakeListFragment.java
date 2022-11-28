package com.abdul_rashiq.cakelistapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdul_rashiq.cakelistapp.R;
import com.abdul_rashiq.cakelistapp.databinding.FragmentCakeListBinding;
import com.abdul_rashiq.cakelistapp.model.Cake;
import com.abdul_rashiq.cakelistapp.ui.adapters.CakesListRvAdapter;
import com.abdul_rashiq.cakelistapp.viewmodel.CakesViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CakeListFragment extends Fragment implements CakesListRvAdapter.OnItemClickListener {

    private FragmentCakeListBinding binding;
    private CakesViewModel cakesViewModel;

    public CakeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCakeListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();
        observeData();

    }

    //function to initialise all objects
    private void initialise() {
        cakesViewModel = new ViewModelProvider(this).get(CakesViewModel.class);

        binding.rvCakesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCakesList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvCakesList.setHasFixedSize(true);

        binding.srlCakesList.setOnRefreshListener(() -> cakesViewModel.callGetCakesListAPI());
    }

    //function to set observe data from viewmodel
    private void observeData() {

        //observing getCakesListState to see any changes to data after api call
        cakesViewModel.getCakesListState.observe(getViewLifecycleOwner(), cakesList -> {
            //disable refreshing if its already in progress
            if (binding.srlCakesList.isRefreshing()) {
                binding.srlCakesList.setRefreshing(false);
            }
            //calling function to get distinct objects in list received from api
            List<Cake> distinctList = cakesViewModel.getDistinctList(cakesList);
            //calling function to get sort objects in list against title received from api
            List<Cake> sortedList = cakesViewModel.getSortedList(distinctList);
            //setting adapter for recycler view
            binding.rvCakesList.setAdapter(new CakesListRvAdapter(sortedList, this));
            //scheduling recycler view the animation for fade-in
            binding.rvCakesList.scheduleLayoutAnimation();
        });

        //observing isErrorState if any error is posted during api call
        cakesViewModel.isErrorState.observe(getViewLifecycleOwner(), errorMessage -> {
            //disable refreshing if its already in progress
            if (binding.srlCakesList.isRefreshing()) {
                binding.srlCakesList.setRefreshing(false);
            }
            //an alert is shown, with message
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.alert_title_sorry)
                    .setMessage(errorMessage)
                    .setCancelable(false)
                    //button to retry the api call
                    .setNegativeButton(R.string.alert_option_retry, (dialogInterface, id) -> {
                        binding.srlCakesList.setRefreshing(true);
                        cakesViewModel.callGetCakesListAPI();
                        dialogInterface.dismiss();
                    })
                    //button to exit the app if error.
                    .setPositiveButton(R.string.alert_option_exit, (dialogInterface, id) -> {
                        requireActivity().finish();
                    }).create().show();
        });

        //setting swipe_refresh_layout refreshing during api call
        binding.srlCakesList.setRefreshing(true);
        //function to call api for cake list
        cakesViewModel.callGetCakesListAPI();
    }

    @Override
    public void onItemClick(Cake cake) {
        //dialog is shown when an item is clicked in list
        //this Cake object against item clicked is passed from adapter class through OnItemClickListener interface
        CakesDetailDialogFragment dialogFragment = CakesDetailDialogFragment.newInstance(cake);
        dialogFragment.show(getParentFragmentManager(), CakesDetailDialogFragment.TAG);
    }
}