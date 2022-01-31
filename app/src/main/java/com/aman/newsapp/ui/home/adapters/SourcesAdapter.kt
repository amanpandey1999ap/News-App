package com.aman.newsapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aman.newsapp.R
import com.aman.newsapp.databinding.LayoutNewsCardItemBinding
import com.aman.newsapp.databinding.LayoutSourcesItemBinding
import com.aman.newsapp.models.response.SourceDetail
import com.aman.newsapp.utils.ListItemClickListener

class SourcesAdapter(private val listener: ListItemClickListener) :
    RecyclerView.Adapter<SourcesAdapter.Holder>() {
    lateinit var context: Context
    private var data: List<SourceDetail>? = null

    fun getSourcesData(listData: List<SourceDetail>?) {
        data = listData
    }

    inner class Holder(private val binding: LayoutSourcesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindList(data: SourceDetail?) {
            binding.data = data
            binding.cvItem.setOnClickListener {
                listener.onListItemClicked(data?.url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val itemBinding: LayoutSourcesItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_sources_item,
            parent,
            false
        )
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindList(data?.get(position))
    }

    override fun getItemCount(): Int {
        return data?.count() ?: 0
    }
}