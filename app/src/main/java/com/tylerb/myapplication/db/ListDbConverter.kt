package com.tylerb.myapplication.db


import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class ListDbConverter {
    @TypeConverter
    fun stringToList(string: String): List<String> = string.split("/&/")

    @TypeConverter
    fun listToString(list: List<String>): String = list.joinToString(separator = "/&/")
}