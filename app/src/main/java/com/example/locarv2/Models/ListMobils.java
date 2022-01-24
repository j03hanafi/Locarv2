package com.example.locarv2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMobils {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama_mobil")
    @Expose
    private String namaMobil;
    @SerializedName("merk_mobil")
    @Expose
    private String merkMobil;
    @SerializedName("deskripsi_mobil")
    @Expose
    private String deskripsiMobil;
    @SerializedName("tahun_mobil")
    @Expose
    private String tahunMobil;
    @SerializedName("kapasitas_mobil")
    @Expose
    private Integer kapasitasMobil;
    @SerializedName("harga_mobil")
    @Expose
    private String hargaMobil;
    @SerializedName("warna_mobil")
    @Expose
    private String warnaMobil;
    @SerializedName("transmisi_mobil")
    @Expose
    private Integer transmisiMobil;
    @SerializedName("plat_no_mobil")
    @Expose
    private String platNoMobil;
    @SerializedName("status_sewa")
    @Expose
    private Object statusSewa;
    @SerializedName("status_mobil")
    @Expose
    private Integer statusMobil;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaMobil() {
        return namaMobil;
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil = namaMobil;
    }

    public String getMerkMobil() {
        return merkMobil;
    }

    public void setMerkMobil(String merkMobil) {
        this.merkMobil = merkMobil;
    }

    public String getDeskripsiMobil() {
        return deskripsiMobil;
    }

    public void setDeskripsiMobil(String deskripsiMobil) {
        this.deskripsiMobil = deskripsiMobil;
    }

    public String getTahunMobil() {
        return tahunMobil;
    }

    public void setTahunMobil(String tahunMobil) {
        this.tahunMobil = tahunMobil;
    }

    public Integer getKapasitasMobil() {
        return kapasitasMobil;
    }

    public void setKapasitasMobil(Integer kapasitasMobil) {
        this.kapasitasMobil = kapasitasMobil;
    }

    public String getHargaMobil() {
        return hargaMobil;
    }

    public void setHargaMobil(String hargaMobil) {
        this.hargaMobil = hargaMobil;
    }

    public String getWarnaMobil() {
        return warnaMobil;
    }

    public void setWarnaMobil(String warnaMobil) {
        this.warnaMobil = warnaMobil;
    }

    public Integer getTransmisiMobil() {
        return transmisiMobil;
    }

    public void setTransmisiMobil(Integer transmisiMobil) {
        this.transmisiMobil = transmisiMobil;
    }

    public String getPlatNoMobil() {
        return platNoMobil;
    }

    public void setPlatNoMobil(String platNoMobil) {
        this.platNoMobil = platNoMobil;
    }

    public Object getStatusSewa() {
        return statusSewa;
    }

    public void setStatusSewa(Object statusSewa) {
        this.statusSewa = statusSewa;
    }

    public Integer getStatusMobil() {
        return statusMobil;
    }

    public void setStatusMobil(Integer statusMobil) {
        this.statusMobil = statusMobil;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
