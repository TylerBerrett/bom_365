package com.tylerb.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tylerb.myapplication.db.ListDbConverter

@Entity(primaryKeys = ["month", "day"], tableName = "Scripture")
@TypeConverters(ListDbConverter::class)
data class DbScripture(
    val month: String,
    val day: Int,
    val title: String,
    val scriptures: List<String>
)