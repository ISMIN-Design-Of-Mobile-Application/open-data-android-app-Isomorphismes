package com.ismin.opendataapp.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ismin.opendataapp.pokapiclass.Pokemon

@Dao
interface PokemonDAO {
    @Query("SELECT * FROM pokemon")
    fun getAllLocalPokemons(): List<Pokemon>

    @Insert
    fun insertPokemon(vararg pokemon: Pokemon)

    @Delete
    fun deletePokemons(pokemon: Pokemon)

    @Query("DELETE FROM pokemon")
    fun deleteAllPokemons()

    @Query("SELECT * FROM pokemon WHERE pokemon LIKE :pokemonName")
    fun findPokemonLocalByName(pokemonName: String): Pokemon
}