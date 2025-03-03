package com.example.blood_aid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.adapter.NewsAdapter
import com.example.blood_aid.viewmodel.NewsViewModel
import com.example.blood_aid.databinding.ActivityNewsBinding // Import the generated binding class
import com.example.blood_aid.repository.NewsRepositoryImpl

class NewsActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel= NewsViewModel(NewsRepositoryImpl())
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: ActivityNewsBinding // Declare the binding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        newsAdapter = NewsAdapter(mutableListOf())
        binding.newsRecyclerView .layoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.adapter = newsAdapter

        // Observe LiveData from ViewModel
        newsViewModel.getNewsLiveData().observe(this) { newsList ->
            newsAdapter.updateNews(newsList)
        }

        binding.backButton.setOnClickListener{
            finish()
        }

        // Fetch all news when the activity is created
        newsViewModel.fetchAllNews()
    }
}