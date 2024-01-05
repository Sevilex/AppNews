package com.example.newsapp.ui.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.ResourceState
import com.example.newsapp.ui.component.EmptyStateComponent
import com.example.newsapp.ui.component.Loader
import com.example.newsapp.ui.component.NewsRowComponent
import com.example.newsapp.ui.viewModel.NewsViewModel

const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {

    val newsRes by newsViewModel.news.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        100
    }

    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.Top),
        pageSize = PageSize.Fill,
        pageSpacing = 8.dp,
        horizontalAlignment = Alignment.Start
    ) { page: Int ->
        when (newsRes) {
            is ResourceState.Loading -> {
                Log.i(TAG, "Inside_Loading")
                Loader()
            }
            is ResourceState.Success -> {
                val response = (newsRes as ResourceState.Success).data
                Log.i(TAG, "Inside_Success ${response.status} = ${response.totalResults}")
                if (response.articles.isNotEmpty()) {
                    NewsRowComponent(page, response.articles[page])
                } else {
                    EmptyStateComponent()
                }
            }
            is ResourceState.Error -> {
                val error = (newsRes as ResourceState.Error)
                Log.i(TAG, "Inside_Error $error")
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}