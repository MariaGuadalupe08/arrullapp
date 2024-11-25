package com.example.arrullapp;

import com.google.gson.annotations.SerializedName;

public class Bebe {
    private int id;
    @SerializedName("imagenBebe")
    private String imagenBebe;
    @SerializedName("descripcionBebe")
    private String descripcionBebe;
    @SerializedName("imagenFruta")
    private String imagenFruta;
    @SerializedName("descripcionFruta")
    private String descripcionFruta;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagenBebe() {
        return imagenBebe;
    }

    public void setImagenBebe(String imagenBebe) {
        this.imagenBebe = imagenBebe;
    }

    public String getDescripcionBebe() {
        return descripcionBebe;
    }

    public void setDescripcionBebe(String descripcionBebe) {
        this.descripcionBebe = descripcionBebe;
    }

    public String getImagenFruta() {
        return imagenFruta;
    }

    public void setImagenFruta(String imagenFruta) {
        this.imagenFruta = imagenFruta;
    }

    public String getDescripcionFruta() {
        return descripcionFruta;
    }

    public void setDescripcionFruta(String descripcionFruta) {
        this.descripcionFruta = descripcionFruta;
    }
}
