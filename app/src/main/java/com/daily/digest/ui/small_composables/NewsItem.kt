package com.daily.digest.ui.small_composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.daily.digest.R
import com.daily.digest.model.News

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    news: News,
    isSaved: Boolean,
    onBookmarkClick: () -> Unit,
    onArrowClick: (String) -> Unit
){
    Card(
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .padding(bottom = 16.dp)
    ) {
        news.urlToImage?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.urlToImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .background(color = Color.Blue))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = news.title ?: "",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f)
                    )
                    news.publishedAt?.let {
                        Text(
                            text = parseDate(news.publishedAt),
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        )
                    }
                }

                Text(
                    text = news.author ?: "",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                news.content?.let {
                    Text(
                        text = news.content,
                        style = TextStyle(
                            fontSize = 11.sp,
                            color = Color.Gray
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                SmallIcon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight) {
                    news.url?.let {
                        onArrowClick(news.url)
                    }
                }
                SmallIcon(
                    painter = painterResource(
                        id = if(isSaved) R.drawable.baseline_bookmark_24
                             else R.drawable.baseline_bookmark_border_24
                    )
                ) {
                    onBookmarkClick()
                }
            }

        }
    }
}

@Composable
private fun SmallIcon(
    imageVector: ImageVector,
    onClick: () -> Unit
){
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(72.dp)
        )
    }
}

@Composable
private fun SmallIcon(
    painter: Painter,
    onClick: () -> Unit,
){
    IconButton(onClick = onClick) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(48.dp)
        )
    }
}

private fun parseDate(date: String) =
    date.split("T")[1].take(5)