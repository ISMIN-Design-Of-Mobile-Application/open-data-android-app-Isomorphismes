package com.ismin.opendataapp.pokapiclass

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ismin.opendataapp.pokapifragments.PokApiInformationFragment
import com.ismin.opendataapp.pokapifragments.PokedexListOfPokemonsFragment
import com.ismin.opendataapp.pokapifragments.PokedexWorldMapFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var pokedexWorldMapFragment: PokedexWorldMapFragment = PokedexWorldMapFragment()
    private var pokApiInformationFragment: PokApiInformationFragment = PokApiInformationFragment()
    private var pokedexListOfPokemonsFragment: PokedexListOfPokemonsFragment =
        PokedexListOfPokemonsFragment()

    private var pokapiFragmentsTitle = ArrayList<String>()
    private var pokapiFragments = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return pokapiFragments.get(position)
    }

    override fun getCount(): Int {
        return pokapiFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pokapiFragmentsTitle.get(position)
    }

    fun addFragments() {
        pokapiFragments.add(pokedexListOfPokemonsFragment)
        pokapiFragmentsTitle.add("Pokedex")

        pokapiFragments.add(pokedexWorldMapFragment)
        pokapiFragmentsTitle.add("Map")

        pokapiFragments.add(pokApiInformationFragment)
        pokapiFragmentsTitle.add("About")
    }
}