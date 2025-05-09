package com.bhyb.celestenote.ui.screen.my

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bhyb.celestenote.R
import com.bhyb.celestenote.di.BASE_URL
import com.bhyb.celestenote.ui.component.ShowBottomSheet
import com.bhyb.celestenote.ui.screen.my.sheetcontent.AboutSheetContent
import com.bhyb.celestenote.ui.screen.my.sheetcontent.HelpAndFeedbackSheetContent
import com.bhyb.celestenote.ui.screen.my.sheetcontent.NotificationSheetContent

data class MiddleRowItem(val icon: Int, val title: String, val onItemClick: () -> Unit)
data class BottomColumnItem(
    val icon: Int,
    val title: String,
    val sheetContent: @Composable () -> Unit,
) {
    var count by mutableIntStateOf(0)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MyScreen(
    onLoginClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    var sheetContent: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    var isClicked by remember { mutableStateOf(false) }

    if (showModalBottomSheet && sheetContent != null) {
        ShowBottomSheet(
            sheetContent = sheetContent!!,
            onDismissRequest = {
                showModalBottomSheet = false
                sheetContent = null
            }
        )
    }

    val userManager = LocalUserManager.current
    val currentUser = userManager.currentUser

    val bottomColumnItems = remember {
        listOf(
            BottomColumnItem(
                icon = R.drawable.ic_my_notify,
                title = "通知"
            ) { NotificationSheetContent() }.apply { count = 1 },
            BottomColumnItem(
                icon = R.drawable.ic_my_help_and_feedback,
                title = "帮助与反馈"
            ) { HelpAndFeedbackSheetContent() },
            BottomColumnItem(
                icon = R.drawable.ic_my_about,
                title = "关于"
            ) { AboutSheetContent() }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                userManager.logout()
            },
            enabled = currentUser != null
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right_from_bracket_solid),
                contentDescription = "退出登录",
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(45.dp))

        AsyncImage(
            model = (BASE_URL + currentUser?.icon),
            contentDescription = "用户头像",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(0.5f))
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (currentUser != null) {
            Text(currentUser.name)
        } else {
            Button(
                onClick = {
                    isClicked = !isClicked
                    onLoginClicked()
                },
                colors = ButtonDefaults.buttonColors(Color.White.copy(0.8f)),
                border = BorderStroke(3.dp, Color.White),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 10.dp
                )
            ) {
                Text("点击登录", color = if (isClicked) Color.Gray else Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MiddleRow(
            items = listOf(
                MiddleRowItem(R.drawable.ic_my_collect, "收藏") {},
                MiddleRowItem(R.drawable.ic_my_recycle_bin, "回收站") {},
                MiddleRowItem(R.drawable.ic_my_cloud_management, "云管理") {},
                MiddleRowItem(R.drawable.ic_my_browse_history, "浏览记录") {}
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        BottomColumn(
            items = bottomColumnItems,
            onItemClicked = { content, resetItemCount ->
                sheetContent = content
                resetItemCount()
                showModalBottomSheet = true
            }
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
        items(items) { item ->
            MiddleRowItemView(item = item)
        }
    }
}

@Composable
fun MiddleRowItemView(
    item: MiddleRowItem
) {
    Column(
        modifier = Modifier
            .width(70.dp)
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White.copy(0.7f))
            .clickable { item.onItemClick },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            item.title,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Composable
fun BottomColumn(
    items: List<BottomColumnItem>,
    onItemClicked: (content: @Composable () -> Unit, resetItemCount: () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Gray.copy(0.1f))
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 6.dp)
    ) {
        items(items) { item ->
            BottomColumnItemView(item = item, onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun BottomColumnItemView(
    item: BottomColumnItem,
    onItemClicked: (content: @Composable () -> Unit, resetItemCount: () -> Unit) -> Unit
) {
    val itemCount = item.count

    Row(
        modifier = Modifier
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White.copy(0.7f))
            .fillMaxWidth()
            .clickable {
                onItemClicked(item.sheetContent) {
                    if (item.count != 0) item.count = 0
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.padding(6.dp)) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterStart)
                )
                if (itemCount > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text("$itemCount")
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))
            Text(item.title, fontSize = 13.sp)
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.padding(10.dp)
        )
    }
}