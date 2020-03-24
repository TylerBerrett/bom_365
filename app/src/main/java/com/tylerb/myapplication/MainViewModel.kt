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
    val repo: Repo = Repo()

    fun getScripture(lang: String, scripture: String): LiveData<ScriptureResponse> {
        return liveData(Dispatchers.IO) {
            emit(repo.getScripture(lang, scripture))
        }
    }

    fun displayDate(): String {
        val local = Locale.getDefault()
        val format = SimpleDateFormat("MMMM d", local)
        val date = Calendar.getInstance().time
        return format.format(date)
    }

    fun getParagraphs(htmlString: String, startVerse: Int, endVerse: Int): ArrayList<String> {
        val doc = Jsoup.parse(htmlString)
        val paragraphs = ArrayList<String>()

        for (i in startVerse..endVerse){
            val paragraph = doc.body().getElementById("p$i")
            val markers = paragraph.getElementsByClass("marker")
            markers.remove()
            paragraphs.add(paragraph.text())
        }

        return paragraphs
    }

}