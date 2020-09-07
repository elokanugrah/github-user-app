package com.elok.githubuserapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elok.githubuserapp.utilities.DATABASE_NAME

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getItemDao(): ItemDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}