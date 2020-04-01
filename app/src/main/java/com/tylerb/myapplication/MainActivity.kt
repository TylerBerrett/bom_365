package com.tylerb.myapplication

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.tylerb.myapplication.adapter.ScriptureRecycler
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var displayDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val verseList = ArrayList<String>()

        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = ScriptureRecycler(verseList)

        displayDate = viewModel.getScripture("$month", "$day")

        viewModel.responseData.observe(this, Observer { response ->
            verseList.clear()
            pb_main.visibility = View.GONE
            tv_ref_main.text = response.main_title
            tv_date_main.text = displayDate
            response.scriptures.forEach {
                verseList.add(it)
            }
            rv_main.adapter?.notifyDataSetChanged()

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.calendar -> {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTheme(R.style.Dialog_Theme)
                    .build()
                picker.show(supportFragmentManager, "tag")
                picker.addOnPositiveButtonClickListener {
                    pb_main.visibility = View.VISIBLE
                    val zone = TimeZone.getTimeZone("UTC")
                    val calendar = Calendar.getInstance(zone)
                    calendar.time = Date(it)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    displayDate = viewModel.getScripture("$month", "$day")
                }
                true

            }
            R.id.library -> {
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
