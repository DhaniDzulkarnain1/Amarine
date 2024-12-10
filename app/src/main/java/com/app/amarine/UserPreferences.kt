package com.app.amarine

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import javax.inject.Inject

class UserPreferences @Inject constructor(context: Context) {
    private val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUser(id: Int, email: String, nama: String, nelayanId: Int) {
        prefs.edit().apply {
            putInt("USER_ID", id)
            putString("USER_EMAIL", email)
            putString("USER_NAMA", nama)
            putInt("NELAYAN_ID", nelayanId)
            apply()
        }
    }

    fun getUserId(): Int {
        return prefs.getInt("USER_ID", 0)
    }

    fun getUserEmail(): String {
        return prefs.getString("USER_EMAIL", "") ?: ""
    }

    fun getUserNama(): String {
        return prefs.getString("USER_NAMA", "Guest") ?: "Guest"
    }

    fun getNelayanId(): Int {
        return prefs.getInt("NELAYAN_ID", 0)
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