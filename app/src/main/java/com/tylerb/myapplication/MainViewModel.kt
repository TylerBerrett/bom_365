package com.tylerb.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerb.myapplication.model.ScriptureResponse
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {
    private val repo: Repo = Repo()

    private val scriptureData = MutableLiveData<ScriptureResponse>()
    val responseData: LiveData<ScriptureResponse> = scriptureData

    private val error = MutableLiveData<String>()
    val responseError: LiveData<String> = error

    fun getScripture(dayOfYear: Int): String{
        viewModelScope.launch {
            val cal = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_YEAR, dayOfYear)
            }
            val month = (cal.get(Calendar.MONTH) + 1).toString()
            val day = cal.get(Calendar.DAY_OF_MONTH).toString()
            try {
                scriptureData.value = repo.getScripture(month, day)
            } catch (e: Exception) {
                error.value = e.toString()
            }
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