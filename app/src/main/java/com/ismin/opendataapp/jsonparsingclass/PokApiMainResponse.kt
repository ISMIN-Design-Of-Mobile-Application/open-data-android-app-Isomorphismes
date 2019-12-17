package com.ismin.opendataapp.jsonparsingclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokApiMainResponse(
    @SerializedName("nhits")
    var nhits: Int,

    @SerializedName("parameters")
    var parameters: PokApiParameters,

    @SerializedName("records")
    var records: List<PokApiRecord>
) : Serializable