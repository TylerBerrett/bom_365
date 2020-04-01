package com.tylerb.myapplication

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

    fun getScripture(month: String, day: String): String{
        viewModelScope.launch {
            scriptureData.value = repo.getScripture(month, day)
        }
        return displayDate(month.toInt() - 1, day.toInt())
    }

    private fun displayDate(month: Int, day: Int): String {
        val local = Locale.getDefault()
        val format = SimpleDateFormat("MMMM d", local)
        val date = Calendar.getInstance()
        date.set(Calendar.MONTH, month)
        date.set(Calendar.DAY_OF_MONTH, day)
        return format.format(date.time)
    }

}