package com.daily.digest.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.daily.digest.DailyDigestApp
import com.daily.digest.di.AppComponent
import com.daily.digest.model.News
import com.daily.digest.retrofit.NewsService
import com.daily.digest.ui.small_composables.CustomTopAppBar
import com.daily.digest.ui.small_composables.NewsItem
import com.daily.digest.ui.small_composables.SearchField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject


val mockNews = News("News Title", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "news source", "", publishedAt = "2024-08-05T03:58:00Z")

@Composable
fun MainScreen(){
    val context = LocalContext.current
    val viewModel: MainScreenViewModel = viewModel(
        factory = MainScreenViewModel.provideFactory(
            (LocalContext.current.applicationContext as DailyDigestApp).component,
            owner = LocalSavedStateRegistryOwner.current
        )
    )

    val newsList by viewModel.newsList.collectAsState()
    val search by viewModel.search.collectAsState("")

    Scaffold(
        topBar = {
            CustomTopAppBar()
        },
        containerColor = Color.Gray.copy(.2f),
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp)
        ) {
            SearchField(value = search) {
                viewModel.updateSearch(it)
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
}

@Composable
@Preview
private fun MainScreenPreview() = MainScreen()

class MainScreenViewModel(
    component: AppComponent,
) : ViewModel(){

    @Inject
    lateinit var newsService: NewsService

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    private var _newsListSource = emptyList<News>()
    private val _newsList = MutableStateFlow(emptyList<News>())
    val newsList = _newsList.asStateFlow()

    init {
        component.inject(this)
        viewModelScope.launch { loadNews() }
    }

    fun updateSearch(value: String){
        _search.value = value
        if (value.trim().isEmpty()){
            _newsList.value = _newsListSource
        } else {
            _newsList.value = _newsListSource.filter { news ->
                (news.title ?: "").lowercase().contains(value.lowercase()) ||
                    (news.author ?: "").lowercase().contains(value.lowercase())
            }
        }

    }

    private suspend fun loadNews() = withContext(Dispatchers.Default){
        newsService.getNews()
            .onFailure { e ->

            }
            .onSuccess { data ->
                if(data != null){
                    _newsListSource = data.articles
                    _newsList.tryEmit(data.articles)
                }
            }
    }

    companion object {
        fun provideFactory(
            component: AppComponent,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
            ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return MainScreenViewModel(component) as T
                }
            }
    }


}