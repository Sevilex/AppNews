package com.example.newsapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ResourceState
import com.example.newsapp.data.AppConstans
import com.example.newsapp.data.entity.NewsResponse
import com.example.newsapp.ui.repository.Newsrepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsrepository: Newsrepository
) : ViewModel() {

    private val _news:MutableStateFlow<ResourceState<NewsResponse>> = MutableStateFlow(ResourceState.Loading())
    val news:StateFlow<ResourceState<NewsResponse>> = _news

    init {
        getNews(AppConstans.COUNTRY)
    }

    private fun getNews(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            newsrepository.getNewsHeadLine(country)
                .collectLatest { newsResponse ->
                    _news.value = newsResponse
                }
        }
    }
}