package com.ismin.opendataapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.ismin.opendataapp.R
import com.ismin.opendataapp.interfaces.PokemonDAO
import com.ismin.opendataapp.interfaces.PokemonService
import com.ismin.opendataapp.jsonparsingclass.PokApiFields
import com.ismin.opendataapp.jsonparsingclass.PokApiMainResponse
import com.ismin.opendataapp.pokapiclass.PagerAdapter
import com.ismin.opendataapp.pokapiclass.Pokedex
import com.ismin.opendataapp.pokapiclass.Pokemon
import com.ismin.opendataapp.pokapifragments.PokApiInformationFragment
import com.ismin.opendataapp.pokapifragments.PokedexWorldMapFragment
import com.ismin.opendataapp.ressources.PokApiDatabase
import com.ismin.opendataapp.ressources.SERVER_BASE_URL
import kotlinx.android.synthetic.main.activity_pokapi_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.xml.transform.Templates

class PokApiMainActivity : AppCompatActivity() {
    private var pokedex : Pokedex = Pokedex()

    private var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()
    private var pokemonService = retrofit.create<PokemonService>(PokemonService::class.java)

    private lateinit var pokemonDAO: PokemonDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokapi_main)
        pokemonDAO = PokApiDatabase.getAppDatabase(this).getPokemonDao()

        val fragmentAdapter = PagerAdapter(supportFragmentManager)
        fragmentAdapter.addFragments()
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab) {
                viewPager.currentItem = p0.position
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                TODO("not implemented")
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                TODO("not implemented")
            }
        })
        getAllPokemonsFromAPI()
    }

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
                        val pokapirecords = allPokemon?.records
                        pokapirecords!!.forEach {
                            pokemonDAO.insertPokemon(createPokemonItemFromField(it.pokApiFields))
                        }
                        success = true
                        Toast.makeText(this@PokApiMainActivity, "Pokemons successfully loaded", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this@PokApiMainActivity, "Error: Could not retrieve Pokemons", Toast.LENGTH_SHORT).show()
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

    private fun createPokemonItemFromField(pokApiFields: PokApiFields) : Pokemon {
        val pokemon: Pokemon = Pokemon(
            0,
            pokApiFields.pokemon,
            pokApiFields.geolocalisation,
            pokApiFields.geopoint.get(0),
            pokApiFields.geopoint.get(1),
            pokApiFields.lieu
        )
        return pokemon
    }
}
