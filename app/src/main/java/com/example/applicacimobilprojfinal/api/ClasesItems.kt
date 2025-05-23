package com.example.applicacimobilprojfinal.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuari(
    @SerializedName("usuId") var usuId: Int?,
    @SerializedName("nomUsuari") var nomUsuari: String?,
    @SerializedName("correu") var correu: String?,
    @SerializedName("contrasenya") var contrasenya: String?,
    @SerializedName("rol") var rol: String?,
) : Parcelable

data class Comerc(
    @SerializedName("comerçId") val comerçId: Int?,
    @SerializedName("nom") val nom: String?,
    @SerializedName("telefon") val telefon: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("nif") val nif: String?,
    @SerializedName("sucursals") val sucursals: List<Sucursal>?,
    @SerializedName("usuaris") val usuaris: List<Usuari>?
)
@Parcelize
data class Encarrec(
    @SerializedName("encarrecId") val encarrecId: Int?,
    @SerializedName("preuTotal") val preuTotal: Double?,
    @SerializedName("data") val data: String?,
    @SerializedName("pagat") val pagat: Boolean?,
    @SerializedName("estat") val estat: String?,
    @SerializedName("sucursalId") var sucursalId: Int?,
    @SerializedName("usuId") var usuId: Int?,
    @SerializedName("CodiDeBarres") val productes: List<Producte>?
) : Parcelable


@Parcelize
data class Producte(
    @SerializedName("codiDeBarres") val codiDeBarres: String?,
    @SerializedName("nom") val nom: String?,
    @SerializedName("imatge") val imatge: String?,
    @SerializedName("descripcio") val descripcio: String?,
    @SerializedName("preu") val preu: Double?,
    @SerializedName("categoria") val categoria: String?,
    @SerializedName("encarrecs") val encarrecs: List<Encarrec>?,
    @SerializedName("sucursals") val sucursals: List<Sucursal>?,
): Parcelable

@Parcelize
data class ProducteEncarrec(
    @SerializedName("codiDeBarres") val codiDeBarres: String?,
    @SerializedName("encarrecId") val encarrecId: Int?,
    @SerializedName("quantitat") val quantitat: Int
) : Parcelable

data class CoordenadesResposta(
    val lat: String,
    val lon: String,
    val display_name: String
)
@Parcelize
data class Sucursal (
    @SerializedName("sucursalId") var sucursalId: Int?,
    @SerializedName("direccio") var direccio: String?,
    @SerializedName("comerçId") var comerçId: Int?,
): Parcelable


