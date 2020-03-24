package com.tylerb.myapplication

import com.tylerb.myapplication.model.ScriptureResponse
import com.tylerb.myapplication.network.CallBuilder.getScripture

class Repo {
    suspend fun getScripture(lang: String, scripture: String): ScriptureResponse {
        return getScripture().getBook(lang, scripture)
    }
}