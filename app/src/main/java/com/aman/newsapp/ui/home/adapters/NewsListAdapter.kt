package com.aman.newsapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aman.newsapp.R
import com.aman.newsapp.databinding.LayoutNewsCardItemBinding
import com.aman.newsapp.models.response.Article
import com.aman.newsapp.utils.ListItemClickListener

class NewsListAdapter(private val listener: ListItemClickListener) :
    RecyclerView.Adapter<NewsListAdapter.Holder>() {
    lateinit var context: Context
    private var newsList: List<Article>? = null

    fun getNewsListData(listData: List<Article>?) {
        newsList = listData
    }

    inner class Holder(private val binding: LayoutNewsCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindList(data: Article?) {
            binding.data = data
            binding.cvItem.setOnClickListener {
                listener.onListItemClicked(data?.url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val itemBinding: LayoutNewsCardItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_news_card_item,
            parent,
            false
        )
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindList(newsList?.get(position))
    }

    override fun getItemCount(): Int {
        return newsList?.count() ?: 0
    }
}