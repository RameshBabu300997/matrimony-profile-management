package com.map.matrimonytest.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.map.matrimonytest.db.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile_table WHERE id = :id")
    suspend fun getProfileById(id: Int): ProfileEntity?

    @Query("DELETE FROM profile_table WHERE id = :id")
    suspend fun deleteProfileById(id: Int)

    @Query("SELECT * FROM profile_table")
    suspend fun getAllProfiles(): List<ProfileEntity>
}
