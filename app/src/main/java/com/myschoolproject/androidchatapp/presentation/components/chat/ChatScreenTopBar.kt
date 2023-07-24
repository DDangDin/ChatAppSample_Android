package com.myschoolproject.androidchatapp.presentation.components.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.ui.theme.blackColor

@Composable
fun ChatScreenTopBar(
    modifier: Modifier = Modifier,
    friendName: String,
    onEvent: (ChatUiEvent) -> Unit
) {

    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = friendName,
                color = blackColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier.size(33.dp),
                imageVector = Icons.Outlined.ExitToApp,
                contentDescription = "exit",
                tint = blackColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatTopBarPreview() {
    ChatScreenTopBar(
        friendName = "홍길동",
        onEvent = {}
    )
}