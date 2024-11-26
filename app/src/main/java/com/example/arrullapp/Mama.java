package com.example.arrullapp;

import com.google.gson.annotations.SerializedName;

public class Mama {
    private int id;

    @SerializedName("descripcionMama")
    private String descripcionMama;

    @SerializedName("imagenSintoma")
    private String imagenSintoma;

    @SerializedName("descripcionSintoma")
    private String descripcionSintoma;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcionMama() {
        return descripcionMama;
    }

    public void setDescripcionMama(String descripcionMama) {
        this.descripcionMama = descripcionMama;
    }

    public String getImagenSintoma() {
        return imagenSintoma;
    }

    public void setImagenSintoma(String imagenSintoma) {
        this.imagenSintoma = imagenSintoma;
    }

    public String getDescripcionSintoma() {
        return descripcionSintoma;
    }

    public void setDescripcionSintoma(String descripcionSintoma) {
        this.descripcionSintoma = descripcionSintoma;
    }
}
