package com.bhyb.celestenote.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GridItem(val icon:Int, val title: String, val color: Color? = null, val enabled: Boolean? = null, val onItemClick: () -> Unit)

@Composable
fun GridSection(
    title: String? = null,
    imageSize: Dp,
    items: List<GridItem>,
    shapeClip: Dp? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxWidth()
    ) {
        title?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 10.dp)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4)
        ) {
            items(items) { item ->
                GridItemView(item = item, imageSize = imageSize, shapeClip = shapeClip)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun GridItemView(
    imageSize: Dp,
    item: GridItem,
    shapeClip: Dp? = null
) {
    val imageResource = painterResource(id = item.icon)

    val contentColor = if (item.enabled == false) {
        LocalContentColor.current.copy(alpha = 0.38f)
    } else {
        item.color
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable(
                enabled = item.enabled?: true
            ) {
                item.onItemClick()
            }
    ) {
        Image(
            painter = imageResource,
            contentDescription = item.title,
            modifier = Modifier
                .size(imageSize)
                .clip(shape = RoundedCornerShape(shapeClip ?: 10.dp)),
            contentScale = ContentScale.Crop,
            colorFilter = contentColor?.let { ColorFilter.tint(it) }
        )
        Text(
            text = item.title,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier
                .width(60.dp)
                .padding(top = 4.dp),
            color = contentColor?: LocalContentColor.current
        )
    }
}