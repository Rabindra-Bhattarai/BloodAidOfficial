package com.example.blood_aid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blood_aid.model.NewsModel
import com.example.blood_aid.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository){

    private val newsLiveData = MutableLiveData<MutableList<NewsModel>>()
    private val statusLiveData = MutableLiveData<Pair<Boolean, String>>()

    fun createNews(title: String, content: String) {
        val news = NewsModel(
            id = System.currentTimeMillis(), // Using current time as ID for simplicity
            title = title,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        repository.addNews(news) { success, message ->
            statusLiveData.postValue(Pair(success, message))
            if (success) {
                fetchAllNews()
            }
        }
    }

    fun fetchAllNews() {
        deleteOldNews()
        repository.fetchAllNews { newsList, success, message ->
            newsLiveData.postValue(newsList)
            statusLiveData.postValue(Pair(success, message))
        }
    }

    fun deleteOldNews() {
        repository.deleteOldNews { success, message ->
            statusLiveData.postValue(Pair(success, message))
            if (success) {
                fetchAllNews()
            }
        }
    }

    fun getNewsLiveData(): LiveData<MutableList<NewsModel>> {
        return newsLiveData
    }

    fun getStatusLiveData(): LiveData<Pair<Boolean, String>> {
        return statusLiveData
    }
}
