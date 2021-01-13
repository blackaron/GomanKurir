package com.GOMAN.gomankurir.model;

public class TransaksiModel {
    public String NamaUser, IdPesanan, IdUser, TotalHarga, IdRestoran, NamaRestoran;

    public TransaksiModel(){

    }

    public String getNamaUser() {
        return NamaUser;
    }

    public void setNamaUser(String namaUser) {
        NamaUser = namaUser;
    }

    public String getIdPesanan() {
        return IdPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        IdPesanan = idPesanan;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getTotalHarga() {
        return TotalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        TotalHarga = totalHarga;
    }

    public String getIdRestoran() {
        return IdRestoran;
    }

    public void setIdRestoran(String idRestoran) {
        IdRestoran = idRestoran;
    }

    public String getNamaRestoran() {
        return NamaRestoran;
    }

    public void setNamaRestoran(String namaRestoran) {
        NamaRestoran = namaRestoran;
    }
}
