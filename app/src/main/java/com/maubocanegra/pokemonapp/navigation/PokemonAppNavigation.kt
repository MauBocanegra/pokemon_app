package com.maubocanegra.pokemonapp.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maubocanegra.pokemonapp.screens.SplashScreen
import com.maubocanegra.pokemonapp.screens.createaccount.CreateAccountScreen
import com.maubocanegra.pokemonapp.screens.details.PokemonDetailsScreen
import com.maubocanegra.pokemonapp.screens.home.HomeScreen
import com.maubocanegra.pokemonapp.screens.login.LoginScreen

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun PokemonAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = PokemonAppScreens.SplashScreen.name){
        composable(PokemonAppScreens.SplashScreen.name){
            SplashScreen(navController)
        }

        composable(PokemonAppScreens.CreateAccountScreen.name){
            CreateAccountScreen(navController)
        }

        composable(PokemonAppScreens.LoginScreen.name){
            LoginScreen(navController)
        }

        composable(PokemonAppScreens.HomeScreen.name){
            HomeScreen(navController)
        }

        val detailScreenName = PokemonAppScreens.DetailScreen.name
        composable(
            route = "$detailScreenName/{pokemon}",
            arguments = listOf(navArgument("pokemon"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("pokemon").let {
                PokemonDetailsScreen(navController, pokemon = it.toString())
            }

        }
    }
}