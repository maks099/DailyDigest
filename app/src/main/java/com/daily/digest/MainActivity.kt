package com.daily.digest

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daily.digest.di.AppComponent
import com.daily.digest.di.DaggerAppComponent
import com.daily.digest.retrofit.NewsService
import com.daily.digest.ui.screens.MainScreen
import com.daily.digest.ui.screens.Routes
import com.daily.digest.ui.theme.DailyDigestTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var newsService: NewsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            DailyDigestTheme {
               NavHost(navController = navController, startDestination = Routes.Main.route){
                   composable(Routes.Main.route){
                       this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                       MainScreen()
                   }
               }
            }
        }
    }
}

fun Context.toast(message: String) {
    if(message.isNotEmpty()){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
