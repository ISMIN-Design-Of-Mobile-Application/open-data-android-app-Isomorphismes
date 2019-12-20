package com.ismin.opendataapp.pokapiclass

import java.io.Serializable

data class Base (
    val HP: Int,
    val Attack: Int,
    val Defense: Int,
    val SpAttack: Int,
    val SpDefense: Int,
    val Speed: Int): Serializable