package com.maubocanegra.pokemonapp.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maubocanegra.pokemonapp.R
import com.maubocanegra.pokemonapp.components.backgroundGradient
import com.maubocanegra.pokemonapp.components.whiteBoldText
import com.maubocanegra.pokemonapp.model.PokemonModel
import com.maubocanegra.pokemonapp.utils.imageSlider
import com.maubocanegra.pokemonapp.utils.pokemonNames

@Composable
fun PokemonDetailsScreen(
    navController: NavController = NavController(
        context = LocalContext.current
    ),
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    pokemon: String = "",
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = backgroundGradient()
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.pokemon_logo),
                    contentDescription = "",
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.Center)
                )
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Box {
                    Image(
                        painter = imageSlider()[pokemonNames().indexOf(pokemon)],
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    whiteBoldText(
                        text = pokemon.replaceFirstChar { it.uppercaseChar() },
                        size = 52,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                if(
                    viewModel.pokemonDetails.value.loading != true
                    || viewModel.pokemonDetails.value.data.toString().isEmpty()
                    ){
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                } else  {
                    val modifier = Modifier
                        .fillMaxWidth()
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val pokemonModel = viewModel.pokemonDetails.value.data
                        Text(
                            text = "Types: "+ getTypes(pokemonModel),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Abilities: "+ getAbilities(pokemonModel),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            ),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(
                                        start = 32.dp,
                                        bottom = 16.dp,
                                        end = 32.dp,
                                        top = 16.dp
                                    ),
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.pokebola),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(32.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = getStats(pokemonModel),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
    viewModel.searchPokemonDetails(pokemon)
}

fun getTypes(pokemonModel: PokemonModel?): String =
    pokemonModel?.types?.map { type ->
        type.type.name.replaceFirstChar {
            it.uppercaseChar()
        }
    }?.joinToString().toString()

fun getAbilities(pokemonModel: PokemonModel?): String =
    pokemonModel?.abilities?.map { ability ->
        ability.ability.name.replaceFirstChar {
            it.uppercaseChar()
        }
    }?.joinToString().toString()

fun getStats(pokemonModel: PokemonModel?): String =
    pokemonModel?.stats?.map { stat ->
        "${stat.stat.name.replaceFirstChar {
            it.uppercaseChar()
        }}: ${stat.base_stat}"
    }?.joinToString(separator = "\n").toString()

