package com.tylerb.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.tylerb.myapplication.model.ScriptureResponse
import com.tylerb.myapplication.network.CallBuilder
import com.tylerb.myapplication.util.ScriptureRefrence
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        var ref = ""
        var start = 0
        var end = 0
        ScriptureRefrence(month, day).getScritureRefrence()?.let {
            ref = it[0]
            start = it[1].toInt()
            end = it[2].toInt()

        }

        CallBuilder.getScripture().getBook("eng", "/scriptures/bofm/$ref").enqueue(
            object: Callback<ScriptureResponse>{
                override fun onFailure(call: Call<ScriptureResponse>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(call: Call<ScriptureResponse>, response: Response<ScriptureResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            println(it.meta.title)
                            val htmlTest = it.content.body
                            println(getParagraphs(htmlTest, start, end))


                        }
                    } else println(response)
                }

            }
        )

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
