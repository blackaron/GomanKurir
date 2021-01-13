package com.GOMAN.gomankurir.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainOrderModel {
//    String OrderId, NamaOrder,OrderPrice, OrderComent, TipePembayaran,OrderAddress;
    @SerializedName("orderid")
    @Expose
    private String OrderId;

    @SerializedName("CustomerName")
    @Expose
    private String NamaOrder;

    @SerializedName("orderPrice")
    @Expose
    private String OrderPrice;

    @SerializedName("orderComment")
    @Expose
    private String OrderComent;

    @SerializedName("tipePembayaran")
    @Expose
    private String TipePembayaran;


    @SerializedName("orderAddress")
    @Expose
    private String OrderAddress;

    @SerializedName("namaRestoran")
    @Expose
    private String namaRestoran;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    public MainOrderModel(){

    }

    public MainOrderModel(String orderId, String namaOrder, String orderPrice, String orderComent, String tipePembayaran, String orderAddress, String namaRestoran, String alamat) {
        this.OrderId = orderId;
        this.NamaOrder = namaOrder;
        this.OrderPrice = orderPrice;
        this.OrderComent = orderComent;
        this.TipePembayaran = tipePembayaran;
        this.OrderAddress = orderAddress;
        this.namaRestoran = namaRestoran;
        this.alamat = alamat;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getNamaOrder() {
        return NamaOrder;
    }

    public void setNamaOrder(String namaOrder) {
        NamaOrder = namaOrder;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderComent() {
        return OrderComent;
    }

    public void setOrderComent(String orderComent) {
        OrderComent = orderComent;
    }

    public String getTipePembayaran() {
        return TipePembayaran;
    }

    public void setTipePembayaran(String tipePembayaran) {
        TipePembayaran = tipePembayaran;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public String getNamaRestoran() {
        return namaRestoran;
    }

    public void setNamaRestoran(String namaRestoran) {
        this.namaRestoran = namaRestoran;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
