package com.dicoding.picodiploma.findmeongithub.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainOfViewModel(private val pref: PreferencesOfSetting) : ViewModel() {
    fun getSettingsOfTheme(): LiveData<Boolean> {
        return pref.getSettingOfTheme().asLiveData()
    }

    fun saveSettingOfTheme(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveSettingOfTheme(isDarkModeActive)
        }
    }
}