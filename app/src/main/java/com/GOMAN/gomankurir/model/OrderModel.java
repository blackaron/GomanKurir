package com.GOMAN.gomankurir.model;

public class OrderModel {

    public String namaDriver, alamat, namaPenerima, harga, tipePembayaran, orderId, coment;

    public static final String mNamaDriver = "namaDriver";
    public static final String mAlamat = "alamat";
    public static final String mNamaPenerima = "namaPenerima";
    public static final String mHarga = "harga";
    public static final String mTipePembayaran = "tipePembayaran";
    public static final String mOrderId = "orderId";
    public static final String mComent = "coment";
    public static final String mNamaRestoran = "namaRestoran";
    public static final String mAlamatRestoran = "alanat";

    public OrderModel(){

    }


    public String getNamaDriver() {
        return namaDriver;
    }

    public void setNamaDriver(String namaDriver) {
        this.namaDriver = namaDriver;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTipePembayaran() {
        return tipePembayaran;
    }

    public void setTipePembayaran(String tipePembayaran) {
        this.tipePembayaran = tipePembayaran;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public static String getmNamaRestoran() {
        return mNamaRestoran;
    }

    public static String getmAlamatRestoran() {
        return mAlamatRestoran;
    }
}
