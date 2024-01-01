package com.tylerb.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.tylerb.myapplication.adapter.ViewPagerFragmentState
import com.tylerb.myapplication.databinding.ActivityMainBinding
import com.tylerb.myapplication.model.DbScripture
import com.tylerb.myapplication.model.RawDbScripture
import com.tylerb.myapplication.util.gospelLibraryUrl
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = "Daily Book Of Mormon"

        MainScope().launch {
            val rawFile = resources.openRawResource(R.raw.scriptures)
            val bruh = Json.decodeFromStream<List<RawDbScripture>>(rawFile).map { it.toDB() }.toTypedArray()
            ScriptureApp.db.scriptureDao().insertAll(*bruh)
        }



//        val pagerAdapter = ViewPagerFragmentState(this)
//        binding.viewPager.adapter = pagerAdapter
//
//        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
//        // have to minus one because DAY_OF_YEAR starts dec 31
//        binding.viewPager.setCurrentItem(dayOfYear - 1, false)
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
                    binding.viewPager.setCurrentItem(dayOfYear - 1, false)
                }
                true

            }
            R.id.library -> {
                val mainTitle = findViewById<TextView>(R.id.tv_ref_main).text.toString()
                val url = gospelLibraryUrl(mainTitle)
                startActivity(Intent(Intent.ACTION_VIEW, url))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
