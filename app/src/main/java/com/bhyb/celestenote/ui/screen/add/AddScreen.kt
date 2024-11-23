package com.bhyb.celestenote.ui.screen.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhyb.celestenote.R
import com.bhyb.celestenote.widget.PassParametersToast

data class GridItem(val icon: Int, val title: String, val onItemClick: () -> Unit)

@Composable
fun AddScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        // Toast 用来占位
        val context = LocalContext.current
        val toastForClickBanner = {
            PassParametersToast.show(context, R.string.no_more)
        }
        val toastForClickGridItem = {
            PassParametersToast.show(context, R.string.functional_development)
        }

        // Banner
        Carousel()

        Spacer(modifier = modifier.height(16.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    toastForClickBanner()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "更多活动挑战",
                fontSize = 16.sp,
                color = colorResource(id = R.color.bottom_navbar_color)
            )
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "更多",
                tint = colorResource(id = R.color.bottom_navbar_color),
                modifier = modifier
                    .size(18.dp)
            )
        }

        GridSection(
            title = "工具", items = listOf(
                GridItem(R.drawable.ic_tool_lockbox, "密码箱") { toastForClickGridItem() },
                GridItem(R.drawable.ic_tool_todo, "待办") { toastForClickGridItem() },
                GridItem(R.drawable.ic_tool_accounting, "记账") { toastForClickGridItem() }
            )
        )
        GridSection(
            title = "新建", items = listOf(
                GridItem(R.drawable.ic_new_note, "新建笔记") { toastForClickGridItem() },
                GridItem(R.drawable.ic_new_painting, "新建绘画") { toastForClickGridItem() },
                GridItem(R.drawable.ic_new_novel, "新建小说") { toastForClickGridItem() },
                GridItem(R.drawable.ic_new_mindmap, "思维导图") { toastForClickGridItem() }
            )
        )
    }
}

@Composable
fun GridSection(
    title: String,
    items: List<GridItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.9f))
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = modifier.padding(top = 14.dp, start = 12.dp, bottom = 10.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = modifier.padding(bottom = 10.dp)
        ) {
            items(items) { item ->
                GridItemView(item = item)
            }
        }
    }
}

@Composable
fun GridItemView(
    item: GridItem,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                item.onItemClick()
            }
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            modifier = modifier
                .size(40.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = item.title,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = modifier
                .width(60.dp)
                .padding(top = 4.dp)
        )
    }
}