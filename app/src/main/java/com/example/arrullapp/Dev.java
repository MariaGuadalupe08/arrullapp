package com.example.arrullapp;

import com.google.gson.annotations.SerializedName;

public class Dev {
    private int id;
    @SerializedName("fotodev")
    private String fotodev;
    @SerializedName("nombredev")
    private String nombredev;
    @SerializedName("apellidodev")
    private String apellidodev;
    @SerializedName("numerodev")
    private String numerodev;
    @SerializedName("nocontroldev")
    private String nocontroldev;
    @SerializedName("correoddev")
    private String correoddev;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFotodev() {
        return fotodev;
    }

    public void setFotodev(String fotodev) {
        this.fotodev = fotodev;
    }

    public String getNombredev() {
        return nombredev;
    }

    public void setNombredev(String nombredev) {
        this.nombredev = nombredev;
    }

    public String getApellidodev() {
        return apellidodev;
    }

    public void setApellidodev(String apellidodev) {
        this.apellidodev = apellidodev;
    }

    public String getNumerodev() {
        return numerodev;
    }

    public void setNumerodev(String numerodev) {
        this.numerodev = numerodev;
    }

    public String getNocontroldev() {
        return nocontroldev;
    }

    public void setNocontroldev(String nocontroldev) {
        this.nocontroldev = nocontroldev;
    }

    public String getCorreoddev() {
        return correoddev;
    }

    public void setCorreoddev(String correoddev) {
        this.correoddev = correoddev;
    }
}
