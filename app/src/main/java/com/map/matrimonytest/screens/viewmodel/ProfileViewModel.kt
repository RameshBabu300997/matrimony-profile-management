package com.map.matrimonytest.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.map.matrimonytest.db.dao.ProfileDao
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.mock.ProfileDetails
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileDao: ProfileDao) : ViewModel() {
    val _allProfiles = MutableLiveData<List<ProfileEntity>>()
    val allProfiles: LiveData<List<ProfileEntity>> get() = _allProfiles

     private fun addProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            profileDao.insertProfile(profile)
        }
    }

    fun deleteProfile(id : Int) {
        viewModelScope.launch {
            profileDao.deleteProfileById(id)
        }
    }

    fun getAllProfiles() {
        viewModelScope.launch {
           _allProfiles.value =  profileDao.getAllProfiles()
            if (allProfiles.value.isNullOrEmpty()) {
                insertProfileDetails()
            }
        }
    }

    private fun insertProfileDetails() {
        ProfileDetails.profileDetails.clear()
        ProfileDetails.getDetails()
        val userDetails = ProfileDetails.profileDetails
        for (i in 0 until userDetails.size) {
            addProfile(userDetails[i])
        }
        getAllProfiles()
    }
}
