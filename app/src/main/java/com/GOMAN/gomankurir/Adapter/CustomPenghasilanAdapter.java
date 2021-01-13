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

public class CustomPenghasilanAdapter extends RecyclerView.Adapter<CustomPenghasilanAdapter.HolderItem> {
    Context context;
    List<PenghasilanModel> adapters;

    public CustomPenghasilanAdapter(Context context, List<PenghasilanModel> adapters) {
        this.context = context;
        this.adapters = adapters;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.penghasilan_list,parent, false);
        HolderItem holderItem = new HolderItem(view);

        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holder, int position) {
        PenghasilanModel model = adapters.get(position);

        holder.penghasilan.setText(new StringBuilder("Total pendapatan anda : Rp").append(model.getTotal()));
    }

    @Override
    public int getItemCount() {
        return adapters.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder{
        TextView penghasilan;

        public HolderItem(@NonNull View itemView) {
            super(itemView);
            penghasilan = (TextView) itemView.findViewById(R.id.penghasil1);
        }
    }
}
