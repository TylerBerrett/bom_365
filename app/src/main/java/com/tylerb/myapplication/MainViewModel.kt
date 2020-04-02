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

    fun gospelLibraryUrl(mainTitle: String): Uri{
        val split = mainTitle.split(":")[0].split(" ")
        val chapter = split.last()

        val book = split.first()
        var finalBook = book.toLowerCase(Locale.getDefault())

        if (book.contains("Nephi")){
            val bookNum = book[0]
            finalBook = "$bookNum-ne"
        }
        when (book){
            "Words" -> finalBook = "w-of-m"
            "Helaman" -> finalBook = "hel"
            "Mormon" -> finalBook = "morm"
            "Moroni" -> finalBook = "moro"
        }
        val url = "https://www.churchofjesuschrist.org/study/scriptures/bofm/$finalBook/$chapter"
        return Uri.parse(url)
    }

}