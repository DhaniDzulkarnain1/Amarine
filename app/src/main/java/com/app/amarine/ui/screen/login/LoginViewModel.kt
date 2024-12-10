package com.app.amarine.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.ApiService
import com.app.amarine.SessionManager
import com.app.amarine.UserPreferences
import com.app.amarine.model.LoginRequest
import com.app.amarine.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModelV2 @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                Log.d("DEBUG_LOGIN", "Attempting login with email: $email")

                val response = apiService.login(LoginRequest(email, password))

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    val userData = loginResponse.data

                    Log.d("DEBUG_LOGIN", "Login successful - userId: ${userData.id}, nelayanId: ${userData.nelayanId}")

                    sessionManager.saveUserId(userData.id)
                    sessionManager.saveNelayanId(userData.nelayanId ?: 0)
                    userPreferences.saveUser(
                        id = userData.id,
                        email = userData.email,
                        nama = userData.nama,
                        nelayanId = userData.nelayanId ?: 0
                    )

                    Log.d("DEBUG_LOGIN", "Saved to preferences - userId: ${userPreferences.getUserId()}, nelayanId: ${userPreferences.getNelayanId()}")

                    _loginState.value = LoginState.Success(loginResponse)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DEBUG_LOGIN", "Login failed with error: $errorBody")
                    _loginState.value = LoginState.Error("Login gagal")
                }
            } catch (e: Exception) {
                Log.e("DEBUG_LOGIN", "Login exception: ${e.message}", e)
                _loginState.value = LoginState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}