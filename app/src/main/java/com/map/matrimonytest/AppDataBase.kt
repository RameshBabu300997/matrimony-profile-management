package com.map.matrimonytest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.map.matrimonytest.db.dao.ProfileDao
import com.map.matrimonytest.db.entity.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_detail" // Name of the database
                )
                    .fallbackToDestructiveMigration()  // Add this to automatically delete and recreate the database when the schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
