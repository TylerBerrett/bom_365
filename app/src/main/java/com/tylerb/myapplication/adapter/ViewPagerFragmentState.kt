package com.tylerb.myapplication.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tylerb.myapplication.MainFragment
import java.util.*

class ViewPagerFragmentState(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MainFragment()
        fragment.arguments = Bundle().apply {
            // have to add one because DAY_OF_YEAR starts dec 31
            putInt("key", position + 1)
        }
        return fragment
    }

}

