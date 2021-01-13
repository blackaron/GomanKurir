package com.GOMAN.gomankurir.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GOMAN.gomankurir.R;
import com.GOMAN.gomankurir.model.MainOrderModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainOrderAdapter extends RecyclerView.Adapter<MainOrderAdapter.HolderItem> {

    Context context;
    List<MainOrderModel> list;

    public MainOrderAdapter(Context context, List<MainOrderModel> list) {
        Comparator<MainOrderModel> cmp = Collections.reverseOrder();
        Collections.sort(list,cmp);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_view, parent, false);
        HolderItem holderItem = new HolderItem(view);

        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holder, int position) {
        MainOrderModel model = list.get(position);

        holder.OrderId.setText(model.getOrderId());
        holder.OrderName.setText(new StringBuilder("nama customer : ").append(model.getNamaOrder()));
        holder.OrderPrice.setText(new StringBuilder("Rp ").append(model.getOrderPrice()));
        holder.OrderAddress.setText(new StringBuilder("Alamat : ").append(model.getOrderAddress()));
        holder.OrderComment.setText(new StringBuilder("komen : ").append(model.getOrderComent()));
        holder.tipePembayaran.setText(new StringBuilder("Tipe Pembayaran : ").append(model.getTipePembayaran()));
        holder.namaRestoran.setText(new StringBuilder("Nama Restoran : ").append(model.getNamaRestoran()));
        holder.alamat.setText(new StringBuilder("Alamat : ").append(model.getAlamat()));

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class HolderItem extends RecyclerView.ViewHolder{
        TextView OrderId, OrderName, OrderPrice, OrderAddress, OrderComment, tipePembayaran, namaRestoran, alamat;

        public HolderItem(@NonNull View itemView) {
            super(itemView);

            OrderId = (TextView) itemView.findViewById(R.id.TVorderid);
            OrderName = (TextView) itemView.findViewById(R.id.TVnamaPenerima);
            OrderPrice = (TextView) itemView.findViewById(R.id.TVorderPrice);
            OrderAddress = (TextView) itemView.findViewById(R.id.TVorderAddress);
            OrderComment = (TextView) itemView.findViewById(R.id.TVComment);
            tipePembayaran = (TextView) itemView.findViewById(R.id.TVtipePembayaran);
            namaRestoran = (TextView) itemView.findViewById(R.id.TVNamaRestoran);
            alamat = (TextView) itemView.findViewById(R.id.TVAlamat);
        }
    }
}
