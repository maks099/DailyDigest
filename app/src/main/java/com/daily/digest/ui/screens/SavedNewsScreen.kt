package com.daily.digest.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.daily.digest.model.News
import com.daily.digest.ui.small_composables.NewsItem
import com.daily.digest.view_models.MainScreenViewModel

@Composable
fun SavedNewsScreen(
    viewModel: MainScreenViewModel
){
    val context = LocalContext.current
    val savedNews by viewModel.savedNewsList.collectAsState()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(savedNews){ news ->
            NewsItem(
                news = news,
                isSaved = true,
                onBookmarkClick = {
                    viewModel.removeNewsFromSaved(savedNews.find { it.title == news.title })
                }
            ){ url ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(context, browserIntent, null)
            }
        }
    }
}