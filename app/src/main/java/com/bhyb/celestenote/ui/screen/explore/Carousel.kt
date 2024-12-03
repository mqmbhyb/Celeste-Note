package com.bhyb.celestenote.ui.screen.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bhyb.celestenote.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Carousel(
    modifier: Modifier = Modifier
) {
    val images = listOf(
        R.drawable.carousel1,
        R.drawable.carousel2,
        R.drawable.carousel3,
        R.drawable.carousel4
    )
    val pageCount = images.size
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(200.dp)
        ) { page ->
            Box {
                Image(
                    painter = rememberAsyncImagePainter(images[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                // 指示器
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    repeat(images.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(4.dp)
                                .clip(CircleShape) //顺序在size后面
                                .background(
                                    if (index == pagerState.currentPage) colorResource(id = R.color.dot_selected_color) else Color.White.copy(
                                        alpha = 0.5f
                                    )
                                )
                        )
                    }
                }
            }
        }

        // 自动滚动逻辑
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(3000L)
                if (!pagerState.isScrollInProgress) {
                    scope.launch {
                        pagerState.animateScrollToPage((pagerState.currentPage + 1) % pageCount)
                    }
                }
            }
        }
    }
}