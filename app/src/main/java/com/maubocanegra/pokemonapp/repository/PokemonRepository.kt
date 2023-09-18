package com.maubocanegra.pokemonapp.repository

import com.maubocanegra.pokemonapp.model.PokemonModel
import com.maubocanegra.pokemonapp.network.PokemonApi
import com.maubocanegra.pokemonapp.data.Result
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokemonApi
) {
    private val result = Result<PokemonModel, Boolean, Exception>()
    suspend fun getPokemon(pokemonId: String): Result<PokemonModel, Boolean, Exception>{
        try{
            result.loading = true
            result.data = api.getPokemonInfo(pokemonId)
            if(result.data != null){
                result.loading = false
            }
        } catch (e: Exception){
            result.exception = e
        }
        return result
    }
}