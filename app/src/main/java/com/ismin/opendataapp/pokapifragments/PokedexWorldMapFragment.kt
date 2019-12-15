package com.ismin.opendataapp.pokapifragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ismin.opendataapp.interfaces.PokemonDAO
import com.ismin.opendataapp.pokapiclass.Pokemon
import com.ismin.opendataapp.ressources.PokApiDatabase
import android.widget.Toast
import com.google.android.gms.maps.model.Marker
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import org.antlr.runtime.misc.IntArray





class PokedexWorldMapFragment : Fragment(), OnMapReadyCallback {
    private var listener: Context? = null
    private lateinit var pokemonDAO: PokemonDAO
    private lateinit var pokemons: List<Pokemon>
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(com.ismin.opendataapp.R.layout.fragment_pokedex_world_map, container, false)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(com.ismin.opendataapp.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        pokemonDAO = PokApiDatabase.getAppDatabase(this.requireContext()).getPokemonDao()
        pokemons = pokemonDAO.getAllLocalPokemons()

        // Inflate the layout for this fragment
        return rootView
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        addPokemonsToMap()
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(47.778970, 7.347283)))
    }

    private fun addPokemonsToMap() {
        var mPokemon: Marker
        pokemons.forEach {
            val pokemonPos = LatLng(it.lat, it.long)
            mPokemon = mMap.addMarker(
                MarkerOptions()
                    .position(pokemonPos)
                    .title(it.pokemon)
            )
            mPokemon.tag = it
        }
    }

    fun onMarkerClick(marker:Marker):Boolean {
        var pokemon = marker.tag as Pokemon?
        if (pokemon != null) {
            Toast.makeText(this.requireContext(), pokemon.pokemon + " living in " + pokemon.lieu, Toast.LENGTH_SHORT).show()
        }
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

}
