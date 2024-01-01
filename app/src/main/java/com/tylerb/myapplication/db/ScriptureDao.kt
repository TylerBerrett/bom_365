package com.tylerb.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tylerb.myapplication.model.DbScripture

@Dao
interface ScriptureDao {

    @Query("SELECT * FROM SCRIPTURE WHERE month = :month AND day = :day LIMIT 1")
    suspend fun getScripture(month: String, day: String): DbScripture

    @Query("SELECT * FROM SCRIPTURE")
    suspend fun getScriptures(): List<DbScripture>

    @Insert
    suspend fun insertAll(vararg scriptures: DbScripture)
}