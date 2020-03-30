package com.tylerb.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tylerb.myapplication.model.ScriptureResponse
import kotlinx.coroutines.Dispatchers
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {
    private val repo: Repo = Repo()

    fun getScripture(month: String, day: String): LiveData<ScriptureResponse> {
        return liveData(Dispatchers.IO) {
            emit(repo.getScripture(month, day))
        }
    }

    fun displayDate(): String {
        val local = Locale.getDefault()
        val format = SimpleDateFormat("MMMM d", local)
        val date = Calendar.getInstance().time
        return format.format(date)
    }

}