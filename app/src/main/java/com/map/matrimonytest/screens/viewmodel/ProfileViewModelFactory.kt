package com.map.matrimonytest.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.map.matrimonytest.db.dao.ProfileDao

class ProfileViewModelFactory(private val profileDao: ProfileDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(profileDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
