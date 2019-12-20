package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PokAppliParameters(
    @SerializedName("dataset")
    var dataset: List<String>,

    @SerializedName("timezone")
    var timezone: String,

    @SerializedName("rows")
    var rows: Int,

    @SerializedName("format")
    var format: String
) : Serializable