package com.bhyb.celestenote.ui.screen.my.sheetcontent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bhyb.celestenote.R
import com.bhyb.celestenote.ui.component.ShowBottomSheet

@Composable
fun AboutSheetContent(
    modifier: Modifier = Modifier
) {
    val openCopyrightSheet = remember { mutableStateOf(false) }
    val openPrivacyPolicySheet = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "关于",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Image(painter = rememberAsyncImagePainter(model = R.mipmap.ic_launcher), contentDescription = null)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Celeste Note",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "版本：v0.1",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(
                onClick = { openCopyrightSheet.value = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("版权声明")
            }
            Spacer(modifier = Modifier.width(6.dp))
            Button(
                onClick = { openPrivacyPolicySheet.value = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("隐私政策")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (openCopyrightSheet.value) {
            ShowBottomSheet(
                sheetContent = {
                    GeneralSheetContent(
                        title = "版权声明",
                        content = """
                            一、软件版权
                            Celeste Note软件（以下简称“本软件”）是由Celeste Note团队（以下简称“我们”）开发的一款笔记应用。本软件及其所有衍生产品、相关文档、图标等版权归我们所有。任何个人或组织未经我们书面授权，不得擅自复制、传播、修改、篡改或用于商业用途。
                            
                            二、知识产权
                            1. 本软件中所涉及的文字、图片、音频、视频等素材，其知识产权归原作者所有。用户在使用本软件过程中，不得侵犯原作者的知识产权。
                            2. 用户在使用本软件时，产生的笔记内容、图片等个人信息，其知识产权归用户所有。我们承诺不侵犯用户的知识产权。
                            
                            三、侵权处理
                            如发现有侵犯本软件版权或其他知识产权的行为，请及时与我们联系，我们将依法追究侵权者的法律责任。
                            
                            四、免责声明
                            1. 本软件提供的所有服务仅供参考，不承担任何法律责任。
                            2. 因使用本软件导致的任何损失，我们概不负责。
                            3. 我们保留随时修改、更新本版权声明的权利。如有变更，请关注最新版本。
                        """.trimIndent(),
                        onDismissRequest = {
                            openCopyrightSheet.value = false
                        }
                    )
                },
                onDismissRequest = {
                    openCopyrightSheet.value = false
                }
            )
        }

        if (openPrivacyPolicySheet.value) {
            ShowBottomSheet(
                sheetContent = {
                    GeneralSheetContent(
                        title = "隐私政策",
                        content = """
                            一、隐私保护原则
                            我们尊重并保护用户的隐私，承诺为您提供安全、可靠的笔记服务。以下隐私政策详细阐述了我们对用户信息的收集、使用、存储和保护措施。
                            
                            二、信息收集
                            1. 注册信息：用户在注册本软件时，需提供手机号码、邮箱等基本信息。这些信息用于账号注册、密码找回及服务通知。
                            2. 笔记内容：用户在使用本软件过程中，产生的笔记内容、图片等个人信息将存储在我们的服务器上。
                            3. 设备信息：为了提供更好的服务，我们可能会收集用户设备的相关信息，如设备型号、操作系统、IP地址等。
                            
                            三、信息使用
                            1. 我们将用户信息用于以下目的：
                            （1）提供、维护和改进本软件的服务；
                            （2）发送通知、公告、活动信息等；
                            （3）统计分析，帮助我们了解用户需求，优化产品功能；
                            （4）遵守法律法规，维护合法权益。
                            2. 我们不会将用户信息用于其他非上述目的，也不会未经用户同意向第三方披露用户信息。
                            
                            四、信息存储与保护
                            1. 我们将采取合理的技术手段，保护用户信息的安全，防止泄露、损毁、丢失。
                            2. 我们会定期对服务器进行安全检查，确保用户信息安全。
                            3. 用户信息存储期限：自用户注册之日起，至用户主动注销账号或本软件停止服务之日止。
                            
                            五、隐私政策修订
                            我们有权根据法律法规、政策调整或业务发展需要，适时修订本隐私政策。如有变更，请关注最新版本。本协议条款变更后，如果您继续使用本软件，即视为您已接受修改后的协议。如果您不接受修改后的协议，应当停止使用本软件。
                            
                            六、联系我们
                            如有疑问或建议，请通过以下方式与我们联系：
                            QQ：3170128085
                            本隐私政策最终解释权归Celeste Note团队所有。
                        """.trimIndent(),
                        onDismissRequest = {
                            openPrivacyPolicySheet.value = false
                        }
                    )
                },
                onDismissRequest = {
                    openPrivacyPolicySheet.value = false
                }
            )
        }
    }
}

@Composable
fun GeneralSheetContent(
    title: String,
    content: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = { onDismissRequest() },
                colors = IconButtonColors(colorResource(id = R.color.screen_background_color), Color.Gray.copy(0.5f), Color.Gray, Color.Gray),
                modifier = Modifier.size(20.dp)
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "收起")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}