package com.tylerb.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.tylerb.myapplication.adapter.ScriptureRecycler
import com.tylerb.myapplication.model.ScriptureResponse
import com.tylerb.myapplication.network.CallBuilder
import com.tylerb.myapplication.util.ScriptureRefrence
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val local = Locale.getDefault()
        val format = SimpleDateFormat("EEE, MMM d, ''yy", local)
        val date = Calendar.getInstance().time
        tv_date_main.text = format.format(date)

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

        rv_main.layoutManager = LinearLayoutManager(this)


        CallBuilder.getScripture().getBook("eng", "/scriptures/bofm/$ref").enqueue(
            object: Callback<ScriptureResponse>{
                override fun onFailure(call: Call<ScriptureResponse>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(call: Call<ScriptureResponse>, response: Response<ScriptureResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val title = "${it.meta.title}: $start-$end"
                            tv_ref_main.text = title

                            val htmlTest = it.content.body

                            rv_main.adapter = ScriptureRecycler(getParagraphs(htmlTest, start, end))


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
