package com.maubocanegra.pokemonapp.data

data class Result <T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var exception: E? = null
)