package com.app.amarine.model

import com.google.gson.annotations.SerializedName

data class RegisterScreen(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String = "Nelayan"
)

