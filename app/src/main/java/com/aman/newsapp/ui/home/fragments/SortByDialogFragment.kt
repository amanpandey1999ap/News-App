package com.aman.newsapp.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aman.newsapp.R
import com.aman.newsapp.databinding.FragmentSortByDialogBinding
import com.aman.newsapp.utils.Constants
import com.aman.newsapp.utils.SortByDialogListener

class SortByDialogFragment(val listener: SortByDialogListener) : DialogFragment() {
    lateinit var binding: FragmentSortByDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.SortByDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortByDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRelevance.setOnClickListener {
            listener.onSelected(Constants.relevancy)
            dismiss()
        }
        binding.tvNewest.setOnClickListener {
            listener.onSelected(Constants.newest)
            dismiss()
        }
        binding.tvPopularity.setOnClickListener {
            listener.onSelected(Constants.popularity)
            dismiss()
        }
    }

}