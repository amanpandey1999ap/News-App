package com.aman.newsapp.ui.home.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.newsapp.R
import com.aman.newsapp.databinding.FragmentSourcesBinding
import com.aman.newsapp.networking.Status
import com.aman.newsapp.ui.home.NewsActivity
import com.aman.newsapp.ui.home.adapters.SourcesAdapter
import com.aman.newsapp.ui.home.viewmodels.SourcesViewModel
import com.aman.newsapp.utils.ListItemClickListener
import com.aman.newsapp.utils.isInternetAvailable
import com.aman.newsapp.utils.openCustomTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SourcesFragment : Fragment(), ListItemClickListener {
    lateinit var binding: FragmentSourcesBinding
    private val adapter = SourcesAdapter(this)
    private val sourcesViewModel : SourcesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSourcesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (requireContext().isInternetAvailable()) {
            sourcesViewModel.getSources()
        } else {
            sourcesViewModel.getSourcesFromLocal()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefresh.setOnRefreshListener {
            if (requireContext().isInternetAvailable()) {
                sourcesViewModel.getSources()
            } else {
                sourcesViewModel.getSourcesFromLocal()
            }
        }

        setupObservers()
        binding.rvSources.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvSources.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        sourcesViewModel.sourcesResponse.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as NewsActivity).hideProgressBar()
                        resource.data?.let { response ->
                            adapter.getSourcesData(response.sources)
                            adapter.notifyDataSetChanged()
                            binding.swipeRefresh.isRefreshing = false
                        }
                    }
                    Status.ERROR -> {
                        (activity as NewsActivity).hideProgressBar()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
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