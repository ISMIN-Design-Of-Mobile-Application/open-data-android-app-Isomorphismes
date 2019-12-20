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
import com.ismin.opendataapp.jsonparsingclass.PokAppliFields
import com.ismin.opendataapp.jsonparsingclass.PokAppliMainResponse
import com.ismin.opendataapp.pokapiclass.PagerAdapter
import com.ismin.opendataapp.pokapiclass.Pokedex
import com.ismin.opendataapp.pokapiclass.Pokemon
import com.ismin.opendataapp.pokapifragments.PokAppliInformationFragment
import com.ismin.opendataapp.pokapifragments.PokAppliListOfPokemonsFragment
import com.ismin.opendataapp.pokapifragments.PokAppliWorldMapFragment
import com.ismin.opendataapp.ressources.PokAppliDatabase
import com.ismin.opendataapp.ressources.SERVER_BASE_URL
import kotlinx.android.synthetic.main.activity_pokappli_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokAppliMainActivity : AppCompatActivity(),
    PokAppliListOfPokemonsFragment.OnFragmentInteractionListener,
    PokAppliWorldMapFragment.OnFragmentInteractionListener,
    PokAppliInformationFragment.OnFragmentInteractionListener {

    private var pokedex: Pokedex = Pokedex()

    private var pokAppliWorldMapFragment: PokAppliWorldMapFragment = PokAppliWorldMapFragment()
    private var pokAppliInformationFragment: PokAppliInformationFragment =
        PokAppliInformationFragment()
    private var pokAppliListOfPokemonsFragment: PokAppliListOfPokemonsFragment =
        PokAppliListOfPokemonsFragment()

    private var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()
    private var pokemonService = retrofit.create<PokemonService>(PokemonService::class.java)

    private lateinit var pokemonDAO: PokemonDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokappli_main)

        pokemonDAO = PokAppliDatabase.getAppDatabase(this).getPokemonDao()
        getAllPokemonsFromAPI()

        val fragmentAdapter = PagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        fragmentAdapter.addFragment(pokAppliListOfPokemonsFragment, "Pokedex")
        fragmentAdapter.addFragment(pokAppliWorldMapFragment, "Map")
        fragmentAdapter.addFragment(pokAppliInformationFragment, "About")
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                //Todo: refresh function
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPokemonClicked(pokemon: Pokemon) {
        val intent = Intent(this, PokemonInformationActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, pokemon.pokemon)
        this.startActivity(intent)
    }

    override fun onFragmentInteraction(uri: Uri) {
    }

    private fun getAllPokemonsFromAPI(): Boolean {
        var success = false
        var pokemon: Pokemon
        pokemonDAO.deleteAllPokemons()
        pokemonService.getAllPokemonsForMaps()
            .enqueue(object : Callback<PokAppliMainResponse> {
                override fun onResponse(
                    call: Call<PokAppliMainResponse>,
                    response: Response<PokAppliMainResponse>
                ) {
                    val allPokemon = response.body()
                    if (allPokemon != null) {
                        val pokapirecords = allPokemon?.records
                        pokapirecords!!.forEach {
                            if (noNullDataInPokemon(it.pokAppliFields)) {
                                pokemon = createPokemonFromField(it.pokAppliFields)
                                pokedex.addPokemonToPokedex(pokemon)
                                pokemonDAO.insertPokemon(pokemon)
                            }
                        }
                        pokAppliListOfPokemonsFragment.updatePokemons()
                        pokAppliWorldMapFragment.updatePokemons()

                        success = true
                        Toast.makeText(
                            this@PokAppliMainActivity,
                            "Pokemons successfully loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@PokAppliMainActivity,
                            "Error: Could not retrieve Pokemons",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PokAppliMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokAppliMainActivity, "Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return success
    }

    private fun getSomePokemonsFromAPI(number: Int): Boolean {
        var success = false
        pokemonService.getSomePokemons(number)
            .enqueue(object : Callback<PokAppliMainResponse> {
                override fun onResponse(
                    call: Call<PokAppliMainResponse>,
                    response: Response<PokAppliMainResponse>
                ) {
                    val allPokemon = response.body()
                    if (allPokemon != null) {
                        success = true
                    } else {
                        Toast.makeText(
                            this@PokAppliMainActivity,
                            "Pokemons not found on API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PokAppliMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokAppliMainActivity, "Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return success
    }

    private fun getNumberOfPokemons(): Boolean {
        var success = false
        pokemonService.getNumberOfPokemons()
            .enqueue(object : Callback<PokAppliMainResponse> {
                override fun onResponse(
                    call: Call<PokAppliMainResponse>,
                    response: Response<PokAppliMainResponse>
                ) {
                    val allPokemon = response.body()
                    if (allPokemon != null) {
                        success = true
                    } else {
                        Toast.makeText(
                            this@PokAppliMainActivity,
                            "Pokemons not found on API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PokAppliMainResponse>, t: Throwable) {
                    Toast.makeText(this@PokAppliMainActivity, "Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return success
    }

    private fun createPokemonFromField(pokAppliFields: PokAppliFields): Pokemon {
        return Pokemon(
            pokAppliFields.pokemon,
            pokAppliFields.lieu,
            pokAppliFields.geolocalisation,
            pokAppliFields.geopoint[0],
            pokAppliFields.geopoint[1]
        )
    }

    private fun noNullDataInPokemon(pokAppliFields: PokAppliFields): Boolean {
        return (pokAppliFields.pokemon != null) && (pokAppliFields.lieu != null) && (pokAppliFields.geolocalisation != null) && (pokAppliFields.geopoint != null)
    }
}
