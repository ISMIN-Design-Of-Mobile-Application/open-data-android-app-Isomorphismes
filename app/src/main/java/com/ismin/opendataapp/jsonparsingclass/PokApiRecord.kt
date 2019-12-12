package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokApiRecord (
    @SerializedName("datasetid")
    var datasetid: String,

    @SerializedName("recordid")
    var recordid: String,

    @SerializedName("fields")
    var pokApiFields: PokApiFields,

    @SerializedName("geometry")
    var pokApiGeometry: PokApiGeometry,

    @SerializedName("record_timestamp")
    var recordTimestamp: String
) : Serializable