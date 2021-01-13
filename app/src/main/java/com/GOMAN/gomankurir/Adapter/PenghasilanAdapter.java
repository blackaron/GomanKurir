package com.GOMAN.gomankurir.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GOMAN.gomankurir.R;
import com.GOMAN.gomankurir.model.PenghasilanModel;

import java.util.List;

public class PenghasilanAdapter extends RecyclerView.Adapter<PenghasilanAdapter.HolderItem> {

    Context context;
    List<PenghasilanModel> list;

    public PenghasilanAdapter(Context context, List<PenghasilanModel> model) {
        this.context = context;
        this.list = model;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.penghasilan_list, parent, false);
        HolderItem holderItem = new HolderItem(view);

        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holder, int position) {
        PenghasilanModel params = list.get(position);

        holder.Penghasilan.setText(new StringBuilder("Pendapatan hari ini : Rp").append(params.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder{
        TextView Penghasilan;
        public HolderItem(@NonNull View itemView) {
            super(itemView);
            Penghasilan = (TextView) itemView.findViewById(R.id.penghasil1);
        }
    }
}
