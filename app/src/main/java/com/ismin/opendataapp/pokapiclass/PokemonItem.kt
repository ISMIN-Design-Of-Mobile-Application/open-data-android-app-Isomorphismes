package com.ismin.opendataapp.pokapiclass

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class PokemonItem : ClusterItem {
    private val position: LatLng
    private var title: String = ""
    private var snippet: String = ""

    constructor(lat: Double, lng: Double) {
        position = LatLng(lat, lng)
    }

    constructor(lat: Double, lng: Double, title: String, snippet: String) {
        position = LatLng(lat, lng)
        this.title = title
        this.snippet = snippet
    }

    override fun getSnippet(): String {
        return this.snippet
    }

    override fun getTitle(): String {
        return this.title
    }

    override fun getPosition(): LatLng {
        return this.position
    }
}