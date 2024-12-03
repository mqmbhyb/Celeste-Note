package com.bhyb.celestenote.ui.screen.my.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationSheetContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "通知",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "您有一条最新通知🔔：",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            IndentedParagraphText("欢迎使用本软件，这是我学习Android Jetpack Compose、Gradle和Kotlin写的毕业设计！")

            Spacer(modifier = Modifier.height(100.dp))
        }

        Text(
            text = "注：切换到其他页面会刷新图标上的数字角标",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun IndentedParagraphText(text: String, indentSize: Int = 24) {
    val annotatedString = AnnotatedString(
        text = text,
        paragraphStyle = ParagraphStyle(textIndent = TextIndent(firstLine = indentSize.sp))
    )
    BasicText(
        text = annotatedString,
        style = LocalTextStyle.current,
        modifier = Modifier.padding(12.dp)
    )
}
