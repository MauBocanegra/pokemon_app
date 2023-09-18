package com.maubocanegra.pokemonapp.screens.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maubocanegra.pokemonapp.data.Result
import com.maubocanegra.pokemonapp.model.PokemonModel
import com.maubocanegra.pokemonapp.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel(){
    var pokemonDetails: MutableState<Result<PokemonModel, Boolean, Exception>> =
        mutableStateOf(Result(null, true, Exception("")))

    fun searchPokemonDetails(pokemon: String) =
        viewModelScope.launch {
            if(pokemon.isEmpty()){
                return@launch
            }
            pokemonDetails.value.loading = true
            pokemonDetails.value = repository.getPokemon(pokemonId = pokemon)
            if(pokemonDetails.value.data.toString().isNotEmpty()){
                pokemonDetails.value.loading = false
            }
        }
}