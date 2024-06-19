package com.example.afinal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [firstEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `new_contact` " +
                            "(`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`Name` TEXT, " +
                            "`Phone_Number` TEXT, " +
                            "`Email` TEXT, " +
                            "`Photo_Path` TEXT)"
                )

                database.execSQL(
                    "INSERT INTO new_contact (uid, Name, Phone_Number, Email) " +
                            "SELECT uid, Name, Phone_Number, Email FROM contact"
                )

                database.execSQL("DROP TABLE contact")

                database.execSQL("ALTER TABLE new_contact RENAME TO contact")
            }
        }
    }
}
