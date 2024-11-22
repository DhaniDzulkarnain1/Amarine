package com.app.amarine.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val message: String,
    val success: Boolean
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)