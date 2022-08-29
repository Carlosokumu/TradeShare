package com.example.smarttrader.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class Apppreference(private val context: Context) {

    companion object {
        private const val USER_PREFERENCES_NAME = "smart-trader"
    }



    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )


    suspend fun setIsLoggedIn(isLogged: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.LOGGED_STATUS] = isLogged
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            val uiMode = preferences[PreferenceKeys.LOGGED_STATUS] ?: false
            uiMode
        }

}

