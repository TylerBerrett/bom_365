package com.tylerb.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tylerb.myapplication.model.DbScripture

@Database(entities = [DbScripture::class], version = 1, exportSchema = false)
abstract class ScriptureDatabase : RoomDatabase() {
    abstract fun scriptureDao(): ScriptureDao

    companion object {
        private const val DATABASE_NAME = "scriptures.db"

        /**
         * As we need only one instance of db in our app will use to store
         * This is to avoid memory leaks in android when there exist multiple instances of db
         */
        @Volatile
        private var INSTANCE: ScriptureDatabase? = null

        fun getInstance(context: Context): ScriptureDatabase {

            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScriptureDatabase::class.java,
                        DATABASE_NAME
                    )
//                        .createFromAsset(DATABASE_NAME)
                        .addTypeConverter(ListDbConverter())
                        .build()
                }

                return INSTANCE!!
            }
        }
    }
}