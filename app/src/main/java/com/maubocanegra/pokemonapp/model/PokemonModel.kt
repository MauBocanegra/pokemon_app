package com.maubocanegra.pokemonapp.model

import com.maubocanegra.pokemonapp.model.pokemonmodels.Ability
import com.maubocanegra.pokemonapp.model.pokemonmodels.Form
import com.maubocanegra.pokemonapp.model.pokemonmodels.Stat
import com.maubocanegra.pokemonapp.model.pokemonmodels.Type

data class PokemonModel(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Form>,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val name: String,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)