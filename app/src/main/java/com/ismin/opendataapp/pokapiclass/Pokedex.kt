package com.ismin.opendataapp.pokapiclass

class Pokedex {
    private var pokemons: ArrayList<Pokemon> = arrayListOf()
    private var nbPokemon: Int = 0

    constructor()

    fun addPokemonToPokedex(pokemon: Pokemon) {
        pokemons.add(pokemon)
        nbPokemon++
    }

    fun addPokemonToPokedex(
        image: Int,
        name: String,
        geolocalisation: String,
        long: Double,
        lat: Double,
        lieu: String
    ) {
        pokemons.add(
            Pokemon(
                image,
                name,
                geolocalisation,
                long,
                lat,
                lieu
            )
        )
    }

    fun removePokemonFromPokedex(position: Int) {
        pokemons.removeAt(position)
    }

    fun removeAllPokemonsFromPokedex() {
        this.pokemons.removeAll(pokemons)
    }

    fun getPokemonFromPokedex(name: String): Pokemon {
        val pokemon: Pokemon
        for (pokemon in pokemons) {
            if (pokemon.pokemon == name) {
                return pokemon
            }
        }
        return Pokemon(
            0,
            "not found",
            "not found",
            0.0,
            0.0,
            "not found"
        )
    }

    fun getPokemonFromPokedex(position: Int) : Pokemon {
        return pokemons[position]
    }

    fun getNumberOfPokemons(): Int {
        return this.nbPokemon
    }

    fun getPokemons(): ArrayList<Pokemon> {
        return this.pokemons
    }
}