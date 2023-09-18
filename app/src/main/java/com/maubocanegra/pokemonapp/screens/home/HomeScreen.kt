package com.maubocanegra.pokemonapp.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.maubocanegra.pokemonapp.R
import com.maubocanegra.pokemonapp.components.backgroundGradient
import com.maubocanegra.pokemonapp.components.whiteBoldText
import com.maubocanegra.pokemonapp.navigation.PokemonAppScreens
import com.maubocanegra.pokemonapp.utils.imageSlider
import com.maubocanegra.pokemonapp.utils.pokemonNames
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun HomeScreen(
    navController: NavController = NavController(
        context = LocalContext.current
    )
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = backgroundGradient()
            )
            .padding(24.dp),
        //verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Column(
            //horizontalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = "",
                modifier = Modifier.height(80.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
            ) {
                Column(
                    Modifier.weight(8f)
                ) {
                    whiteBoldText(
                        text = "Cards",
                        size = 12
                    )
                    whiteBoldText(
                        text = "Ultra pokemons",
                        size = 24
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .clickable { signOut(navController) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                            .clickable { signOut(navController) }
                    )
                    whiteBoldText(
                        text = "Sign out?",
                        size = 12,
                        modifier = Modifier.clickable { signOut(navController) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokemonCarouselCards(navController){

            }
        }
    }
}

fun signOut(navController: NavController){
    FirebaseAuth.getInstance().signOut().run {
        navController.navigate(PokemonAppScreens.LoginScreen.name)
    }
}

@ExperimentalFoundationApi
@Composable
fun PokemonCarouselCards(
    navController: NavController,
    onCardClicked: () -> Unit = {},
) {
    val imageSlider = imageSlider()
    val pokemonNames = pokemonNames()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        imageSlider.size
    }

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    HorizontalPager(
        contentPadding = PaddingValues(horizontal = 32.dp),
        pageSpacing = 16.dp,
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
    ) {page ->
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = imageSlider[page],
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onCardClicked()
                        navController.navigate(
                            PokemonAppScreens.DetailScreen.name + "/${pokemonNames.get(page)}"
                        )
                    }
            )
        }
    }

}