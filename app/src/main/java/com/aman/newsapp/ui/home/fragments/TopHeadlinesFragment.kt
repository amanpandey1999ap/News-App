package com.aman.newsapp.ui.home.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aman.newsapp.R
import com.aman.newsapp.databinding.FragmentTopHeadlinesBinding
import com.aman.newsapp.networking.Status
import com.aman.newsapp.ui.home.NewsActivity
import com.aman.newsapp.ui.home.adapters.NewsListAdapter
import com.aman.newsapp.ui.home.viewmodels.TopHeadlinesViewModel
import com.aman.newsapp.utils.Constants
import com.aman.newsapp.utils.ListItemClickListener
import com.aman.newsapp.utils.isInternetAvailable
import com.aman.newsapp.utils.openCustomTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopHeadlinesFragment : Fragment(), ListItemClickListener {
    lateinit var binding: FragmentTopHeadlinesBinding
    private val adapter = NewsListAdapter(this)
    private val topHeadlinesViewModel: TopHeadlinesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentTopHeadlinesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_list -> {
                val linearManager = LinearLayoutManager(requireContext())
                binding.rvTopHeadlines.layoutManager = linearManager
            }
            R.id.menu_grid -> {
                val gridManager = GridLayoutManager(requireContext(), Constants.SPAN_COUNT)
                binding.rvTopHeadlines.layoutManager = gridManager
            }
            R.id.menu_staggered -> {
                val staggeredManager = StaggeredGridLayoutManager(Constants.SPAN_COUNT, RecyclerView.VERTICAL)
                binding.rvTopHeadlines.layoutManager = staggeredManager
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        if (requireContext().isInternetAvailable()) {
            topHeadlinesViewModel.getTopHeadlines("us")
        } else {
            topHeadlinesViewModel.getTopHeadlinesFromLocal()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener {
            if (requireContext().isInternetAvailable()) {
                topHeadlinesViewModel.getTopHeadlines(Constants.DEFAULT_COUNTRY)
            } else {
                topHeadlinesViewModel.getTopHeadlinesFromLocal()
            }
        }

        setupObservers()
        binding.rvTopHeadlines.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTopHeadlines.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        topHeadlinesViewModel.topHeadlinesResponse.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as NewsActivity).hideProgressBar()
                        resource.data?.let { response ->
                            adapter.getNewsListData(response.articles)
                            adapter.notifyDataSetChanged()
                            binding.swipeRefresh.isRefreshing = false
                        }
                    }
                    Status.ERROR -> {
                        (activity as NewsActivity).hideProgressBar()
                        binding.swipeRefresh.isRefreshing = false
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