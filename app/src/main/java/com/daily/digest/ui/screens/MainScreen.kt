package com.daily.digest.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.daily.digest.R
import com.daily.digest.di.AppComponent
import com.daily.digest.model.News
import com.daily.digest.retrofit.NewsService
import com.daily.digest.room.NewsDao
import com.daily.digest.ui.small_composables.CustomTopAppBar
import com.daily.digest.ui.small_composables.NewsItem
import com.daily.digest.ui.small_composables.SearchField
import com.daily.digest.view_models.MainScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@Composable
fun MainScreen(){
    val context = LocalContext.current
    val viewModel: MainScreenViewModel = viewModel(
        factory = MainScreenViewModel.provideFactory(
            (LocalContext.current.applicationContext as DailyDigestApp).component,
            owner = LocalSavedStateRegistryOwner.current
        )
    )

    val search by viewModel.search.collectAsState("")

    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        TabTitle(R.string.all_news, R.drawable.baseline_dynamic_feed_24),
        TabTitle(R.string.saved, R.drawable.baseline_save_24),
    )

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
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, tabTitle ->
                    Tab(
                        text = { Text(stringResource(id = tabTitle.textRes)) },
                        icon = {
                            Icon(painter = painterResource(id = tabTitle.iconRes), contentDescription = null)
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        modifier = Modifier
                            .background(Color.Gray.copy(.2f)),
                    )
                }
            }
            when (tabIndex) {
                0 -> ApiNewsScreen(viewModel)
                1 -> SavedNewsScreen(viewModel)
            }
        }
    }
}

@Composable
@Preview
private fun MainScreenPreview() = MainScreen()

private data class TabTitle(
    @StringRes val textRes: Int,
    @DrawableRes val iconRes: Int
)