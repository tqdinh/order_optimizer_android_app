package com.example.myoptimizationbill.presenters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*
import kotlin.collections.ArrayList

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {


    var fragments: ArrayList<Fragment> = ArrayList()

    fun updateFragment( _fragments: ArrayList<Fragment>)
    {
        fragments.clear()
        fragments.addAll(_fragments)
    }


    fun add(index: Int, fragment: Fragment) {
        fragments.add(index, fragment)
        notifyItemChanged(index)
    }

    fun remove(index: Int) {
        fragments.removeAt(index)
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


    fun refreshFragment(
        index: Int,
        fragment: Fragment
    ) {
        fragments[index]=fragment
        notifyItemChanged(index)
    }

}