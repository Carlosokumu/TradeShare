package com.android.swingwizards.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserRepo(private val context: Context) {


    private val Context.dataStore by preferencesDataStore(
        name = "SwingWizards"
    )


    private object PreferenceKeys {
        val isUserLoggedIn = booleanPreferencesKey("is_user_logged_in")
        val accountId = stringPreferencesKey("account_id")
        val username = stringPreferencesKey("username")
        val accessToken = stringPreferencesKey("access_token")

    }


    suspend fun saveUserLoggedInStatus(isUserLoggedIn: Boolean) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.isUserLoggedIn] = isUserLoggedIn
        }
    }


    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.username] = username
        }
    }

    suspend fun saveAccessToken(accessToken : String) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.accessToken] = accessToken
        }
    }


    val getUsername: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[PreferenceKeys.username]
        }


    val getAccessToken: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[PreferenceKeys.accessToken]
        }


    suspend fun saveUserAccountId(accountId: String) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.accountId] = accountId
        }
    }


    val getAccountId: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[PreferenceKeys.accountId]
        }


    val isUserLoggedIn: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[PreferenceKeys.isUserLoggedIn] ?: false
        }


}