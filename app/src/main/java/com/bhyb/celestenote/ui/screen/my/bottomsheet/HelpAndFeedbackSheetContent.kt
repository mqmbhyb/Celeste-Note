package com.bhyb.celestenote.ui.screen.my.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bhyb.celestenote.R

@Composable
fun HelpAndFeedbackSheetContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "帮助与反馈",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = "如果您有任何问题或建议，请联系我们。",
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {  },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("联系客服")
        }

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = "常见问题",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(listOf(
                "如何创建新的笔记？",
                "如何同步我的笔记到云端？",
                "忘记密码怎么办？"
            )) { question  ->
                FAQItem(question  = question )
                Spacer(modifier = modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun FAQItem(question: String) {
    Text(
        text = question,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.screen_background_color))
            .padding(12.dp)
    )
}