package com.tylerb.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerb.myapplication.model.ScriptureResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class MainViewModel: ViewModel() {
    private val repo: Repo = Repo()

    private val scriptureData = MutableLiveData<ScriptureResponse>()
    val responseData: LiveData<ScriptureResponse> = scriptureData

    private val error = MutableLiveData<String>()
    val responseError: LiveData<String> = error

    private val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun getScripture(dayOfYear: Int): String{
        viewModelScope.launch {
            val cal = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_YEAR, dayOfYear)
            }
            val month = months[cal.get(Calendar.MONTH)]
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