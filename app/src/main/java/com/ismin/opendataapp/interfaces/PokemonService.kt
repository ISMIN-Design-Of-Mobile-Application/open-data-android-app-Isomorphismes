package com.ismin.opendataapp.interfaces

import com.ismin.opendataapp.jsonparsingclass.PokApiMainResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface PokemonService {
    @GET("api/records/1.0/search/?dataset=pokedex_clean%40public&rows=151")
    fun getAllPokemonsForMaps(): Call<PokApiMainResponse>

    @GET("api/records/1.0/search/?dataset=pokedex_clean%40public")
    fun getSomePokemons(@Query("rows") nbRows: Int): Call<PokApiMainResponse> //From start of dataset only

    @GET("api/records/1.0/search/?dataset=pokedex_clean%40public&rows=0")
    fun getNumberOfPokemons(): Call<PokApiMainResponse>
}