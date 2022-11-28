package com.abdul_rashiq.cakelistapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdul_rashiq.cakelistapp.R;
import com.abdul_rashiq.cakelistapp.databinding.RowItemCakesListBinding;
import com.abdul_rashiq.cakelistapp.model.Cake;
import com.bumptech.glide.Glide;

import java.util.List;

public class CakesListRvAdapter extends RecyclerView.Adapter<CakesListRvAdapter.ViewHolder> {

    private final List<Cake> cakesList;
    private final OnItemClickListener onItemClickListener;

    public CakesListRvAdapter(List<Cake> cakesList, OnItemClickListener onItemClickListener) {
        this.cakesList = cakesList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CakesListRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowItemCakesListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CakesListRvAdapter.ViewHolder holder, int position) {

        holder.binding.tvCake.setText(cakesList.get(position).getTitle());

        Glide.with(holder.itemView.getContext())
                .load(cakesList.get(position).getImageURL())
                .placeholder(R.drawable.iv_placeholder)
                .into(holder.binding.ivCake);

        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(cakesList.get(position)));

    }

    @Override
    public int getItemCount() {
        return cakesList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        RowItemCakesListBinding binding;

        public ViewHolder(@NonNull RowItemCakesListBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Cake cake);
    }
}
