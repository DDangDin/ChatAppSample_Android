package com.myschoolproject.androidchatapp.presentation.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import com.myschoolproject.androidchatapp.ui.theme.blackColor

@Composable
fun ChatScreenCardView(
    modifier: Modifier = Modifier,
    isMyChat: Boolean,
    chat: Chat,
) {

    val context = LocalContext.current
    val textColor = if (isMyChat) blackColor else Color.White
    val backgroundColor = if (isMyChat) Color(0xFFF6F6F6) else Color(0xFF4992ED)
    val horizontalArrangement = if (isMyChat) Arrangement.End else Arrangement.Start

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(23.dp))
                    .background(backgroundColor)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp),
                    text = chat.message,
                    fontWeight = FontWeight.Light,
                    fontSize = 17.sp,
                    color = textColor,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}


@Preview
@Composable
fun FriendChatCardViewPreview() {
    ChatScreenCardView(
        isMyChat = false,
        chat = Chat(
            "홍길동",
            "오늘 기분도 안 좋은데 한잔?오늘 기분도 안 좋은데 한잔?오늘 기분도 안 좋은데 한잔?오늘 기분도 안 좋은데 한잔?오늘 기분도 안 좋은데 한잔?오늘 기분도 안 좋은데 한잔?오늘 기분도 안 좋은데 한잔?"
        )
    )
}

@Preview
@Composable
fun MyChatCardViewPreview() {
    ChatScreenCardView(
        isMyChat = true,
        chat = Chat("홍길동", "혼자 마시지 좀 그랬는데 좋지!")
    )
}