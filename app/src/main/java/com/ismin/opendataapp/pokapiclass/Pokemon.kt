package com.ismin.opendataapp.pokapiclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey(autoGenerate = false)
    var pokemon: String = "",
    var lieu: String = "",
    var geolocalisation: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0
) : Serializable