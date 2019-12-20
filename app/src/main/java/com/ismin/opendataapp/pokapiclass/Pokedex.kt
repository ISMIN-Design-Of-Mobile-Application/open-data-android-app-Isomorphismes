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
        name: String,
        geolocalisation: String,
        long: Double,
        lat: Double,
        lieu: String
    ) {
        pokemons.add(
            Pokemon(
                name,
                lieu,
                geolocalisation,
                long,
                lat
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
            "not found",
            "not found",
            "not found",
            0.0,
            0.0
        )
    }

    fun getPokemonFromPokedex(position: Int): Pokemon {
        return pokemons[position]
    }

    fun getNumberOfPokemons(): Int {
        return this.nbPokemon
    }

    fun getPokemons(): ArrayList<Pokemon> {
        return this.pokemons
    }

    fun setPokemons(pokemons: ArrayList<Pokemon>) {
        this.pokemons = pokemons
        this.nbPokemon = pokemons.size
    }
}