package com.maubocanegra.pokemonapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.maubocanegra.pokemonapp.R

@Composable
fun imageSlider() = listOf(
    painterResource(id = R.drawable.charizard),
    painterResource(id = R.drawable.pikachu),
    painterResource(id = R.drawable.squirtle),
    painterResource(id = R.drawable.bulbasaur),
)

@Composable
fun pokemonNames() = listOf(
    "charizard", "pikachu", "squirtle", "bulbasaur"
)