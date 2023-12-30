package com.tylerb.myapplication

import android.app.Application
import com.tylerb.myapplication.db.ScriptureDatabase

class ScriptureApp : Application() {

    override fun onCreate() {
        super.onCreate()
        db = ScriptureDatabase.getInstance(this)
    }


    companion object {
        lateinit var db: ScriptureDatabase
    }

}