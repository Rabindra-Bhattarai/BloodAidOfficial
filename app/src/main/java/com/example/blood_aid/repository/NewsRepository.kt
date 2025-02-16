package com.example.blood_aid.repository

import com.example.blood_aid.model.NewsModel

interface NewsRepository {
    fun addNews(news: NewsModel, callback: (Boolean, String) -> Unit)
    fun fetchAllNews(callback: (MutableList<NewsModel>, Boolean, String) -> Unit)
    fun deleteOldNews(callback: (Boolean, String) -> Unit)
}
