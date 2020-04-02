package com.tylerb.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CallBuilder {
    private val base = "https://bom-365.herokuapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(base)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun getScripture(): GetScripture {
        return retrofit.create(GetScripture::class.java)
    }
}