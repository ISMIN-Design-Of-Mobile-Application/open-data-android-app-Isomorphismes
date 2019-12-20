package com.ismin.opendataapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ismin.opendataapp.R
import com.ismin.opendataapp.interfaces.PokApiPokemonService
import com.ismin.opendataapp.interfaces.PokemonDAO
import com.ismin.opendataapp.pokapiclass.PokApiPokemon
import com.ismin.opendataapp.ressources.POKAPI_URL
import com.ismin.opendataapp.ressources.POKEDEX_ID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokemonInformationActivity : AppCompatActivity() {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(POKAPI_URL)
        .build()

    val pokApiPokemonService = retrofit.create<PokApiPokemonService>(PokApiPokemonService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_information)
    }

    private fun getPokemonInformationRequest(name: String){
        
    }

}
