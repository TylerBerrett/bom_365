package com.tylerb.myapplication

import android.net.Uri
import androidx.lifecycle.*
import com.tylerb.myapplication.model.ScriptureResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {
    private val repo: Repo = Repo()

    private val scriptureData = MutableLiveData<ScriptureResponse>()
    val responseData: LiveData<ScriptureResponse> = scriptureData

    fun getScripture(dayOfYear: Int): String{
        viewModelScope.launch {
            val cal = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_YEAR, dayOfYear)
            }
            val month = (cal.get(Calendar.MONTH) + 1).toString()
            println(month)
            val day = cal.get(Calendar.DAY_OF_MONTH).toString()
            scriptureData.value = repo.getScripture(month, day)
        }
        return displayDate(dayOfYear)
    }

    private fun displayDate(dayOfYear: Int): String {
        val local = Locale.getDefault()
        val format = SimpleDateFormat("MMMM d", local)
        val date = Calendar.getInstance()
        date.set(Calendar.DAY_OF_YEAR, dayOfYear)
        return format.format(date.time)
    }

}