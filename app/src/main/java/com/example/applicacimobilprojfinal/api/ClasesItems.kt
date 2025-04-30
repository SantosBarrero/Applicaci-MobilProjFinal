package com.example.applicacimobilprojfinal.api

import com.google.gson.annotations.SerializedName

data class Usuari(
    @SerializedName("usuId") var usuId: Int?,
    @SerializedName("nomUsuari") var nomUsuari: String?,
    @SerializedName("correu") var correu: String?,
    @SerializedName("contrasenya") var contrasenya: String?,
    @SerializedName("rol") var rol: String?,
)