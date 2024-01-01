package com.tylerb.myapplication

import com.tylerb.myapplication.model.DbScripture
import com.tylerb.myapplication.model.ScriptureResponse

class Repo {
    suspend fun getScripture(month: String, day: String): ScriptureResponse {
        val dbScripture = ScriptureApp.db.scriptureDao().getScripture(month, day)
        return ScriptureResponse(dbScripture.title, dbScripture.scriptures)
    }

    suspend fun getScriptures(): List<DbScripture> {
        return ScriptureApp.db.scriptureDao().getScriptures()
    }

}