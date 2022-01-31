package com.aman.newsapp.ui.home.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.aman.newsapp.databinding.FragmentHomeBinding
import com.aman.newsapp.ui.home.adapters.HomeViewPagerAdapter
import com.aman.newsapp.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: HomeViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        adapter = HomeViewPagerAdapter(childFragmentManager, lifecycle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpNewsCategories.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.vpNewsCategories) { tab, position ->
            when (position) {
                0 -> tab.text = Constants.TOP_HEADLINES
                1 -> tab.text = Constants.EVERYTHING
                2 -> tab.text = Constants.SOURCES
            }
        }.attach()
    }

}