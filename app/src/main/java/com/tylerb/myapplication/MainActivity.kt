package com.tylerb.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
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



        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)


        val script = ScriptureReference(month, day).getScriptureReference()

        val ref = script[0]
        val start = script[1].toInt()
        val end = script[2].toInt()


        val verseList = ArrayList<String>()

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = ScriptureRecycler(verseList)

        viewModel.getScripture("eng", "/scriptures/bofm/$ref")
            .observe(this, Observer {
                val title = "${it.meta.title}: $start-$end"
                tv_ref_main.text = title
                tv_date_main.text = viewModel.displayDate()
                pb_main.visibility = View.GONE
                viewModel.getParagraphs(it.content.body, start, end).forEach {
                    verseList.add(it)
                }
                rv_main.adapter?.notifyDataSetChanged()
                if (script.size > 3) {
                    val ref2 = script[3]
                    val start2 = script[4].toInt()
                    val end2 = script[5].toInt()
                    viewModel.getScripture("eng", "/scriptures/bofm/$ref2")
                        .observe(this, Observer {
                            val refTitle = "${it.meta.title}: $start2-$end2"
                            val title2 = "${tv_ref_main.text} - $refTitle"
                            tv_ref_main.text = title2
                            verseList.add(refTitle)
                            viewModel.getParagraphs(it.content.body, start2, end2).forEach {
                                verseList.add(it)
                            }
                            rv_main.adapter?.notifyDataSetChanged()
                        })
                }

            })


    }

}
