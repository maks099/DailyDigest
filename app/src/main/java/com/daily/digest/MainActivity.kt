package com.daily.digest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.daily.digest.di.AppComponent
import com.daily.digest.di.DaggerAppComponent
import com.daily.digest.retrofit.NewsService
import com.daily.digest.ui.theme.DailyDigestTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DATA_LOG = "DATA_LOG"

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var newsService: NewsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            DailyDigestTheme {
                Text(text = "test")
            }
        }
        getNews()
    }

    private fun getNews() {
        (applicationContext as DailyDigestApp).component.inject(this)
        lifecycleScope.launch(Dispatchers.Default) {

        }
    }
}

