package com.app.amarine

import android.content.Context
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val PREF_NAME = "AMarine"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_NELAYAN_ID = "nelayan_id"  // Tambahan key untuk nelayan_id
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUserId(userId: Int) {
        sharedPreferences.edit()
            .putInt(KEY_USER_ID, userId)
            .apply()
    }

    fun saveNelayanId(nelayanId: Int) {  // Fungsi baru untuk menyimpan nelayan_id
        sharedPreferences.edit()
            .putInt(KEY_NELAYAN_ID, nelayanId)
            .apply()
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, 0)
    }

    fun getNelayanId(): Int {  // Fungsi baru untuk mengambil nelayan_id
        return sharedPreferences.getInt(KEY_NELAYAN_ID, 0)
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}