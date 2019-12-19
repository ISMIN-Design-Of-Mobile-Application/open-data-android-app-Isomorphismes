package com.ismin.opendataapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
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
import com.ismin.opendataapp.pokapifragments.PokedexListOfPokemonsFragment
import com.ismin.opendataapp.pokapifragments.PokedexWorldMapFragment
import com.ismin.opendataapp.ressources.PokApiDatabase
import com.ismin.opendataapp.ressources.SERVER_BASE_URL
import kotlinx.android.synthetic.main.activity_pokapi_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokApiMainActivity : AppCompatActivity(),
    PokedexListOfPokemonsFragment.OnFragmentInteractionListener,
    PokedexWorldMapFragment.OnFragmentInteractionListener,
    PokApiInformationFragment.OnFragmentInteractionListener {
    private var pokedex: Pokedex = Pokedex()

    private var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()
    private var pokemonService = retrofit.create<PokemonService>(PokemonService::class.java)

    private lateinit var pokemonDAO: PokemonDAO
    private lateinit var fragmentAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokapi_main)
        pokemonDAO = PokApiDatabase.getAppDatabase(this).getPokemonDao()
        getAllPokemonsFromAPI()

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        fragmentAdapter = PagerAdapter(supportFragmentManager)
        fragmentAdapter.addFragments()
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab) {
                viewPager.currentItem = p0.position
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }
        })
    }

    // Menu, si besoin
    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.updtate_pokemons -> {
                getAllPokemonsFromAPI()
                (fragmentAdapter.getItem(0) as PokedexListOfPokemonsFragment).updatePokemons()
                true
            }
            // If we got here, the user's action was not recognized.
            else -> super.onOptionsItemSelected(item)
        }
    }
     */
    override fun onPokemonClicked(pokemon: Pokemon) {
        //Fonction pour lancer l'activity d'information
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented")
    }

    private fun getAllPokemonsFromAPI(): Boolean {
        var success = false
        var pokemon: Pokemon
        pokemonDAO.deleteAllPokemons()
        pokemonService.getAllPokemonsForMaps()
            .enqueue(object : Callback<PokApiMainResponse> {
                override fun onResponse(
                    call: Call<PokApiMainResponse>,
                    response: Response<PokApiMainResponse>
                ) {
                    val allPokemon = response.body()
                    if (allPokemon != null) {
                        println("OOOOOOOOOOOOOOOOOOOOOOKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")
                        val pokapirecords = allPokemon?.records
                        pokapirecords!!.forEach {
                            if(noNullDataInPokemon(it.pokApiFields)) {
                                pokemon = createPokemonFromField(it.pokApiFields)
                                pokemonDAO.insertPokemon(pokemon)
                                pokedex.addPokemonToPokedex(pokemon)
                            }
                        }
                        success = true
                        Toast.makeText(
                            this@PokApiMainActivity,
                            "Pokemons successfully loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@PokApiMainActivity,
                            "Error: Could not retrieve Pokemons",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<PokApiMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokApiMainActivity, "Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return success
    }

    private fun getSomePokemonsFromAPI(number: Int): Boolean {
        var success = false
        pokemonService.getSomePokemons(number)
            .enqueue(object : Callback<PokApiMainResponse> {
                override fun onResponse(
                    call: Call<PokApiMainResponse>,
                    response: Response<PokApiMainResponse>
                ) {
                    val allPokemon = response.body()
                    if (allPokemon != null) {
                        success = true
                    } else {
                        Toast.makeText(
                            this@PokApiMainActivity,
                            "Pokemons not found on API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<PokApiMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokApiMainActivity, "Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return success
    }

    private fun getNumberOfPokemons(): Boolean {
        var success = false
        pokemonService.getNumberOfPokemons()
            .enqueue(object : Callback<PokApiMainResponse> {
                override fun onResponse(
                    call: Call<PokApiMainResponse>,
                    response: Response<PokApiMainResponse>
                ) {
                    val allPokemon = response.body()
                    if (allPokemon != null) {
                        success = true
                    } else {
                        Toast.makeText(
                            this@PokApiMainActivity,
                            "Pokemons not found on API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<PokApiMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokApiMainActivity, "Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return success
    }

    private fun createPokemonFromField(pokApiFields: PokApiFields): Pokemon {
        return Pokemon(
            pokApiFields.pokemon,
            pokApiFields.lieu,
            pokApiFields.geolocalisation,
            pokApiFields.geopoint[0],
            pokApiFields.geopoint[1]
        )
    }

    private fun noNullDataInPokemon(pokApiFields: PokApiFields) : Boolean {
        return (pokApiFields.pokemon != null) && (pokApiFields.lieu != null) && (pokApiFields.geolocalisation != null) && (pokApiFields.geopoint != null)
    }
}
