package com.app.amarine.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("nama") val nama: String,
    @SerializedName("role") val role: String = "Nelayan"
)

data class RegisterResponse(
    val message: String,
    val success: Boolean
)
