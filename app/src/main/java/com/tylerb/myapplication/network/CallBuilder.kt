package com.tylerb.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CallBuilder {
    private val base = "https://www.churchofjesuschrist.org/study/api/v3/language-pages/type/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(base)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun getScripture(): GetScripture {
        return retrofit.create(GetScripture::class.java)
    }
}