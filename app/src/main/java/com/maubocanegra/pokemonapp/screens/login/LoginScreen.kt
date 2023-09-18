package com.maubocanegra.pokemonapp.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maubocanegra.pokemonapp.R
import com.maubocanegra.pokemonapp.components.EmailInput
import com.maubocanegra.pokemonapp.components.PasswordInput
import com.maubocanegra.pokemonapp.components.backgroundGradient
import com.maubocanegra.pokemonapp.components.loginSignUpButtonColor
import com.maubocanegra.pokemonapp.navigation.PokemonAppScreens

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun LoginScreen(
    navController: NavController = NavController(
        context = LocalContext.current
    ),
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = backgroundGradient()
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(
            painter = painterResource(id = R.drawable.pokemon_logo),
            contentDescription = "",
            modifier = Modifier.height(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(showLoginForm.value) {
            UserForm(
                loading = false,
                isCreateAccount = false,
            ) { email, password ->
                Log.d("UserForm", "login: e[$email] p[$password]")
                // Firebase login
                viewModel.signInWithEmailAndPassword(
                    email = email,
                    password = password
                ) {
                    navController.navigate(PokemonAppScreens.HomeScreen.name)
                }
            }
        } else {
            UserForm(
                loading = false,
                isCreateAccount = true,
            ){ email, password ->
                viewModel.createUserWithEmailAndPassword(
                    email = email,
                    password = password,
                ){
                    navController.navigate(PokemonAppScreens.HomeScreen.name)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            val introText = if(showLoginForm.value)
                "New User?"
            else
                "Already have an account?"
            val buttonText = if(showLoginForm.value)
                "Sign up"
            else
                "Login"
            Text(
                text = introText,
                style = TextStyle(
                    color = Color.White
                ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buttonText,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    color = Color.White
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    showLoginForm.value = !showLoginForm.value
                }
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, password -> },
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isFormValid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .verticalScroll(rememberScrollState())

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if(isCreateAccount)
            Text(
                text = stringResource(id = R.string.account_requirements),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                style = TextStyle(
                    color = Color.White
                ),
                fontWeight = FontWeight.Bold,
            )
        else
            Text(text = "")

        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions{
                passwordFocusRequest.requestFocus()
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(
            modifier = Modifier
                .focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if(!isFormValid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        SubmitButton(
            textId = if(isCreateAccount)
                "Create Account"
            else
                "Login",
            loading = loading,
            validInputs = isFormValid,
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors = loginSignUpButtonColor()
    ) {
        if(loading) 
            CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else
            Text(
                text = textId,
                modifier = Modifier.padding(5.dp)
            )
    }
    
}
