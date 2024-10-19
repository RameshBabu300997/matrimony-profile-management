package com.map.matrimonytest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primary key with auto-increment
    val name: String,
    val age: Int,
    val height: String,
    val profession: String,
    val location: String,
    val imageResId: Int,
    val caste:String,
    val state: String,
    val country: String,
    val imageIds: String,
    val zodiac : String,
    val isDailyRecommendation: Boolean
)
