package com.tylerb.myapplication.network

import com.tylerb.myapplication.model.ScriptureResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetScripture {

    @GET("scripture/{month}/{day}/{lang}")
    suspend fun getBook(
        @Path("month") month: String,
        @Path("day") day: String,
        @Path("lang") lang: String
    ) : ScriptureResponse

}