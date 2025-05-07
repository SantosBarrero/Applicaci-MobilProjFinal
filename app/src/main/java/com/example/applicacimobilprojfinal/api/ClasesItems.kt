package com.example.applicacimobilprojfinal.api

import com.google.gson.annotations.SerializedName

data class Usuari(
    @SerializedName("usuId") var usuId: Int?,
    @SerializedName("nomUsuari") var nomUsuari: String?,
    @SerializedName("correu") var correu: String?,
    @SerializedName("contrasenya") var contrasenya: String?,
    @SerializedName("rol") var rol: String?,
)

data class Comerc(val nom: String, val carrer: String)


data class CoordenadesResposta(
    val lat: String,
    val lon: String,
    val display_name: String
)

data class Sucursal (
    @SerializedName("sucursalId") var usuId: Int?,
    @SerializedName("direccio") var direccio: String?,
    @SerializedName("comerçId") var comerçId: Int?,
)
