package com.example.blood_aid.repository

import com.example.blood_aid.model.NewsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewsRepositoryImpl : NewsRepository {

    private val database = FirebaseDatabase.getInstance().reference.child("news")

    override fun addNews(news: NewsModel, callback: (Boolean, String) -> Unit) {
        database.child(news.id.toString()).setValue(news)
            .addOnSuccessListener { callback(true, "News added successfully.") }
            .addOnFailureListener { e -> callback(false, "Failed to add news: ${e.message}") }
    }

    override fun fetchAllNews(callback: (MutableList<NewsModel>, Boolean, String) -> Unit) {
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newsList = mutableListOf<NewsModel>()
                snapshot.children.forEach { newsSnapshot ->
                    val news = newsSnapshot.getValue(NewsModel::class.java)
                    news?.let { newsList.add(it) }
                }
                callback(newsList, true, "Fetched all news successfully.")
            }

            override fun onCancelled(error: DatabaseError) {
                callback(mutableListOf(), false, "Failed to fetch news: ${error.message}")
            }
        })
    }

    override fun deleteOldNews(callback: (Boolean, String) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    callback(false, "News database is empty.")
                } else {
                    val currentTime = System.currentTimeMillis()
                    snapshot.children.forEach { newsSnapshot ->
                        val news = newsSnapshot.getValue(NewsModel::class.java)
                        if (news != null && currentTime - news.timestamp > 2 * 24 * 60 * 60 * 1000) {
                            newsSnapshot.ref.removeValue()
                        }
                    }
                    callback(true, "Old news deleted successfully.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, "Failed to delete old news: ${error.message}")
            }
        })
    }
}
