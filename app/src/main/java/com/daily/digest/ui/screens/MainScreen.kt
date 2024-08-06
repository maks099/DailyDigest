package com.daily.digest.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daily.digest.model.News
import com.daily.digest.retrofit.NewsService
import com.daily.digest.ui.small_composables.NewsItem
import com.daily.digest.ui.small_composables.SearchField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


val mockNews = News("News Title", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "news source", "", publishedAt = "2024-08-05T03:58:00Z")

@Composable
fun MainScreen(){
    val context = LocalContext.current
    val newsList = listOf(mockNews, mockNews.copy(urlToImage = "https://donpatriot.news/wp-content/uploads/2024/08/korostylov.jpg"))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Gray.copy(.2f)
            )
            .padding(8.dp)
    ) {
        SearchField(value = "") {
            
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(newsList){ news ->
                NewsItem(news = news){ url ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(context, browserIntent, null)
                }
            }
        }
    }
}

@Composable
@Preview
private fun MainScreenPreview() = MainScreen()

class MainScreenViewModel : ViewModel(){
    @Inject
    lateinit var newsService: NewsService

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    init {
        viewModelScope.launch { loadNews() }
    }

    fun updateSearch(value: String){
        _search.value = value
    }

    private suspend fun loadNews() = withContext(Dispatchers.Default){
        newsService.getNews()
            .onFailure { e ->

            }
            .onSuccess { data ->
                if(data != null){

                }
            }
    }

}