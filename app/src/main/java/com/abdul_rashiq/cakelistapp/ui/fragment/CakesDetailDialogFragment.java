package com.abdul_rashiq.cakelistapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdul_rashiq.cakelistapp.R;
import com.abdul_rashiq.cakelistapp.databinding.DialogCakesDetailBinding;
import com.abdul_rashiq.cakelistapp.model.Cake;
import com.bumptech.glide.Glide;

public class CakesDetailDialogFragment extends DialogFragment {

    public static String CAKE_INTENT_KEY = "CAKE_INTENT_KEY";
    public static String TAG = "CakesDetailDialogFragment";

    private DialogCakesDetailBinding binding;
    private Cake cake;

    public CakesDetailDialogFragment() {
        // Required empty public constructor
    }

    public static CakesDetailDialogFragment newInstance(Cake cake) {
        CakesDetailDialogFragment fragment = new CakesDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(CAKE_INTENT_KEY, cake);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);

        getExtras();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DialogCakesDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();

    }

    private void getExtras() {
        if (getArguments() != null){
            cake = (Cake) getArguments().getSerializable(CAKE_INTENT_KEY);
        }
    }

    private void initialise() {
        if (cake != null){
            binding.tvCake.setText(cake.getTitle());
            binding.tvCakeDescription.setText(cake.getDescription());

            Glide.with(requireContext())
                    .load(cake.getImageURL())
                    .placeholder(R.drawable.iv_placeholder)
                    .into(binding.ivCake);
        }
    }
}