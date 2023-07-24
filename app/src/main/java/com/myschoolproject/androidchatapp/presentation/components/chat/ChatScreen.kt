package com.myschoolproject.androidchatapp.presentation.components.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.core.common.Constants
import com.myschoolproject.androidchatapp.core.utils.CustomSharedPreference
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import com.myschoolproject.androidchatapp.presentation.state.ChatState
import com.myschoolproject.androidchatapp.ui.theme.SpacerCustomColor
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatState: ChatState,
    friendName: String,
    onEvent: (ChatUiEvent) -> Unit
) {

    val context = LocalContext.current
    val myName = CustomSharedPreference(context).getUserPrefs(Constants.PREFERENCE_USERNAME)

    val scrollState = rememberLazyListState(
        if (CustomSharedPreference(context).getUserPrefs("chat_scroll_value").isEmpty()) {
            0
        } else {
            CustomSharedPreference(context).getUserPrefs("chat_scroll_value").toInt()
        }
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = chatState.chatList) {
        Log.d("ChattingLog_Scroll", "ChattingLog_Scroll")
        if (chatState.chatList.size - 1 >= 0) {
            scrollState.animateScrollToItem(chatState.chatList.size - 1)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(9f)) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ChatScreenTopBar(friendName = friendName, onEvent = {})
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(SpacerCustomColor)
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(top = 80.dp, start = 10.dp, end = 10.dp),
            ) {
                if (chatState.chatList.isNotEmpty() && !chatState.loading) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            17.dp, alignment = Alignment.CenterVertically
                        ),
                        state = scrollState
                    ) {
                        items(chatState.chatList) { chat ->
                            val isMyChat = myName == chat.username
                            ChatScreenCardView(
                                modifier = Modifier.fillMaxWidth(),
                                isMyChat = isMyChat,
                                chat = chat,
                            )
                        }
                    }
                } else {
                    Text(
                        text = if (!chatState.loading) {
                            "상대방이 나가거나\n아직 대화를 시작 하지 않은 방 입니다"
                        } else {
                            ""
                        },
                        fontWeight = FontWeight.Light,
                        fontSize = 17.sp,
                        color = Color.Red.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        ChatScreenBottomBar(
            modifier = modifier
                .weight(0.8f)
                .fillMaxWidth(),
            inputMessage = "",
            inputMessageChanged = { },
            onSend = {
//                onSend() // onEvent
                coroutineScope.launch {
                    if (chatState.chatList.size - 1 >= 0) {
                        scrollState.animateScrollToItem(chatState.chatList.size - 1)
                        CustomSharedPreference(context).setUserPrefs(
                            "chat_scroll_value", (chatState.chatList.size - 1).toString()
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {

    val chatState = ChatState(
        chatList = listOf(
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
            Chat("", "ㅇㅁㅇㅁㄴㅇㅁㄴㅇㅁㅇㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ"),
        )
    )

    ChatScreen(chatState = chatState, friendName = "홍길동2", onEvent = {})
}