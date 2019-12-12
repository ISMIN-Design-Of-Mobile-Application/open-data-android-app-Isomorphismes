package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokApiFields (
    @SerializedName("geolocalisation")
    var geolocalisation: String,

    @SerializedName("geopoint")
    var geopoint: List<Double>,

    @SerializedName("pokemon")
    var pokemon: String,

    @SerializedName("lieu")
    var lieu: String
) : Serializable