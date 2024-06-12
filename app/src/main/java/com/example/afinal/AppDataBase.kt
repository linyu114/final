package com.example.afinal
import android.content.Context
import androidx.room.*

@Database(entities = [firstEntity::class], version = 1)
abstract class AppDatabase  : RoomDatabase() {
    abstract fun userDao(): firstDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mysqlite.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
