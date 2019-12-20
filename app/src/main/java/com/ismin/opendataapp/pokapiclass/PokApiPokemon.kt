package com.ismin.opendataapp.pokapiclass

import java.io.Serializable

data class PokApiPokemon(val id: Int, val type: List<String>, val name: Name, val base: Base): Serializable