package com.GOMAN.gomankurir.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GOMAN.gomankurir.R;
import com.GOMAN.gomankurir.model.TransaksiModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.HolderItem> {

    Context context;
    List<TransaksiModel> models;

    public TransaksiAdapter(Context context, List<TransaksiModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public TransaksiAdapter.HolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaksi_list, parent,false);
        HolderItem holderItem = new HolderItem(view);

        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.HolderItem holder, int position) {
        TransaksiModel model = models.get(position);

        holder.idPesanan.setText(new StringBuilder("#").append(model.getIdPesanan()));
        holder.NamaUser.setText(new StringBuilder("Nama Customer : ").append(model.getNamaUser()));
        holder.IdUser.setText(new StringBuilder("id User : ").append(model.getIdUser()));
        holder.TotalHarga.setText(new StringBuilder("Total Harga : ").append(model.getTotalHarga()));
        holder.IdRestoran.setText(new StringBuilder("Id Restoran : ").append(model.getIdRestoran()));
        holder.NamaRestoran.setText(new StringBuilder("Nama Restoran : ").append(model.getNamaRestoran()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder {
        TextView idPesanan, NamaUser, IdUser, TotalHarga, IdRestoran, NamaRestoran;

        public HolderItem(@NonNull View itemView) {
            super(itemView);

            idPesanan = (TextView) itemView.findViewById(R.id.idPesanan);
            NamaUser = (TextView) itemView.findViewById(R.id.NamaUser);
            IdUser = (TextView) itemView.findViewById(R.id.idUser);
            TotalHarga = (TextView) itemView.findViewById(R.id.totalHarga);
            IdRestoran = (TextView) itemView.findViewById(R.id.idRestoran);
            NamaRestoran = (TextView) itemView.findViewById(R.id.NamaRestoran);

        }
    }
}
