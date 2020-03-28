package com.tylerb.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tylerb.myapplication.adapter.ScriptureRecycler
import com.tylerb.myapplication.util.ScriptureReference
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val verseBlockList = ArrayList<ArrayList<String>>()
        val verseList = ArrayList<String>()

        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val script = ScriptureReference(month, day).getScriptureReference()



        for (i in script.indices step 3) {
            val ref = script[i]
            val start = script[i + 1].toInt()
            val end = script[i + 2].toInt()

            rv_main.layoutManager = LinearLayoutManager(this)
            rv_main.adapter = ScriptureRecycler(verseList)

            viewModel.getScripture("eng", "/scriptures/bofm/$ref")
                .observe(this, Observer { response ->
                    val title = "${response.meta.title}: $start-$end"
                    val block = viewModel.getParagraphs(response.content.body, start, end)
                    block.add(0, title)
                    verseBlockList.add(block)

                    if (verseBlockList.size == (script.size / 3)){
                        pb_main.visibility = View.GONE
                        val sortedList = verseBlockList.sortedBy { list -> list[0] }
                        var finalTitle = sortedList.first()[0] + " - " + sortedList.last()[0]
                        sortedList.forEach {blockItem ->
                            blockItem.forEach { verse ->
                                verseList.add(verse)
                            }
                        }
                        if (sortedList.size == 1) {
                            finalTitle = sortedList.first()[0]
                            verseList.removeAt(0)
                        }
                        tv_date_main.text = viewModel.displayDate()
                        tv_ref_main.text = finalTitle
                        rv_main.adapter?.notifyDataSetChanged()
                    }
                })
        }
    }
}
