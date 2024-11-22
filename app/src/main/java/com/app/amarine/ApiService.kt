package com.app.amarine

import com.app.amarine.model.LoginRequest
import com.app.amarine.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.app.amarine.model.RegisterRequest
import com.app.amarine.model.RegisterResponse

interface ApiService {
    @POST("/akun")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/masuk")  // Assuming this is your login endpoint
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}

