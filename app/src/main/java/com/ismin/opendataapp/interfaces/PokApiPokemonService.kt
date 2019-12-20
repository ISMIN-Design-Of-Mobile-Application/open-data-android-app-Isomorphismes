package com.ismin.opendataapp.interfaces

import com.ismin.opendataapp.pokapiclass.PokApiPokemon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

public interface PokApiPokemonService {
    @GET("pokedexes/{id}/pokemons")
    fun getDiscoveredPokemons(@Path("pokedexId") pokedexId: String): Call<List<PokApiPokemon>>

    @POST("pokedexes/{id}/pokemons")
    fun addDiscoveredPokemon(@Path("pokedexId") pokedexId: String, @Body pokemon: PokApiPokemon): Call<PokApiPokemon>

    @GET("pokemoninformation/{name}")
    fun getInformation(@Path("name") name: String): Call<PokApiPokemon>

    @GET("pokemoninformation")
    fun getAllInformation(): Call<List<PokApiPokemon>>
}