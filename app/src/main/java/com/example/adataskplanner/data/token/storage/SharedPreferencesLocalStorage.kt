package org.adaschool.rest.storage

import android.content.SharedPreferences
import org.adaschool.rest.utils.TOKEN_KEY
import javax.inject.Inject

class SharedPreferencesLocalStorage(private val sharedPreferences: SharedPreferences) :
    TokenStorage {
    override fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, "")
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}