package com.abdul_rashiq.cakelistapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdul_rashiq.cakelistapp.databinding.FragmentCakeListBinding;
import com.abdul_rashiq.cakelistapp.viewmodel.CakesViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CakeListFragment extends Fragment {

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
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cakesViewModel = new ViewModelProvider(this).get(CakesViewModel.class);

    }
}