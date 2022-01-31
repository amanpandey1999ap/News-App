package com.aman.newsapp.ui.home.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.newsapp.databinding.FragmentEverythingBinding
import com.aman.newsapp.networking.Status
import com.aman.newsapp.ui.home.NewsActivity
import com.aman.newsapp.ui.home.adapters.NewsListAdapter
import com.aman.newsapp.ui.home.viewmodels.EverythingViewModel
import com.aman.newsapp.utils.Constants
import com.aman.newsapp.utils.ListItemClickListener
import com.aman.newsapp.utils.SortByDialogListener
import com.aman.newsapp.utils.openCustomTab
import dagger.hilt.android.AndroidEntryPoint
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

@AndroidEntryPoint
class EverythingFragment : Fragment(), SortByDialogListener, ListItemClickListener {
    lateinit var binding: FragmentEverythingBinding
    private val adapter = NewsListAdapter(this)
    private val everythingViewModel: EverythingViewModel by viewModels()
    private var sortBy: String? = Constants.RELEVANCY
    private var searchQuery: String? = ""
    private var from: String? = ""
    private var to: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEverythingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.svEverything.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = query
                callEveryThingAPI()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = newText
                callEveryThingAPI()
                return false
            }
        })

        setupObservers()
        binding.rvEverything.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvEverything.adapter = adapter

        binding.btnFrom.setOnClickListener {
            binding.cvCalendarView.visibility = View.VISIBLE
            getCalenderDate(Constants.from)
        }

        binding.btnTo.setOnClickListener {
            binding.cvCalendarView.visibility = View.VISIBLE
            getCalenderDate(Constants.to)
        }

        binding.tvSortBy.setOnClickListener {
            binding.cvCalendarView.visibility = View.GONE
            val sortByDialogFragment = SortByDialogFragment(this)
            sortByDialogFragment.show(parentFragmentManager, sortByDialogFragment.tag)
        }
    }

    private fun getCalenderDate(scope: Int) {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$year-${month + 1}-$dayOfMonth"
            binding.cvCalendarView.visibility = View.GONE

            if (scope == Constants.from)
                from = date
            else to = date

            callEveryThingAPI()
        }
    }

    private fun callEveryThingAPI() {
        if (searchQuery?.isNotEmpty() == true)
            searchQuery?.let { everythingViewModel.getEverything(it, from, to, sortBy) }
        else
            Toast.makeText(requireContext(), "Enter Query", Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("SetTextI18n")
    override fun onSelected(id: Int) {
        when (id) {
            Constants.relevancy -> {
                binding.tvSortBy.text = "Relevance"
                sortBy = Constants.RELEVANCY
                callEveryThingAPI()
            }
            Constants.newest -> {
                binding.tvSortBy.text = "Newest"
                sortBy = Constants.NEWEST
                callEveryThingAPI()
            }
            Constants.popularity -> {
                binding.tvSortBy.text = "Popularity"
                sortBy = Constants.POPULARITY
                callEveryThingAPI()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        everythingViewModel.everyThingResponse.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as NewsActivity).hideProgressBar()
                        binding.llTrySearching.visibility = View.GONE
                        binding.rvEverything.visibility = View.VISIBLE

                        resource.data?.let { response ->
                            adapter.getNewsListData(response.articles)
                            adapter.notifyDataSetChanged()
                        }
                    }
                    Status.ERROR -> {
                        (activity as NewsActivity).hideProgressBar()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        (activity as NewsActivity).showProgressBar()
                    }
                }
            }
        })
    }

    override fun onListItemClicked(url: String?) {
        url?.let { requireContext().openCustomTab(it) }
    }

}