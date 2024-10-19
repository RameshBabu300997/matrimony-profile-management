package com.map.matrimonytest.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.map.matrimonytest.db.dao.ProfileDao
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.mock.ProfileDetails
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileDao: ProfileDao) : ViewModel() {

    var allProfiles = mutableListOf<ProfileEntity>()
    private val _loading = MutableLiveData(true) // Track loading state
    val loading: LiveData<Boolean> get() = _loading
    val _pendingProfiles = MutableLiveData<List<ProfileEntity>>()
    val pendingProfiles: LiveData<List<ProfileEntity>> get() = _pendingProfiles
    val _dailyRecommendations = MutableLiveData<List<ProfileEntity>>()
    val dailyRecommendations: LiveData<List<ProfileEntity>> get() = _dailyRecommendations

    // Function to insert a single profile into the database
    private suspend fun addProfile(profile: ProfileEntity) {
        profileDao.insertProfile(profile)
    }

    // Function to fetch all profiles from the database
    fun getAllProfiles() {
        _loading.value = true
        viewModelScope.launch {
           allProfiles =  profileDao.getAllProfiles().toMutableList()
            if (allProfiles.isNullOrEmpty()) {
                insertProfileDetails()
            }
            else {
                getPendingProfileDetails()
            }
        }
    }

    // Function to insert mock profile data into the database
    private fun insertProfileDetails() {
        _loading.value = true
        ProfileDetails.profileDetails.clear()
        ProfileDetails.getDetails()
        val userDetails = ProfileDetails.profileDetails
        viewModelScope.launch {
            val insertTasks = userDetails.map { profile ->
                async { addProfile(profile) }
            }
            insertTasks.awaitAll()
            getAllProfiles()
        }
    }

    private fun getPendingProfileDetails() {
        _loading.value = false
        _pendingProfiles.value =  allProfiles.
        filter { !it.isDailyRecommendation }
    }

    fun getDailyRecommendations() {
        _dailyRecommendations.value =  allProfiles.
        filter { it.isDailyRecommendation }
    }

    fun removeProfile(profileId : Int) {
        _dailyRecommendations.value = dailyRecommendations.value?.filter {
            it.id != profileId
        }
    }
}
