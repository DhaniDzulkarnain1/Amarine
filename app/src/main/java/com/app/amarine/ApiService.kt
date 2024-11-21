package com.app.amarine

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.app.amarine.model.RegisterRequest
import com.app.amarine.model.RegisterResponse

interface ApiService {
    @POST("/akun")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}