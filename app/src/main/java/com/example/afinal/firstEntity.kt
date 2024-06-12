package com.example.afinal
import androidx.room.*
@Entity(tableName = "contact") //資料表名稱=聯絡人
data class firstEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "Name") var name: String?,
    @ColumnInfo(name = "Phone_Number") var phone: String?,
    @ColumnInfo(name = "Email") var email: String?,

)


