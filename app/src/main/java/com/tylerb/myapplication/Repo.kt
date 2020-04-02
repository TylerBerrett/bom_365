package com.tylerb.myapplication

import com.tylerb.myapplication.model.ScriptureResponse
import com.tylerb.myapplication.network.CallBuilder.getScripture

class Repo {
    suspend fun getScripture(month: String, day: String): ScriptureResponse {
        return getScripture().getBook(month, day)
    }
}