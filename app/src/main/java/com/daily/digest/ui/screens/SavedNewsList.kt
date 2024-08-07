package com.daily.digest.ui.screens

import android.content.Intent
import android.icu.text.UnicodeSetIterator
import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.daily.digest.ui.small_composables.ListItemSlideAnimation
import com.daily.digest.ui.small_composables.NewsItem
import com.daily.digest.view_models.MainScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SavedNewsList(
    viewModel: MainScreenViewModel
){
    val context = LocalContext.current
    val savedNews by viewModel.savedNewsList.collectAsState()
    var visible by remember { mutableStateOf(false) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(savedNews){ index, news ->
           ListItemSlideAnimation(visible = visible, index = index) {
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

    LaunchedEffect(Unit) {
        delay(400)
        visible = true
    }
}