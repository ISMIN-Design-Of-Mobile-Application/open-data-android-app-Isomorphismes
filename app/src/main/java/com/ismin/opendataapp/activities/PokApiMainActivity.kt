package com.ismin.opendataapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismin.opendataapp.R
import com.ismin.opendataapp.pokapifragments.PokedexWorldMapFragment

class PokApiMainActivity : AppCompatActivity() {

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
