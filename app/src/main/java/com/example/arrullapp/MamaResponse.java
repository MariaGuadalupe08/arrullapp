package com.example.arrullapp;

import com.google.gson.annotations.SerializedName;

public class MamaResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcionMama")
    private String descripcionMama;

    @SerializedName("imagenSintoma")
    private String imagenSintoma;

    @SerializedName("descripcionSintoma")
    private String descripcionSintoma;

    public int getId() {
        return id;
    }

    public String getDescripcionMama() {
        return descripcionMama;
    }

    public String getImagenSintoma() {
        return imagenSintoma;
    }

    public String getDescripcionSintoma() {
        return descripcionSintoma;
    }
}
