package com.app.amarine.model

import com.google.gson.annotations.SerializedName

data class UserData(
    val id: Int,
    val email: String,
    val nama: String,
    val role: String,
    @SerializedName("nelayan_id") val nelayanId: Int?
)