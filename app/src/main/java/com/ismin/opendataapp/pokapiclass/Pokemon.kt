package com.ismin.opendataapp.pokapiclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pokemon(
    val image: Int = 0
) : Serializable {
    @PrimaryKey(autoGenerate = false)
    var name: String = ""
}