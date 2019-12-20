package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokAppliMainResponse(
    @SerializedName("nhits")
    var nhits: Int,

    @SerializedName("parameters")
    var parameters: PokAppliParameters,

    @SerializedName("records")
    var records: List<PokAppliRecord>
) : Serializable