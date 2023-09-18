package com.maubocanegra.pokemonapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.maubocanegra.pokemonapp.model.UserModel
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val mutableLoading = MutableLiveData(false)
    val loading: LiveData<Boolean> = mutableLoading

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.e("","SIGNED IN: ${task.result}")
                    navigateToHomeScreen()
                } else if(task.exception is FirebaseAuthInvalidCredentialsException) {
                    Log.e("","SignIn ERR: InvalidCredential")
                } else if(task.exception is FirebaseAuthInvalidUserException){
                    Log.e("","SignIn ERR: InvalidUser")
                } else if (task.exception is FirebaseAuthException){
                    Log.e("","SignIn ERR: Firebase Exception")
                }
            }.addOnFailureListener { exception ->
                Log.e("","SignIn FAILURE ERR: ${exception.message}")
            }

        }catch (exception: Exception){
            Log.e("","SignIn GENERIC ERR: ${exception.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit,
    ) {
        if(mutableLoading.value == false){
            mutableLoading.value = true
            auth.createUserWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val displayName = task.result.user?.email?.split('@')?.get(0)
                    createUserInFirestoreDB(displayName)
                    navigateToHomeScreen()
                } else if (task.exception is FirebaseAuthException){
                    Log.e("","SignIn ERR: Firebase Exception")
                }
                mutableLoading.value = false
            }
        }

    }

    private fun createUserInFirestoreDB(displayName: String?) {
        val userId = auth.currentUser?.uid
        val userFirebaseMap = UserModel(
            id = "",
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
        ).toFirebaseMap()
        FirebaseFirestore.getInstance().collection("users")
            .add(userFirebaseMap)
    }
}