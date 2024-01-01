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
import com.tylerb.myapplication.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var displayDate: String
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val page = arguments?.getInt("key")
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//
//        val verseList = ArrayList<String>()
//
//        binding.rvMain.layoutManager = LinearLayoutManager(context)
//        binding.rvMain.adapter = ScriptureRecycler(verseList)
//
//
//        page?.let {dayOfYear ->
//            displayDate = viewModel.getScripture(dayOfYear)
//        }
//
//
//        viewModel.responseData.observe(viewLifecycleOwner, Observer { response ->
//            verseList.clear()
//            binding.pbMain.visibility = View.GONE
//            binding.tvRefMain.text = response.mainTitle
//            binding.tvDateMain.text = displayDate
//            response.scriptures.forEach {
//                verseList.add(it)
//            }
//            binding.rvMain.adapter?.notifyDataSetChanged()
//
//        })
//
//        viewModel.responseError.observe(viewLifecycleOwner, Observer {
//            binding.pbMain.visibility = View.GONE
//            val snack = Snackbar.make(binding.snackbar, R.string.snack_bar_text, Snackbar.LENGTH_INDEFINITE)
//            snack.setAction(R.string.refresh) {
//                binding.pbMain.visibility = View.VISIBLE
//                page?.let {
//                    displayDate = viewModel.getScripture(it)
//                }
//            }
//            context?.let {
//                snack.setActionTextColor(ContextCompat.getColor(it, R.color.colorAccent))
//                snack.setTextColor(ContextCompat.getColor(it, R.color.design_default_color_surface))
//                snack.setBackgroundTint(ContextCompat.getColor(it, R.color.colorPrimary))
//            }
//            snack.show()
//
//        })
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}