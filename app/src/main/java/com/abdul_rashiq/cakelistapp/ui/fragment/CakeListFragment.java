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

    private void initialise() {
        cakesViewModel = new ViewModelProvider(this).get(CakesViewModel.class);

        binding.rvCakesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCakesList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvCakesList.setHasFixedSize(true);

        binding.srlCakesList.setOnRefreshListener(() -> cakesViewModel.callGetCakesListAPI());
    }

    private void observeData() {
        cakesViewModel.getCakesListState.observe(getViewLifecycleOwner(), cakesList -> {
            if (binding.srlCakesList.isRefreshing()) {
                binding.srlCakesList.setRefreshing(false);
            }
            List<Cake> distinctList = cakesViewModel.getDistinctList(cakesList);
            List<Cake> sortedList = cakesViewModel.getSortedList(distinctList);
            binding.rvCakesList.setAdapter(new CakesListRvAdapter(sortedList, this));
        });

        cakesViewModel.isErrorState.observe(getViewLifecycleOwner(), errorMessage -> {
            if (binding.srlCakesList.isRefreshing()) {
                binding.srlCakesList.setRefreshing(false);
            }
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.alert_title_sorry)
                    .setMessage(errorMessage)
                    .setCancelable(false)
                    .setNegativeButton(R.string.alert_option_retry, (dialogInterface, id) -> {
                        binding.srlCakesList.setRefreshing(true);
                        cakesViewModel.callGetCakesListAPI();
                        dialogInterface.dismiss();
                    })
                    .setPositiveButton(R.string.alert_option_exit, (dialogInterface, id) -> {
                        requireActivity().finish();
                    }).create().show();
        });

        binding.srlCakesList.setRefreshing(true);
        cakesViewModel.callGetCakesListAPI();
    }

    @Override
    public void onItemClick(Cake cake) {
        CakesDetailDialogFragment dialogFragment = CakesDetailDialogFragment.newInstance(cake);
        dialogFragment.show(getParentFragmentManager(), CakesDetailDialogFragment.TAG);
    }
}