package com.tylerb.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tylerb.myapplication.adapter.ScriptureRecycler
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var displayDate: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val page = arguments?.getInt("key")
        println(page)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val verseList = ArrayList<String>()

        rv_main.layoutManager = LinearLayoutManager(context)
        rv_main.adapter = ScriptureRecycler(verseList)


        page?.let {dayOfYear ->
            displayDate = viewModel.getScripture(dayOfYear)
        }


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
}