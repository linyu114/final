package com.example.afinal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface firstDAO {

    @Query("SELECT * FROM contact")
    fun getAll(): List<firstEntity>

    @Insert
    fun insert(contact: firstEntity)

    @Update
    fun update(contact: firstEntity)

    @Delete
    fun delete(contact: firstEntity)

    @Query("SELECT * FROM contact WHERE name LIKE :name LIMIT 1")
    fun findUserByName(name: String): firstEntity?
}
