package com.ismin.opendataapp.activities

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ismin.opendataapp.R
import com.ismin.opendataapp.interfaces.PokemonDAO
import com.ismin.opendataapp.interfaces.PokemonService
import com.ismin.opendataapp.jsonparsingclass.PokApiMainResponse
import com.ismin.opendataapp.pokapiclass.Pokedex
import com.ismin.opendataapp.pokapifragments.PokedexWorldMapFragment
import com.ismin.opendataapp.ressources.PokApiDatabase
import com.ismin.opendataapp.ressources.SERVER_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokApiMainActivity : AppCompatActivity() {
    private val pokedex : Pokedex = Pokedex()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()
    private val pokemonService = retrofit.create<PokemonService>(PokemonService::class.java)

    private lateinit var pokemonDAO: PokemonDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokapi_main)
        pokemonDAO = PokApiDatabase.getAppDatabase(this).getPokemonDao()
        createWorldMapFragment()
    }

    /* TODO: add menu to user interface
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }*/

    private fun createWorldMapFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = PokedexWorldMapFragment()
        fragmentTransaction.replace(R.id.PokApi_Main_Display, fragment)
        fragmentTransaction.commit()
    }

    private fun getAllPokemonsFromAPI() : Boolean {
        var success = false
        pokemonService.getAllPokemonsForMaps()
            .enqueue(object : Callback<PokApiMainResponse> {
                override fun onResponse(
                    call: Call<PokApiMainResponse>,
                    response: Response<PokApiMainResponse>
                ) {
                    val allPokemon = response.body()
                    if(allPokemon != null) {
                        success = true
                    }
                    else {
                        Toast.makeText(this@PokApiMainActivity, "Pokemons not found on API", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<PokApiMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokApiMainActivity, "Request failed", Toast.LENGTH_SHORT).show()
                }
            })
        return success
    }

    private fun getSomePokemonsFromAPI(number: Int) : Boolean {
        var success = false
        pokemonService.getSomePokemons(number)
            .enqueue(object : Callback<PokApiMainResponse> {
                override fun onResponse(
                    call: Call<PokApiMainResponse>,
                    response: Response<PokApiMainResponse>
                ) {
                    val allPokemon = response.body()
                    if(allPokemon != null) {
                        success = true
                    }
                    else {
                        Toast.makeText(this@PokApiMainActivity, "Pokemons not found on API", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<PokApiMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokApiMainActivity, "Request failed", Toast.LENGTH_SHORT).show()
                }
            })
        return success
    }

    private fun getNumberOfPokemons() : Boolean {
        var success = false
        pokemonService.getNumberOfPokemons()
            .enqueue(object : Callback<PokApiMainResponse> {
                override fun onResponse(
                    call: Call<PokApiMainResponse>,
                    response: Response<PokApiMainResponse>
                ) {
                    val allPokemon = response.body()
                    if(allPokemon != null) {
                        success = true
                    }
                    else {
                        Toast.makeText(this@PokApiMainActivity, "Pokemons not found on API", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<PokApiMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokApiMainActivity, "Request failed", Toast.LENGTH_SHORT).show()
                }
            })
        return success
    }
}
