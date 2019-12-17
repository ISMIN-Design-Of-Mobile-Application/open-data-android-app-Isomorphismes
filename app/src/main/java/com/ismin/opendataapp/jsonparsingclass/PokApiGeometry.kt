package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokApiGeometry(
    @SerializedName("type")
    private var type: String,

    @SerializedName("coordinates")
    private var coordinates: List<Double>
) : Serializable