package com.tylerb.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.datepicker.MaterialDatePicker
import com.tylerb.myapplication.adapter.ScriptureRecycler
import com.tylerb.myapplication.adapter.ViewPagerFragmentState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        setSupportActionBar(toolbar)

        val pagerAdapter = ViewPagerFragmentState(this)
        view_pager.adapter = pagerAdapter

        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        // have to minus one because DAY_OF_YEAR starts dec 31
        view_pager.setCurrentItem(dayOfYear - 1, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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
                    val zone = TimeZone.getTimeZone("UTC")
                    val calendar = Calendar.getInstance(zone)
                    calendar.time = Date(it)
                    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
                    // have to minus one because DAY_OF_YEAR starts dec 31
                    view_pager.setCurrentItem(dayOfYear - 1, false)
                }
                true

            }
            R.id.library -> {
                val url = viewModel.gospelLibraryUrl(tv_ref_main.text.toString())
                startActivity(Intent(Intent.ACTION_VIEW, url))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
