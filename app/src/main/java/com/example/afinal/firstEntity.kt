package com.example.afinal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class firstEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "Name") var name: String?,
    @ColumnInfo(name = "Phone_Number") var phone: String?,
    @ColumnInfo(name = "Email") var email: String?,
    @ColumnInfo(name = "Photo_Path") var photoPath: String?
)
