package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokAppliRecord(
    @SerializedName("datasetid")
    var datasetid: String,

    @SerializedName("recordid")
    var recordid: String,

    @SerializedName("fields")
    var pokAppliFields: PokAppliFields,

    @SerializedName("geometry")
    var pokAppliGeometry: PokAppliGeometry,

    @SerializedName("record_timestamp")
    var recordTimestamp: String
) : Serializable