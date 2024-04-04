package com.dicoding.picodiploma.findmeongithub.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FactoryOfViewModel(private val pref: PreferencesOfSetting) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainOfViewModel::class.java)) {
            return MainOfViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}