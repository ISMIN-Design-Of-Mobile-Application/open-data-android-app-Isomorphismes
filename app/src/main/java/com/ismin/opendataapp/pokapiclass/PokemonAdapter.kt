package com.ismin.opendataapp.pokapiclass

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R
import com.ismin.opendataapp.pokapifragments.PokedexListOfPokemonsFragment

class PokemonAdapter(
    private val pokedex: Pokedex,
    private val fragmentInteractionListener: PokedexListOfPokemonsFragment.OnFragmentInteractionListener?,
    private val context: Context?
) :
    RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.pokemon_row, parent,
            false
        )
        return PokemonViewHolder(row)
    }

    override fun onBindViewHolder(viewholder: PokemonViewHolder, position: Int) {
        val pokemon = this.pokedex.getPokemonFromPokedex(position)

        viewholder.txvName.text = pokemon.pokemon
        viewholder.txvLieu.text = pokemon.lieu
        viewholder.itemView.setOnClickListener {
            fragmentInteractionListener?.onPokemonClicked(pokemon)
        }

    }

    override fun getItemCount(): Int {
        return this.pokedex.getNumberOfPokemons()
    }

    fun updatePokemons(pokemons: ArrayList<Pokemon>) {
        pokedex.setPokemons(pokemons)
        notifyDataSetChanged()
    }
}