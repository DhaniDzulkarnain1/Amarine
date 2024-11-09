package com.app.amarine.ui.screen.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    val user = MutableStateFlow<FirebaseUser?>(null)

    fun signInGoogle(context: Context) = viewModelScope.launch {
        val credentialManager = CredentialManager.create(context.applicationContext)
        val googleIdOptions = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(context.getString(R.string.web_client_id))
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()
        val result = credentialManager.getCredential(
            context, request
        )
        val credential = result.credential
        val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(credential.data)
        val googleIdToken = googleIdTokenCredential.idToken
        val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        val signInTask = Firebase.auth.signInWithCredential(firebaseCredential).await()
        user.update {
            signInTask.user
        }
    }
}