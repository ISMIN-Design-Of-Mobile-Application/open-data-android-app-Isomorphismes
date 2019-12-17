package com.ismin.opendataapp.pokapiclass

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R

class PokemonAdaptater(private val pokedex: Pokedex) :
    RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.pokemon_row, parent,
            false
        )
        return PokemonViewHolder(row)
    }

    override fun onBindViewHolder(viewholder: PokemonViewHolder, position: Int) {
        val (image, pokemon, lieu) = this.pokedex.getPokemonFromPokedex(position)

        viewholder.txvName.text = pokemon
        viewholder.txvLieu.text = lieu.toString()
        viewholder.imvImage.setImageResource(image)
    }

    override fun getItemCount(): Int {
        return this.pokedex.getNumberOfPokemons()
    }
}