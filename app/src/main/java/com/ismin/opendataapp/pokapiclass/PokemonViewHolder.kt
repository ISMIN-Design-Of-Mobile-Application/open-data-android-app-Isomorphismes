package com.ismin.opendataapp.pokapiclass

import com.ismin.opendataapp.R

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    var txvName: TextView
    var txvLieu: TextView
    var imvImage: ImageView

    init {
        this.txvName = rootView.findViewById(R.id.pokemon_name)
        this.txvLieu = rootView.findViewById(R.id.pokemon_lieu)
        this.imvImage = rootView.findViewById(R.id.pokemon_image)

    }
}