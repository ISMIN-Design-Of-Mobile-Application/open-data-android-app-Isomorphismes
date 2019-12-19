package com.ismin.opendataapp.pokapifragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R
import com.ismin.opendataapp.interfaces.PokemonDAO
import com.ismin.opendataapp.pokapiclass.Pokedex
import com.ismin.opendataapp.pokapiclass.Pokemon
import com.ismin.opendataapp.pokapiclass.PokemonAdapter
import com.ismin.opendataapp.ressources.PokApiDatabase

class PokedexListOfPokemonsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private var pokedex: Pokedex = Pokedex()
    private lateinit var pokemonDAO: PokemonDAO
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =
            inflater.inflate(R.layout.fragment_pokedex_list_of_pokemons, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        pokemonDAO = PokApiDatabase.getAppDatabase(this.requireContext()).getPokemonDao()
        pokedex.setPokemons(pokemonDAO.getAllLocalPokemons() as ArrayList<Pokemon>)
        pokemonAdapter = PokemonAdapter(pokedex, listener, context)
        recyclerView.adapter = pokemonAdapter
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU")
        println(pokedex.getNumberOfPokemons())
        // Inflate the layout for this fragment
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onPokemonClicked(pokemon: Pokemon)
    }

    fun updatePokemons() {
        pokedex.setPokemons(pokemonDAO.getAllLocalPokemons() as ArrayList<Pokemon>)
        pokemonAdapter.updatePokemons(pokedex.getPokemons())
    }
}
