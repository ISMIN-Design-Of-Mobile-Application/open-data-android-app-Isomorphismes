package com.ismin.opendataapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.ismin.opendataapp.R
import com.ismin.opendataapp.interfaces.PokApiPokemonService
import com.ismin.opendataapp.pokapiclass.PokApiPokemon
import com.ismin.opendataapp.ressources.POKAPI_URL
import com.ismin.opendataapp.ressources.POKIMAGE_ROOT
import kotlinx.android.synthetic.main.activity_pokemon_information.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokemonInformationActivity : AppCompatActivity() {

    private lateinit var pokemonStats: PokApiPokemon

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(POKAPI_URL)
        .build()

    val pokApiPokemonService = retrofit.create<PokApiPokemonService>(PokApiPokemonService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_information)

        val data = intent.getStringExtra(Intent.EXTRA_TEXT)
        val returnIntent = Intent()

        if(data != null){
            val pokemonImage = findViewById<ImageView>(R.id.pokemonImage)
            val pokemonId = findViewById<TextView>(R.id.pokemonId)
            val pokemonName = findViewById<TextView>(R.id.pokemonName)
            pokemonName.text = data
            val atkValue = findViewById<TextView>(R.id.atkValue)
            val defValue = findViewById<TextView>(R.id.defValue)
            val spAtkValue = findViewById<TextView>(R.id.spAtkValue)
            val spDefValue = findViewById<TextView>(R.id.spDefValue)
            val speedValue = findViewById<TextView>(R.id.speedValue)


            radarChart.refreshDrawableState();
            val xLabels = ArrayList<String>()
            xLabels.clear()

            xLabels.add("HP")
            xLabels.add("ATK")
            xLabels.add("DEF")
            xLabels.add("SPATK")
            xLabels.add("SPDEF")
            xLabels.add("SPD")

            val xAxis = radarChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(xLabels)

            val yAxis = radarChart.yAxis
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 100f
            yAxis.setDrawLabels(false)

            radarChart.legend.isEnabled = false
            radarChart.description.isEnabled = false

            getPokemonInformationRequest(data)


        } else{
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()
        }


    }

    private fun getPokemonInformationRequest(pokeName: String){
        pokApiPokemonService.getInformation(pokeName)
            .enqueue(object : Callback<PokApiPokemon> {
                override fun onResponse(
                    call: Call<PokApiPokemon>,
                    response: Response<PokApiPokemon>
                ) {
                    if (response.body() != null){
                        pokemonStats = response.body()!!
                        fillInfo(pokemonStats)
                        fetchPicture(pokemonStats)
                        getChartData(pokemonStats)

                    } else{
                        Toast.makeText(baseContext,"Can't find pokemon: $pokeName", Toast.LENGTH_LONG).show()
                    }

                }
                override fun onFailure(call: Call<PokApiPokemon>, t: Throwable) {
                    Toast.makeText(baseContext, "Request to PokApi service failed: $t", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun fillInfo(pokemonInformation: PokApiPokemon){
        pokemonId.text = pokemonInformation.id.toString()
        pokemonName.text = "${pokemonName.text} (${pokemonInformation.name.french})"
        hpValue.text = pokemonInformation.base.HP.toString()
        atkValue.text = pokemonInformation.base.Attack.toString()
        defValue.text = pokemonInformation.base.Defense.toString()
        spAtkValue.text = pokemonInformation.base.SpAttack.toString()
        spDefValue.text = pokemonInformation.base.SpDefense.toString()
        speedValue.text = pokemonInformation.base.Speed.toString()
        typeValue.text = pokemonInformation.type.toString()
    }

    private fun fetchPicture(pokemonInformation: PokApiPokemon){
        var formattedId  = pokemonInformation.id.toString()
        while(formattedId.length < 3){
            formattedId = "${0}${formattedId}"
        }
        val pokemonPicUrl = "${POKIMAGE_ROOT}${formattedId}.png"
        Glide.with(this).load(pokemonPicUrl).into(pokemonImage)
    }

    private fun getChartData(pokemonInformation: PokApiPokemon){
        val radarEntries = ArrayList<RadarEntry>()
        radarEntries.clear()
        radarEntries.add(RadarEntry(pokemonInformation.base.HP.toFloat(), 0))
        radarEntries.add(RadarEntry(pokemonInformation.base.Attack.toFloat(), 1))
        radarEntries.add(RadarEntry(pokemonInformation.base.Defense.toFloat(), 2))
        radarEntries.add(RadarEntry(pokemonInformation.base.SpAttack.toFloat(), 3))
        radarEntries.add(RadarEntry(pokemonInformation.base.SpDefense.toFloat(), 4))
        radarEntries.add(RadarEntry(pokemonInformation.base.Speed.toFloat(), 5))

        val radarDataSet = RadarDataSet(radarEntries, "")
        val radarData = RadarData(radarDataSet)


        radarChart.notifyDataSetChanged()
        radarChart.data = radarData
    }

}
