package com.example.afinal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
/*
@I新增(Insert，稱作Create)
@Q取得(Select，稱作Retrieve，或Query)->取得資料又可以再細分用唯一碼(pkey)取得一筆資料，或透過條件取得資料，或是取得全部的資料
@U更新(Update)
@D刪除(Delete)
以上四個常被簡稱為CRUD
*/
@Dao
interface firstDAO {
    @Query("SELECT * FROM contact")//取得
    fun getAll(): List<firstEntity>
    @Insert
    fun insert(contact:firstEntity)
    @Update
    fun update(contact:firstEntity)
    @Delete
    fun delete(contact:firstEntity)

    @Query("SELECT * FROM contact WHERE Name LIKE :name LIMIT 1")
    fun findUserByName(name: String): List<firstEntity>

}