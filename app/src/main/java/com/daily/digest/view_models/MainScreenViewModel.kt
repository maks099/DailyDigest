package com.daily.digest.view_models

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.daily.digest.di.AppComponent
import com.daily.digest.model.News
import com.daily.digest.retrofit.NewsService
import com.daily.digest.room.NewsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainScreenViewModel(
    component: AppComponent,
) : ViewModel(){

    @Inject
    lateinit var newsService: NewsService

    @Inject
    lateinit var newsDao: NewsDao

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    private var _newsListSource = emptyList<News>()
    private val _newsList = MutableStateFlow(emptyList<News>())
    val newsList = _newsList.asStateFlow()

    private var _savedNewsListSource = emptyList<News>()
    private val _savedNewsList = MutableStateFlow(emptyList<News>())
    val savedNewsList = _savedNewsList.asStateFlow()

    init {
        component.inject(this)
        viewModelScope.launch { getNewsFromAPI() }
        viewModelScope.launch { loadSavedNews() }
    }

    private suspend fun loadSavedNews() = withContext(Dispatchers.Default){
        newsDao.getAll().collect { data ->
            _savedNewsListSource = data
            _savedNewsList.tryEmit(_savedNewsListSource)
        }
    }

    fun updateSearch(value: String){
        _search.value = value
        if (value.trim().isEmpty()){
            _newsList.value = _newsListSource
            _savedNewsList.value = _savedNewsListSource
        } else {
            _newsList.value = filterList(_newsListSource)
            _savedNewsList.value = filterList(_savedNewsListSource)
        }
    }

    private fun filterList(newsList: List<News>): List<News> {
        return newsList.filter { news ->
            (news.title ?: "").lowercase().contains(_search.value.lowercase()) ||
                    (news.author ?: "").lowercase().contains(_search.value.lowercase())
        }
    }

    private suspend fun getNewsFromAPI() = withContext(Dispatchers.Default){
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

    fun saveNews(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            newsDao.insertAll(news)
        }
    }

    fun removeNewsFromSaved(news: News?) {
        news?.let {
            viewModelScope.launch(Dispatchers.IO) {
                newsDao.delete(news)
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