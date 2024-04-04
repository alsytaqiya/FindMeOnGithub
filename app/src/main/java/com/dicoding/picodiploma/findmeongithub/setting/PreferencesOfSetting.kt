package com.dicoding.picodiploma.findmeongithub.setting

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesOfSetting private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_MODE_KEY = booleanPreferencesKey("theme_setting")

    fun getSettingOfTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_MODE_KEY] ?: false
        }
    }

    suspend fun saveSettingOfTheme(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferencesOfSetting? = null

        fun getInstance(dataStore: DataStore<Preferences>): PreferencesOfSetting {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferencesOfSetting(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
