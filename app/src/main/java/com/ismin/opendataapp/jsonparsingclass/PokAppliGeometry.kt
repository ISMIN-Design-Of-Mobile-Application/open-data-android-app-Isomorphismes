package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokAppliGeometry(
    @SerializedName("type")
    private var type: String,

    @SerializedName("coordinates")
    private var coordinates: List<Double>
) : Serializable