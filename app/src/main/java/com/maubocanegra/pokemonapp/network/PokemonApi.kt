package com.maubocanegra.pokemonapp.network

import com.maubocanegra.pokemonapp.model.PokemonModel
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface PokemonApi {
    @GET("pokemon/{pokemon}")
    suspend fun getPokemonInfo(
        @Path(value = "pokemon") pokemonId: String
    ): PokemonModel?
}