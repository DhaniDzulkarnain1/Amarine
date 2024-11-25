package com.app.amarine

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class UserPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUser(id: Int, email: String, nama: String) {
        prefs.edit().apply {
            putInt("USER_ID", id)
            putString("USER_EMAIL", email)
            putString("USER_NAMA", nama)
            apply()
        }
    }

    fun getUserNama(): String {
        return prefs.getString("USER_NAMA", "Guest") ?: "Guest"
    }

    fun getUserEmail(): String {
        return prefs.getString("USER_EMAIL", "") ?: ""
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }

    companion object {
        @Composable
        fun current(): UserPreferences {
            val context = LocalContext.current
            return remember { UserPreferences(context) }
        }
    }
}