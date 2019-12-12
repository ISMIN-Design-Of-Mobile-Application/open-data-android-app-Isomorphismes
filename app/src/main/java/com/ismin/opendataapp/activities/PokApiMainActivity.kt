package com.ismin.opendataapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismin.opendataapp.R
import com.ismin.opendataapp.pokapiclass.Pokedex
import com.ismin.opendataapp.pokapiclass.Pokemon
import com.ismin.opendataapp.pokapifragments.PokedexWorldMapFragment
import com.ismin.opendataapp.ressources.SERVER_BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class PokApiMainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokapi_main)
        createWorldMapFragment()
    }

    private fun createWorldMapFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = PokedexWorldMapFragment()
        fragmentTransaction.replace(R.id.PokApi_Main_Display, fragment)
        fragmentTransaction.commit()
    }
}
