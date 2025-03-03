package com.example.blood_aid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.NewsModel

class NewsAdapter(private var newsList: MutableList<NewsModel>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.news_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.news_description)

        fun bind(news: NewsModel) {
            titleTextView.text = news.title
            descriptionTextView.text = news.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun updateNews(newNewsList: MutableList<NewsModel>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }
}