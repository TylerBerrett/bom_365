package com.tylerb.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tylerb.myapplication.adapter.ScriptureRecycler
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var displayDate: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val page = arguments?.getInt("key")
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

        viewModel.responseError.observe(this, Observer {
            pb_main.visibility = View.GONE
            val snack = Snackbar.make(snackbar, R.string.snack_bar_text, Snackbar.LENGTH_INDEFINITE)
            snack.setAction(R.string.refresh) {
                pb_main.visibility = View.VISIBLE
                page?.let {
                    displayDate = viewModel.getScripture(it)
                }
            }
            context?.let {
                snack.setActionTextColor(ContextCompat.getColor(it, R.color.colorAccent))
                snack.setTextColor(ContextCompat.getColor(it, R.color.design_default_color_surface))
                snack.setBackgroundTint(ContextCompat.getColor(it, R.color.colorPrimary))
            }
            snack.show()

        })

    }
}