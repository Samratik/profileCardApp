package com.example.profilecardapp

import android.content.Context
import androidx.room.*
import androidx.room.Room

@Database(entities = [UserProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "profiles.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
