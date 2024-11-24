package com.app.amarine

import com.app.amarine.model.LoginRequest
import com.app.amarine.model.LoginResponse
import com.app.amarine.model.RegisterRequest
import com.app.amarine.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/akun")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/masuk")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}