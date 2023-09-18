package com.maubocanegra.pokemonapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maubocanegra.pokemonapp.R

@Composable
fun backgroundGradient() = Brush.verticalGradient(
    colors = listOf(
        Color(92, 152, 219),
        Color(14, 67, 126)
    )
)

@Composable
fun loginSignUpButtonColor() = ButtonDefaults.buttonColors(
    containerColor = Color(249,198,35),
    contentColor = Color(14,67,126),
    disabledContainerColor = Color.Gray,
    disabledContentColor = Color.White,
)

@Composable
fun whiteBoldText(text: String, size: Int, modifier: Modifier = Modifier) = Text(
    text = text,
    style = TextStyle(
        color = Color.White
    ),
    fontWeight = FontWeight.Bold,
    fontSize = size.sp,
    modifier = modifier
)