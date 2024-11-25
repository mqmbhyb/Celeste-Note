package com.bhyb.celestenote.ui.screen.my

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bhyb.celestenote.R

data class MiddleRowItem(val icon:Int, val title: String, val onItemClick: () -> Unit)
data class BottomColumnItem(val icon:Int, val title: String, val onItemClick: () -> Unit)

@Composable
fun MyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = modifier.align(Alignment.End),
            onClick = { }
        ) {
            Icon(Icons.Filled.Settings, contentDescription = "设置")
        }

        Spacer(modifier = modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_bottom_my_focused),
            contentDescription = "用户头像",
            modifier = modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = modifier.height(8.dp))

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(Color.White.copy(0.9f)),
            border = BorderStroke(3.dp, Color.White),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Text("去登录", color = Color.Black)
        }

        Spacer(modifier = modifier.height(16.dp))

        MiddleRow(
            items = listOf(
                MiddleRowItem(R.drawable.ic_my_sentence_excerpts, "句摘") {} ,
                MiddleRowItem(R.drawable.ic_my_recycle_bin, "回收站") {},
                MiddleRowItem(R.drawable.ic_my_cloud_sync, "云同步") {},
                MiddleRowItem(R.drawable.ic_my_grass, "草稿") {}
            )
        )

        Spacer(modifier = modifier.height(16.dp))

        BottomColumn(
            items = listOf(
                BottomColumnItem(R.drawable.ic_my_notify, "通知") {},
                BottomColumnItem(R.drawable.ic_my_help_and_feedback, "帮助与反馈") {},
                BottomColumnItem(R.drawable.ic_my_about, "关于") {}
            )
        )
    }
}

@Composable
fun MiddleRow(
    items: List<MiddleRowItem>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Gray.copy(0.1f))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(items) {item ->
            MiddleRowItemView(item = item, modifier)
        }
    }
}

@Composable
fun MiddleRowItemView(
    item: MiddleRowItem,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .width(70.dp)
            .padding(vertical = 12.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White.copy(0.7f))
            .clickable { item.onItemClick },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = modifier
                .size(40.dp)
                .padding(top = 4.dp)
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(item.title, modifier = modifier.padding(bottom = 4.dp))
    }
}

@Composable
fun BottomColumn(
    items: List<BottomColumnItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Gray.copy(0.1f))
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(items) {item ->
            BottomColumnItemView(item = item, modifier)
        }
    }
}

@Composable
fun BottomColumnItemView(
    item: BottomColumnItem,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .padding(12.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White.copy(0.7f))
            .fillMaxWidth()
            .clickable { item.onItemClick },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = modifier
                    .size(40.dp)
                    .padding(6.dp)
            )
            Spacer(modifier = modifier.width(12.dp))
            Text(item.title)
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = modifier.padding(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    MyScreen()
}