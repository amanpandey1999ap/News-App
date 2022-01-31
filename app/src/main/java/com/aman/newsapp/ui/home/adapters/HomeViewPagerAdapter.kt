package com.aman.newsapp.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aman.newsapp.ui.home.fragments.EverythingFragment
import com.aman.newsapp.ui.home.fragments.SourcesFragment
import com.aman.newsapp.ui.home.fragments.TopHeadlinesFragment

class HomeViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TopHeadlinesFragment()
            1 -> EverythingFragment()
            2 -> SourcesFragment()
            else -> Fragment()
        }
    }
}