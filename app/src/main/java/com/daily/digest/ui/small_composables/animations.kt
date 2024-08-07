package com.daily.digest.ui.small_composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun ListItemSlideAnimation(
    modifier: Modifier = Modifier,
    visible: Boolean,
    index: Int,
    child: @Composable() () -> Unit
){
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally (
           initialOffsetX = { fullWidth ->  if(index % 2 == 0) - fullWidth else fullWidth * 2 }
        ) + fadeIn(),
        modifier = modifier
    ) {
        child()
    }

}