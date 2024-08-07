package com.daily.digest.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.daily.digest.R
import com.daily.digest.ui.small_composables.ListItemSlideAnimation
import com.daily.digest.ui.small_composables.NewsItem
import com.daily.digest.view_models.MainScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun ApiNewsList(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel
){
    val context = LocalContext.current
    val newsFromApi by viewModel.newsList.collectAsState()
    val savedNews by viewModel.savedNewsList.collectAsState()
    var visible by remember { mutableStateOf(false) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        if(newsFromApi.isNotEmpty()){
            itemsIndexed(newsFromApi){ index, news ->
                val isSaved = savedNews.any { it.title == news.title }
                ListItemSlideAnimation(
                    visible = visible,
                    index = index
                ) {
                    NewsItem(
                        news = news,
                        isSaved = isSaved,
                        onBookmarkClick = {
                            if(isSaved){
                                viewModel.removeNewsFromSaved(savedNews.find { it.title == news.title })
                            } else {
                                viewModel.saveNews(news)
                            }
                        }
                    ){ url ->
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(context, browserIntent, null)
                    }
                }

            }
        } else {
            item {
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 32.dp)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(400)
        visible = true
    }
}